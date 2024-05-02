package com.project.compagnoserver.domain.NeighborBoard;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNeighborBoardBookmark is a Querydsl query type for NeighborBoardBookmark
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNeighborBoardBookmark extends EntityPathBase<NeighborBoardBookmark> {

    private static final long serialVersionUID = 153469753L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNeighborBoardBookmark neighborBoardBookmark = new QNeighborBoardBookmark("neighborBoardBookmark");

    public final NumberPath<Integer> neighborBoardCode = createNumber("neighborBoardCode", Integer.class);

    public final NumberPath<Integer> neighborBookmarkCode = createNumber("neighborBookmarkCode", Integer.class);

    public final DateTimePath<java.util.Date> neighborBookmarkDate = createDateTime("neighborBookmarkDate", java.util.Date.class);

    public final com.project.compagnoserver.domain.user.QUser user;

    public QNeighborBoardBookmark(String variable) {
        this(NeighborBoardBookmark.class, forVariable(variable), INITS);
    }

    public QNeighborBoardBookmark(Path<? extends NeighborBoardBookmark> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNeighborBoardBookmark(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNeighborBoardBookmark(PathMetadata metadata, PathInits inits) {
        this(NeighborBoardBookmark.class, metadata, inits);
    }

    public QNeighborBoardBookmark(Class<? extends NeighborBoardBookmark> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.project.compagnoserver.domain.user.QUser(forProperty("user")) : null;
    }

}

