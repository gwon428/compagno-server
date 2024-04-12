package com.project.compagnoserver.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAnimalBoard is a Querydsl query type for AnimalBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAnimalBoard extends EntityPathBase<AnimalBoard> {

    private static final long serialVersionUID = 383500179L;

    public static final QAnimalBoard animalBoard = new QAnimalBoard("animalBoard");

    public final NumberPath<Integer> animalBoardCode = createNumber("animalBoardCode", Integer.class);

    public final StringPath animalBoardContent = createString("animalBoardContent");

    public final DateTimePath<java.util.Date> animalBoardDate = createDateTime("animalBoardDate", java.util.Date.class);

    public final StringPath animalBoardTitle = createString("animalBoardTitle");

    public final NumberPath<Integer> animalBoardView = createNumber("animalBoardView", Integer.class);

    public final StringPath animalMainImage = createString("animalMainImage");

    public final ListPath<AnimalBoardImage, QAnimalBoardImage> images = this.<AnimalBoardImage, QAnimalBoardImage>createList("images", AnimalBoardImage.class, QAnimalBoardImage.class, PathInits.DIRECT2);

    public QAnimalBoard(String variable) {
        super(AnimalBoard.class, forVariable(variable));
    }

    public QAnimalBoard(Path<? extends AnimalBoard> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAnimalBoard(PathMetadata metadata) {
        super(AnimalBoard.class, metadata);
    }

}

