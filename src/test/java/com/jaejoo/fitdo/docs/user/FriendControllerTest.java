package com.jaejoo.fitdo.docs.user;

import com.jaejoo.fitdo.docs.RestDocsSupport;
import com.jaejoo.fitdo.domain.user.core.Tier;
import com.jaejoo.fitdo.domain.user.service.application.FriendService;
import com.jaejoo.fitdo.domain.user.service.application.req.FriendSimpleInfo;
import com.jaejoo.fitdo.domain.user.service.application.req.ReadApplierInfo;
import com.jaejoo.fitdo.domain.user.web.FriendController;
import com.jaejoo.fitdo.domain.user.web.req.FriendApplyConfirmRequest;
import com.jaejoo.fitdo.domain.user.web.req.FriendApplyRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
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
    void readSimpleFriends() throws Exception {
        // given
        List<FriendSimpleInfo> friendSimpleInfos1 = new ArrayList<>();
        FriendSimpleInfo joomi = new FriendSimpleInfo(1L, "joomi", Tier.BRONZE);
        FriendSimpleInfo jaepyo = new FriendSimpleInfo(2L, "jaepyo", Tier.DIAMOND);
        // when
        when(service.readFriendInfos(any())).thenReturn(List.of(jaepyo, joomi));

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