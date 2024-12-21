package com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QExerciseJpaEntity is a Querydsl query type for ExerciseJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExerciseJpaEntity extends EntityPathBase<ExerciseJpaEntity> {

    private static final long serialVersionUID = 641161445L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QExerciseJpaEntity exerciseJpaEntity = new QExerciseJpaEntity("exerciseJpaEntity");

    public final QCategoryJpaEntity category;

    public final EnumPath<com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.enumerate.DeleteDelimiter> deleteDelimiter = createEnum("deleteDelimiter", com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.enumerate.DeleteDelimiter.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.QUserJpaEntity user;

    public QExerciseJpaEntity(String variable) {
        this(ExerciseJpaEntity.class, forVariable(variable), INITS);
    }

    public QExerciseJpaEntity(Path<? extends ExerciseJpaEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QExerciseJpaEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QExerciseJpaEntity(PathMetadata metadata, PathInits inits) {
        this(ExerciseJpaEntity.class, metadata, inits);
    }

    public QExerciseJpaEntity(Class<? extends ExerciseJpaEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QCategoryJpaEntity(forProperty("category")) : null;
        this.user = inits.isInitialized("user") ? new com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.QUserJpaEntity(forProperty("user")) : null;
    }

}

