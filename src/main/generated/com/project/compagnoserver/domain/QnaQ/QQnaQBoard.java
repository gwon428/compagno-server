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

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQnaQBoard qnaQBoard = new QQnaQBoard("qnaQBoard");

    public final ListPath<QnaQBoardImage, QQnaQBoardImage> files = this.<QnaQBoardImage, QQnaQBoardImage>createList("files", QnaQBoardImage.class, QQnaQBoardImage.class, PathInits.DIRECT2);

    public final com.project.compagnoserver.domain.QnaA.QQnaABoard qnaACode;

    public final NumberPath<Integer> qnaQCode = createNumber("qnaQCode", Integer.class);

    public final StringPath qnaQContent = createString("qnaQContent");

    public final DateTimePath<java.sql.Timestamp> qnaQDate = createDateTime("qnaQDate", java.sql.Timestamp.class);

    public final StringPath qnaQStatus = createString("qnaQStatus");

    public final StringPath qnaQTitle = createString("qnaQTitle");

    public final StringPath secret = createString("secret");

    public final StringPath userId = createString("userId");

    public final StringPath userNickname = createString("userNickname");

    public QQnaQBoard(String variable) {
        this(QnaQBoard.class, forVariable(variable), INITS);
    }

    public QQnaQBoard(Path<? extends QnaQBoard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQnaQBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQnaQBoard(PathMetadata metadata, PathInits inits) {
        this(QnaQBoard.class, metadata, inits);
    }

    public QQnaQBoard(Class<? extends QnaQBoard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.qnaACode = inits.isInitialized("qnaACode") ? new com.project.compagnoserver.domain.QnaA.QQnaABoard(forProperty("qnaACode")) : null;
    }

}

