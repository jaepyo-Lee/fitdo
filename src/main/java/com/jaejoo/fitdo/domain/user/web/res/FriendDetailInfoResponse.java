package com.jaejoo.fitdo.domain.user.web.res;

import com.jaejoo.fitdo.domain.user.service.application.res.UserRoutineInfo;
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
