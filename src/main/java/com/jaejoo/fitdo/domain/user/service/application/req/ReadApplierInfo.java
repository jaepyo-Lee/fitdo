package com.jaejoo.fitdo.domain.user.service.application.req;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReadApplierInfo {
    private String name;
    private Long senderId;
    //티어까지 들어갈 예정
}
