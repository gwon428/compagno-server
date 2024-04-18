package com.project.compagnoserver.domain.SitterBoard;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSitterBoardComment is a Querydsl query type for SitterBoardComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSitterBoardComment extends EntityPathBase<SitterBoardComment> {

    private static final long serialVersionUID = -310509410L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSitterBoardComment sitterBoardComment = new QSitterBoardComment("sitterBoardComment");

    public final QSitterBoardComment parent;

    public final NumberPath<Integer> sitterBoardCode = createNumber("sitterBoardCode", Integer.class);

    public final NumberPath<Integer> sitterCommentCode = createNumber("sitterCommentCode", Integer.class);

    public final StringPath sitterCommentContent = createString("sitterCommentContent");

    public final DateTimePath<java.util.Date> sitterCommentDelDate = createDateTime("sitterCommentDelDate", java.util.Date.class);

    public final NumberPath<Integer> sitterCommentParentCode = createNumber("sitterCommentParentCode", Integer.class);

    public final DateTimePath<java.util.Date> sitterCommentRegiDate = createDateTime("sitterCommentRegiDate", java.util.Date.class);

    public final StringPath sitterCommentStatus = createString("sitterCommentStatus");

    public final com.project.compagnoserver.domain.user.QUser user;

    public QSitterBoardComment(String variable) {
        this(SitterBoardComment.class, forVariable(variable), INITS);
    }

    public QSitterBoardComment(Path<? extends SitterBoardComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSitterBoardComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSitterBoardComment(PathMetadata metadata, PathInits inits) {
        this(SitterBoardComment.class, metadata, inits);
    }

    public QSitterBoardComment(Class<? extends SitterBoardComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.parent = inits.isInitialized("parent") ? new QSitterBoardComment(forProperty("parent"), inits.get("parent")) : null;
        this.user = inits.isInitialized("user") ? new com.project.compagnoserver.domain.user.QUser(forProperty("user")) : null;
    }

}

