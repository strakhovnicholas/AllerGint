package org.allergit.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.allergit.diary.TriggerState;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "allergy_trigger")
public class AllergyTrigger {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotBlank
    private String triggerName;

    private TriggerState triggerState;
}