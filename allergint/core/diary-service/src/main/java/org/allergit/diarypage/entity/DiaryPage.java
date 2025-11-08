package org.allergit.diarypage.entity;

import jakarta.persistence.*;
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
    @Column(name = "id", nullable = false)
    private UUID id;

    private UUID userId;

    @Enumerated(EnumType.STRING)
    private HealthState healthState;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "diary_page_id")
    private Set<Medicine> medicines = new LinkedHashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "diary_page_id")
    private Set<UserSymptom> userSymptoms = new LinkedHashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "diary_page_id")
    private Set<Weather> weathers = new LinkedHashSet<>();

    private ZonedDateTime timestamp;

    @Length(max = 1000, message = "Too much characters")
    private String userNotes;

    public Set<Medicine> getMedicines() {
        if (medicines == null) medicines = new LinkedHashSet<>();
        return medicines;
    }

    public Set<UserSymptom> getUserSymptoms() {
        if (userSymptoms == null) userSymptoms = new LinkedHashSet<>();
        return userSymptoms;
    }

    public Set<Weather> getWeathers() {
        if (weathers == null) weathers = new LinkedHashSet<>();
        return weathers;
    }


}