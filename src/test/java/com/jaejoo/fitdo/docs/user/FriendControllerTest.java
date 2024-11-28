package com.jaejoo.fitdo.docs.user;

import com.jaejoo.fitdo.docs.RestDocsSupport;
import com.jaejoo.fitdo.domain.user.service.application.FriendService;
import com.jaejoo.fitdo.domain.user.web.FriendController;
import com.jaejoo.fitdo.domain.user.web.req.FriendApplyConfirmRequest;
import com.jaejoo.fitdo.domain.user.web.req.FriendApplyRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
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
    void applyFriend() throws Exception {
        // given
        FriendApplyRequest request = new FriendApplyRequest(1L);
        // mock the service method
        doNothing().when(service).applyFriend(any());

        // when
        mvc.perform(
                        RestDocumentationRequestBuilders.post("/api/v1/friends")
                                .header("Authorization", "Bearer Token")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("apply-friend",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName("Authorization").description("로그인후 받은 Bearer 토큰(accessToken)\n Bearer {Authorization Code}형식으로 요청")
                                ),
                                requestFields(
                                        fieldWithPath("receiverId").type(JsonFieldType.NUMBER).description("친구신청을 하고싶은 사용자의 ID")
                                )
                        )
                );
    }

    @Test
    void approveFriend() throws Exception {
        // given
        FriendApplyConfirmRequest request = new FriendApplyConfirmRequest(1L, true);
        // when
        doNothing().when(service).manageFriendApply(any());

        mvc.perform(
                        RestDocumentationRequestBuilders.post("/api/v1/friends/confirm")
                                .header("Authorization", "Bearer Token")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("confirm-friend-apply",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName("Authorization").description("로그인후 받은 Bearer 토큰(accessToken)\n Bearer {Authorization Code}형식으로 요청")
                                ), requestFields(
                                        fieldWithPath("applyUserId").type(JsonFieldType.NUMBER).description("친구신청을 보낸 사용자의 id"),
                                        fieldWithPath("isAccept").type(JsonFieldType.BOOLEAN).description("친구신청 수락 여부, true시 수학, false시 거절")
                                )
                        )
                );
    }

}