package com.project.compagnoserver.domain.QnaQ;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQnaQBoardImage is a Querydsl query type for QnaQBoardImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQnaQBoardImage extends EntityPathBase<QnaQBoardImage> {

    private static final long serialVersionUID = -1650186644L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQnaQBoardImage qnaQBoardImage = new QQnaQBoardImage("qnaQBoardImage");

    public final QQnaQBoard qnaQCode;

    public final NumberPath<Integer> qnaQImgCode = createNumber("qnaQImgCode", Integer.class);

    public final StringPath qnaQUrl = createString("qnaQUrl");

    public QQnaQBoardImage(String variable) {
        this(QnaQBoardImage.class, forVariable(variable), INITS);
    }

    public QQnaQBoardImage(Path<? extends QnaQBoardImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQnaQBoardImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQnaQBoardImage(PathMetadata metadata, PathInits inits) {
        this(QnaQBoardImage.class, metadata, inits);
    }

    public QQnaQBoardImage(Class<? extends QnaQBoardImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.qnaQCode = inits.isInitialized("qnaQCode") ? new QQnaQBoard(forProperty("qnaQCode"), inits.get("qnaQCode")) : null;
    }

}

