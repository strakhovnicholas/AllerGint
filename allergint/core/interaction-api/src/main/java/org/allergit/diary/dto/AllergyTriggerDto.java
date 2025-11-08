package org.allergit.diary.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.allergit.diary.enums.TriggerState;

import java.io.Serializable;
import java.util.UUID;

@Data
public class AllergyTriggerDto implements Serializable {
    UUID id;
    @NotBlank
    String triggerName;
    TriggerState triggerState;
}