package com.jaejoo.fitdo.docs.exercise;

import com.jaejoo.fitdo.docs.RestDocsSupport;
import com.jaejoo.fitdo.domain.exercise.service.application.ExerciseRecordService;
import com.jaejoo.fitdo.domain.exercise.service.application.res.FindDateExerciseRecords;
import com.jaejoo.fitdo.domain.exercise.service.application.res.FindExerciseRecords;
import com.jaejoo.fitdo.domain.exercise.service.application.res.FindMonthExerciseRecords;
import com.jaejoo.fitdo.domain.exercise.web.ExerciseRecordController;
import com.jaejoo.fitdo.domain.exercise.web.req.DailyRecordCreateRequest;
import com.jaejoo.fitdo.domain.exercise.web.req.dto.ExerciseRecordRequestDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ExerciseControllerDocsTest extends RestDocsSupport {
    @Mock
    private ExerciseRecordService service;

    @Override
    protected Object initController() {
        return new ExerciseRecordController(service);
    }

    @Test
    void createExerciseRecords() throws Exception {
        // given
        DailyRecordCreateRequest request1 = new DailyRecordCreateRequest(LocalDate.now(),
                1L,
                List.of(new ExerciseRecordRequestDto(1, 60, 10, true),
                        new ExerciseRecordRequestDto(2, 70, 10, false)));
        DailyRecordCreateRequest request2 = new DailyRecordCreateRequest(LocalDate.now(),
                2L,
                List.of(new ExerciseRecordRequestDto(1, 30, 15, false),
                        new ExerciseRecordRequestDto(2, 100, 10, false)));
        List<DailyRecordCreateRequest> createRequests = new ArrayList<>(List.of(request1, request2));

        // mock the service method
        when(service.writeDailyExerciseFrom(any(), any())).thenReturn(true);

        // when
        mvc.perform(
                        post("/api/v1/exercise-record")
                                .header("Authorization", "Bearer Token")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createRequests))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("create-exercise-records",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName("Authorization").description("로그인후 받은 Bearer 토큰(accessToken)\n Bearer {Authorization Code}형식으로 요청")
                                ),
                                requestFields(
                                        fieldWithPath("[].todayDate").type(JsonFieldType.STRING).description("기록된 날짜"),
                                        fieldWithPath("[].exerciseId").type(JsonFieldType.NUMBER).description("기록하려는 운동의 아이디"),
                                        fieldWithPath("[].records").type(JsonFieldType.ARRAY).description("기록목록"),
                                        fieldWithPath("[].records[].set").type(JsonFieldType.NUMBER).description("해당 운동의 세트번호"),
                                        fieldWithPath("[].records[].weight").type(JsonFieldType.NUMBER).description("해당 세트의 무게"),
                                        fieldWithPath("[].records[].count").type(JsonFieldType.NUMBER).description("해당 세트의 반복수"),
                                        fieldWithPath("[].records[].progress").type(JsonFieldType.BOOLEAN).description("해당 운동의 진행여부")
                                ),
                                responseFields(
                                        fieldWithPath("result").type(JsonFieldType.BOOLEAN).description("운동 기록이 성공적으로 생성되었는지 여부. `true`이면 성공, `false`이면 실패")
                                )
                        )
                );

        // verify
        verify(service, times(1)).writeDailyExerciseFrom(any(), any());
    }


    @Test
    void getExerciseRecordsInMonthTest() throws Exception {
        // given
        FindExerciseRecords exerciseRecords1 = FindExerciseRecords.builder().exerciseSet(1).weight(50).volume(10).isProgress(true).build();
        FindExerciseRecords exerciseRecords2 = FindExerciseRecords.builder().exerciseSet(2).weight(50).volume(10).isProgress(true).build();
        FindExerciseRecords exerciseRecords3 = FindExerciseRecords.builder().exerciseSet(3).weight(60).volume(8).isProgress(true).build();

        FindExerciseRecords exerciseRecords4 = FindExerciseRecords.builder().exerciseSet(1).weight(50).volume(10).isProgress(false).build();
        FindExerciseRecords exerciseRecords5 = FindExerciseRecords.builder().exerciseSet(2).weight(50).volume(10).isProgress(true).build();

        List<FindExerciseRecords> records1 = List.of(exerciseRecords1, exerciseRecords2, exerciseRecords3);
        List<FindExerciseRecords> records2 = List.of(exerciseRecords4, exerciseRecords5);

        FindDateExerciseRecords dateExerciseRecord = FindDateExerciseRecords.builder().exerciseId(1L).exerciseName("벤치프레스").records(records1).categoryName("가슴").build();

        FindDateExerciseRecords dateExerciseRecord1 = FindDateExerciseRecords.builder().exerciseId(2L).exerciseName("플라이머신").records(records2).categoryName("가슴").build();
        List<FindDateExerciseRecords> dateExerciseRecords = List.of(dateExerciseRecord, dateExerciseRecord1);

        FindMonthExerciseRecords findMonthExerciseRecords = FindMonthExerciseRecords.builder().exerciseDate(LocalDate.of(2024, 11, 20)).dateRecords(dateExerciseRecords).build();

        //---//

        FindExerciseRecords backexerciseRecords1 = FindExerciseRecords.builder().exerciseSet(1).weight(50).volume(10).isProgress(true).build();
        FindExerciseRecords backexerciseRecords2 = FindExerciseRecords.builder().exerciseSet(2).weight(50).volume(10).isProgress(true).build();

        FindExerciseRecords backexerciseRecords4 = FindExerciseRecords.builder().exerciseSet(1).weight(50).volume(10).isProgress(false).build();
        FindExerciseRecords backexerciseRecords5 = FindExerciseRecords.builder().exerciseSet(2).weight(50).volume(10).isProgress(true).build();

        List<FindExerciseRecords> backrecords1 = List.of(backexerciseRecords1, backexerciseRecords2);
        List<FindExerciseRecords> backrecords2 = List.of(backexerciseRecords4, backexerciseRecords5);

        FindDateExerciseRecords backdateExerciseRecord = FindDateExerciseRecords.builder().exerciseId(1L).exerciseName("데드리프트").records(backrecords1).categoryName("등").build();

        FindDateExerciseRecords backdateExerciseRecord1 = FindDateExerciseRecords.builder().exerciseId(2L).exerciseName("시티드로우").records(backrecords2).categoryName("등").build();
        List<FindDateExerciseRecords> backdateExerciseRecords = List.of(backdateExerciseRecord, backdateExerciseRecord1);

        FindMonthExerciseRecords backfindMonthExerciseRecords = FindMonthExerciseRecords.builder().exerciseDate(LocalDate.of(2024, 11, 21)).dateRecords(backdateExerciseRecords).build();

        //--//
        List<FindMonthExerciseRecords> monthAllExerciseRecords = new ArrayList<>(List.of(findMonthExerciseRecords, backfindMonthExerciseRecords));

        // mock the service method
        when(service.findExerciseRecordsOfUserInMonth(any(), any())).thenReturn(monthAllExerciseRecords);

        // when
        mvc.perform(
                        get("/api/v1/exercise")
                                .header("Authorization", "Bearer Token")
                                .param("yearMonth", "2024-11")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("read-exercise-records",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),

                                requestHeaders(
                                        headerWithName("Authorization").description("로그인후 받은 Bearer 토큰(accessToken)\n Bearer {Authorization Code}형식으로 요청")
                                ),
                                queryParameters(
                                        parameterWithName("yearMonth").description("운동 기록을 구하고자하는 연도와 월\n").description("[연도,월,일] 베열형식으로 반환")
                                ),
                                responseFields(
                                        fieldWithPath("[].exerciseDate").type(JsonFieldType.ARRAY).description("운동한 날짜. yyyy-MM-dd 형식"),
                                        fieldWithPath("[].dateRecords").type(JsonFieldType.ARRAY).description("운동 기록"),
                                        fieldWithPath("[].dateRecords[].exerciseId").type(JsonFieldType.NUMBER).description("진행한 운동종목 ID"),
                                        fieldWithPath("[].dateRecords[].categoryName").type(JsonFieldType.STRING).description("진행한 운동종목의 부위명"),
                                        fieldWithPath("[].dateRecords[].records").type(JsonFieldType.ARRAY).description("진행한 운동의 기록"),
                                        fieldWithPath("[].dateRecords[].records[].weight").type(JsonFieldType.NUMBER).description("운동 중량"),
                                        fieldWithPath("[].dateRecords[].records[].volume").type(JsonFieldType.NUMBER).description("운동 횟수"),
                                        fieldWithPath("[].dateRecords[].records[].exerciseSet").type(JsonFieldType.NUMBER).description("세트번호"),
                                        fieldWithPath("[].dateRecords[].records[].progress").type(JsonFieldType.BOOLEAN).description("운동 진행 여부")
                                )
                        )
                );

        // verify
        verify(service, times(1)).findExerciseRecordsOfUserInMonth(any(), any());
    }
}
