package com.jaejoo.fitdo.domain.exercise.service.domain;

import com.jaejoo.fitdo.domain.exercise.core.BodyPart;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.CategoryJpaEntity;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity.UserJpaEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryInitializer {
    /*private final List<BodyPart> bodyParts = new ArrayList<>(List.of(BodyPart.values()));

    *//*public List<CategoryJpaEntity> init(UserJpaEntity saveUser) {
        List<CategoryJpaEntity> categories = new ArrayList<>();
        for (BodyPart bodyPart : bodyParts) {
            CategoryJpaEntity category = CategoryJpaEntity.builder().part(bodyPart).user(saveUser).build();
            categories.add(category);
        }
        return categories;
    }*/
}
