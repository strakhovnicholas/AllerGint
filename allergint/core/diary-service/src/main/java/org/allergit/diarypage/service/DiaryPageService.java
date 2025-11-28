package org.allergit.diarypage.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.allergit.diary.dto.*;
import org.allergit.diary.kafka.DiaryNoteMessage;
import org.allergit.diarypage.entity.DiaryPage;
import org.allergit.diarypage.mapper.DiaryPageMapper;
import org.allergit.diarypage.repository.DiaryPageRepository;
import org.allergit.exception.NotFoundException;
import org.allergit.feign.UserClient;
import org.allergit.kafka.service.DiaryNoteService;
import org.allergit.medicine.mapper.MedicineMapper;
import org.allergit.medicine.service.MedicineService;
import org.allergit.symptom.mapper.UserSymptomMapper;
import org.allergit.symptom.service.UserSymptomService;
import org.allergit.weather.mapper.WeatherMapper;
import org.allergit.weather.service.WeatherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DiaryPageService {
    private final DiaryPageRepository repository;
    private final DiaryPageMapper mapper;
    private final MedicineService medicineService;
    private final UserSymptomService symptomService;
    private final WeatherService weatherService;
    private final MedicineMapper medicineMapper;
    private final UserSymptomMapper symptomMapper;
    private final WeatherMapper weatherMapper;
    private final DiaryNoteService diaryNoteService;
    private final UserClient userClient;

    public DiaryPageDto create(DiaryPageDto dto, UUID userId) {
        log.info("Creating diary page for userId={}", userId);
        validateUserExists(userId);

        dto.setUserId(userId);
        DiaryPage diary = mapper.toEntity(dto);

        log.debug("Creating nested entities for diary page");
        if (dto.getMedicines() != null) {
            dto.getMedicines().forEach(m -> medicineMapper.toEntity(medicineService.create(m)));
        }
        if (dto.getUserSymptoms() != null) {
            dto.getUserSymptoms().forEach(s -> symptomMapper.toEntity(symptomService.create(s)));
        }
        if (dto.getWeathers() != null) {
            dto.getWeathers().forEach(w -> weatherMapper.toEntity(weatherService.create(w)));
        }

        diary = repository.save(diary);
        log.info("DiaryPage created id={}", diary.getId());

        checkAndSendToKafka(diary);
        return mapper.toDto(diary);
    }

    public DiaryPageDto getById(UUID id, UUID userId) {
        log.info("Fetching diary page id={} for userId={}", id, userId);
        validateUserExists(userId);

        DiaryPage diary = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("DiaryPage not found id={}", id);
                    return new NotFoundException("DiaryPage not found");
                });

        validateOwnership(userId, diary.getUserId());
        log.debug("Ownership validated for diaryPageId={} and userId={}", id, userId);
        String notes;
        try {
            notes = diary.getAiNotes();
            log.debug("This diary page id={} has ai notes={}", diary.getId(), diary.getAiNotes());
        } catch (Exception ex) {
            log.error("DiaryPage not found for id={}", id, ex);
        }
        return mapper.toDto(diary);
    }

    public DiaryPageDto update(UUID id, DiaryPageDto dto, UUID userId) {
        log.info("Updating diaryPage id={} by userId={}", id, userId);
        validateUserExists(userId);

        DiaryPage diary = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("DiaryPage not found"));

        validateOwnership(userId, diary.getUserId());

        diary.setHealthState(dto.getHealthState());
        diary.setTimestamp(dto.getTimestamp());
        diary.setUserNotes(dto.getUserNotes());

        if (dto.getMedicines() != null) {
            diary.getMedicines().clear();
            for (MedicineDto medicineDto : dto.getMedicines()) {
                var created = medicineService.create(medicineDto);
                var entity = medicineMapper.toEntity(created);
                diary.getMedicines().add(entity);
            }
        }

        // --- Symptoms ---
        if (dto.getUserSymptoms() != null) {
            diary.getUserSymptoms().clear();
            for (UserSymptomDto symptomDto : dto.getUserSymptoms()) {
                var created = symptomService.create(symptomDto);
                var entity = symptomMapper.toEntity(created);
                diary.getUserSymptoms().add(entity);
            }
        }

        // --- Weather ---
        if (dto.getWeathers() != null) {
            diary.getWeathers().clear();
            for (WeatherDto weatherDto : dto.getWeathers()) {
                var created = weatherService.create(weatherDto);
                var entity = weatherMapper.toEntity(created);
                diary.getWeathers().add(entity);
            }
        }

        diary = repository.save(diary);
        log.info("DiaryPage updated id={}", id);

        checkAndSendToKafka(diary);
        return mapper.toDto(diary);
    }


    public void delete(UUID id, UUID userId) {
        log.info("Deleting diaryPage id={} by userId={}", id, userId);
        validateUserExists(userId);

        DiaryPage diary = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("DiaryPage not found for delete id={}", id);
                    return new NotFoundException("DiaryPage not found");
                });

        validateOwnership(userId, diary.getUserId());

        repository.delete(diary);
        log.info("DiaryPage deleted id={}", id);
    }

    public DiaryPageDto addMedicine(UUID diaryPageId, MedicineDto dto, UUID userId) {
        log.info("Adding medicine to diaryPage id={} by userId={}", diaryPageId, userId);
        return modifyWithOwnershipCheck(diaryPageId, userId, diary -> {
            diary.getMedicines().add(
                    medicineMapper.toEntity(medicineService.create(dto))
            );
            log.debug("Medicine added to diaryPage id={}", diaryPageId);
        });
    }

    public DiaryPageDto addSymptom(UUID diaryPageId, UserSymptomDto dto, UUID userId) {
        log.info("Adding symptom to diaryPage id={} by userId={}", diaryPageId, userId);
        return modifyWithOwnershipCheck(diaryPageId, userId, diary -> {
            diary.getUserSymptoms().add(
                    symptomMapper.toEntity(symptomService.create(dto))
            );
            log.debug("Symptom added to diaryPage id={}", diaryPageId);
        });
    }

    public DiaryPageDto addWeather(UUID diaryPageId, WeatherDto dto, UUID userId) {
        log.info("Adding weather to diaryPage id={} by userId={}", diaryPageId, userId);
        return modifyWithOwnershipCheck(diaryPageId, userId, diary -> {
            diary.getWeathers().add(
                    weatherMapper.toEntity(weatherService.create(dto))
            );
            log.debug("Weather added to diaryPage id={}", diaryPageId);
        });
    }

    public DiaryPageDto getByUserAndDay(UUID userId, ZonedDateTime timestamp) {
        log.info("Fetching diary page for userId={} on day={}", userId, timestamp.toLocalDate());
        validateUserExists(userId);

        LocalDate day = timestamp.withZoneSameInstant(ZoneId.systemDefault()).toLocalDate();

        DiaryPage diary = repository.findByUserIdAndExactDay(userId, day)
                .orElseThrow(() -> {
                    log.warn("DiaryPage not found for userId={} on day={}", userId, day);
                    return new NotFoundException("DiaryPage not found for the specified day");
                });

        validateOwnership(userId, diary.getUserId());

        log.debug("DiaryPage found id={} for userId={} on day={}", diary.getId(), userId, day);
        return mapper.toDto(diary);
    }

    public AiNotesDto getAiNotes(UUID diaryPageId, UUID userId) {
        log.info("Fetching AI notes for diaryPage id={} by userId={}", diaryPageId, userId);
        validateUserExists(userId);

        DiaryPage diary = repository.findById(diaryPageId)
                .orElseThrow(() -> {
                    log.warn("DiaryPage not found id={}", diaryPageId);
                    return new NotFoundException("DiaryPage not found");
                });

        validateOwnership(userId, diary.getUserId());
        log.debug("Ownership validated for diaryPageId={} and userId={}", diaryPageId, userId);

        String notes = diary.getAiNotes();
        log.debug("AI notes fetched for diaryPageId={} length={}",
                diaryPageId,
                notes != null ? notes.length() : 0
        );

        return new AiNotesDto(diaryPageId, notes);
    }


    private void validateUserExists(UUID userId) {
        log.debug("Validating user existence userId={}", userId);
        try {
            userClient.getById(userId);
        } catch (Exception e) {
            log.warn("User not found userId={}", userId);
            throw new NotFoundException("User not found");
        }
    }

    private void validateOwnership(UUID userId, UUID ownerId) {
        if (!userId.equals(ownerId)) {
            log.warn("User {} attempted to modify diary owned by {}", userId, ownerId);
            throw new NotFoundException("Forbidden: User does not own this diary page");
        }
        log.debug("User {} ownership validated", userId);
    }

    private DiaryPageDto modifyWithOwnershipCheck(
            UUID id,
            UUID userId,
            java.util.function.Consumer<DiaryPage> action
    ) {
        log.debug("Modifying diaryPage id={} by userId={}", id, userId);

        validateUserExists(userId);

        DiaryPage diary = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("DiaryPage not found id={}", id);
                    return new NotFoundException("DiaryPage not found");
                });

        validateOwnership(userId, diary.getUserId());

        action.accept(diary);
        diary = repository.save(diary);

        log.info("DiaryPage updated id={} after modification", id);

        checkAndSendToKafka(diary);
        return mapper.toDto(diary);
    }

    private void checkAndSendToKafka(DiaryPage diary) {
        log.debug("Checking if diaryPage id={} should be sent to Kafka", diary.getId());

        if (diary.getHealthState() != null &&
                !diary.getUserSymptoms().isEmpty()) {

            log.info("Sending DiaryPage id={} to Kafka", diary.getId());

            DiaryNoteMessage.DiaryNoteMessageBuilder builder = DiaryNoteMessage.builder()
                    .userId(diary.getUserId())
                    .diaryPageId(diary.getId())
                    .userNotes(diary.getUserNotes())
                    .healthState(diary.getHealthState())
                    .userSymptoms(diary.getUserSymptoms()
                            .stream().map(symptomMapper::toDto).collect(Collectors.toSet()))
                    .medicines(diary.getMedicines()
                            .stream().map(medicineMapper::toDto).collect(Collectors.toSet()));

            if (diary.getWeathers() != null && !diary.getWeathers().isEmpty()) {
                builder.weathers(diary.getWeathers().stream().map(weatherMapper::toDto).collect(Collectors.toSet()));
            }

            DiaryNoteMessage message = builder.build();

            diaryNoteService.sendNoteMessage(message);
        }
    }
}
