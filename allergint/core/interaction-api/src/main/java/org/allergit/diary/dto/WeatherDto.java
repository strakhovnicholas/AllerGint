package org.allergit.diary.dto;

import lombok.Value;
import org.allergit.diary.enums.WeatherCondition;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link org.allergit.entity.entity.Weather}
 */
@Value
public class WeatherDto implements Serializable {
    UUID id;
    WeatherCondition weatherCondition;
    LocalDateTime timestamp;
}