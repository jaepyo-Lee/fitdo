package com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity;

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
    private Long id;

    @Getter
    private String categoryName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserJpaEntity user;

    @Builder
    public CategoryJpaEntity(String categoryName, UserJpaEntity user) {
        this.categoryName = categoryName;
        this.user = user;
    }
}
