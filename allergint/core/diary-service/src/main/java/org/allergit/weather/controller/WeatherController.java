package org.allergit.weather.controller;

import lombok.RequiredArgsConstructor;
import org.allergit.diary.dto.WeatherDto;
import org.allergit.weather.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @PostMapping
    public ResponseEntity<WeatherDto> create(@RequestBody WeatherDto weatherDto) {
        WeatherDto created = weatherService.create(weatherDto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WeatherDto> getById(@PathVariable UUID id) {
        Optional<WeatherDto> weather = weatherService.getById(id);
        return weather.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<WeatherDto>> getAll() {
        List<WeatherDto> all = weatherService.getAll();
        return ResponseEntity.ok(all);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WeatherDto> update(@PathVariable UUID id,
                                             @RequestBody WeatherDto weatherDto) {
        WeatherDto updated = weatherService.update(id, weatherDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        weatherService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/query")
    public ResponseEntity<WeatherDto> getByYearMonthDayHour(
            @RequestParam int year,
            @RequestParam int month,
            @RequestParam int day,
            @RequestParam int hour
    ) {
        Optional<WeatherDto> weather = weatherService.getByYearMonthDayHour(year, month, day, hour);
        return weather.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
