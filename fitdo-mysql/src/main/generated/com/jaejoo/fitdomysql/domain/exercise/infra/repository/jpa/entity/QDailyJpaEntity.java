package com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDailyJpaEntity is a Querydsl query type for DailyJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDailyJpaEntity extends EntityPathBase<DailyJpaEntity> {

    private static final long serialVersionUID = -923820634L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDailyJpaEntity dailyJpaEntity = new QDailyJpaEntity("dailyJpaEntity");

    public final DatePath<java.time.LocalDate> date = createDate("date", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.QUserJpaEntity user;

    public QDailyJpaEntity(String variable) {
        this(DailyJpaEntity.class, forVariable(variable), INITS);
    }

    public QDailyJpaEntity(Path<? extends DailyJpaEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDailyJpaEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDailyJpaEntity(PathMetadata metadata, PathInits inits) {
        this(DailyJpaEntity.class, metadata, inits);
    }

    public QDailyJpaEntity(Class<? extends DailyJpaEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.QUserJpaEntity(forProperty("user")) : null;
    }

}

