package com.jaejoo.fitdo.global.batch.mapping;

import lombok.Data;
import lombok.Getter;

@Data
public class UserScoreRow {
    private Long userId;
    private Double score;

    public UserScoreRow(Long userId, Double score) {
        this.userId = userId;
        this.score = score;
    }
}
