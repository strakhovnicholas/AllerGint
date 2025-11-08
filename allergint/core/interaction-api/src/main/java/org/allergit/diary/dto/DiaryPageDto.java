package org.allergit.diary.dto;

import lombok.Value;
import org.allergit.diary.enums.HealthState;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link org.allergit.diarypage.entity.DiaryPage}
 */
@Value
public class DiaryPageDto implements Serializable {
    UUID id;
    UUID userId;
    HealthState healthState;
    LocalDate timestamp;
    Set<MedicineDto> medicines;
    List<UserSymptomDto> userSymptoms;
    Set<WeatherDto> weathers;
    @Length(message = "Too much characters", max = 1000)
    String userNotes;
}