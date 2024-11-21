package com.jaejoo.fitdo.docs.exercise;

import com.jaejoo.fitdo.docs.RestDocsSupport;
import com.jaejoo.fitdo.domain.exercise.service.application.ExerciseService;
import com.jaejoo.fitdo.domain.exercise.web.ExerciseController;
import com.jaejoo.fitdo.domain.exercise.web.req.ExerciseCreateRequest;
import com.jaejoo.fitdo.global.format.success.SuccessResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
                        post("/api/v1/exercise")
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
}