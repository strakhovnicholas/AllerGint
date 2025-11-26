package org.allergit.medicine.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.allergit.diary.enums.DoseMeasureType;
import org.allergit.diary.enums.MedicineType;
import org.allergit.diarypage.entity.DiaryPage;
import org.hibernate.proxy.HibernateProxy;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "medicine")
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;

    private String medicineName;

    private String medicineDescription;

    @Enumerated(EnumType.STRING)
    private MedicineType medicineType;

    @NotNull
    private Double dose;

    @ManyToMany
    @JoinTable(
            name = "diary_medicine",
            joinColumns = @JoinColumn(name = "medicine_id"),
            inverseJoinColumns = @JoinColumn(name = "diary_id")
    )
    private Set<DiaryPage> diaryPages = new LinkedHashSet<>();

    @Enumerated(EnumType.STRING)
    private DoseMeasureType doseMeasureType;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy
                ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
                : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
                : getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Medicine medicine = (Medicine) o;
        return id != null && id.equals(medicine.id);
    }

    @Override
    public final int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
