package com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCategoryJpaEntity is a Querydsl query type for CategoryJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCategoryJpaEntity extends EntityPathBase<CategoryJpaEntity> {

    private static final long serialVersionUID = 1970194559L;

    public static final QCategoryJpaEntity categoryJpaEntity = new QCategoryJpaEntity("categoryJpaEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.jaejoo.fitdomysql.domain.exercise.core.BodyPart> part = createEnum("part", com.jaejoo.fitdomysql.domain.exercise.core.BodyPart.class);

    public QCategoryJpaEntity(String variable) {
        super(CategoryJpaEntity.class, forVariable(variable));
    }

    public QCategoryJpaEntity(Path<? extends CategoryJpaEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCategoryJpaEntity(PathMetadata metadata) {
        super(CategoryJpaEntity.class, metadata);
    }

}

