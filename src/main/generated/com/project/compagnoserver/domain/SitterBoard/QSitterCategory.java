package com.project.compagnoserver.domain.SitterBoard;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSitterCategory is a Querydsl query type for SitterCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSitterCategory extends EntityPathBase<SitterCategory> {

    private static final long serialVersionUID = -1224515709L;

    public static final QSitterCategory sitterCategory = new QSitterCategory("sitterCategory");

    public final NumberPath<Integer> sitterCategoryCode = createNumber("sitterCategoryCode", Integer.class);

    public final StringPath sitterCategoryType = createString("sitterCategoryType");

    public QSitterCategory(String variable) {
        super(SitterCategory.class, forVariable(variable));
    }

    public QSitterCategory(Path<? extends SitterCategory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSitterCategory(PathMetadata metadata) {
        super(SitterCategory.class, metadata);
    }

}

