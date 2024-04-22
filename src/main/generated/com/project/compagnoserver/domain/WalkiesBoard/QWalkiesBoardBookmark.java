package com.project.compagnoserver.domain.WalkiesBoard;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWalkiesBoardBookmark is a Querydsl query type for WalkiesBoardBookmark
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWalkiesBoardBookmark extends EntityPathBase<WalkiesBoardBookmark> {

    private static final long serialVersionUID = 1298105901L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWalkiesBoardBookmark walkiesBoardBookmark = new QWalkiesBoardBookmark("walkiesBoardBookmark");

    public final com.project.compagnoserver.domain.user.QUser user;

    public final NumberPath<Integer> walkiesBoardCode = createNumber("walkiesBoardCode", Integer.class);

    public final NumberPath<Integer> walkiesBookmarkCode = createNumber("walkiesBookmarkCode", Integer.class);

    public final DateTimePath<java.util.Date> walkiesBookmarkDate = createDateTime("walkiesBookmarkDate", java.util.Date.class);

    public QWalkiesBoardBookmark(String variable) {
        this(WalkiesBoardBookmark.class, forVariable(variable), INITS);
    }

    public QWalkiesBoardBookmark(Path<? extends WalkiesBoardBookmark> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWalkiesBoardBookmark(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWalkiesBoardBookmark(PathMetadata metadata, PathInits inits) {
        this(WalkiesBoardBookmark.class, metadata, inits);
    }

    public QWalkiesBoardBookmark(Class<? extends WalkiesBoardBookmark> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.project.compagnoserver.domain.user.QUser(forProperty("user")) : null;
    }

}

