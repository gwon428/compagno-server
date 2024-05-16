package com.project.compagnoserver.domain.UserQnaBoard;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserQnaQuestionLike is a Querydsl query type for UserQnaQuestionLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserQnaQuestionLike extends EntityPathBase<UserQnaQuestionLike> {

    private static final long serialVersionUID = 143025260L;

    public static final QUserQnaQuestionLike userQnaQuestionLike = new QUserQnaQuestionLike("userQnaQuestionLike");

    public final StringPath userId = createString("userId");

    public final NumberPath<Integer> userQuestionBoardCode = createNumber("userQuestionBoardCode", Integer.class);

    public final NumberPath<Integer> userQuestionLikeCode = createNumber("userQuestionLikeCode", Integer.class);

    public QUserQnaQuestionLike(String variable) {
        super(UserQnaQuestionLike.class, forVariable(variable));
    }

    public QUserQnaQuestionLike(Path<? extends UserQnaQuestionLike> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserQnaQuestionLike(PathMetadata metadata) {
        super(UserQnaQuestionLike.class, metadata);
    }

}

