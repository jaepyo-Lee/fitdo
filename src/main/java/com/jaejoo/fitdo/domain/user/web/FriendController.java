package com.jaejoo.fitdo.domain.user.web;

import com.jaejoo.fitdo.domain.auth.service.domain.CustomUserDetail;
import com.jaejoo.fitdo.domain.user.service.application.FriendService;
import com.jaejoo.fitdo.domain.user.service.application.req.FriendApplyCommand;
import com.jaejoo.fitdo.domain.user.service.application.req.FriendApplyConfirmCommand;
import com.jaejoo.fitdo.domain.user.service.application.req.ReadApplierInfo;
import com.jaejoo.fitdo.domain.user.web.req.FriendApplyConfirmRequest;
import com.jaejoo.fitdo.domain.user.web.req.FriendApplyRequest;
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

    @PostMapping("/api/v1/friends")
    public SuccessResponse applyFriend(@AuthenticationPrincipal CustomUserDetail sender,
                                       @RequestBody FriendApplyRequest request) {
        friendService.applyFriend(new FriendApplyCommand(request.getReceiverId(), sender.userId()));
        return SuccessResponse.ok();
    }

    @PostMapping("/api/v1/friends/confirm")
    public SuccessResponse classifyFriendApply(@AuthenticationPrincipal CustomUserDetail customUserDetail,
                                               @RequestBody FriendApplyConfirmRequest request) {
        friendService.manageFriendApply(new FriendApplyConfirmCommand(request.getApplyUserId(), customUserDetail.userId(), request.getIsAccept()));
        return SuccessResponse.ok();
    }

    @GetMapping("/api/v1/friends/apply-receive")
    public SuccessResponse<List<ReadApplierInfoResponse>> readReceivedFriendApply(@AuthenticationPrincipal CustomUserDetail customUserDetail) {
        List<ReadApplierInfo> readApplierInfos = friendService.readFriendApplies(customUserDetail.userId());
        List<ReadApplierInfoResponse> response = ToResponseMapper.INSTANCE.toReadApplierInfoResponse(readApplierInfos);
        return new SuccessResponse(response);
    }
}
