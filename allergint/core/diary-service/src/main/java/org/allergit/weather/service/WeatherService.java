package org.allergit.weather.service;

import lombok.RequiredArgsConstructor;
import org.allergit.diary.dto.WeatherDto;
import org.allergit.weather.entity.Weather;
import org.allergit.weather.mapper.WeatherMapper;
import org.allergit.weather.repository.WeatherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class WeatherService {

    private final WeatherRepository weatherRepository;
    private final WeatherMapper weatherMapper;

    public WeatherDto create(WeatherDto weatherDto) {
        Weather entity = weatherMapper.toEntity(weatherDto);
        Weather saved = weatherRepository.save(entity);
        return weatherMapper.toDto(saved);
    }

    public Optional<WeatherDto> getById(UUID id) {
        return weatherRepository.findById(id).map(weatherMapper::toDto);
    }

    public List<WeatherDto> getAll() {
        return weatherMapper.toDto(weatherRepository.findAll());
    }

    public WeatherDto update(UUID id, WeatherDto weatherDto) {
        Weather entity = weatherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Weather with id " + id + " not found"));
        weatherMapper.updateEntityFromDto(weatherDto, entity);
        Weather updated = weatherRepository.save(entity);
        return weatherMapper.toDto(updated);
    }

    public void delete(UUID id) {
        if (!weatherRepository.existsById(id)) {
            throw new IllegalArgumentException("Weather with id " + id + " not found");
        }
        weatherRepository.deleteById(id);
    }

    public Optional<WeatherDto> getByYearMonthDayHour(int year, int month, int day, int hour) {
        return weatherRepository.findByYearMonthDayHour(year, month, day, hour)
                .map(weatherMapper::toDto);
    }
}
