package com.jaejoo.fitdocore.user.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FriendDetailInfo {
    private Long userId;
    private String userName;
    private Integer weight;
    private Integer height;
    private List<UserRoutineInfo> routines;
}
