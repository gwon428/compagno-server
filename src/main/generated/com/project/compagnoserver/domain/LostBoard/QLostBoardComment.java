package com.project.compagnoserver.domain.LostBoard;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLostBoardComment is a Querydsl query type for LostBoardComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLostBoardComment extends EntityPathBase<LostBoardComment> {

    private static final long serialVersionUID = -1470577696L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLostBoardComment lostBoardComment = new QLostBoardComment("lostBoardComment");

    public final StringPath commentContent = createString("commentContent");

    public final DateTimePath<java.sql.Timestamp> commentDate = createDateTime("commentDate", java.sql.Timestamp.class);

    public final NumberPath<Integer> lostBoardCode = createNumber("lostBoardCode", Integer.class);

    public final NumberPath<Integer> lostCommentCode = createNumber("lostCommentCode", Integer.class);

    public final QLostBoardComment lostParentCode;

    public final com.project.compagnoserver.domain.QUser user;

    public final StringPath userImg = createString("userImg");

    public final StringPath userNickname = createString("userNickname");

    public QLostBoardComment(String variable) {
        this(LostBoardComment.class, forVariable(variable), INITS);
    }

    public QLostBoardComment(Path<? extends LostBoardComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLostBoardComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLostBoardComment(PathMetadata metadata, PathInits inits) {
        this(LostBoardComment.class, metadata, inits);
    }

    public QLostBoardComment(Class<? extends LostBoardComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.lostParentCode = inits.isInitialized("lostParentCode") ? new QLostBoardComment(forProperty("lostParentCode"), inits.get("lostParentCode")) : null;
        this.user = inits.isInitialized("user") ? new com.project.compagnoserver.domain.QUser(forProperty("user")) : null;
    }

}

