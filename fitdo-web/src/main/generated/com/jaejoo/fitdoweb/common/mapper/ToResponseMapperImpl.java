package com.jaejoo.fitdoweb.common.mapper;

import com.jaejoo.fitdocore.exercise.res.CategoryAndExerciseWithinRoutine;
import com.jaejoo.fitdocore.exercise.res.ExercisesWithinCategory;
import com.jaejoo.fitdocore.exercise.res.FindDateExerciseRecords;
import com.jaejoo.fitdocore.exercise.res.FindExerciseRecords;
import com.jaejoo.fitdocore.exercise.res.FindExercisesWithCategory;
import com.jaejoo.fitdocore.exercise.res.FindMonthExerciseRecords;
import com.jaejoo.fitdocore.exercise.res.ProgressPercentage;
import com.jaejoo.fitdocore.exercise.res.ReadRoutineOfUser;
import com.jaejoo.fitdocore.user.req.FriendSimpleInfo;
import com.jaejoo.fitdocore.user.req.ReadApplierInfo;
import com.jaejoo.fitdocore.user.res.ExerciseInfoInRoutine;
import com.jaejoo.fitdocore.user.res.FriendDetailInfo;
import com.jaejoo.fitdocore.user.res.UserRoutineInfo;
import com.jaejoo.fitdoweb.exercise.web.res.CategoryAndExerciseWithinRoutineDto;
import com.jaejoo.fitdoweb.exercise.web.res.ExerciseReadResponse;
import com.jaejoo.fitdoweb.exercise.web.res.ExercisesWithinCategoryDto;
import com.jaejoo.fitdoweb.exercise.web.res.FindDateExerciseRecordsResponseDto;
import com.jaejoo.fitdoweb.exercise.web.res.FindExerciseRecordsResponseDto;
import com.jaejoo.fitdoweb.exercise.web.res.FindMonthExerciseRecordsResponse;
import com.jaejoo.fitdoweb.exercise.web.res.ProgressPercentageDto;
import com.jaejoo.fitdoweb.exercise.web.res.ProgressPercentageWithMonthInfoResponse;
import com.jaejoo.fitdoweb.exercise.web.res.ReadRoutinesOfUserResponse;
import com.jaejoo.fitdoweb.user.web.res.ExerciseInfoInRoutineDto;
import com.jaejoo.fitdoweb.user.web.res.FriendDetailInfoResponse;
import com.jaejoo.fitdoweb.user.web.res.FriendSimpleInfosResponse;
import com.jaejoo.fitdoweb.user.web.res.ReadApplierInfoResponse;
import com.jaejoo.fitdoweb.user.web.res.UserRoutineInfoDto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-21T17:35:26+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.11 (Amazon.com Inc.)"
)
public class ToResponseMapperImpl implements ToResponseMapper {

    @Override
    public List<ExerciseReadResponse> toExerciseReadResponse(List<FindExercisesWithCategory> findExercisesWithCategories) {
        if ( findExercisesWithCategories == null ) {
            return null;
        }

        List<ExerciseReadResponse> list = new ArrayList<ExerciseReadResponse>( findExercisesWithCategories.size() );
        for ( FindExercisesWithCategory findExercisesWithCategory : findExercisesWithCategories ) {
            list.add( toExerciseReadResponse( findExercisesWithCategory ) );
        }

        return list;
    }

    @Override
    public ExerciseReadResponse toExerciseReadResponse(FindExercisesWithCategory findExercisesWithCategory) {
        if ( findExercisesWithCategory == null ) {
            return null;
        }

        ExerciseReadResponse exerciseReadResponse = new ExerciseReadResponse();

        exerciseReadResponse.setCategoryId( findExercisesWithCategory.getCategoryId() );
        exerciseReadResponse.setCategoryName( findExercisesWithCategory.getCategoryName() );
        exerciseReadResponse.setExercises( toExerciseWithinCategoryDto( findExercisesWithCategory.getExercises() ) );

        return exerciseReadResponse;
    }

    @Override
    public List<ExercisesWithinCategoryDto> toExerciseWithinCategoryDto(List<ExercisesWithinCategory> exercisesWithinCategory) {
        if ( exercisesWithinCategory == null ) {
            return null;
        }

        List<ExercisesWithinCategoryDto> list = new ArrayList<ExercisesWithinCategoryDto>( exercisesWithinCategory.size() );
        for ( ExercisesWithinCategory exercisesWithinCategory1 : exercisesWithinCategory ) {
            list.add( toExercisesWithinCategoryDto( exercisesWithinCategory1 ) );
        }

        return list;
    }

    @Override
    public ExercisesWithinCategoryDto toExercisesWithinCategoryDto(ExercisesWithinCategory exercisesWithinCategory) {
        if ( exercisesWithinCategory == null ) {
            return null;
        }

        ExercisesWithinCategoryDto exercisesWithinCategoryDto = new ExercisesWithinCategoryDto();

        exercisesWithinCategoryDto.setExerciseName( exercisesWithinCategory.getExerciseName() );
        exercisesWithinCategoryDto.setExerciseId( exercisesWithinCategory.getExerciseId() );

        return exercisesWithinCategoryDto;
    }

    @Override
    public List<FindMonthExerciseRecordsResponse> toFindMonthExerciseRecordsResponse(List<FindMonthExerciseRecords> findMonthExerciseRecords) {
        if ( findMonthExerciseRecords == null ) {
            return null;
        }

        List<FindMonthExerciseRecordsResponse> list = new ArrayList<FindMonthExerciseRecordsResponse>( findMonthExerciseRecords.size() );
        for ( FindMonthExerciseRecords findMonthExerciseRecords1 : findMonthExerciseRecords ) {
            list.add( toResponse( findMonthExerciseRecords1 ) );
        }

        return list;
    }

    @Override
    public FindMonthExerciseRecordsResponse toResponse(FindMonthExerciseRecords findMonthExerciseRecords) {
        if ( findMonthExerciseRecords == null ) {
            return null;
        }

        FindMonthExerciseRecordsResponse findMonthExerciseRecordsResponse = new FindMonthExerciseRecordsResponse();

        findMonthExerciseRecordsResponse.setExerciseDate( findMonthExerciseRecords.getExerciseDate() );
        findMonthExerciseRecordsResponse.setDateRecords( maps( findMonthExerciseRecords.getDateRecords() ) );

        return findMonthExerciseRecordsResponse;
    }

    @Override
    public FindDateExerciseRecordsResponseDto map(FindDateExerciseRecords dateRecord) {
        if ( dateRecord == null ) {
            return null;
        }

        FindDateExerciseRecordsResponseDto findDateExerciseRecordsResponseDto = new FindDateExerciseRecordsResponseDto();

        findDateExerciseRecordsResponseDto.setExerciseId( dateRecord.getExerciseId() );
        findDateExerciseRecordsResponseDto.setExerciseName( dateRecord.getExerciseName() );
        findDateExerciseRecordsResponseDto.setCategoryName( dateRecord.getCategoryName() );
        findDateExerciseRecordsResponseDto.setRecords( map( dateRecord.getRecords() ) );

        return findDateExerciseRecordsResponseDto;
    }

    @Override
    public List<FindDateExerciseRecordsResponseDto> maps(List<FindDateExerciseRecords> dateRecords) {
        if ( dateRecords == null ) {
            return null;
        }

        List<FindDateExerciseRecordsResponseDto> list = new ArrayList<FindDateExerciseRecordsResponseDto>( dateRecords.size() );
        for ( FindDateExerciseRecords findDateExerciseRecords : dateRecords ) {
            list.add( map( findDateExerciseRecords ) );
        }

        return list;
    }

    @Override
    public List<FindExerciseRecordsResponseDto> map(List<FindExerciseRecords> exerciseRecords) {
        if ( exerciseRecords == null ) {
            return null;
        }

        List<FindExerciseRecordsResponseDto> list = new ArrayList<FindExerciseRecordsResponseDto>( exerciseRecords.size() );
        for ( FindExerciseRecords findExerciseRecords : exerciseRecords ) {
            list.add( map( findExerciseRecords ) );
        }

        return list;
    }

    @Override
    public FindExerciseRecordsResponseDto map(FindExerciseRecords exerciseRecord) {
        if ( exerciseRecord == null ) {
            return null;
        }

        FindExerciseRecordsResponseDto findExerciseRecordsResponseDto = new FindExerciseRecordsResponseDto();

        findExerciseRecordsResponseDto.setWeight( exerciseRecord.getWeight() );
        findExerciseRecordsResponseDto.setVolume( exerciseRecord.getVolume() );
        findExerciseRecordsResponseDto.setExerciseSet( exerciseRecord.getExerciseSet() );
        findExerciseRecordsResponseDto.setProgress( exerciseRecord.isProgress() );

        return findExerciseRecordsResponseDto;
    }

    @Override
    public List<ReadRoutinesOfUserResponse> toReadRoutineOfUserResponse(List<ReadRoutineOfUser> dto) {
        if ( dto == null ) {
            return null;
        }

        List<ReadRoutinesOfUserResponse> list = new ArrayList<ReadRoutinesOfUserResponse>( dto.size() );
        for ( ReadRoutineOfUser readRoutineOfUser : dto ) {
            list.add( toReadRoutineOfUserResponse( readRoutineOfUser ) );
        }

        return list;
    }

    @Override
    public ReadRoutinesOfUserResponse toReadRoutineOfUserResponse(ReadRoutineOfUser dto) {
        if ( dto == null ) {
            return null;
        }

        ReadRoutinesOfUserResponse readRoutinesOfUserResponse = new ReadRoutinesOfUserResponse();

        readRoutinesOfUserResponse.setRoutineId( dto.getRoutineId() );
        readRoutinesOfUserResponse.setRoutineName( dto.getRoutineName() );
        readRoutinesOfUserResponse.setCategoryAndExercise( toCategoryAndExerciseWithinRoutine( dto.getCategoryAndExercise() ) );

        return readRoutinesOfUserResponse;
    }

    @Override
    public CategoryAndExerciseWithinRoutineDto toCategoryAndExerciseWithinRoutine(CategoryAndExerciseWithinRoutine dto) {
        if ( dto == null ) {
            return null;
        }

        CategoryAndExerciseWithinRoutineDto categoryAndExerciseWithinRoutineDto = new CategoryAndExerciseWithinRoutineDto();

        categoryAndExerciseWithinRoutineDto.setCategoryId( dto.getCategoryId() );
        categoryAndExerciseWithinRoutineDto.setCategoryName( dto.getCategoryName() );
        categoryAndExerciseWithinRoutineDto.setExerciseId( dto.getExerciseId() );
        categoryAndExerciseWithinRoutineDto.setExerciseName( dto.getExerciseName() );

        return categoryAndExerciseWithinRoutineDto;
    }

    @Override
    public List<CategoryAndExerciseWithinRoutineDto> toCategoryAndExerciseWithinRoutine(List<CategoryAndExerciseWithinRoutine> dto) {
        if ( dto == null ) {
            return null;
        }

        List<CategoryAndExerciseWithinRoutineDto> list = new ArrayList<CategoryAndExerciseWithinRoutineDto>( dto.size() );
        for ( CategoryAndExerciseWithinRoutine categoryAndExerciseWithinRoutine : dto ) {
            list.add( toCategoryAndExerciseWithinRoutine( categoryAndExerciseWithinRoutine ) );
        }

        return list;
    }

    @Override
    public ReadApplierInfoResponse toReadApplierInfoResponse(ReadApplierInfo readApplierInfo) {
        if ( readApplierInfo == null ) {
            return null;
        }

        ReadApplierInfoResponse readApplierInfoResponse = new ReadApplierInfoResponse();

        readApplierInfoResponse.setUserId( readApplierInfo.getSenderId() );
        readApplierInfoResponse.setName( readApplierInfo.getName() );

        return readApplierInfoResponse;
    }

    @Override
    public List<ReadApplierInfoResponse> toReadApplierInfoResponse(List<ReadApplierInfo> readApplierInfo) {
        if ( readApplierInfo == null ) {
            return null;
        }

        List<ReadApplierInfoResponse> list = new ArrayList<ReadApplierInfoResponse>( readApplierInfo.size() );
        for ( ReadApplierInfo readApplierInfo1 : readApplierInfo ) {
            list.add( toReadApplierInfoResponse( readApplierInfo1 ) );
        }

        return list;
    }

    @Override
    public List<FriendSimpleInfosResponse> toFriendSimpleInfosResponse(List<FriendSimpleInfo> friendSimpleInfos) {
        if ( friendSimpleInfos == null ) {
            return null;
        }

        List<FriendSimpleInfosResponse> list = new ArrayList<FriendSimpleInfosResponse>( friendSimpleInfos.size() );
        for ( FriendSimpleInfo friendSimpleInfo : friendSimpleInfos ) {
            list.add( toFriendSimpleInfosResponse( friendSimpleInfo ) );
        }

        return list;
    }

    @Override
    public FriendSimpleInfosResponse toFriendSimpleInfosResponse(FriendSimpleInfo friendSimpleInfo) {
        if ( friendSimpleInfo == null ) {
            return null;
        }

        FriendSimpleInfosResponse friendSimpleInfosResponse = new FriendSimpleInfosResponse();

        friendSimpleInfosResponse.setUserId( friendSimpleInfo.getUserId() );
        friendSimpleInfosResponse.setNickname( friendSimpleInfo.getNickname() );
        friendSimpleInfosResponse.setTier( friendSimpleInfo.getTier() );

        return friendSimpleInfosResponse;
    }

    @Override
    public FriendDetailInfoResponse toFriendDetailInfoResponse(FriendDetailInfo friendDetailInfo) {
        if ( friendDetailInfo == null ) {
            return null;
        }

        FriendDetailInfoResponse friendDetailInfoResponse = new FriendDetailInfoResponse();

        friendDetailInfoResponse.setUserId( friendDetailInfo.getUserId() );
        friendDetailInfoResponse.setUserName( friendDetailInfo.getUserName() );
        friendDetailInfoResponse.setWeight( friendDetailInfo.getWeight() );
        friendDetailInfoResponse.setHeight( friendDetailInfo.getHeight() );
        friendDetailInfoResponse.setRoutines( toUserRoutineInfoDto( friendDetailInfo.getRoutines() ) );

        return friendDetailInfoResponse;
    }

    @Override
    public List<UserRoutineInfoDto> toUserRoutineInfoDto(List<UserRoutineInfo> userRoutineInfos) {
        if ( userRoutineInfos == null ) {
            return null;
        }

        List<UserRoutineInfoDto> list = new ArrayList<UserRoutineInfoDto>( userRoutineInfos.size() );
        for ( UserRoutineInfo userRoutineInfo : userRoutineInfos ) {
            list.add( toUserRoutineInfoDto( userRoutineInfo ) );
        }

        return list;
    }

    @Override
    public UserRoutineInfoDto toUserRoutineInfoDto(UserRoutineInfo userRoutineInfo) {
        if ( userRoutineInfo == null ) {
            return null;
        }

        UserRoutineInfoDto userRoutineInfoDto = new UserRoutineInfoDto();

        userRoutineInfoDto.setName( userRoutineInfo.getRoutineName() );
        userRoutineInfoDto.setExercises( toExerciseInfoInRoutineDto( userRoutineInfo.getExercises() ) );

        return userRoutineInfoDto;
    }

    @Override
    public ExerciseInfoInRoutineDto toExerciseInfoInRoutineDto(ExerciseInfoInRoutine exerciseInfoInRoutine) {
        if ( exerciseInfoInRoutine == null ) {
            return null;
        }

        ExerciseInfoInRoutineDto exerciseInfoInRoutineDto = new ExerciseInfoInRoutineDto();

        exerciseInfoInRoutineDto.setBodyPart( exerciseInfoInRoutine.getBodyPart() );
        exerciseInfoInRoutineDto.setExerciseName( exerciseInfoInRoutine.getExerciseName() );

        return exerciseInfoInRoutineDto;
    }

    @Override
    public List<ExerciseInfoInRoutineDto> toExerciseInfoInRoutineDto(List<ExerciseInfoInRoutine> exerciseInfoInRoutine) {
        if ( exerciseInfoInRoutine == null ) {
            return null;
        }

        List<ExerciseInfoInRoutineDto> list = new ArrayList<ExerciseInfoInRoutineDto>( exerciseInfoInRoutine.size() );
        for ( ExerciseInfoInRoutine exerciseInfoInRoutine1 : exerciseInfoInRoutine ) {
            list.add( toExerciseInfoInRoutineDto( exerciseInfoInRoutine1 ) );
        }

        return list;
    }

    @Override
    public ProgressPercentageWithMonthInfoResponse toProgressPercentageWithMonthInfoResponse(int value, List<ProgressPercentage> progressPercentages) {
        if ( progressPercentages == null ) {
            return null;
        }

        int startDayValue = 0;
        startDayValue = value;
        List<ProgressPercentageDto> percentagesInMonth = null;
        percentagesInMonth = progressPercentageListToProgressPercentageDtoList( progressPercentages );

        ProgressPercentageWithMonthInfoResponse progressPercentageWithMonthInfoResponse = new ProgressPercentageWithMonthInfoResponse( startDayValue, percentagesInMonth );

        return progressPercentageWithMonthInfoResponse;
    }

    protected ProgressPercentageDto progressPercentageToProgressPercentageDto(ProgressPercentage progressPercentage) {
        if ( progressPercentage == null ) {
            return null;
        }

        LocalDate date = null;
        Double percentage = null;

        date = progressPercentage.getDate();
        percentage = progressPercentage.getPercentage();

        ProgressPercentageDto progressPercentageDto = new ProgressPercentageDto( date, percentage );

        return progressPercentageDto;
    }

    protected List<ProgressPercentageDto> progressPercentageListToProgressPercentageDtoList(List<ProgressPercentage> list) {
        if ( list == null ) {
            return null;
        }

        List<ProgressPercentageDto> list1 = new ArrayList<ProgressPercentageDto>( list.size() );
        for ( ProgressPercentage progressPercentage : list ) {
            list1.add( progressPercentageToProgressPercentageDto( progressPercentage ) );
        }

        return list1;
    }
}
