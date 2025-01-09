package com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity;


import com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.UserJpaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
public class RoutineJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    private String name;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserJpaEntity user;

    public RoutineJpaEntity(String name, UserJpaEntity user) {
        this.name = name;
        this.user = user;
    }
}
