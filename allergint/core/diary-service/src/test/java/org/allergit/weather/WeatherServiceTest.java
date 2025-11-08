package org.allergit.weather;

import lombok.RequiredArgsConstructor;
import org.allergit.DiaryServiceApplication;
import org.allergit.diary.dto.WeatherDto;
import org.allergit.diary.enums.WeatherCondition;
import org.allergit.weather.mapper.WeatherMapper;
import org.allergit.weather.repository.WeatherRepository;
import org.allergit.weather.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(
        properties = {
                "spring.cloud.config.enabled=false",
                "spring.cloud.discovery.enabled=false",
                "eureka.client.enabled=false",
                "eureka.client.register-with-eureka=false",
                "eureka.client.fetch-registry=false"
        },
        classes = DiaryServiceApplication.class
)
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Transactional
class WeatherServiceTest {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private WeatherMapper weatherMapper;

    private WeatherDto sampleWeather;

    @BeforeEach
    void setUp() {
        sampleWeather = new WeatherDto();
        sampleWeather.setWeatherCondition(WeatherCondition.CLOUDY);
        sampleWeather.setTimestamp(ZonedDateTime.now());
    }

    @Test
    void testCreateAndGetById() {
        WeatherDto created = weatherService.create(sampleWeather);

        assertThat(created.getId()).isNotNull();

        Optional<WeatherDto> found = weatherService.getById(created.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getWeatherCondition()).isEqualTo(sampleWeather.getWeatherCondition());
    }

    @Test
    void testGetAll() {
        weatherService.create(sampleWeather);
        WeatherDto newWeather = new WeatherDto();

        newWeather.setWeatherCondition(WeatherCondition.RAINY);
        newWeather.setTimestamp(ZonedDateTime.now());
        weatherService.create(newWeather);

        List<WeatherDto> all = weatherService.getAll();

        assertThat(all).hasSize(2);
    }

    @Test
    void testUpdate() {
        WeatherDto created = weatherService.create(sampleWeather);

        WeatherDto updatedDto = new WeatherDto();

        updatedDto.setWeatherCondition(WeatherCondition.RAINY);
        updatedDto.setTimestamp(ZonedDateTime.now());

        WeatherDto updated = weatherService.update(created.getId(), updatedDto);

        assertThat(updated.getWeatherCondition()).isEqualTo(WeatherCondition.RAINY);
    }

    @Test
    void testDelete() {
        WeatherDto created = weatherService.create(sampleWeather);

        weatherService.delete(created.getId());

        assertThat(weatherRepository.findById(created.getId())).isEmpty();
    }

    @Test
    void testDeleteNonExistentThrows() {
        UUID randomId = UUID.randomUUID();
        assertThrows(IllegalArgumentException.class, () -> weatherService.delete(randomId));
    }
}
