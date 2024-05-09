package com.project.compagnoserver.domain.UserQnaBoard;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserQnaAnswerChoose is a Querydsl query type for UserQnaAnswerChoose
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserQnaAnswerChoose extends EntityPathBase<UserQnaAnswerChoose> {

    private static final long serialVersionUID = -1563573436L;

    public static final QUserQnaAnswerChoose userQnaAnswerChoose = new QUserQnaAnswerChoose("userQnaAnswerChoose");

    public final NumberPath<Integer> chooseCode = createNumber("chooseCode", Integer.class);

    public final NumberPath<Integer> userAnswerBoardCode = createNumber("userAnswerBoardCode", Integer.class);

    public final NumberPath<Integer> userQuestionBoardCode = createNumber("userQuestionBoardCode", Integer.class);

    public QUserQnaAnswerChoose(String variable) {
        super(UserQnaAnswerChoose.class, forVariable(variable));
    }

    public QUserQnaAnswerChoose(Path<? extends UserQnaAnswerChoose> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserQnaAnswerChoose(PathMetadata metadata) {
        super(UserQnaAnswerChoose.class, metadata);
    }

}

