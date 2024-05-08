package com.project.compagnoserver.domain.AdoptionBoard;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAdoptionBoardComment is a Querydsl query type for AdoptionBoardComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAdoptionBoardComment extends EntityPathBase<AdoptionBoardComment> {

    private static final long serialVersionUID = 214265416L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAdoptionBoardComment adoptionBoardComment = new QAdoptionBoardComment("adoptionBoardComment");

    public final NumberPath<Integer> adopBoardCode = createNumber("adopBoardCode", Integer.class);

    public final NumberPath<Integer> adopCommentCode = createNumber("adopCommentCode", Integer.class);

    public final NumberPath<Integer> adopParentCode = createNumber("adopParentCode", Integer.class);

    public final StringPath commentContent = createString("commentContent");

    public final DateTimePath<java.sql.Timestamp> commentDate = createDateTime("commentDate", java.sql.Timestamp.class);

    public final QAdoptionBoardComment parent;

    public final com.project.compagnoserver.domain.user.QUser user;

    public final StringPath userImg = createString("userImg");

    public final StringPath userNickname = createString("userNickname");

    public QAdoptionBoardComment(String variable) {
        this(AdoptionBoardComment.class, forVariable(variable), INITS);
    }

    public QAdoptionBoardComment(Path<? extends AdoptionBoardComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAdoptionBoardComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAdoptionBoardComment(PathMetadata metadata, PathInits inits) {
        this(AdoptionBoardComment.class, metadata, inits);
    }

    public QAdoptionBoardComment(Class<? extends AdoptionBoardComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.parent = inits.isInitialized("parent") ? new QAdoptionBoardComment(forProperty("parent"), inits.get("parent")) : null;
        this.user = inits.isInitialized("user") ? new com.project.compagnoserver.domain.user.QUser(forProperty("user")) : null;
    }

}

