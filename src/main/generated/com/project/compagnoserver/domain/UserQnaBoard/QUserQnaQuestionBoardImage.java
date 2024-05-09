package com.project.compagnoserver.domain.UserQnaBoard;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserQnaQuestionBoardImage is a Querydsl query type for UserQnaQuestionBoardImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserQnaQuestionBoardImage extends EntityPathBase<UserQnaQuestionBoardImage> {

    private static final long serialVersionUID = -1966462166L;

    public static final QUserQnaQuestionBoardImage userQnaQuestionBoardImage = new QUserQnaQuestionBoardImage("userQnaQuestionBoardImage");

    public final NumberPath<Integer> userQuestionBoardCode = createNumber("userQuestionBoardCode", Integer.class);

    public final NumberPath<Integer> userQuestionImgCode = createNumber("userQuestionImgCode", Integer.class);

    public final StringPath userQuestionImgUrl = createString("userQuestionImgUrl");

    public QUserQnaQuestionBoardImage(String variable) {
        super(UserQnaQuestionBoardImage.class, forVariable(variable));
    }

    public QUserQnaQuestionBoardImage(Path<? extends UserQnaQuestionBoardImage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserQnaQuestionBoardImage(PathMetadata metadata) {
        super(UserQnaQuestionBoardImage.class, metadata);
    }

}

