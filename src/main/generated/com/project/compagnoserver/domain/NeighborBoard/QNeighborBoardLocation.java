package com.project.compagnoserver.domain.NeighborBoard;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNeighborBoardLocation is a Querydsl query type for NeighborBoardLocation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNeighborBoardLocation extends EntityPathBase<NeighborBoardLocation> {

    private static final long serialVersionUID = 49135032L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNeighborBoardLocation neighborBoardLocation = new QNeighborBoardLocation("neighborBoardLocation");

    public final NumberPath<Integer> locationCode = createNumber("locationCode", Integer.class);

    public final StringPath locationName = createString("locationName");

    public final NumberPath<Integer> locationParentCode = createNumber("locationParentCode", Integer.class);

    public final QNeighborBoardLocation parent;

    public QNeighborBoardLocation(String variable) {
        this(NeighborBoardLocation.class, forVariable(variable), INITS);
    }

    public QNeighborBoardLocation(Path<? extends NeighborBoardLocation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNeighborBoardLocation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNeighborBoardLocation(PathMetadata metadata, PathInits inits) {
        this(NeighborBoardLocation.class, metadata, inits);
    }

    public QNeighborBoardLocation(Class<? extends NeighborBoardLocation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.parent = inits.isInitialized("parent") ? new QNeighborBoardLocation(forProperty("parent"), inits.get("parent")) : null;
    }

}

