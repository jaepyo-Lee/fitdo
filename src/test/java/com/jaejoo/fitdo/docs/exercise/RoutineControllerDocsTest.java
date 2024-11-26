package com.jaejoo.fitdo.docs.exercise;

import com.jaejoo.fitdo.docs.RestDocsSupport;
import com.jaejoo.fitdo.domain.exercise.service.application.RoutineService;
import com.jaejoo.fitdo.domain.exercise.service.application.req.RoutineCreateCommand;
import com.jaejoo.fitdo.domain.exercise.web.RoutineController;
import com.jaejoo.fitdo.domain.exercise.web.req.DailyRecordCreateRequest;
import com.jaejoo.fitdo.domain.exercise.web.req.RoutineCreateRequest;
import com.jaejoo.fitdo.domain.exercise.web.req.dto.ExerciseRecordRequestDto;
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
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RoutineControllerDocsTest extends RestDocsSupport {

    @Mock
    private RoutineService service;

    @Override
    protected Object initController() {
        return new RoutineController(service);
    }
    @Test
    void createRoutine() throws Exception {
        // given
        RoutineCreateRequest request = new RoutineCreateRequest("name", List.of(1L, 2L));
        // mock the service method
        doNothing().when(service).create(any());

        // when
        mvc.perform(
                        post("/api/v1/routine")
                                .header("Authorization", "Bearer Token")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("create-routine",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName("Authorization").description("로그인후 받은 Bearer 토큰(accessToken)\n Bearer {Authorization Code}형식으로 요청")
                                ),
                                requestFields(
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("루틴명"),
                                        fieldWithPath("exerciseIds").type(JsonFieldType.ARRAY).description("루틴으로 만들고자하는 운동 아이디")
                                )
                        )
                );

    }
}