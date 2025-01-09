package com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity;

import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.enumerate.DeleteDelimiter;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.UserJpaEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class ExerciseJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private DeleteDelimiter deleteDelimiter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserJpaEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryJpaEntity category;

    @Builder
    public ExerciseJpaEntity(UserJpaEntity user, Long id, String name, DeleteDelimiter deleteDelimiter, CategoryJpaEntity category) {
        this.id = id;
        this.name = name;
        this.deleteDelimiter = deleteDelimiter;
        this.category = category;
        this.user = user;
    }

    @Builder
    public ExerciseJpaEntity(UserJpaEntity user, String name, DeleteDelimiter deleteDelimiter, CategoryJpaEntity category) {
        this.name = name;
        this.deleteDelimiter = deleteDelimiter;
        this.category = category;
        this.user = user;
    }

    public ExerciseJpaEntity(String name, CategoryJpaEntity category, DeleteDelimiter deleteDelimiter) {
        this.name = name;
        this.deleteDelimiter = deleteDelimiter;
        this.category = category;
    }

    public static ExerciseJpaEntity create(UserJpaEntity user, String name, CategoryJpaEntity category) {
        return new ExerciseJpaEntity(user, name, DeleteDelimiter.IN_USER,category );
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        ExerciseJpaEntity obj1 = (ExerciseJpaEntity) obj;
        if (obj1.getId().equals(this.getId())) {
            return true;
        }
        return false;
    }

    public void delete() {
        this.deleteDelimiter = DeleteDelimiter.DELETE;
    }
}
