package org.allergit.diary.dto;

import lombok.Data;
import org.allergit.diary.enums.WeatherCondition;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class WeatherDto implements Serializable {
    UUID id;
    WeatherCondition weatherCondition;
    ZonedDateTime timestamp;
    private Double temperature;
}