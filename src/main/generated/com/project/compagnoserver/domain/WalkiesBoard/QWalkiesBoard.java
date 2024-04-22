package com.project.compagnoserver.domain.WalkiesBoard;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWalkiesBoard is a Querydsl query type for WalkiesBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWalkiesBoard extends EntityPathBase<WalkiesBoard> {

    private static final long serialVersionUID = -898153513L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWalkiesBoard walkiesBoard = new QWalkiesBoard("walkiesBoard");

    public final com.project.compagnoserver.domain.Animal.QAnimalCategory animalCategoryCode;

    public final ListPath<WalkiesBoardImage, QWalkiesBoardImage> images = this.<WalkiesBoardImage, QWalkiesBoardImage>createList("images", WalkiesBoardImage.class, QWalkiesBoardImage.class, PathInits.DIRECT2);

    public final com.project.compagnoserver.domain.user.QUser user;

    public final NumberPath<Integer> walkiesBoardCode = createNumber("walkiesBoardCode", Integer.class);

    public final StringPath walkiesBoardContent = createString("walkiesBoardContent");

    public final DateTimePath<java.util.Date> walkiesBoardRegiDate = createDateTime("walkiesBoardRegiDate", java.util.Date.class);

    public final StringPath walkiesBoardStatus = createString("walkiesBoardStatus");

    public final StringPath walkiesBoardTitle = createString("walkiesBoardTitle");

    public final DateTimePath<java.util.Date> walkiesBoardUpdateDate = createDateTime("walkiesBoardUpdateDate", java.util.Date.class);

    public final NumberPath<Integer> walkiesBoardViewCount = createNumber("walkiesBoardViewCount", Integer.class);

    public final StringPath walkiesLocation = createString("walkiesLocation");

    public QWalkiesBoard(String variable) {
        this(WalkiesBoard.class, forVariable(variable), INITS);
    }

    public QWalkiesBoard(Path<? extends WalkiesBoard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWalkiesBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWalkiesBoard(PathMetadata metadata, PathInits inits) {
        this(WalkiesBoard.class, metadata, inits);
    }

    public QWalkiesBoard(Class<? extends WalkiesBoard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.animalCategoryCode = inits.isInitialized("animalCategoryCode") ? new com.project.compagnoserver.domain.Animal.QAnimalCategory(forProperty("animalCategoryCode")) : null;
        this.user = inits.isInitialized("user") ? new com.project.compagnoserver.domain.user.QUser(forProperty("user")) : null;
    }

}

