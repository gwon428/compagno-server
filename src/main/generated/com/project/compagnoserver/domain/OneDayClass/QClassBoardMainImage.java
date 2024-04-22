package com.project.compagnoserver.domain.OneDayClass;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QClassBoardMainImage is a Querydsl query type for ClassBoardMainImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClassBoardMainImage extends EntityPathBase<ClassBoardMainImage> {

    private static final long serialVersionUID = -1529797391L;

    public static final QClassBoardMainImage classBoardMainImage = new QClassBoardMainImage("classBoardMainImage");

    public final NumberPath<Integer> odcCode = createNumber("odcCode", Integer.class);

    public final NumberPath<Integer> odcImageCode = createNumber("odcImageCode", Integer.class);

    public final StringPath odcMainImage = createString("odcMainImage");

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public QClassBoardMainImage(String variable) {
        super(ClassBoardMainImage.class, forVariable(variable));
    }

    public QClassBoardMainImage(Path<? extends ClassBoardMainImage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClassBoardMainImage(PathMetadata metadata) {
        super(ClassBoardMainImage.class, metadata);
    }

}

