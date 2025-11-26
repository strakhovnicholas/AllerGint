package org.allergit.diarypage.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.allergit.diary.enums.HealthState;
import org.allergit.medicine.entity.Medicine;
import org.allergit.symptom.entity.UserSymptom;
import org.allergit.weather.entity.Weather;
import org.hibernate.validator.constraints.Length;

import java.time.ZonedDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "diary")
public class DiaryPage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotNull
    private UUID userId;

    @Enumerated(EnumType.STRING)
    private HealthState healthState;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "diary_medicine",
            joinColumns = @JoinColumn(name = "diary_id"),
            inverseJoinColumns = @JoinColumn(name = "medicine_id")
    )
    private Set<Medicine> medicines = new LinkedHashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "diary_symptom",
            joinColumns = @JoinColumn(name = "diary_id"),
            inverseJoinColumns = @JoinColumn(name = "symptom_id")
    )
    private Set<UserSymptom> userSymptoms = new LinkedHashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "diary_weather",
            joinColumns = @JoinColumn(name = "diary_id"),
            inverseJoinColumns = @JoinColumn(name = "weather_id")
    )
    private Set<Weather> weathers = new LinkedHashSet<>();

    private ZonedDateTime timestamp;

    @Length(max = 1000)
    private String userNotes;

    @Length(max = 2000)
    private String aiNotes;

}
