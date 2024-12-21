package com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity;

import com.jaejoo.fitdomysql.domain.exercise.core.BodyPart;
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
