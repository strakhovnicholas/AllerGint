package org.allergit.weather.mapper;

import org.allergit.diary.dto.WeatherDto;
import org.allergit.weather.entity.Weather;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface WeatherMapper {
    Weather toEntity(WeatherDto weatherDto);

    WeatherDto toDto(Weather weather);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Weather partialUpdate(WeatherDto weatherDto, @MappingTarget Weather weather);

    List<WeatherDto> toDto(List<Weather> weatherList);

    void updateEntityFromDto(WeatherDto dto, @MappingTarget Weather entity);
}