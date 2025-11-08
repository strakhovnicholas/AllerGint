package org.allergit.trigger.mapper;

import org.allergit.diary.dto.AllergyTriggerDto;
import org.allergit.trigger.entity.AllergyTrigger;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AllergyTriggerMapper {
    AllergyTrigger toEntity(AllergyTriggerDto allergyTriggerDto);

    AllergyTriggerDto toDto(AllergyTrigger allergyTrigger);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AllergyTrigger partialUpdate(AllergyTriggerDto allergyTriggerDto, @MappingTarget AllergyTrigger allergyTrigger);

    List<AllergyTriggerDto> toDto(List<AllergyTrigger> weatherList);

    void updateEntityFromDto(AllergyTriggerDto dto, @MappingTarget AllergyTrigger entity);
}