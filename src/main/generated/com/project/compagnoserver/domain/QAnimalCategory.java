package com.project.compagnoserver.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAnimalCategory is a Querydsl query type for AnimalCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAnimalCategory extends EntityPathBase<AnimalCategory> {

    private static final long serialVersionUID = -1319333583L;

    public static final QAnimalCategory animalCategory = new QAnimalCategory("animalCategory");

    public final NumberPath<Integer> animalCategoryCode = createNumber("animalCategoryCode", Integer.class);

    public final StringPath animalType = createString("animalType");

    public QAnimalCategory(String variable) {
        super(AnimalCategory.class, forVariable(variable));
    }

    public QAnimalCategory(Path<? extends AnimalCategory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAnimalCategory(PathMetadata metadata) {
        super(AnimalCategory.class, metadata);
    }

}

