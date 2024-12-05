package com.jaejoo.fitdo.domain.user.web.res;

import lombok.Data;

@Data
public class IsNickNameDuplicateResponse {
    private boolean isNickNameDuplicate;

    public IsNickNameDuplicateResponse(boolean isNickNameDuplicate) {
        this.isNickNameDuplicate = isNickNameDuplicate;
    }
}
