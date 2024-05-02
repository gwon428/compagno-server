package com.project.compagnoserver.domain.QnaA;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QQnaABoardImage is a Querydsl query type for QnaABoardImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQnaABoardImage extends EntityPathBase<QnaABoardImage> {

    private static final long serialVersionUID = 909843564L;

    public static final QQnaABoardImage qnaABoardImage = new QQnaABoardImage("qnaABoardImage");

    public final NumberPath<Integer> qnaACode = createNumber("qnaACode", Integer.class);

    public final NumberPath<Integer> qnaAImgCode = createNumber("qnaAImgCode", Integer.class);

    public final StringPath qnaAUrl = createString("qnaAUrl");

    public QQnaABoardImage(String variable) {
        super(QnaABoardImage.class, forVariable(variable));
    }

    public QQnaABoardImage(Path<? extends QnaABoardImage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QQnaABoardImage(PathMetadata metadata) {
        super(QnaABoardImage.class, metadata);
    }

}

