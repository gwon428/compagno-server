package com.project.compagnoserver.domain.QnaQ;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QQnaQBoardImage is a Querydsl query type for QnaQBoardImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQnaQBoardImage extends EntityPathBase<QnaQBoardImage> {

    private static final long serialVersionUID = -1650186644L;

    public static final QQnaQBoardImage qnaQBoardImage = new QQnaQBoardImage("qnaQBoardImage");

    public final NumberPath<Integer> qnaQCode = createNumber("qnaQCode", Integer.class);

    public final NumberPath<Integer> qnaQImgCode = createNumber("qnaQImgCode", Integer.class);

    public final StringPath qnaQUrl = createString("qnaQUrl");

    public QQnaQBoardImage(String variable) {
        super(QnaQBoardImage.class, forVariable(variable));
    }

    public QQnaQBoardImage(Path<? extends QnaQBoardImage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QQnaQBoardImage(PathMetadata metadata) {
        super(QnaQBoardImage.class, metadata);
    }

}

