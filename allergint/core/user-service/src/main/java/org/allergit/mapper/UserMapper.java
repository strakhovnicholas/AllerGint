package org.allergit.mapper;

import org.allergit.entity.User;
import org.allergit.user.dto.UserDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    org.allergit.entity.User toEntity(UserDto userDto);

    UserDto toDto(org.allergit.entity.User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserDto userDto, @MappingTarget User user);
}