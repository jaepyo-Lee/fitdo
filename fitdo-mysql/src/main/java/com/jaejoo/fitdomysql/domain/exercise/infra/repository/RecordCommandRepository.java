package com.jaejoo.fitdomysql.domain.exercise.infra.repository;

import java.time.LocalDate;

public interface RecordCommandRepository {
    void deleteDateRecordOf(Long userId, LocalDate deleteDate);
}
