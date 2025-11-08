package org.allergit.diarypage.mapper;

import org.allergit.diary.dto.DiaryPageDto;
import org.allergit.diarypage.entity.DiaryPage;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DiaryPageMapper {
    DiaryPage toEntity(DiaryPageDto diaryPageDto);

    DiaryPageDto toDto(DiaryPage diaryPage);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DiaryPage partialUpdate(DiaryPageDto diaryPageDto, @MappingTarget DiaryPage diaryPage);
}