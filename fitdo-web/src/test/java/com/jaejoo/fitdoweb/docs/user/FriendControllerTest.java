package com.jaejoo.fitdoweb.docs.user;

import com.jaejoo.fitdocore.user.FriendService;
import com.jaejoo.fitdocore.user.req.FriendSimpleInfo;
import com.jaejoo.fitdocore.user.res.ExerciseInfoInRoutine;
import com.jaejoo.fitdocore.user.res.FriendDetailInfo;
import com.jaejoo.fitdocore.user.res.UserRoutineInfo;
import com.jaejoo.fitdoweb.docs.RestDocsSupport;
import com.jaejoo.fitdoweb.user.web.FriendController;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FriendControllerTest extends RestDocsSupport {

    @Mock
    private FriendService service;

    @Override
    protected Object initController() {
        return new FriendController(service);
    }

    @Test
    void 친구_구체정보_조회() throws Exception {
        // given
        FriendDetailInfo response = new FriendDetailInfo(1L, "jaepyo", 172, 70,
                List.of(
                        new UserRoutineInfo("등죽이기", List.of(
                                new ExerciseInfoInRoutine("등", "데드리프트"),
                                new ExerciseInfoInRoutine("등", "시티드로우")))));
        // when
        when(service.readFriendDetailInfo(anyLong())).thenReturn(response);

        mvc.perform(
                        RestDocumentationRequestBuilders.get("/api/v1/friends/{friendId}", 1L)
                                .header("Authorization", "Bearer Token")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("read-detail-friends",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName("Authorization").description("로그인후 받은 Bearer 토큰(accessToken)\n Bearer {Authorization Code}형식으로 요청")
                                ),
                                pathParameters(parameterWithName("friendId").description("친구목록조회시 얻은 사용자ID(userId)")),
                                responseFields(
                                        beneathPath("result").withSubsectionId("result"),
                                        fieldWithPath("userId").type(JsonFieldType.NUMBER).description("친구의 userId"),
                                        fieldWithPath("userName").type(JsonFieldType.STRING).description("친구의 닉네임"),
                                        fieldWithPath("weight").type(JsonFieldType.NUMBER).description("친구의 몸무게"),
                                        fieldWithPath("height").type(JsonFieldType.NUMBER).description("친구의 키"),
                                        fieldWithPath("routines").type(JsonFieldType.ARRAY).description("친구의 운동루틴배열"),
                                        fieldWithPath("routines[].name").type(JsonFieldType.STRING).description("친구의 루틴명"),
                                        fieldWithPath("routines[].exercises").type(JsonFieldType.ARRAY).description("루틴에 속한 운동배열"),
                                        fieldWithPath("routines[].exercises[].bodyPart").type(JsonFieldType.STRING).description("루틴에 속한 운동부위"),
                                        fieldWithPath("routines[].exercises[].exerciseName").type(JsonFieldType.STRING).description("루틴에 속한 운동명")
                                )
                        )
                );
    }

    @Test
    void readSimpleFriends() throws Exception {
        // given
        List<FriendSimpleInfo> friendSimpleInfos1 = new ArrayList<>();
        FriendSimpleInfo joomi = FriendSimpleInfo.of(1L, "joomi", "BRONZE");
        FriendSimpleInfo jaepyo = FriendSimpleInfo.of(2L, "jaepyo", "DIAMOND");
        // when
        when(service.readFriendsInfos(any())).thenReturn(List.of(jaepyo, joomi));

        mvc.perform(
                        RestDocumentationRequestBuilders.get("/api/v1/friends")
                                .header("Authorization", "Bearer Token")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("read-simple-friends",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName("Authorization").description("로그인후 받은 Bearer 토큰(accessToken)\n Bearer {Authorization Code}형식으로 요청")
                                ), responseFields(
                                        beneathPath("result").withSubsectionId("result"),
                                        fieldWithPath("userId").type(JsonFieldType.NUMBER).description("친구의 userId"),
                                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("친구의 닉네임"),
                                        fieldWithPath("tier").type(JsonFieldType.STRING).description("친구의 운동계급")
                                )
                        )
                );
    }

}