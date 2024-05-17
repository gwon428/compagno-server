package com.project.compagnoserver.domain.UserQnaBoard;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserQnaQuestionBoard is a Querydsl query type for UserQnaQuestionBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserQnaQuestionBoard extends EntityPathBase<UserQnaQuestionBoard> {

    private static final long serialVersionUID = 129750193L;

    public static final QUserQnaQuestionBoard userQnaQuestionBoard = new QUserQnaQuestionBoard("userQnaQuestionBoard");

    public final NumberPath<Integer> animalCategoryCode = createNumber("animalCategoryCode", Integer.class);

    public final NumberPath<Integer> likecount = createNumber("likecount", Integer.class);

    public final StringPath userId = createString("userId");

    public final StringPath userImg = createString("userImg");

    public final StringPath userNickname = createString("userNickname");

    public final NumberPath<Integer> userQuestionBoardCode = createNumber("userQuestionBoardCode", Integer.class);

    public final StringPath userQuestionBoardContent = createString("userQuestionBoardContent");

    public final NumberPath<Integer> userQuestionBoardCount = createNumber("userQuestionBoardCount", Integer.class);

    public final DateTimePath<java.sql.Timestamp> userQuestionBoardDate = createDateTime("userQuestionBoardDate", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> userQuestionBoardDateUpdate = createDateTime("userQuestionBoardDateUpdate", java.sql.Timestamp.class);

    public final ComparablePath<Character> userQuestionBoardStatus = createComparable("userQuestionBoardStatus", Character.class);

    public final StringPath userQuestionBoardTitle = createString("userQuestionBoardTitle");

    public final NumberPath<Integer> viewcount = createNumber("viewcount", Integer.class);

    public QUserQnaQuestionBoard(String variable) {
        super(UserQnaQuestionBoard.class, forVariable(variable));
    }

    public QUserQnaQuestionBoard(Path<? extends UserQnaQuestionBoard> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserQnaQuestionBoard(PathMetadata metadata) {
        super(UserQnaQuestionBoard.class, metadata);
    }

}

