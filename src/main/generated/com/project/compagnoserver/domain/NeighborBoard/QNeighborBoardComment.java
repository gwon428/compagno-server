package com.project.compagnoserver.domain.NeighborBoard;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNeighborBoardComment is a Querydsl query type for NeighborBoardComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNeighborBoardComment extends EntityPathBase<NeighborBoardComment> {

    private static final long serialVersionUID = 1167754236L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNeighborBoardComment neighborBoardComment = new QNeighborBoardComment("neighborBoardComment");

    public final QNeighborBoard neighborBoard;

    public final NumberPath<Integer> neighborCommentCode = createNumber("neighborCommentCode", Integer.class);

    public final StringPath neighborCommentContent = createString("neighborCommentContent");

    public final DateTimePath<java.util.Date> neighborCommentDelDate = createDateTime("neighborCommentDelDate", java.util.Date.class);

    public final NumberPath<Integer> neighborCommentParentCode = createNumber("neighborCommentParentCode", Integer.class);

    public final DateTimePath<java.util.Date> neighborCommentRegiDate = createDateTime("neighborCommentRegiDate", java.util.Date.class);

    public final ComparablePath<Character> neighborCommentStatus = createComparable("neighborCommentStatus", Character.class);

    public final QNeighborBoardComment neighborParent;

    public final com.project.compagnoserver.domain.user.QUser user;

    public QNeighborBoardComment(String variable) {
        this(NeighborBoardComment.class, forVariable(variable), INITS);
    }

    public QNeighborBoardComment(Path<? extends NeighborBoardComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNeighborBoardComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNeighborBoardComment(PathMetadata metadata, PathInits inits) {
        this(NeighborBoardComment.class, metadata, inits);
    }

    public QNeighborBoardComment(Class<? extends NeighborBoardComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.neighborBoard = inits.isInitialized("neighborBoard") ? new QNeighborBoard(forProperty("neighborBoard"), inits.get("neighborBoard")) : null;
        this.neighborParent = inits.isInitialized("neighborParent") ? new QNeighborBoardComment(forProperty("neighborParent"), inits.get("neighborParent")) : null;
        this.user = inits.isInitialized("user") ? new com.project.compagnoserver.domain.user.QUser(forProperty("user")) : null;
    }

}

