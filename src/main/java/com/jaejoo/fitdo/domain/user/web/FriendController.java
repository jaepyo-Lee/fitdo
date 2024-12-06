package com.jaejoo.fitdo.domain.user.web;

import com.jaejoo.fitdo.domain.auth.service.domain.CustomUserDetail;
import com.jaejoo.fitdo.domain.user.service.application.FriendService;
import com.jaejoo.fitdo.domain.user.service.application.req.FriendSimpleInfo;
import com.jaejoo.fitdo.domain.user.service.application.res.FriendDetailInfo;
import com.jaejoo.fitdo.domain.user.web.res.FriendDetailInfoResponse;
import com.jaejoo.fitdo.domain.user.web.res.FriendSimpleInfosResponse;
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
        List<FriendSimpleInfo> friendSimpleInfos = friendService.readFriendsInfos(user.userId());
        List<FriendSimpleInfosResponse> response = ToResponseMapper.INSTANCE.toFriendSimpleInfosResponse(friendSimpleInfos);
        return new SuccessResponse<>(response);
    }

    @GetMapping("/api/v1/friends/{friendId}")
    public SuccessResponse<FriendDetailInfoResponse> readFriendInfo(@PathVariable("friendId") Long friendId) {
        FriendDetailInfo friendDetailInfo = friendService.readFriendDetailInfo(friendId);
        FriendDetailInfoResponse response = ToResponseMapper.INSTANCE.toFriendDetailInfoResponse(friendDetailInfo);
        return new SuccessResponse<>(response);
    }

}
