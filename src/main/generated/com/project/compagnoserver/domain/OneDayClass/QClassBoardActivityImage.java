package com.project.compagnoserver.domain.OneDayClass;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QClassBoardActivityImage is a Querydsl query type for ClassBoardActivityImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClassBoardActivityImage extends EntityPathBase<ClassBoardActivityImage> {

    private static final long serialVersionUID = -645947941L;

    public static final QClassBoardActivityImage classBoardActivityImage = new QClassBoardActivityImage("classBoardActivityImage");

    public final StringPath odcActiveImage = createString("odcActiveImage");

    public final NumberPath<Integer> odcCode = createNumber("odcCode", Integer.class);

    public final NumberPath<Integer> odcReviewCode = createNumber("odcReviewCode", Integer.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public QClassBoardActivityImage(String variable) {
        super(ClassBoardActivityImage.class, forVariable(variable));
    }

    public QClassBoardActivityImage(Path<? extends ClassBoardActivityImage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClassBoardActivityImage(PathMetadata metadata) {
        super(ClassBoardActivityImage.class, metadata);
    }

}

