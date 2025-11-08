package org.allergit.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.allergit.diary.HealthState;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "diary")
public class DiaryPage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    private UUID userId;

    @Enumerated(EnumType.STRING)
    private HealthState healthState;

    private LocalDate timestamp;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "diary_page_id")
    private List<UserSymptom> userSymptoms = new ArrayList<>();

}