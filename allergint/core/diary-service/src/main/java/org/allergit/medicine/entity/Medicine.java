package org.allergit.medicine.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.allergit.diary.enums.DoseMeasureType;
import org.allergit.diary.enums.MedicineType;
import org.allergit.diarypage.entity.DiaryPage;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "medicine")
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    private String medicineName;

    private String medicineDescription;

    @Enumerated(EnumType.STRING)
    private MedicineType medicineType;

    @NotNull
    private Double dose;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_page_id")
    private DiaryPage diaryPage;

    @Enumerated(EnumType.STRING)
    private DoseMeasureType doseMeasureType;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Medicine medicine = (Medicine) o;
        return getId() != null && Objects.equals(getId(), medicine.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}