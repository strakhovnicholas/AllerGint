package org.allergit.diarypage.controller;

import lombok.RequiredArgsConstructor;
import org.allergit.diary.dto.*;
import org.allergit.diarypage.service.DiaryPageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/diary")
@RequiredArgsConstructor
public class DiaryPageController {

    private final DiaryPageService diaryPageService;

    @PostMapping
    public ResponseEntity<DiaryPageDto> create(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody DiaryPageDto dto
    ) {
        return ResponseEntity.ok(diaryPageService.create(dto, userId));
    }

    @GetMapping("/day")
    public ResponseEntity<DiaryPageDto> getByDay(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestParam("timestamp") ZonedDateTime timestamp
    ) {
        return ResponseEntity.ok(diaryPageService.getByUserAndDay(userId, timestamp));
    }


    @GetMapping("/{id}")
    public ResponseEntity<DiaryPageDto> getById(
            @RequestHeader("X-User-Id") UUID userId,
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(diaryPageService.getById(id, userId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DiaryPageDto> update(
            @RequestHeader("X-User-Id") UUID userId,
            @PathVariable UUID id,
            @RequestBody DiaryPageDto dto
    ) {
        return ResponseEntity.ok(diaryPageService.update(id, dto, userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @RequestHeader("X-User-Id") UUID userId,
            @PathVariable UUID id
    ) {
        diaryPageService.delete(id, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/ai-notes")
    public ResponseEntity<AiNotesDto> getAiNotes(
            @RequestHeader("X-User-Id") UUID userId,
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(diaryPageService.getAiNotes(id, userId));
    }

    @PostMapping("/{id}/medicine")
    public ResponseEntity<DiaryPageDto> addMedicine(
            @RequestHeader("X-User-Id") UUID userId,
            @PathVariable UUID id,
            @RequestBody MedicineDto dto
    ) {
        return ResponseEntity.ok(diaryPageService.addMedicine(id, dto, userId));
    }

    @PostMapping("/{id}/symptom")
    public ResponseEntity<DiaryPageDto> addSymptom(
            @RequestHeader("X-User-Id") UUID userId,
            @PathVariable UUID id,
            @RequestBody UserSymptomDto dto
    ) {
        return ResponseEntity.ok(diaryPageService.addSymptom(id, dto, userId));
    }

    @PostMapping("/{id}/weather")
    public ResponseEntity<DiaryPageDto> addWeather(
            @RequestHeader("X-User-Id") UUID userId,
            @PathVariable UUID id,
            @RequestBody WeatherDto dto
    ) {
        return ResponseEntity.ok(diaryPageService.addWeather(id, dto, userId));
    }
}
