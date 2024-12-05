package com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity;

import com.jaejoo.fitdo.domain.exercise.core.BodyPart;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity.UserJpaEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class CategoryJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Enumerated(EnumType.STRING)
    private BodyPart part;

    @Builder
    public CategoryJpaEntity(BodyPart part) {
        this.part = part;
    }

    public String getPartName() {
        return part.getKr();
    }
}
