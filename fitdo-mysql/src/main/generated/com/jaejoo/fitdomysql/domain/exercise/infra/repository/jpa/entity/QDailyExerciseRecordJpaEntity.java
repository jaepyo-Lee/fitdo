package com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDailyExerciseRecordJpaEntity is a Querydsl query type for DailyExerciseRecordJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDailyExerciseRecordJpaEntity extends EntityPathBase<DailyExerciseRecordJpaEntity> {

    private static final long serialVersionUID = -1350289059L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDailyExerciseRecordJpaEntity dailyExerciseRecordJpaEntity = new QDailyExerciseRecordJpaEntity("dailyExerciseRecordJpaEntity");

    public final QDailyRecordJpaEntity dailyRecord;

    public final QExerciseJpaEntity exercise;

    public final NumberPath<Integer> exerciseSet = createNumber("exerciseSet", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isProgress = createBoolean("isProgress");

    public final NumberPath<Integer> volume = createNumber("volume", Integer.class);

    public final NumberPath<Integer> weight = createNumber("weight", Integer.class);

    public QDailyExerciseRecordJpaEntity(String variable) {
        this(DailyExerciseRecordJpaEntity.class, forVariable(variable), INITS);
    }

    public QDailyExerciseRecordJpaEntity(Path<? extends DailyExerciseRecordJpaEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDailyExerciseRecordJpaEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDailyExerciseRecordJpaEntity(PathMetadata metadata, PathInits inits) {
        this(DailyExerciseRecordJpaEntity.class, metadata, inits);
    }

    public QDailyExerciseRecordJpaEntity(Class<? extends DailyExerciseRecordJpaEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.dailyRecord = inits.isInitialized("dailyRecord") ? new QDailyRecordJpaEntity(forProperty("dailyRecord"), inits.get("dailyRecord")) : null;
        this.exercise = inits.isInitialized("exercise") ? new QExerciseJpaEntity(forProperty("exercise"), inits.get("exercise")) : null;
    }

}

