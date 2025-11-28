package org.allergit.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.allergit.user.enums.SymptomFrequency;

import java.io.Serializable;
import java.util.UUID;

@Data
public class UserSymptomPreferenceDto implements Serializable {
    UUID id;

    @NotBlank
    private String symptomName;

    private SymptomFrequency frequency;
}
