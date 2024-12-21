package com.jaejoo.fitdomysql.domain.user.repository.jpa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QScoreJpaEntity is a Querydsl query type for ScoreJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QScoreJpaEntity extends EntityPathBase<ScoreJpaEntity> {

    private static final long serialVersionUID = 132843932L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QScoreJpaEntity scoreJpaEntity = new QScoreJpaEntity("scoreJpaEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Double> score = createNumber("score", Double.class);

    public final QUserJpaEntity user;

    public QScoreJpaEntity(String variable) {
        this(ScoreJpaEntity.class, forVariable(variable), INITS);
    }

    public QScoreJpaEntity(Path<? extends ScoreJpaEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QScoreJpaEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QScoreJpaEntity(PathMetadata metadata, PathInits inits) {
        this(ScoreJpaEntity.class, metadata, inits);
    }

    public QScoreJpaEntity(Class<? extends ScoreJpaEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUserJpaEntity(forProperty("user")) : null;
    }

}

