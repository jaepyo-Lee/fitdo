
package com.jaejoo.fitdobatch.batch.mapping;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BodyPart {
    BACK("등"),
    CHEST("가슴"),
    SHOULDER("어깨"),
    LEG("하체"),
    FULL_BODY("전신"),
    ABS("복근"),
    ARM("팔"),
    HIP("엉덩이");
    private String kr;
}