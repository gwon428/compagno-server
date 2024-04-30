package com.project.compagnoserver.domain.NeighborBoard;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNeighborBoard is a Querydsl query type for NeighborBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNeighborBoard extends EntityPathBase<NeighborBoard> {

    private static final long serialVersionUID = 1965116643L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNeighborBoard neighborBoard = new QNeighborBoard("neighborBoard");

    public final com.project.compagnoserver.domain.Animal.QAnimalCategory animalCategoryCode;

    public final ListPath<NeighborBoardImage, QNeighborBoardImage> images = this.<NeighborBoardImage, QNeighborBoardImage>createList("images", NeighborBoardImage.class, QNeighborBoardImage.class, PathInits.DIRECT2);

    public final com.project.compagnoserver.domain.Parsing.QLocationParsing locationCode;

    public final NumberPath<Integer> neighborBoardCode = createNumber("neighborBoardCode", Integer.class);

    public final StringPath neighborBoardContent = createString("neighborBoardContent");

    public final DateTimePath<java.util.Date> neighborBoardRegiDate = createDateTime("neighborBoardRegiDate", java.util.Date.class);

    public final StringPath neighborBoardStatus = createString("neighborBoardStatus");

    public final StringPath neighborBoardTitle = createString("neighborBoardTitle");

    public final DateTimePath<java.util.Date> neighborBoardUpdateDate = createDateTime("neighborBoardUpdateDate", java.util.Date.class);

    public final NumberPath<Integer> neighborBoardViewCount = createNumber("neighborBoardViewCount", Integer.class);

    public final com.project.compagnoserver.domain.user.QUser user;

    public QNeighborBoard(String variable) {
        this(NeighborBoard.class, forVariable(variable), INITS);
    }

    public QNeighborBoard(Path<? extends NeighborBoard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNeighborBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNeighborBoard(PathMetadata metadata, PathInits inits) {
        this(NeighborBoard.class, metadata, inits);
    }

    public QNeighborBoard(Class<? extends NeighborBoard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.animalCategoryCode = inits.isInitialized("animalCategoryCode") ? new com.project.compagnoserver.domain.Animal.QAnimalCategory(forProperty("animalCategoryCode")) : null;
        this.locationCode = inits.isInitialized("locationCode") ? new com.project.compagnoserver.domain.Parsing.QLocationParsing(forProperty("locationCode"), inits.get("locationCode")) : null;
        this.user = inits.isInitialized("user") ? new com.project.compagnoserver.domain.user.QUser(forProperty("user")) : null;
    }

}

