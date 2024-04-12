package com.project.compagnoserver.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAnimalBoardImage is a Querydsl query type for AnimalBoardImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAnimalBoardImage extends EntityPathBase<AnimalBoardImage> {

    private static final long serialVersionUID = -424428152L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAnimalBoardImage animalBoardImage1 = new QAnimalBoardImage("animalBoardImage1");

    public final QAnimalBoard animalBoard;

    public final StringPath animalBoardImage = createString("animalBoardImage");

    public final NumberPath<Integer> animalImageCode = createNumber("animalImageCode", Integer.class);

    public QAnimalBoardImage(String variable) {
        this(AnimalBoardImage.class, forVariable(variable), INITS);
    }

    public QAnimalBoardImage(Path<? extends AnimalBoardImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAnimalBoardImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAnimalBoardImage(PathMetadata metadata, PathInits inits) {
        this(AnimalBoardImage.class, metadata, inits);
    }

    public QAnimalBoardImage(Class<? extends AnimalBoardImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.animalBoard = inits.isInitialized("animalBoard") ? new QAnimalBoard(forProperty("animalBoard")) : null;
    }

}

