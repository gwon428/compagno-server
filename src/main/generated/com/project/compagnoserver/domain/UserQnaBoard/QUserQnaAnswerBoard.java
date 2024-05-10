package com.project.compagnoserver.domain.UserQnaBoard;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserQnaAnswerBoard is a Querydsl query type for UserQnaAnswerBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserQnaAnswerBoard extends EntityPathBase<UserQnaAnswerBoard> {

    private static final long serialVersionUID = 1057212441L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserQnaAnswerBoard userQnaAnswerBoard = new QUserQnaAnswerBoard("userQnaAnswerBoard");

    public final NumberPath<Integer> answerParentCode = createNumber("answerParentCode", Integer.class);

    public final QUserQnaAnswerBoard parent;

    public final com.project.compagnoserver.domain.user.QUser user;

    public final NumberPath<Integer> userAnswerBoardCode = createNumber("userAnswerBoardCode", Integer.class);

    public final StringPath userAnswerContent = createString("userAnswerContent");

    public final DateTimePath<java.sql.Timestamp> userAnswerDate = createDateTime("userAnswerDate", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> userAnswerDateUpdate = createDateTime("userAnswerDateUpdate", java.sql.Timestamp.class);

    public final StringPath userImg = createString("userImg");

    public final StringPath userNickname = createString("userNickname");

    public final NumberPath<Integer> userQuestionBoardCode = createNumber("userQuestionBoardCode", Integer.class);

    public QUserQnaAnswerBoard(String variable) {
        this(UserQnaAnswerBoard.class, forVariable(variable), INITS);
    }

    public QUserQnaAnswerBoard(Path<? extends UserQnaAnswerBoard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserQnaAnswerBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserQnaAnswerBoard(PathMetadata metadata, PathInits inits) {
        this(UserQnaAnswerBoard.class, metadata, inits);
    }

    public QUserQnaAnswerBoard(Class<? extends UserQnaAnswerBoard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.parent = inits.isInitialized("parent") ? new QUserQnaAnswerBoard(forProperty("parent"), inits.get("parent")) : null;
        this.user = inits.isInitialized("user") ? new com.project.compagnoserver.domain.user.QUser(forProperty("user")) : null;
    }

}

