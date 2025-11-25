package org.allergit.diarypage.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.allergit.diary.dto.DiaryPageDto;
import org.allergit.diary.dto.MedicineDto;
import org.allergit.diary.dto.UserSymptomDto;
import org.allergit.diary.dto.WeatherDto;
import org.allergit.diarypage.entity.DiaryPage;
import org.allergit.diarypage.mapper.DiaryPageMapper;
import org.allergit.diarypage.repository.DiaryPageRepository;
import org.allergit.exception.NotFoundException;
import org.allergit.kafka.payload.DiaryNoteMessage;
import org.allergit.kafka.service.DiaryNoteService;
import org.allergit.medicine.mapper.MedicineMapper;
import org.allergit.medicine.service.MedicineService;
import org.allergit.symptom.mapper.UserSymptomMapper;
import org.allergit.symptom.service.UserSymptomService;
import org.allergit.weather.mapper.WeatherMapper;
import org.allergit.weather.service.WeatherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
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
    private final WeatherService weatherService;
    private final UserSymptomService symptomService;
    private final UserSymptomMapper symptomMapper;
    private final MedicineMapper medicineMapper;
    private final WeatherMapper weatherMapper;
    private final DiaryNoteService diaryNoteService;

    public DiaryPageDto create(DiaryPageDto dto) {
        DiaryPage saved = repository.save(mapper.toEntity(dto));
        checkAndSendToKafka(saved);
        return mapper.toDto(saved);
    }

    public Optional<DiaryPageDto> getById(UUID id) {
        return repository.findById(id).map(mapper::toDto);
    }

    public Optional<DiaryPageDto> getByFullId(UUID id) {
        return repository.findFullById(id).map(mapper::toDto);
    }

    public List<DiaryPageDto> getAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    public DiaryPageDto update(UUID id, DiaryPageDto dto) {
        DiaryPage diary = findDiaryOrThrow(id);
        diary.setUserId(dto.getUserId());
        diary.setHealthState(dto.getHealthState());
        diary.setTimestamp(dto.getTimestamp());
        diary.setUserNotes(dto.getUserNotes());
        DiaryPage updated = repository.save(diary);
        checkAndSendToKafka(updated);
        return mapper.toDto(updated);
    }

    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("DiaryPage not found");
        }
        repository.deleteById(id);
    }

    public DiaryPageDto getByUserIdAndZonedDateTime(UUID userId, ZonedDateTime zonedDateTime) {
        return repository.findByUserIdAndExactDay(userId, zonedDateTime)
                .map(mapper::toDto)
                .orElseThrow(() -> new NotFoundException("DiaryPage instance not found"));
    }

    public DiaryPageDto addMedicine(UUID diaryPageId, MedicineDto medicineDto) {
        DiaryPage diary = repository.findFullById(diaryPageId)
                .orElseThrow(() -> new NotFoundException("DiaryPage not found"));
        MedicineDto saved = medicineService.create(medicineDto);
        diary.getMedicines().add(medicineMapper.toEntity(saved));
        DiaryPage updated = repository.save(diary);
        checkAndSendToKafka(updated);
        return mapper.toDto(updated);
    }

    public DiaryPageDto addSymptom(UUID diaryPageId, UserSymptomDto symptomDto) {
        DiaryPage diary = repository.findFullById(diaryPageId)
                .orElseThrow(() -> new NotFoundException("DiaryPage not found"));
        UserSymptomDto saved = symptomService.create(symptomDto);
        diary.getUserSymptoms().add(symptomMapper.toEntity(saved));
        DiaryPage updated = repository.save(diary);
        checkAndSendToKafka(updated);
        return mapper.toDto(updated);
    }

    public DiaryPageDto addWeather(UUID diaryPageId, WeatherDto weatherDto) {
        DiaryPage diary = repository.findFullById(diaryPageId)
                .orElseThrow(() -> new NotFoundException("DiaryPage not found"));
        WeatherDto saved = weatherService.create(weatherDto);
        diary.getWeathers().add(weatherMapper.toEntity(saved));
        DiaryPage updated = repository.save(diary);
        checkAndSendToKafka(updated);
        return mapper.toDto(updated);
    }

    public DiaryPage updateUserNote(UUID userId, String userNotes, UUID diaryPageId) {
        DiaryPage diary = repository.findById(diaryPageId)
                .orElseThrow(() -> new NotFoundException("DiaryPage not found"));
        diary.setUserNotes(userNotes);
        DiaryPage entity = repository.save(diary);
        checkAndSendToKafka(entity);
        return entity;
    }

    private DiaryPage findDiaryOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("DiaryPage not found"));
    }

    private boolean isDiaryComplete(DiaryPage diary) {
        if (diary.getHealthState() == null) return false;
        if (diary.getUserSymptoms() == null || diary.getUserSymptoms().isEmpty()) return false;
        return diary.getWeathers() != null && !diary.getWeathers().isEmpty();
    }

    private void checkAndSendToKafka(DiaryPage diary) {
        if (isDiaryComplete(diary)) {
            log.info("DiaryPage id={} is complete, sending to Kafka", diary.getId());

            DiaryNoteMessage message = new DiaryNoteMessage(
                    diary.getUserId(),
                    diary.getId(),
                    diary.getUserNotes(),
                    diary.getHealthState(),
                    diary.getUserSymptoms().stream().map(symptomMapper::toDto).collect(Collectors.toSet()),
                    diary.getWeathers().stream().map(weatherMapper::toDto).collect(Collectors.toSet()),
                    diary.getMedicines().stream().map(medicineMapper::toDto).collect(Collectors.toSet())
            );

            diaryNoteService.sendNoteMessage(message);
        } else {
            log.info("DiaryPage id={} is not complete, skipping Kafka send", diary.getId());
        }
    }


}
