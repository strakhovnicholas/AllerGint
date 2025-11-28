package org.allergit.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.allergit.user.enums.AllergenSeverity;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Data
public class AllergenDto implements Serializable {
    UUID id;

    @NotBlank
    private String name;

    @NotNull
    private AllergenSeverity severity;

    private Set<String> monthsOfManifestation;
}
