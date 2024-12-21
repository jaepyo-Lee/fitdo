package com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDailyRecordJpaEntity is a Querydsl query type for DailyRecordJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDailyRecordJpaEntity extends EntityPathBase<DailyRecordJpaEntity> {

    private static final long serialVersionUID = -714475947L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDailyRecordJpaEntity dailyRecordJpaEntity = new QDailyRecordJpaEntity("dailyRecordJpaEntity");

    public final DatePath<java.time.LocalDate> date = createDate("date", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.QUserJpaEntity user;

    public QDailyRecordJpaEntity(String variable) {
        this(DailyRecordJpaEntity.class, forVariable(variable), INITS);
    }

    public QDailyRecordJpaEntity(Path<? extends DailyRecordJpaEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDailyRecordJpaEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDailyRecordJpaEntity(PathMetadata metadata, PathInits inits) {
        this(DailyRecordJpaEntity.class, metadata, inits);
    }

    public QDailyRecordJpaEntity(Class<? extends DailyRecordJpaEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.QUserJpaEntity(forProperty("user")) : null;
    }

}

