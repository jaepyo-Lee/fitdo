package com.jaejoo.fitdoweb.user.web.res;

import lombok.Data;

@Data
public class FriendSimpleInfosResponse {
    private Long userId;
    private String nickname;
    //이정도의 중복은 허용하자,,!
    private String tier;
}
