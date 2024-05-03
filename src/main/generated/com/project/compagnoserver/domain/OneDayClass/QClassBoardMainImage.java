package com.project.compagnoserver.domain.OneDayClass;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClassBoardMainImage is a Querydsl query type for ClassBoardMainImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClassBoardMainImage extends EntityPathBase<ClassBoardMainImage> {

    private static final long serialVersionUID = -1529797391L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClassBoardMainImage classBoardMainImage = new QClassBoardMainImage("classBoardMainImage");

    public final QClassBoard classBoard;

    public final NumberPath<Integer> odcImageCode = createNumber("odcImageCode", Integer.class);

    public final StringPath odcMainImage = createString("odcMainImage");

    public QClassBoardMainImage(String variable) {
        this(ClassBoardMainImage.class, forVariable(variable), INITS);
    }

    public QClassBoardMainImage(Path<? extends ClassBoardMainImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClassBoardMainImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClassBoardMainImage(PathMetadata metadata, PathInits inits) {
        this(ClassBoardMainImage.class, metadata, inits);
    }

    public QClassBoardMainImage(Class<? extends ClassBoardMainImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.classBoard = inits.isInitialized("classBoard") ? new QClassBoard(forProperty("classBoard")) : null;
    }

}

