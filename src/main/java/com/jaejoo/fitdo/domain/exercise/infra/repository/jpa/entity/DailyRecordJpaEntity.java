package com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity;

import com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity.UserJpaEntity;
import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * 사용자의 매일 운동기록
*/
@Entity
public class DailyRecordJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserJpaEntity user;

    public DailyRecordJpaEntity() {
    }

    public DailyRecordJpaEntity(LocalDate date, UserJpaEntity user) {
        this.date = date;
        this.user = user;
    }
}
