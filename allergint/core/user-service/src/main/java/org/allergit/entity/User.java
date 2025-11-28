package org.allergit.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.allergit.allergen.Allergen;
import org.allergit.allergen.UserSymptomPreference;
import org.allergit.user.enums.UserRole;

import java.util.HashSet;
import java.util.Set;
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
    @Min(value = 12, message = "Age should be greater than 12!")
    @Max(value = 99, message = "Age should be smaller than 100!")
    private Integer age;

    @NotBlank(message = "Gender cannot be blank!")
    private String gender;

    @NotBlank(message = "Email cannot be blank!")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Password cannot be blank!")
    private String password;

    @NotNull(message = "User role cannot be null")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    private UUID diaryId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Allergen> allergens = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserSymptomPreference> symptomPreferences = new HashSet<>();
}
