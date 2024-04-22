package com.project.compagnoserver.domain.WalkiesBoard;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWalkiesBoardComment is a Querydsl query type for WalkiesBoardComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWalkiesBoardComment extends EntityPathBase<WalkiesBoardComment> {

    private static final long serialVersionUID = -1704815992L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWalkiesBoardComment walkiesBoardComment = new QWalkiesBoardComment("walkiesBoardComment");

    public final com.project.compagnoserver.domain.user.QUser user;

    public final NumberPath<Integer> walkiesBoardCode = createNumber("walkiesBoardCode", Integer.class);

    public final NumberPath<Integer> walkiesCommentCode = createNumber("walkiesCommentCode", Integer.class);

    public final StringPath walkiesCommentContent = createString("walkiesCommentContent");

    public final DateTimePath<java.util.Date> walkiesCommentDelDate = createDateTime("walkiesCommentDelDate", java.util.Date.class);

    public final NumberPath<Integer> walkiesCommentParentCode = createNumber("walkiesCommentParentCode", Integer.class);

    public final DateTimePath<java.util.Date> walkiesCommentRegiDate = createDateTime("walkiesCommentRegiDate", java.util.Date.class);

    public final ComparablePath<Character> walkiesCommentStatus = createComparable("walkiesCommentStatus", Character.class);

    public final QWalkiesBoardComment walkiesParent;

    public QWalkiesBoardComment(String variable) {
        this(WalkiesBoardComment.class, forVariable(variable), INITS);
    }

    public QWalkiesBoardComment(Path<? extends WalkiesBoardComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWalkiesBoardComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWalkiesBoardComment(PathMetadata metadata, PathInits inits) {
        this(WalkiesBoardComment.class, metadata, inits);
    }

    public QWalkiesBoardComment(Class<? extends WalkiesBoardComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.project.compagnoserver.domain.user.QUser(forProperty("user")) : null;
        this.walkiesParent = inits.isInitialized("walkiesParent") ? new QWalkiesBoardComment(forProperty("walkiesParent"), inits.get("walkiesParent")) : null;
    }

}

