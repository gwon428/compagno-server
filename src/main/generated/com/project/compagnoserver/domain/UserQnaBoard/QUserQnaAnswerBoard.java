package com.project.compagnoserver.domain.UserQnaBoard;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserQnaAnswerBoard is a Querydsl query type for UserQnaAnswerBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserQnaAnswerBoard extends EntityPathBase<UserQnaAnswerBoard> {

    private static final long serialVersionUID = 1057212441L;

    public static final QUserQnaAnswerBoard userQnaAnswerBoard = new QUserQnaAnswerBoard("userQnaAnswerBoard");

    public final NumberPath<Integer> userAnswerBoardCode = createNumber("userAnswerBoardCode", Integer.class);

    public final StringPath userAnswerContent = createString("userAnswerContent");

    public final StringPath userAnswerDateUpdate = createString("userAnswerDateUpdate");

    public final StringPath userId = createString("userId");

    public final StringPath userImg = createString("userImg");

    public final StringPath userNickname = createString("userNickname");

    public final NumberPath<Integer> userQuestionBoardCode = createNumber("userQuestionBoardCode", Integer.class);

    public QUserQnaAnswerBoard(String variable) {
        super(UserQnaAnswerBoard.class, forVariable(variable));
    }

    public QUserQnaAnswerBoard(Path<? extends UserQnaAnswerBoard> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserQnaAnswerBoard(PathMetadata metadata) {
        super(UserQnaAnswerBoard.class, metadata);
    }

}

