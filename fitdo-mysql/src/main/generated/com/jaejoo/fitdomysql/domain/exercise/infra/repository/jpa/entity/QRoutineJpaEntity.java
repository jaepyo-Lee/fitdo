package com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoutineJpaEntity is a Querydsl query type for RoutineJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoutineJpaEntity extends EntityPathBase<RoutineJpaEntity> {

    private static final long serialVersionUID = -1393858789L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoutineJpaEntity routineJpaEntity = new QRoutineJpaEntity("routineJpaEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.QUserJpaEntity user;

    public QRoutineJpaEntity(String variable) {
        this(RoutineJpaEntity.class, forVariable(variable), INITS);
    }

    public QRoutineJpaEntity(Path<? extends RoutineJpaEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoutineJpaEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoutineJpaEntity(PathMetadata metadata, PathInits inits) {
        this(RoutineJpaEntity.class, metadata, inits);
    }

    public QRoutineJpaEntity(Class<? extends RoutineJpaEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.QUserJpaEntity(forProperty("user")) : null;
    }

}

