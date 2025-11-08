package org.allergit.diary.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.allergit.diary.enums.DoseMeasureType;
import org.allergit.diary.enums.MedicineType;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link org.allergit.medicine.entity.Medicine}
 */
@Value
public class MedicineDto implements Serializable {
    UUID id;
    String medicineName;
    String medicineDescription;
    MedicineType medicineType;
    @NotNull
    Double dose;
    DoseMeasureType doseMeasureType;
}