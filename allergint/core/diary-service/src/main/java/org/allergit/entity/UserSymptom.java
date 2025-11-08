package org.allergit.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.allergit.diary.SymptomState;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "user_symptom")
public class UserSymptom {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    private UUID diary_page_id;

    @NotBlank
    private String symptomName;


    @NotNull
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    private SymptomState symptomState;
}