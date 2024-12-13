package com.jaejoo.fitdobatch.batch.mapping;

import lombok.Data;

@Data
public class CalculateScoreRow {
    private Long userId;
    private Integer userWeight;
    private Integer userHeight;
    private Integer recordWeight;
    private Integer recordVolume;
    private Boolean isProgress;
    private BodyPart bodyPart;

    public CalculateScoreRow(Long userId, Integer userWeight, Integer userHeight, Integer recordWeight, Integer recordVolume, Boolean isProgress, BodyPart bodyPart) {
        this.userId = userId;
        this.userWeight = userWeight;
        this.userHeight = userHeight;
        this.recordWeight = recordWeight;
        this.recordVolume = recordVolume;
        this.isProgress = isProgress;
        this.bodyPart = bodyPart;
    }
}
