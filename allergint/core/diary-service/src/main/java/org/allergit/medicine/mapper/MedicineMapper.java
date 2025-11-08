package org.allergit.medicine.mapper;

import org.allergit.diary.dto.MedicineDto;
import org.allergit.medicine.entity.Medicine;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface MedicineMapper {
    Medicine toEntity(MedicineDto medicineDto);

    MedicineDto toDto(Medicine medicine);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Medicine partialUpdate(MedicineDto medicineDto, @MappingTarget Medicine medicine);
}