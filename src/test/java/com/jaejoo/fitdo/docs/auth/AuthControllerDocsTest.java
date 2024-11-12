package com.jaejoo.fitdo.docs.auth;

import com.jaejoo.fitdo.docs.RestDocsSupport;
import com.jaejoo.fitdo.domain.auth.service.application.AuthService;
import com.jaejoo.fitdo.domain.auth.service.application.req.AuthType;
import com.jaejoo.fitdo.domain.auth.service.application.res.LoginResult;
import com.jaejoo.fitdo.domain.auth.web.AuthController;
import com.jaejoo.fitdo.domain.auth.web.req.LoginRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class AuthControllerDocsTest extends RestDocsSupport {
    @Mock
    private AuthService authService;

    @Override
    protected Object initController() {
        return new AuthController(authService);
    }

    @DisplayName("OAuth회원가입 진행[신규회원]")
    @Test
    void loginNewUser() throws Exception {
        // given
        LoginRequest loginRequest = LoginRequest.builder().loginType(AuthType.KAKAO).build();
        boolean isNewFlag = true;
        String refreshToken = "refreshToken";
        String accessToken = "accessToken";

        LoginResult loginResult = new LoginResult(accessToken, refreshToken, isNewFlag);

        when(authService.login(any())).thenReturn(loginResult);

        // when
        System.out.println("=====Logic Start=====");
        mvc.perform(
                        post("/auth/login")
                                .header("Authorization", "Bearer Token")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(loginRequest))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("user-login",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName("Authorization").description("OAuth서버에서 받은 Authorization Code\n Bearer {Authorization Code}형식으로 요청")
                                ),

                                requestFields(
                                        fieldWithPath("loginType").type(JsonFieldType.STRING).description("로그인을 진행하는 플랫폼")),
                                responseFields(
                                        fieldWithPath("accessToken").type(JsonFieldType.STRING).description("서비스에 사용될 jwt토큰"),
                                        fieldWithPath("refreshToken").type(JsonFieldType.STRING).description("토큰 재발급에 사용될 refreshToken"),
                                        fieldWithPath("isNewFlag").type(JsonFieldType.BOOLEAN).description("신규회원여부"))
                        )
                );

        System.out.println("=====Logic End=====");
        // then
    }

    /*@DisplayName("OAuth회원가입 진행[기존회원]")
    @Test
    void loginOldUser() throws Exception {
        // given
        LoginRequest loginRequest = LoginRequest.builder().loginType(AuthType.KAKAO).build();
        boolean isNewFlag = false;
        String refreshToken = "refreshToken";
        String accessToken = "accessToken";

        LoginResult loginResult = new LoginResult(accessToken, refreshToken, isNewFlag);

        when(authService.login(any())).thenReturn(loginResult);

        // when
        System.out.println("=====Logic Start=====");
        mvc.perform(
                        post("/auth/login")
                                .header("Authorization", "Bearer Token")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(loginRequest))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("user-login",
                                requestHeaders(
                                        headerWithName("Authorization").description("OAuth서버에서 받은 Authorization Code\n Bearer {Authorization Code}형식으로 요청")
                                ),

                                requestFields(
                                        fieldWithPath("loginType").type(JsonFieldType.STRING).description("로그인을 진행하는 플랫폼")),
                                responseFields(
                                        fieldWithPath("accessToken").type(JsonFieldType.STRING).description("서비스에 사용될 jwt토큰"),
                                        fieldWithPath("refreshToken").type(JsonFieldType.STRING).description("토큰 재발급에 사용될 refreshToken"),
                                        fieldWithPath("isNewFlag").type(JsonFieldType.BOOLEAN).description("신규회원여부"))
                        )
                );

        System.out.println("=====Logic End=====");
        // then
    }*/
}