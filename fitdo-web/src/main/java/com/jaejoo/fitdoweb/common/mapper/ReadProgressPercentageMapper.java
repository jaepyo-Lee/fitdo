package com.jaejoo.fitdoweb.common.mapper;

import com.jaejoo.fitdocore.exercise.res.ProgressPercentage;
import com.jaejoo.fitdoweb.exercise.web.res.ProgressPercentageWithMonthInfoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ReadProgressPercentageMapper {
    ReadProgressPercentageMapper INSTANCE = Mappers.getMapper(ReadProgressPercentageMapper.class);

    @Named("toProgressPercentageWithMonthInfoResponse")
    @Mapping(target = "startDayValue", source = "value")
    @Mapping(target = "percentagesInMonth", source = "progressPercentages")
    ProgressPercentageWithMonthInfoResponse toProgressPercentageWithMonthInfoResponse(int value, List<ProgressPercentage> progressPercentages);
}
