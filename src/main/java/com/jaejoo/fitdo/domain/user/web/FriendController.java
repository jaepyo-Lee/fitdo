package com.jaejoo.fitdo.domain.user.web;

import com.jaejoo.fitdo.domain.auth.service.domain.CustomUserDetail;
import com.jaejoo.fitdo.domain.user.service.application.FriendService;
import com.jaejoo.fitdo.domain.user.service.application.req.FriendApplyCommand;
import com.jaejoo.fitdo.domain.user.service.application.req.FriendApplyConfirmCommand;
import com.jaejoo.fitdo.domain.user.service.application.req.FriendSimpleInfo;
import com.jaejoo.fitdo.domain.user.service.application.req.ReadApplierInfo;
import com.jaejoo.fitdo.domain.user.web.req.FriendApplyConfirmRequest;
import com.jaejoo.fitdo.domain.user.web.req.FriendApplyRequest;
import com.jaejoo.fitdo.domain.user.web.res.FriendSimpleInfosResponse;
import com.jaejoo.fitdo.domain.user.web.res.ReadApplierInfoResponse;
import com.jaejoo.fitdo.global.format.success.SuccessResponse;
import com.jaejoo.fitdo.global.mapper.ToResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class FriendController {
    private final FriendService friendService;

    @GetMapping("/api/v1/friends")
    public SuccessResponse<List<FriendSimpleInfosResponse>> readFriends(@AuthenticationPrincipal CustomUserDetail user) {
        List<FriendSimpleInfo> friendSimpleInfos = friendService.readFriendInfos(user.userId());
        List<FriendSimpleInfosResponse> response = ToResponseMapper.INSTANCE.toFriendSimpleInfosResponse(friendSimpleInfos);
        return new SuccessResponse<>(response);
    }

}
