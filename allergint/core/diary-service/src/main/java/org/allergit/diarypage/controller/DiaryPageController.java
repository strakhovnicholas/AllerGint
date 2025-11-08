package org.allergit.diarypage.controller;

import lombok.RequiredArgsConstructor;
import org.allergit.diary.dto.DiaryPageDto;
import org.allergit.diary.dto.MedicineDto;
import org.allergit.diary.dto.UserSymptomDto;
import org.allergit.diary.dto.WeatherDto;
import org.allergit.diarypage.service.DiaryPageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/diary")
@RequiredArgsConstructor
public class DiaryPageController {

    private final DiaryPageService service;

    @PostMapping
    public ResponseEntity<DiaryPageDto> create(@RequestBody DiaryPageDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiaryPageDto> getById(@PathVariable UUID id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/full/{id}")
    public ResponseEntity<DiaryPageDto> getByFullId(@PathVariable UUID id) {
        return service.getByFullId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<DiaryPageDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiaryPageDto> update(@PathVariable UUID id, @RequestBody DiaryPageDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<DiaryPageDto> getByUserAndDate(
            @PathVariable UUID userId,
            @RequestParam("date") ZonedDateTime date
    ) {
        return ResponseEntity.ok(service.getByUserIdAndZonedDateTime(userId, date));
    }

    @PostMapping("/{id}/medicine")
    public ResponseEntity<DiaryPageDto> addMedicine(
            @PathVariable UUID id,
            @RequestBody MedicineDto dto
    ) {
        return ResponseEntity.ok(service.addMedicine(id, dto));
    }

    @PostMapping("/{id}/symptom")
    public ResponseEntity<DiaryPageDto> addSymptom(
            @PathVariable UUID id,
            @RequestBody UserSymptomDto dto
    ) {
        return ResponseEntity.ok(service.addSymptom(id, dto));
    }

    @PostMapping("/{id}/weather")
    public ResponseEntity<DiaryPageDto> addWeather(
            @PathVariable UUID id,
            @RequestBody WeatherDto dto
    ) {
        return ResponseEntity.ok(service.addWeather(id, dto));
    }
}
