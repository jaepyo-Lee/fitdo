package com.jaejoo.fitdoweb.common.mapper;

import com.jaejoo.fitdocore.user.res.ExerciseInfoInRoutine;
import com.jaejoo.fitdocore.user.res.FriendDetailInfo;
import com.jaejoo.fitdocore.user.res.UserRoutineInfo;
import com.jaejoo.fitdoweb.user.web.res.ExerciseInfoInRoutineDto;
import com.jaejoo.fitdoweb.user.web.res.FriendDetailInfoResponse;
import com.jaejoo.fitdoweb.user.web.res.UserRoutineInfoDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ReadFriendDetailsMapper {
    ReadFriendDetailsMapper INSTANCE = Mappers.getMapper(ReadFriendDetailsMapper.class);
    @Named("toFriendDetailInfoResponse")
    FriendDetailInfoResponse toFriendDetailInfoResponse(FriendDetailInfo friendDetailInfo);

    @IterableMapping(qualifiedByName = "toUserRoutineInfoDto")
    List<UserRoutineInfoDto> toUserRoutineInfoDto(List<UserRoutineInfo> userRoutineInfos);

    @Named("toUserRoutineInfoDto")
    @Mapping(target = "name", source = "routineName")
    UserRoutineInfoDto toUserRoutineInfoDto(UserRoutineInfo userRoutineInfo);

    @Named("toExerciseInfoInRoutineDto")
    ExerciseInfoInRoutineDto toExerciseInfoInRoutineDto(ExerciseInfoInRoutine exerciseInfoInRoutine);

    @IterableMapping(qualifiedByName = "toExerciseInfoInRoutineDto")
    List<ExerciseInfoInRoutineDto> toExerciseInfoInRoutineDto(List<ExerciseInfoInRoutine> exerciseInfoInRoutine);
}
