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

    public static final QAnimalBoardImage animalBoardImage = new QAnimalBoardImage("animalBoardImage");

    public final StringPath aBoardImage = createString("aBoardImage");

    public final NumberPath<Integer> aImageCode = createNumber("aImageCode", Integer.class);

    public final QAnimalBoard animalBoard;

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
        this.animalBoard = inits.isInitialized("animalBoard") ? new QAnimalBoard(forProperty("animalBoard"), inits.get("animalBoard")) : null;
    }

}

