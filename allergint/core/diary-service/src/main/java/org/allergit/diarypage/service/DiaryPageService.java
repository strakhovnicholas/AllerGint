package org.allergit.diarypage.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.allergit.diary.dto.DiaryPageDto;
import org.allergit.diary.dto.MedicineDto;
import org.allergit.diary.dto.UserSymptomDto;
import org.allergit.diary.dto.WeatherDto;
import org.allergit.diary.kafka.DiaryNoteMessage;
import org.allergit.diarypage.entity.DiaryPage;
import org.allergit.diarypage.mapper.DiaryPageMapper;
import org.allergit.diarypage.repository.DiaryPageRepository;
import org.allergit.exception.NotFoundException;
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
    private final UserSymptomService symptomService;
    private final WeatherService weatherService;
    private final MedicineMapper medicineMapper;
    private final UserSymptomMapper symptomMapper;
    private final WeatherMapper weatherMapper;
    private final DiaryNoteService diaryNoteService;

    public DiaryPageDto create(DiaryPageDto dto) {
        DiaryPage diary = mapper.toEntity(dto);
        if (dto.getMedicines() != null) {
            dto.getMedicines()
                    .forEach(medDto -> medicineMapper.toEntity(medicineService.create(medDto)));
        }
        if (dto.getUserSymptoms() != null) {
            dto.getUserSymptoms()
                    .forEach(symptomDto -> symptomMapper.toEntity(symptomService.create(symptomDto)));
        }
        if (dto.getWeathers() != null) {
            dto.getWeathers()
                    .forEach(weatherDto -> weatherMapper.toEntity(weatherService.create(weatherDto)));
        }

        diary = repository.save(diary);
        checkAndSendToKafka(diary);
        return mapper.toDto(diary);
    }


    public Optional<DiaryPageDto> getById(UUID id) {
        return repository.findById(id).map(mapper::toDto);
    }

    public Optional<DiaryPageDto> getByFullId(UUID id) {
        return repository.findFullById(id).map(mapper::toDto);
    }

    public List<DiaryPageDto> getAll() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public DiaryPageDto update(UUID id, DiaryPageDto dto) {
        DiaryPage diary = repository.findById(id).orElseThrow(() -> new NotFoundException("DiaryPage not found"));
        diary.setUserId(dto.getUserId());
        diary.setHealthState(dto.getHealthState());
        diary.setTimestamp(dto.getTimestamp());
        diary.setUserNotes(dto.getUserNotes());
        diary = repository.save(diary);
        checkAndSendToKafka(diary);
        return mapper.toDto(diary);
    }

    public void delete(UUID id) {
        if (!repository.existsById(id)) throw new NotFoundException("DiaryPage not found");
        repository.deleteById(id);
    }

    public DiaryPageDto getByUserIdAndZonedDateTime(UUID userId, ZonedDateTime date) {
        return repository.findByUserIdAndExactDay(userId, date)
                .map(mapper::toDto)
                .orElseThrow(() -> new NotFoundException("DiaryPage not found for user and date"));
    }

    public DiaryPageDto addMedicine(UUID diaryPageId, MedicineDto dto) {
        DiaryPage diary = repository.findById(diaryPageId)
                .orElseThrow(() -> new NotFoundException("DiaryPage not found"));
        diary.getMedicines().add(medicineMapper.toEntity(medicineService.create(dto)));
        diary = repository.save(diary);
        checkAndSendToKafka(diary);
        return mapper.toDto(diary);
    }

    public DiaryPageDto addSymptom(UUID diaryPageId, UserSymptomDto dto) {
        DiaryPage diary = repository.findById(diaryPageId)
                .orElseThrow(() -> new NotFoundException("DiaryPage not found"));
        diary.getUserSymptoms().add(symptomMapper.toEntity(symptomService.create(dto)));
        diary = repository.save(diary);
        checkAndSendToKafka(diary);
        return mapper.toDto(diary);
    }

    public DiaryPageDto addWeather(UUID diaryPageId, WeatherDto dto) {
        DiaryPage diary = repository.findById(diaryPageId)
                .orElseThrow(() -> new NotFoundException("DiaryPage not found"));
        diary.getWeathers().add(weatherMapper.toEntity(weatherService.create(dto)));
        diary = repository.save(diary);
        checkAndSendToKafka(diary);
        return mapper.toDto(diary);
    }

    public void updateUserNote(UUID userId, String userNotes, UUID diaryPageId) {
        DiaryPage diary = repository.findById(diaryPageId)
                .orElseThrow(() -> new NotFoundException("DiaryPage not found"));
        if (!diary.getUserId().equals(userId)) throw new NotFoundException("User not allowed to update this note");
        diary.setUserNotes(userNotes);
        repository.save(diary);
        checkAndSendToKafka(diary);
    }

    private void checkAndSendToKafka(DiaryPage diary) {
        if (diary.getHealthState() != null
                && !diary.getUserSymptoms().isEmpty()
                && !diary.getWeathers().isEmpty()) {

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
        }
    }
}
