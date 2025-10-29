package org.allergit.user.dto;

import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link org.allergit.entity.User}
 */
@Value
public class UserDto implements Serializable {
    @jakarta.validation.constraints.NotNull
    UUID id;
    String name;
    Integer age;
    String gender;
    String email;
}