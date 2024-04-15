package com.project.compagnoserver.domain.QnaQ;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQnaQBoard is a Querydsl query type for QnaQBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQnaQBoard extends EntityPathBase<QnaQBoard> {

    private static final long serialVersionUID = 805197871L;

    public static final QQnaQBoard qnaQBoard = new QQnaQBoard("qnaQBoard");

    public final ListPath<QnaQBoardImage, QQnaQBoardImage> files = this.<QnaQBoardImage, QQnaQBoardImage>createList("files", QnaQBoardImage.class, QQnaQBoardImage.class, PathInits.DIRECT2);

    public final NumberPath<Integer> qnaQBoardCode = createNumber("qnaQBoardCode", Integer.class);

    public final StringPath qnaQContent = createString("qnaQContent");

    public final DateTimePath<java.util.Date> qnaQDate = createDateTime("qnaQDate", java.util.Date.class);

    public final StringPath qnaQStatus = createString("qnaQStatus");

    public final StringPath qnaQTitle = createString("qnaQTitle");

    public final StringPath userId = createString("userId");

    public final StringPath userNickname = createString("userNickname");

    public QQnaQBoard(String variable) {
        super(QnaQBoard.class, forVariable(variable));
    }

    public QQnaQBoard(Path<? extends QnaQBoard> path) {
        super(path.getType(), path.getMetadata());
    }

    public QQnaQBoard(PathMetadata metadata) {
        super(QnaQBoard.class, metadata);
    }

}

