package com.jaejoo.fitdoweb.user.web.res;

import com.jaejoo.fitdocore.user.res.UserRoutineInfo;
import lombok.Data;

import java.util.List;
@Data
public class FriendDetailInfoResponse {
    private Long userId;
    private String userName;
    private Integer weight;
    private Integer height;
    private List<UserRoutineInfoDto> routines;
}
