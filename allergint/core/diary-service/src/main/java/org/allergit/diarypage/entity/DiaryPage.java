package org.allergit.diarypage.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.allergit.diary.enums.HealthState;
import org.allergit.entity.entity.Weather;
import org.allergit.medicine.entity.Medicine;
import org.allergit.symptom.entity.UserSymptom;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.*;

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

    @OneToMany(mappedBy = "diaryPage", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Medicine> medicines = new LinkedHashSet<>();

    @OneToMany(mappedBy = "diaryPage", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserSymptom> userSymptoms = new ArrayList<>();

    @OneToMany(mappedBy = "diaryPage", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Weather> weathers = new LinkedHashSet<>();

    @Length(max = 1000, message = "Too much characters")
    private String userNotes;

}