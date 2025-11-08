package org.allergit.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.allergit.user.enums.UserRole;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;
    @NotBlank(message = "Name cannot be blank!")
    private String name;
    @NotNull(message = "Age cannot be null!")
    @Max(value = 99, message = "Age should be smaller than 100!")
    @Min(value = 12, message = "Age should be greater than 12!")
    private Integer age;
    @NotBlank(message = "Gender cannot be blank!")
    private String gender;
    @NotBlank(message = "Email cannot be blank!")
    private String email;
    @NotBlank(message = "Password cannot be blank!")
    private String password;
    @NotNull(message = "User role cannot be null")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    private UUID diaryId;

}