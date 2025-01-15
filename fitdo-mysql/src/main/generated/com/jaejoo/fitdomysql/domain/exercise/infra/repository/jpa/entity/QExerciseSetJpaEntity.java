package com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QExerciseSetJpaEntity is a Querydsl query type for ExerciseSetJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExerciseSetJpaEntity extends EntityPathBase<ExerciseSetJpaEntity> {

    private static final long serialVersionUID = -839993675L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QExerciseSetJpaEntity exerciseSetJpaEntity = new QExerciseSetJpaEntity("exerciseSetJpaEntity");

    public final QDailyExerciseJpaEntity dailyExercise;

    public final BooleanPath done = createBoolean("done");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> number = createNumber("number", Integer.class);

    public final NumberPath<Integer> volume = createNumber("volume", Integer.class);

    public final NumberPath<Integer> weight = createNumber("weight", Integer.class);

    public QExerciseSetJpaEntity(String variable) {
        this(ExerciseSetJpaEntity.class, forVariable(variable), INITS);
    }

    public QExerciseSetJpaEntity(Path<? extends ExerciseSetJpaEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QExerciseSetJpaEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QExerciseSetJpaEntity(PathMetadata metadata, PathInits inits) {
        this(ExerciseSetJpaEntity.class, metadata, inits);
    }

    public QExerciseSetJpaEntity(Class<? extends ExerciseSetJpaEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.dailyExercise = inits.isInitialized("dailyExercise") ? new QDailyExerciseJpaEntity(forProperty("dailyExercise"), inits.get("dailyExercise")) : null;
    }

}

