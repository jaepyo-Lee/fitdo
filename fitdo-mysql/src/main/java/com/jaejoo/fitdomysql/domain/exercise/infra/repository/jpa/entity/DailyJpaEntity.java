package com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity;

import com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.UserJpaEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

/**
 * 사용자의 매일 운동기록
*/
@Entity
public class DailyJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "EXERCISE_DATE")
    @Getter
    private LocalDate date;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserJpaEntity user;

    public DailyJpaEntity() {
    }

    public DailyJpaEntity(LocalDate date, UserJpaEntity user) {
        this.date = date;
        this.user = user;
    }
}
