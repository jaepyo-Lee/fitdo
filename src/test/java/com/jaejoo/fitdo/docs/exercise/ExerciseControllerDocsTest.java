package com.jaejoo.fitdo.docs.exercise;

import com.jaejoo.fitdo.docs.RestDocsSupport;
import com.jaejoo.fitdo.domain.exercise.service.application.ExerciseService;
import com.jaejoo.fitdo.domain.exercise.service.application.res.ExercisesWithinCategory;
import com.jaejoo.fitdo.domain.exercise.service.application.res.FindExercisesWithCategory;
import com.jaejoo.fitdo.domain.exercise.web.ExerciseController;
import com.jaejoo.fitdo.domain.exercise.web.req.ExerciseCreateRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ExerciseControllerDocsTest extends RestDocsSupport {
    @Mock
    private ExerciseService service;

    @Override
    protected Object initController() {
        return new ExerciseController(service);
    }


    @Test
    void createExerciseRecords() throws Exception {
        // given
        String exerciseName = "벤치프레스";
        ExerciseCreateRequest request = ExerciseCreateRequest.builder().exerciseName(exerciseName).categoryId(1L).build();

        // mock the service method
        when(service.createExercise(any())).thenReturn(exerciseName);

        // when
        MvcResult mvcResult = mvc.perform(
                        post("/api/v1/exercises")
                                .header("Authorization", "Bearer Token")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("create-exercise",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName("Authorization").description("로그인후 받은 Bearer 토큰(accessToken)\n Bearer {Authorization Code}형식으로 요청")
                                ),
                                requestFields(
                                        fieldWithPath("categoryId").type(NUMBER).description("저장할 운동의 부위Id"),
                                        fieldWithPath("exerciseName").type(STRING).description("저장할 운동이름")
                                ),
                                responseFields(
                                        beneathPath("result").withSubsectionId("result"),
                                        fieldWithPath("exerciseName").type(STRING).description("저장된 운동")
                                )
                        )
                ).andReturn();

/*        // verify
        String contentAsString = mvcResult.getResponse().getContentAsString();
        SuccessResponse responseDto = objectMapper.readValue(contentAsString, SuccessResponse.class);
        assertThat(responseDto.getResult()).isEqualTo(exerciseName);*/
    }

    @Test
    void readExerciseRecords() throws Exception {
        // given
        String exerciseName = "벤치프레스";

        List<FindExercisesWithCategory> response = new ArrayList<>();

        ExercisesWithinCategory bench = ExercisesWithinCategory.builder().exerciseId(1L).exerciseName("벤치프레스").build();
        ExercisesWithinCategory pressmachine = ExercisesWithinCategory.builder().exerciseId(2L).exerciseName("프레스머신").build();
        List<ExercisesWithinCategory> exercisesWithinCategories = new ArrayList<>(List.of(bench, pressmachine));
        FindExercisesWithCategory exercises = FindExercisesWithCategory.builder().categoryId(1l).categoryName("가슴").exercises(exercisesWithinCategories).build();
        response.add(exercises);

        // mock the service method
        when(service.findExercisesWithCategoryOf(any())).thenReturn(response);

        // when
        MvcResult mvcResult = mvc.perform(
                        get("/api/v1/exercises")
                                .header("Authorization", "Bearer Token")
                                .contentType(MediaType.APPLICATION_JSON)

                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("read-exercise",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName("Authorization").description("로그인후 받은 Bearer 토큰(accessToken)\n Bearer {Authorization Code}형식으로 요청")
                                ),
                                responseFields(
                                        beneathPath("result").withSubsectionId("result"),
                                        fieldWithPath("categoryId").type(NUMBER).description("운동부위id"),
                                        fieldWithPath("categoryName").type(STRING).description("운동부위명"),
                                        fieldWithPath("exercises").type(ARRAY).description("부위에 속한 운동리스트"),
                                        fieldWithPath("exercises[].exerciseName").type(STRING).description("운동명"),
                                        fieldWithPath("exercises[].exerciseId").type(NUMBER).description("운동Id")
                                )
                        )
                ).andReturn();
    }

    @Test
    void deleteExerciseRecords() throws Exception {
        // given
        doNothing().when(service).removeExercises(any());

        // when
        mvc.perform(
                        RestDocumentationRequestBuilders.delete("/api/v1/exercises/{exerciseId}", 1) // 경로 변수 전달
                                .header("Authorization", "Bearer Token")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent()) // 204 상태 코드 기대
                .andDo(document("delete-exercise",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("exerciseId").description("삭제할 운동의 Id") // 경로 변수 문서화
                        ),
                        requestHeaders(
                                headerWithName("Authorization").description("로그인 후 받은 Bearer 토큰(accessToken)\n Bearer {Authorization Code} 형식으로 요청")
                        )
                ));
    }

}