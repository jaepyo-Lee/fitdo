package com.jaejoo.fitdo.global.mapper;

import com.jaejoo.fitdo.domain.exercise.service.application.res.*;
import com.jaejoo.fitdo.domain.exercise.web.res.*;
import com.jaejoo.fitdo.domain.user.service.application.req.FriendSimpleInfo;
import com.jaejoo.fitdo.domain.user.service.application.req.ReadApplierInfo;
import com.jaejoo.fitdo.domain.user.service.application.res.ExerciseInfoInRoutine;
import com.jaejoo.fitdo.domain.user.service.application.res.FriendDetailInfo;
import com.jaejoo.fitdo.domain.user.service.application.res.UserRoutineInfo;
import com.jaejoo.fitdo.domain.user.web.res.*;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ToResponseMapper {
    ToResponseMapper INSTANCE = Mappers.getMapper(ToResponseMapper.class);

    @IterableMapping(qualifiedByName = "toExerciseReadResponse")
    List<ExerciseReadResponse> toExerciseReadResponse(List<FindExercisesWithCategory> findExercisesWithCategories);

    @Named("toExerciseReadResponse")
    ExerciseReadResponse toExerciseReadResponse(FindExercisesWithCategory findExercisesWithCategory);


    @IterableMapping(qualifiedByName = "toExerciseWithinCategoryDto")
    List<ExercisesWithinCategoryDto> toExerciseWithinCategoryDto(List<ExercisesWithinCategory> exercisesWithinCategory);

    @Named("toExerciseWithinCategoryDto")
    ExercisesWithinCategoryDto toExercisesWithinCategoryDto(ExercisesWithinCategory exercisesWithinCategory);

    @IterableMapping(qualifiedByName = "toResponse")
    List<FindMonthExerciseRecordsResponse> toFindMonthExerciseRecordsResponse(List<FindMonthExerciseRecords> findMonthExerciseRecords);

    @Named("toResponse")
    FindMonthExerciseRecordsResponse toResponse(FindMonthExerciseRecords findMonthExerciseRecords);

    // 중첩 리스트 매핑
    @Named("toResponseDto1")
    FindDateExerciseRecordsResponseDto map(FindDateExerciseRecords dateRecord);

    @IterableMapping(qualifiedByName = "toResponseDto1")
    List<FindDateExerciseRecordsResponseDto> maps(List<FindDateExerciseRecords> dateRecords);

    @IterableMapping(qualifiedByName = "toResponseDto2")
    List<FindExerciseRecordsResponseDto> map(List<FindExerciseRecords> exerciseRecords);

    // 중첩 객체 매핑
    @Named("toResponseDto2")
    FindExerciseRecordsResponseDto map(FindExerciseRecords exerciseRecord);


    @IterableMapping(qualifiedByName = "toReadRoutineOfUserResponse")
    List<ReadRoutinesOfUserResponse> toReadRoutineOfUserResponse(List<ReadRoutineOfUser> dto);

    @Named("toReadRoutineOfUserResponse")
    ReadRoutinesOfUserResponse toReadRoutineOfUserResponse(ReadRoutineOfUser dto);

    @Named("toCategoryAndExerciseWithinRoutine")
    CategoryAndExerciseWithinRoutineDto toCategoryAndExerciseWithinRoutine(CategoryAndExerciseWithinRoutine dto);

    @IterableMapping(qualifiedByName = "toCategoryAndExerciseWithinRoutine")
    List<CategoryAndExerciseWithinRoutineDto> toCategoryAndExerciseWithinRoutine(List<CategoryAndExerciseWithinRoutine> dto);

    @Mapping(target = "userId", source = "senderId")
    @Named("toReadApplierInfoResponse")
    ReadApplierInfoResponse toReadApplierInfoResponse(ReadApplierInfo readApplierInfo);

    @IterableMapping(qualifiedByName = "toReadApplierInfoResponse")
    List<ReadApplierInfoResponse> toReadApplierInfoResponse(List<ReadApplierInfo> readApplierInfo);

    @IterableMapping(qualifiedByName = "toFriendSimpleInfosResponse")
    List<FriendSimpleInfosResponse> toFriendSimpleInfosResponse(List<FriendSimpleInfo> friendSimpleInfos);

    @Named("toFriendSimpleInfosResponse")
    FriendSimpleInfosResponse toFriendSimpleInfosResponse(FriendSimpleInfo friendSimpleInfo);

    @Named("toFriendDetailInfoResponse")
    FriendDetailInfoResponse toFriendDetailInfoResponse(FriendDetailInfo friendDetailInfo);

    @IterableMapping(qualifiedByName = "toUserRoutineInfoDto")
    List<UserRoutineInfoDto> toUserRoutineInfoDto(List<UserRoutineInfo> userRoutineInfos);

    @Named("toUserRoutineInfoDto")
    @Mapping(target = "name",source = "routineName")
    UserRoutineInfoDto toUserRoutineInfoDto(UserRoutineInfo userRoutineInfo);

    @Named("toExerciseInfoInRoutineDto")
    ExerciseInfoInRoutineDto toExerciseInfoInRoutineDto(ExerciseInfoInRoutine exerciseInfoInRoutine);

    @IterableMapping(qualifiedByName = "toExerciseInfoInRoutineDto")
    List<ExerciseInfoInRoutineDto> toExerciseInfoInRoutineDto(List<ExerciseInfoInRoutine> exerciseInfoInRoutine);
}
