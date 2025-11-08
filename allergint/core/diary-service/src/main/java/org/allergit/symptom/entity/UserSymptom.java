package org.allergit.symptom.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.allergit.diary.enums.SymptomState;
import org.allergit.diarypage.entity.DiaryPage;

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

    @Column(name = "diary_page_id", nullable = false)
    private UUID diaryPageId;

    @NotBlank
    private String symptomName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_page_id")
    private DiaryPage diaryPage;

    @NotNull
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    private SymptomState symptomState;
}