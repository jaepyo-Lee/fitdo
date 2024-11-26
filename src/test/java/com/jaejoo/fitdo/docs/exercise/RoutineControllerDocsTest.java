package com.jaejoo.fitdo.docs.exercise;

import com.jaejoo.fitdo.docs.RestDocsSupport;
import com.jaejoo.fitdo.domain.exercise.service.application.RoutineService;
import com.jaejoo.fitdo.domain.exercise.service.application.res.CategoryAndExerciseWithinRoutine;
import com.jaejoo.fitdo.domain.exercise.service.application.res.ReadRoutineOfUser;
import com.jaejoo.fitdo.domain.exercise.web.RoutineController;
import com.jaejoo.fitdo.domain.exercise.web.req.RoutineCreateRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Test
    void readRoutine() throws Exception {
        // given
        // mock the service method
        CategoryAndExerciseWithinRoutine ce1 = new CategoryAndExerciseWithinRoutine(1L, "등", 1L, "데드리프트");
        CategoryAndExerciseWithinRoutine ce2 = new CategoryAndExerciseWithinRoutine(1L, "등", 2L, "시티드로우");
        CategoryAndExerciseWithinRoutine ce3 = new CategoryAndExerciseWithinRoutine(2L, "가슴", 3L, "벤치프레스");
        ReadRoutineOfUser readRoutineOfUser1 = new ReadRoutineOfUser(1L, "등,가슴", List.of(ce1, ce2, ce3));

        CategoryAndExerciseWithinRoutine ce4 = new CategoryAndExerciseWithinRoutine(3L, "어깨", 4L, "밀리터리프레스");
        CategoryAndExerciseWithinRoutine ce5 = new CategoryAndExerciseWithinRoutine(3L, "어깨", 5L, "프레스머신");
        CategoryAndExerciseWithinRoutine ce6 = new CategoryAndExerciseWithinRoutine(4L, "하체", 6L, "스쿼트");
        ReadRoutineOfUser readRoutineOfUser2 = new ReadRoutineOfUser(2L, "어깨,하체", List.of(ce4, ce5, ce6));

        List<ReadRoutineOfUser> response = List.of(readRoutineOfUser1, readRoutineOfUser2);
        // when
        when(service.readRoutine(any())).thenReturn(response);

        mvc.perform(
                        get("/api/v1/routine")
                                .header("Authorization", "Bearer Token")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("read-routine",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName("Authorization").description("로그인후 받은 Bearer 토큰(accessToken)\n Bearer {Authorization Code}형식으로 요청")
                                ),
                                responseFields(
                                        beneathPath("result").withSubsectionId("result"),
                                        fieldWithPath("routineId").type(NUMBER).description("루틴 ID"),
                                        fieldWithPath("routineName").type(STRING).description("루틴명"),
                                        fieldWithPath("categoryAndExercise").type(ARRAY).description("루틴에 저장된 운동과 운동카테고리 정보"),
                                        fieldWithPath("categoryAndExercise[].categoryId").type(NUMBER).description("운동카테고리(부위)ID"),
                                        fieldWithPath("categoryAndExercise[].categoryName").type(STRING).description("운동카테고리(부위)명"),
                                        fieldWithPath("categoryAndExercise[].exerciseId").type(NUMBER).description("운동ID"),
                                        fieldWithPath("categoryAndExercise[].exerciseName").type(STRING).description("운동명")
                                )
                        )
                );
    }
}