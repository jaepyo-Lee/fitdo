package com.jaejoo.fitdo.docs.exercise;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jaejoo.fitdo.docs.RestDocsSupport;
import com.jaejoo.fitdo.domain.auth.service.application.req.AuthType;
import com.jaejoo.fitdo.domain.auth.service.application.res.LoginResult;
import com.jaejoo.fitdo.domain.auth.web.req.LoginRequest;
import com.jaejoo.fitdo.domain.exercise.service.application.ExerciseRecordService;
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
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
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

}
