package com.project.compagnoserver.domain.SitterBoard;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSitterBoardBookmark is a Querydsl query type for SitterBoardBookmark
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSitterBoardBookmark extends EntityPathBase<SitterBoardBookmark> {

    private static final long serialVersionUID = 1571936983L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSitterBoardBookmark sitterBoardBookmark = new QSitterBoardBookmark("sitterBoardBookmark");

    public final QSitterBoard sitterBoard;

    public final NumberPath<Integer> sitterBookmarkCode = createNumber("sitterBookmarkCode", Integer.class);

    public final com.project.compagnoserver.domain.user.QUser user;

    public QSitterBoardBookmark(String variable) {
        this(SitterBoardBookmark.class, forVariable(variable), INITS);
    }

    public QSitterBoardBookmark(Path<? extends SitterBoardBookmark> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSitterBoardBookmark(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSitterBoardBookmark(PathMetadata metadata, PathInits inits) {
        this(SitterBoardBookmark.class, metadata, inits);
    }

    public QSitterBoardBookmark(Class<? extends SitterBoardBookmark> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.sitterBoard = inits.isInitialized("sitterBoard") ? new QSitterBoard(forProperty("sitterBoard"), inits.get("sitterBoard")) : null;
        this.user = inits.isInitialized("user") ? new com.project.compagnoserver.domain.user.QUser(forProperty("user")) : null;
    }

}

