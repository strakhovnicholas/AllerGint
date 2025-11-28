package org.allergit.allergen;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.allergit.entity.User;
import org.allergit.user.enums.SymptomFrequency;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "user_symptom_preferences")
public class UserSymptomPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String symptomName;

    @Enumerated(EnumType.STRING)
    private SymptomFrequency frequency;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}

