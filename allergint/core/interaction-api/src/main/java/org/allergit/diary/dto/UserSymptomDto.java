package org.allergit.diary.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.allergit.diary.enums.SymptomState;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class UserSymptomDto implements Serializable {
    UUID id;
    @NotBlank
    String symptomName;
    @NotNull
    ZonedDateTime timestamp;
    SymptomState symptomState;
}