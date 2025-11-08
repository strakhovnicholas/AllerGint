package org.allergit.symptom.mapper;

import org.allergit.diary.dto.UserSymptomDto;
import org.allergit.symptom.entity.UserSymptom;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserSymptomMapper {
    UserSymptom toEntity(UserSymptomDto userSymptomDto);

    UserSymptomDto toDto(UserSymptom userSymptom);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserSymptom partialUpdate(UserSymptomDto userSymptomDto, @MappingTarget UserSymptom userSymptom);
}