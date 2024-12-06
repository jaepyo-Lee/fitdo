package com.jaejoo.fitdo.domain.user.web.req;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class FriendApplyRequest{
    private Long receiverId;

    public FriendApplyRequest(Long receiverId) {
        this.receiverId = receiverId;
    }
}
