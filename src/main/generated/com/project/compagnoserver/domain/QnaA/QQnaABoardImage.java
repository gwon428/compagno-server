package com.project.compagnoserver.domain.QnaA;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQnaABoardImage is a Querydsl query type for QnaABoardImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQnaABoardImage extends EntityPathBase<QnaABoardImage> {

    private static final long serialVersionUID = 909843564L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQnaABoardImage qnaABoardImage = new QQnaABoardImage("qnaABoardImage");

    public final QQnaABoard qnaACode;

    public final NumberPath<Integer> qnaAImgCode = createNumber("qnaAImgCode", Integer.class);

    public final StringPath qnaAUrl = createString("qnaAUrl");

    public QQnaABoardImage(String variable) {
        this(QnaABoardImage.class, forVariable(variable), INITS);
    }

    public QQnaABoardImage(Path<? extends QnaABoardImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQnaABoardImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQnaABoardImage(PathMetadata metadata, PathInits inits) {
        this(QnaABoardImage.class, metadata, inits);
    }

    public QQnaABoardImage(Class<? extends QnaABoardImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.qnaACode = inits.isInitialized("qnaACode") ? new QQnaABoard(forProperty("qnaACode")) : null;
    }

}

