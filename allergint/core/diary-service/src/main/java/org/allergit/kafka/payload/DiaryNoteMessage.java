package org.allergit.kafka.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.allergit.diary.dto.MedicineDto;
import org.allergit.diary.dto.UserSymptomDto;
import org.allergit.diary.dto.WeatherDto;
import org.allergit.diary.enums.HealthState;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DiaryNoteMessage {
    private UUID userId;
    private UUID diaryPageId;
    private String userNotes;
    private HealthState healthState;
    private Set<UserSymptomDto> symptoms;
    private Set<WeatherDto> weathers;
    private Set<MedicineDto> medicines;
}


