package org.allergit.user.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.allergit.user.enums.UserRole;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Data
public class UserDto implements Serializable {
    UUID id;

    @NotBlank
    String name;

    @NotNull
    @Min(value = 12, message = "Age should be greater than 12!")
    @Max(value = 99, message = "Age should be smaller than 100!")
    Integer age;

    @NotBlank
    String gender;

    @NotBlank
    String email;

    @NotBlank
    String password;

    @NotNull
    UserRole role;

    UUID diaryId;

    private Set<AllergenDto> allergens;

    private Set<UserSymptomPreferenceDto> symptomPreferences;
}
