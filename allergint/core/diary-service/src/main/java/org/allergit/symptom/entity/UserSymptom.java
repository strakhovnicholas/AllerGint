package org.allergit.symptom.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.allergit.diary.enums.SymptomState;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "user_symptom")
public class UserSymptom {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String symptomName;

    @Enumerated(EnumType.STRING)
    private SymptomState symptomState;

    @Column(nullable = false)
    private ZonedDateTime timestamp;
}
