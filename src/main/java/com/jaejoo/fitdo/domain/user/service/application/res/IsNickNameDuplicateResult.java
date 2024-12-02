package com.jaejoo.fitdo.domain.user.service.application.res;

import lombok.Getter;

@Getter
public class IsNickNameDuplicateResult {
    private boolean isDuplicateNickname;

    public IsNickNameDuplicateResult(boolean isDuplicateNickname) {
        this.isDuplicateNickname = isDuplicateNickname;
    }
}
