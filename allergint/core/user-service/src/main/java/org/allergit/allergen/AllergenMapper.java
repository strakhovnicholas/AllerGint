package org.allergit.allergen;

import org.allergit.user.dto.AllergenDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AllergenMapper {
    Allergen toEntity(AllergenDto dto);

    AllergenDto toDto(Allergen entity);

    List<Allergen> toEntityList(List<AllergenDto> dtos);

    List<AllergenDto> toDtoList(List<Allergen> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Allergen partialUpdate(AllergenDto dto, @MappingTarget Allergen entity);
}
