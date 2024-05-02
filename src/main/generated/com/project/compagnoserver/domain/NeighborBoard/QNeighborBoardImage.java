package com.project.compagnoserver.domain.NeighborBoard;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNeighborBoardImage is a Querydsl query type for NeighborBoardImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNeighborBoardImage extends EntityPathBase<NeighborBoardImage> {

    private static final long serialVersionUID = -815660488L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNeighborBoardImage neighborBoardImage = new QNeighborBoardImage("neighborBoardImage");

    public final QNeighborBoard neighborBoard;

    public final StringPath neighborImage = createString("neighborImage");

    public final NumberPath<Integer> neighborImageCode = createNumber("neighborImageCode", Integer.class);

    public QNeighborBoardImage(String variable) {
        this(NeighborBoardImage.class, forVariable(variable), INITS);
    }

    public QNeighborBoardImage(Path<? extends NeighborBoardImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNeighborBoardImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNeighborBoardImage(PathMetadata metadata, PathInits inits) {
        this(NeighborBoardImage.class, metadata, inits);
    }

    public QNeighborBoardImage(Class<? extends NeighborBoardImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.neighborBoard = inits.isInitialized("neighborBoard") ? new QNeighborBoard(forProperty("neighborBoard"), inits.get("neighborBoard")) : null;
    }

}

