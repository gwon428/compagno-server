package com.project.compagnoserver.domain.QnaA;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QQnaABoard is a Querydsl query type for QnaABoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQnaABoard extends EntityPathBase<QnaABoard> {

    private static final long serialVersionUID = -966417873L;

    public static final QQnaABoard qnaABoard = new QQnaABoard("qnaABoard");

    public final NumberPath<Integer> qnaACode = createNumber("qnaACode", Integer.class);

    public final StringPath qnaAContent = createString("qnaAContent");

    public final DateTimePath<java.sql.Timestamp> qnaADate = createDateTime("qnaADate", java.sql.Timestamp.class);

    public final StringPath qnaATitle = createString("qnaATitle");

    public final NumberPath<Integer> qnaQCode = createNumber("qnaQCode", Integer.class);

    public final StringPath userId = createString("userId");

    public QQnaABoard(String variable) {
        super(QnaABoard.class, forVariable(variable));
    }

    public QQnaABoard(Path<? extends QnaABoard> path) {
        super(path.getType(), path.getMetadata());
    }

    public QQnaABoard(PathMetadata metadata) {
        super(QnaABoard.class, metadata);
    }

}

