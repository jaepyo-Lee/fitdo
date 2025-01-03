package com.jaejoo.fitdoweb.docs.exercise;


import com.jaejoo.fitdocore.exercise.ExerciseRecordService;
import com.jaejoo.fitdocore.exercise.ExerciseRecordWriteService;
import com.jaejoo.fitdocore.exercise.res.FindDateExerciseRecords;
import com.jaejoo.fitdocore.exercise.res.FindExerciseRecords;
import com.jaejoo.fitdocore.exercise.res.FindMonthExerciseRecords;
import com.jaejoo.fitdocore.exercise.res.ProgressPercentage;
import com.jaejoo.fitdoweb.docs.RestDocsSupport;
import com.jaejoo.fitdoweb.exercise.web.ExerciseRecordController;
import com.jaejoo.fitdoweb.exercise.web.req.DailyExerciseRecordsRequest;
import com.jaejoo.fitdoweb.exercise.web.req.DailyRecordCreateRequest;
import com.jaejoo.fitdoweb.exercise.web.req.dto.ExerciseRecordRequestDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ExerciseRecordControllerDocsTest extends RestDocsSupport {
    @Mock
    private ExerciseRecordService service;

    @Mock
    private ExerciseRecordWriteService writeService;

    @Override
    protected Object initController() {
        return new ExerciseRecordController(service,writeService);
    }

    @Test
    void createExerciseRecords() throws Exception {
        // given
        DailyExerciseRecordsRequest request1 = new DailyExerciseRecordsRequest(1L,
                List.of(
                        new ExerciseRecordRequestDto(1, 60, 10, true),
                        new ExerciseRecordRequestDto(2, 70, 10, false)));
        DailyExerciseRecordsRequest request2 = new DailyExerciseRecordsRequest(2L,
                List.of(
                        new ExerciseRecordRequestDto(1, 30, 15, false),
                        new ExerciseRecordRequestDto(2, 100, 10, false)));
        List<DailyExerciseRecordsRequest> createRequests = new ArrayList<>(List.of(request1, request2));
        DailyRecordCreateRequest request = new DailyRecordCreateRequest(LocalDate.now(), createRequests);
        // mock the service method
        when(writeService.writeDailyExerciseFrom(any(), any())).thenReturn(true);

        // when
        mvc.perform(
                        post("/api/v1/exercise-record")
                                .header("Authorization", "Bearer Token")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
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
                                        fieldWithPath("recordDate").type(JsonFieldType.STRING).description("기록된 날짜"),
                                        fieldWithPath("dailyExerciseRecords").type(JsonFieldType.ARRAY).description("저장하고자 하는 운동기록"),
                                        fieldWithPath("dailyExerciseRecords[].exerciseId").type(JsonFieldType.NUMBER).description("기록하려는 운동의 아이디"),
                                        fieldWithPath("dailyExerciseRecords[].records").type(JsonFieldType.ARRAY).description("기록목록"),
                                        fieldWithPath("dailyExerciseRecords[].records[].set").type(JsonFieldType.NUMBER).description("해당 운동의 세트번호"),
                                        fieldWithPath("dailyExerciseRecords[].records[].weight").type(JsonFieldType.NUMBER).description("해당 세트의 무게"),
                                        fieldWithPath("dailyExerciseRecords[].records[].count").type(JsonFieldType.NUMBER).description("해당 세트의 반복수"),
                                        fieldWithPath("dailyExerciseRecords[].records[].progress").type(JsonFieldType.BOOLEAN).description("해당 운동의 진행여부")
                                ),
                                responseFields(
                                        fieldWithPath("result").type(JsonFieldType.BOOLEAN).description("운동 기록이 성공적으로 생성되었는지 여부. `true`이면 성공, `false`이면 실패")
                                )
                        )
                );

        // verify
        verify(writeService, times(1)).writeDailyExerciseFrom(any(), any());
    }


    @Test
    void getExerciseRecordAtDayTest() throws Exception {
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


        // mock the service method
        when(service.findExerciseRecordsOfUserAtDate(any(), any())).thenReturn(findMonthExerciseRecords);

        // when
        mvc.perform(
                        get("/api/v1/exercises/records")
                                .header("Authorization", "Bearer Token")
                                .param("date", "2024-11-20")
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
                                        parameterWithName("date").description("운동 기록을 구하고자하는 연도와 월\n").description("yyyy-MM 베열형식으로 반환")
                                ),
                                responseFields(
                                        beneathPath("result").withSubsectionId("result"),
                                        fieldWithPath("exerciseDate").type(JsonFieldType.STRING).description("운동한 날짜. yyyy-MM-dd 형식"),
                                        fieldWithPath("dateRecords").type(JsonFieldType.ARRAY).description("운동 기록"),
                                        fieldWithPath("dateRecords[].exerciseId").type(JsonFieldType.NUMBER).description("진행한 운동종목 ID"),
                                        fieldWithPath("dateRecords[].exerciseName").type(JsonFieldType.STRING).description("진행한 운동종목명"),
                                        fieldWithPath("dateRecords[].categoryName").type(JsonFieldType.STRING).description("진행한 운동종목의 부위명"),
                                        fieldWithPath("dateRecords[].records").type(JsonFieldType.ARRAY).description("진행한 운동의 기록"),
                                        fieldWithPath("dateRecords[].records[].weight").type(JsonFieldType.NUMBER).description("운동 중량"),
                                        fieldWithPath("dateRecords[].records[].volume").type(JsonFieldType.NUMBER).description("운동 횟수"),
                                        fieldWithPath("dateRecords[].records[].exerciseSet").type(JsonFieldType.NUMBER).description("세트번호"),
                                        fieldWithPath("dateRecords[].records[].progress").type(JsonFieldType.BOOLEAN).description("운동 진행 여부")
                                )
                        )
                );

        // verify
        verify(service, times(1)).findExerciseRecordsOfUserAtDate(any(), any());
    }


    @Test
    void 해당월의모든일의운동진행퍼센트값구하기() throws Exception {
        // given
        List<ProgressPercentage> response = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            response.add(new ProgressPercentage(LocalDate.of(2024, 12, i), Math.random() * 101));
        }

        // mock the service method
        when(service.readProgressPercentage(any(), any())).thenReturn(response);

        // when
        mvc.perform(
                        get("/api/v1/exercises/percentage")
                                .header("Authorization", "Bearer Token")
                                .param("yearMonth", "2024-12")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("read-exercise-percentage",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName("Authorization").description("로그인후 받은 Bearer 토큰(accessToken)\n Bearer {Authorization Code}형식으로 요청")
                                ),
                                queryParameters(
                                        parameterWithName("yearMonth").description("운동 기록을 구하고자하는 연도와 월\n").description("yyyy-MM 베열형식으로 반환")
                                ),
                                responseFields(
                                        beneathPath("result").withSubsectionId("result"),
                                        fieldWithPath("startDayValue").type(JsonFieldType.NUMBER).description("해당월의 시작요일 \n 1(월요일)~7(일요일)"),
                                        fieldWithPath("percentagesInMonth").type(JsonFieldType.ARRAY).description("해당월의 운동진행 퍼센티지 배열"),
                                        fieldWithPath("percentagesInMonth[].date").type(JsonFieldType.STRING).description("날짜"),
                                        fieldWithPath("percentagesInMonth[].percentage").type(JsonFieldType.NUMBER).description("퍼센트")
                                )
                        )
                );
    }
}
