package org.allergit.diary.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.allergit.diary.enums.SymptomState;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link org.allergit.symptom.entity.UserSymptom}
 */
@Value
public class UserSymptomDto implements Serializable {
    UUID id;
    UUID diaryPageId;
    @NotBlank
    String symptomName;
    @NotNull
    LocalDateTime timestamp;
    SymptomState symptomState;
}