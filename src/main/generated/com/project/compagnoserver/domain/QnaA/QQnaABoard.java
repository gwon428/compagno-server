package com.project.compagnoserver.domain.QnaA;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQnaABoard is a Querydsl query type for QnaABoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQnaABoard extends EntityPathBase<QnaABoard> {

    private static final long serialVersionUID = -966417873L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQnaABoard qnaABoard = new QQnaABoard("qnaABoard");

    public final NumberPath<Integer> qnaACode = createNumber("qnaACode", Integer.class);

    public final StringPath qnaAContent = createString("qnaAContent");

    public final DateTimePath<java.sql.Timestamp> qnaADate = createDateTime("qnaADate", java.sql.Timestamp.class);

    public final StringPath qnaATitle = createString("qnaATitle");

    public final com.project.compagnoserver.domain.QnaQ.QQnaQBoard qnaQCode;

    public final StringPath userId = createString("userId");

    public QQnaABoard(String variable) {
        this(QnaABoard.class, forVariable(variable), INITS);
    }

    public QQnaABoard(Path<? extends QnaABoard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQnaABoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQnaABoard(PathMetadata metadata, PathInits inits) {
        this(QnaABoard.class, metadata, inits);
    }

    public QQnaABoard(Class<? extends QnaABoard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.qnaQCode = inits.isInitialized("qnaQCode") ? new com.project.compagnoserver.domain.QnaQ.QQnaQBoard(forProperty("qnaQCode"), inits.get("qnaQCode")) : null;
    }

}

