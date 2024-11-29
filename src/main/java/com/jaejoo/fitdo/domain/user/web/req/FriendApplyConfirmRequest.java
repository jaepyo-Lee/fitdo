package com.jaejoo.fitdo.domain.user.web.req;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class FriendApplyConfirmRequest {
    private Long applyUserId;
    private Boolean isAccept;

    public FriendApplyConfirmRequest(Long applyUserId, Boolean isAccept) {
        this.applyUserId = applyUserId;
        this.isAccept = isAccept;
    }
}
