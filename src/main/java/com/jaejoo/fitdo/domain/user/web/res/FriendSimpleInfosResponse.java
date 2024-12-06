package com.jaejoo.fitdo.domain.user.web.res;

import com.jaejoo.fitdo.domain.user.core.Tier;
import lombok.Data;

@Data
public class FriendSimpleInfosResponse {
    private Long userId;
    private String nickname;
    private Tier tier;
}
