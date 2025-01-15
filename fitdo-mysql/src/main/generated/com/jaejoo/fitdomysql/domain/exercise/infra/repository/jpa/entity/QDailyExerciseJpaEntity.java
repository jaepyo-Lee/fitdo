package com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDailyExerciseJpaEntity is a Querydsl query type for DailyExerciseJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDailyExerciseJpaEntity extends EntityPathBase<DailyExerciseJpaEntity> {

    private static final long serialVersionUID = -929380690L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDailyExerciseJpaEntity dailyExerciseJpaEntity = new QDailyExerciseJpaEntity("dailyExerciseJpaEntity");

    public final QDailyJpaEntity daily;

    public final QExerciseJpaEntity exercise;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QDailyExerciseJpaEntity(String variable) {
        this(DailyExerciseJpaEntity.class, forVariable(variable), INITS);
    }

    public QDailyExerciseJpaEntity(Path<? extends DailyExerciseJpaEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDailyExerciseJpaEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDailyExerciseJpaEntity(PathMetadata metadata, PathInits inits) {
        this(DailyExerciseJpaEntity.class, metadata, inits);
    }

    public QDailyExerciseJpaEntity(Class<? extends DailyExerciseJpaEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.daily = inits.isInitialized("daily") ? new QDailyJpaEntity(forProperty("daily"), inits.get("daily")) : null;
        this.exercise = inits.isInitialized("exercise") ? new QExerciseJpaEntity(forProperty("exercise"), inits.get("exercise")) : null;
    }

}

