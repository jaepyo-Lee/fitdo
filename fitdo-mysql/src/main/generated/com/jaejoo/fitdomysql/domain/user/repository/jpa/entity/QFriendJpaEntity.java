package com.jaejoo.fitdomysql.domain.user.repository.jpa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFriendJpaEntity is a Querydsl query type for FriendJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFriendJpaEntity extends EntityPathBase<FriendJpaEntity> {

    private static final long serialVersionUID = 1219102768L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFriendJpaEntity friendJpaEntity = new QFriendJpaEntity("friendJpaEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QUserJpaEntity receiver;

    public final QUserJpaEntity sender;

    public QFriendJpaEntity(String variable) {
        this(FriendJpaEntity.class, forVariable(variable), INITS);
    }

    public QFriendJpaEntity(Path<? extends FriendJpaEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFriendJpaEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFriendJpaEntity(PathMetadata metadata, PathInits inits) {
        this(FriendJpaEntity.class, metadata, inits);
    }

    public QFriendJpaEntity(Class<? extends FriendJpaEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.receiver = inits.isInitialized("receiver") ? new QUserJpaEntity(forProperty("receiver")) : null;
        this.sender = inits.isInitialized("sender") ? new QUserJpaEntity(forProperty("sender")) : null;
    }

}

