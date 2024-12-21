package com.jaejoo.fitdomysql.domain.user.repository.jpa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserJpaEntity is a Querydsl query type for UserJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserJpaEntity extends EntityPathBase<UserJpaEntity> {

    private static final long serialVersionUID = 976440739L;

    public static final QUserJpaEntity userJpaEntity = new QUserJpaEntity("userJpaEntity");

    public final StringPath authId = createString("authId");

    public final EnumPath<com.jaejoo.fitdomysql.domain.auth.enumerate.AuthType> authType = createEnum("authType", com.jaejoo.fitdomysql.domain.auth.enumerate.AuthType.class);

    public final NumberPath<Integer> height = createNumber("height", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath newFlag = createBoolean("newFlag");

    public final StringPath nickname = createString("nickname");

    public final EnumPath<com.jaejoo.fitdomysql.domain.user.core.GrantRole> role = createEnum("role", com.jaejoo.fitdomysql.domain.user.core.GrantRole.class);

    public final StringPath username = createString("username");

    public final NumberPath<Integer> weight = createNumber("weight", Integer.class);

    public QUserJpaEntity(String variable) {
        super(UserJpaEntity.class, forVariable(variable));
    }

    public QUserJpaEntity(Path<? extends UserJpaEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserJpaEntity(PathMetadata metadata) {
        super(UserJpaEntity.class, metadata);
    }

}

