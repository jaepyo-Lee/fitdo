package com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QExerciseRoutineJpaEntity is a Querydsl query type for ExerciseRoutineJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExerciseRoutineJpaEntity extends EntityPathBase<ExerciseRoutineJpaEntity> {

    private static final long serialVersionUID = 1274579987L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QExerciseRoutineJpaEntity exerciseRoutineJpaEntity = new QExerciseRoutineJpaEntity("exerciseRoutineJpaEntity");

    public final QExerciseJpaEntity exercise;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QRoutineJpaEntity routine;

    public QExerciseRoutineJpaEntity(String variable) {
        this(ExerciseRoutineJpaEntity.class, forVariable(variable), INITS);
    }

    public QExerciseRoutineJpaEntity(Path<? extends ExerciseRoutineJpaEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QExerciseRoutineJpaEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QExerciseRoutineJpaEntity(PathMetadata metadata, PathInits inits) {
        this(ExerciseRoutineJpaEntity.class, metadata, inits);
    }

    public QExerciseRoutineJpaEntity(Class<? extends ExerciseRoutineJpaEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.exercise = inits.isInitialized("exercise") ? new QExerciseJpaEntity(forProperty("exercise"), inits.get("exercise")) : null;
        this.routine = inits.isInitialized("routine") ? new QRoutineJpaEntity(forProperty("routine"), inits.get("routine")) : null;
    }

}

