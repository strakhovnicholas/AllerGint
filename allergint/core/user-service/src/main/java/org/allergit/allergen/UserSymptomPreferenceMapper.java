package org.allergit.allergen;

import org.allergit.user.dto.UserSymptomPreferenceDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserSymptomPreferenceMapper {
    UserSymptomPreference toEntity(UserSymptomPreferenceDto dto);

    UserSymptomPreferenceDto toDto(UserSymptomPreference entity);

    List<UserSymptomPreference> toEntityList(List<UserSymptomPreferenceDto> dtos);

    List<UserSymptomPreferenceDto> toDtoList(List<UserSymptomPreference> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserSymptomPreference partialUpdate(UserSymptomPreferenceDto dto, @MappingTarget UserSymptomPreference entity);
}
