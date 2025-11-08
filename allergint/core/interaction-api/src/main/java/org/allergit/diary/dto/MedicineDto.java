package org.allergit.diary.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.allergit.diary.enums.DoseMeasureType;
import org.allergit.diary.enums.MedicineType;

import java.io.Serializable;
import java.util.UUID;

@Data
public class MedicineDto implements Serializable {
    UUID id;
    String medicineName;
    String medicineDescription;
    MedicineType medicineType;
    @NotNull
    Double dose;
    DoseMeasureType doseMeasureType;
}