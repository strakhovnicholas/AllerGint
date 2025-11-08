package org.allergit.diary.dto;

import lombok.Data;
import org.allergit.diary.enums.HealthState;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
public class DiaryPageDto implements Serializable {
    UUID id;
    UUID userId;
    HealthState healthState;
    ZonedDateTime timestamp;
    Set<MedicineDto> medicines;
    List<UserSymptomDto> userSymptoms;
    Set<WeatherDto> weathers;
    @Length(message = "Too much characters", max = 1000)
    String userNotes;
}