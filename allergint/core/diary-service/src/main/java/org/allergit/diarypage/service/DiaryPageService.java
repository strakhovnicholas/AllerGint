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

    public DiaryPageDto create(DiaryPageDto dto) {
        log.info("Creating new DiaryPage for userId={}", dto.getUserId());
        DiaryPage saved = repository.save(mapper.toEntity(dto));
        log.debug("Created DiaryPage with id={}", saved.getId());
        return mapper.toDto(saved);
    }

    public Optional<DiaryPageDto> getById(UUID id) {
        log.info("Fetching DiaryPage by id={}", id);
        return repository.findById(id).map(mapper::toDto);
    }

    public Optional<DiaryPageDto> getByFullId(UUID id) {
        log.info("Fetching full DiaryPage by id={}", id);
        return repository.findFullById(id).map(mapper::toDto);
    }

    public List<DiaryPageDto> getAll() {
        log.info("Fetching all DiaryPages");
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    public DiaryPageDto update(UUID id, DiaryPageDto dto) {
        log.info("Updating DiaryPage id={}", id);
        DiaryPage existing = findDiaryOrThrow(id);
        existing.setUserId(dto.getUserId());
        existing.setHealthState(dto.getHealthState());
        existing.setTimestamp(dto.getTimestamp());
        existing.setUserNotes(dto.getUserNotes());
        DiaryPage updated = repository.save(existing);
        log.debug("Updated DiaryPage id={}", updated.getId());
        return mapper.toDto(updated);
    }

    public void delete(UUID id) {
        log.info("Deleting DiaryPage id={}", id);
        if (!repository.existsById(id)) {
            log.warn("DiaryPage id={} not found for deletion", id);
            throw new NotFoundException("DiaryPage not found");
        }
        repository.deleteById(id);
        log.debug("Deleted DiaryPage id={}", id);
    }

    public DiaryPageDto getByUserIdAndZonedDateTime(UUID userId, ZonedDateTime zonedDateTime) {
        log.info("Fetching DiaryPage for userId={} on date={}", userId, zonedDateTime);
        return repository.findByUserIdAndExactDay(userId, zonedDateTime)
                .map(mapper::toDto)
                .orElseThrow(() -> {
                    log.warn("DiaryPage for userId={} on date={} not found", userId, zonedDateTime);
                    return new NotFoundException("DiaryPage instance not found");
                });
    }

    public DiaryPageDto addMedicine(UUID diaryPageId, MedicineDto medicineDto) {
        log.info("Adding Medicine '{}' to DiaryPage id={}", medicineDto.getMedicineName(), diaryPageId);
        DiaryPage diary = repository.findFullById(diaryPageId).orElseThrow(() -> new NotFoundException("DiaryPage not found"));
        MedicineDto saved = medicineService.create(medicineDto);
        diary.getMedicines().add(medicineMapper.toEntity(saved));
        DiaryPage updated = repository.save(diary);
        log.debug("Added Medicine to DiaryPage id={}", diaryPageId);
        return mapper.toDto(updated);
    }

    public DiaryPageDto addSymptom(UUID diaryPageId, UserSymptomDto symptomDto) {
        log.info("Adding Symptom '{}' to DiaryPage id={}", symptomDto.getSymptomName(), diaryPageId);
        DiaryPage diary = repository.findFullById(diaryPageId).orElseThrow(() -> new NotFoundException("DiaryPage not found"));
        UserSymptomDto saved = symptomService.create(symptomDto);
        diary.getUserSymptoms().add(symptomMapper.toEntity(saved));
        DiaryPage updated = repository.save(diary);
        log.debug("Added Symptom to DiaryPage id={}", diaryPageId);
        return mapper.toDto(updated);
    }

    public DiaryPageDto addWeather(UUID diaryPageId, WeatherDto weatherDto) {
        log.info("Adding Weather entry '{}' to DiaryPage id={}", weatherDto.getWeatherCondition(), diaryPageId);
        DiaryPage diary = repository.findFullById(diaryPageId).orElseThrow(() -> new NotFoundException("DiaryPage not found"));
        WeatherDto saved = weatherService.create(weatherDto);
        diary.getWeathers().add(weatherMapper.toEntity(saved));
        DiaryPage updated = repository.save(diary);
        log.debug("Added Weather to DiaryPage id={}", diaryPageId);
        return mapper.toDto(updated);
    }

    private DiaryPage findDiaryOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("DiaryPage id={} not found", id);
                    return new NotFoundException("DiaryPage not found");
                });
    }
}
