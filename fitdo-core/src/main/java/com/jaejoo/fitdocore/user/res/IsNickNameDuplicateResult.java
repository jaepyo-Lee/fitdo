package com.jaejoo.fitdocore.user.res;

import lombok.Getter;

@Getter
public class IsNickNameDuplicateResult {
    private boolean isDuplicateNickname;

    public IsNickNameDuplicateResult(boolean isDuplicateNickname) {
        this.isDuplicateNickname = isDuplicateNickname;
    }
}
