package com.jaejoo.fitdoweb.user.web;

import com.jaejoo.fitdocore.user.FriendService;
import com.jaejoo.fitdocore.user.req.FriendSimpleInfo;
import com.jaejoo.fitdocore.user.res.FriendDetailInfo;
import com.jaejoo.fitdoweb.common.format.success.SuccessResponse;
import com.jaejoo.fitdoweb.common.mapper.ToResponseMapper;
import com.jaejoo.fitdoweb.security.CustomUserDetail;
import com.jaejoo.fitdoweb.user.web.res.FriendDetailInfoResponse;
import com.jaejoo.fitdoweb.user.web.res.FriendSimpleInfosResponse;
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
