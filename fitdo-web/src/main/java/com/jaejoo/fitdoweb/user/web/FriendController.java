package com.jaejoo.fitdoweb.user.web;

import com.jaejoo.fitdocore.user.FriendService;
import com.jaejoo.fitdocore.user.FriendShareService;
import com.jaejoo.fitdocore.user.req.FriendApplyCommand;
import com.jaejoo.fitdocore.user.req.FriendSimpleInfo;
import com.jaejoo.fitdocore.user.res.FriendDetailInfo;
import com.jaejoo.fitdoweb.common.format.success.SuccessResponse;
import com.jaejoo.fitdoweb.common.mapper.ReadFriendDetailsMapper;
import com.jaejoo.fitdoweb.common.mapper.ReadFriendsMapper;
import com.jaejoo.fitdoweb.security.CustomUserDetail;
import com.jaejoo.fitdoweb.user.web.res.DeepLinkResponse;
import com.jaejoo.fitdoweb.user.web.res.FriendDetailInfoResponse;
import com.jaejoo.fitdoweb.user.web.res.FriendSimpleInfosResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class FriendController {
    private final FriendService friendService;
    private final FriendShareService friendShareService;

    @GetMapping("/api/v1/friends")
    public SuccessResponse<List<FriendSimpleInfosResponse>> readFriends(@AuthenticationPrincipal CustomUserDetail user) {
        List<FriendSimpleInfo> friendSimpleInfos = friendService.readFriendsInfos(user.userId());
        List<FriendSimpleInfosResponse> response = ReadFriendsMapper.INSTANCE.toFriendSimpleInfosResponse(friendSimpleInfos);
        return new SuccessResponse<>(response);
    }

    @GetMapping("/api/v1/friends/{friendId}")
    public SuccessResponse<FriendDetailInfoResponse> readFriendDetails(@PathVariable("friendId") Long friendId) {
        FriendDetailInfo friendDetailInfo = friendService.readFriendDetailInfo(friendId);
        FriendDetailInfoResponse response = ReadFriendDetailsMapper.INSTANCE.toFriendDetailInfoResponse(friendDetailInfo);
        return new SuccessResponse<>(response);
    }

    @PostMapping("/api/v1/friends/{DeepLinkUserId}")
    public SuccessResponse registerFriend(@PathVariable("DeepLinkUserId") String friendId,
                                          @AuthenticationPrincipal CustomUserDetail user) throws Exception {
        friendShareService.applyFriend(new FriendApplyCommand(user.userId(), friendId));
        return SuccessResponse.ok();
    }

    @GetMapping("/api/v1/user/link")
    public SuccessResponse<DeepLinkResponse> generateFriendLink(@AuthenticationPrincipal CustomUserDetail user) throws Exception {
        String deepLink = friendShareService.generateDeepLink(user.userId());
        return new SuccessResponse<>(new DeepLinkResponse(deepLink));
    }

}
