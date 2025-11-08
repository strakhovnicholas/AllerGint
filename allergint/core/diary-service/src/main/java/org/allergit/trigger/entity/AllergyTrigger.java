package org.allergit.trigger.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.allergit.diary.enums.TriggerState;

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

    @Enumerated(EnumType.STRING)
    private TriggerState triggerState;
}