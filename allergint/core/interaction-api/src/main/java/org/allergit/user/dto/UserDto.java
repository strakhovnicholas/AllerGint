package org.allergit.user.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.allergit.user.enums.UserRole;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link org.allergit.entity.User}
 */
@Data
public class UserDto implements Serializable {
    UUID id;
    @NotBlank(message = "Name cannot be blank!")
    String name;
    @NotNull(message = "Age cannot be null!")
    @Min(message = "Age should be greater than 12!", value = 12)
    @Max(message = "Age should be smaller than 100!", value = 99)
    Integer age;
    @NotBlank(message = "Gender cannot be blank!")
    String gender;
    @NotBlank(message = "Email cannot be blank!")
    String email;
    @NotBlank(message = "Password cannot be blank!")
    String password;
    @NotNull(message = "User role cannot be null")
    UserRole role;
    UUID diaryId;
}