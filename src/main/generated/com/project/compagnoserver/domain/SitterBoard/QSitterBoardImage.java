package com.project.compagnoserver.domain.SitterBoard;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSitterBoardImage is a Querydsl query type for SitterBoardImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSitterBoardImage extends EntityPathBase<SitterBoardImage> {

    private static final long serialVersionUID = 501235546L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSitterBoardImage sitterBoardImage = new QSitterBoardImage("sitterBoardImage");

    public final QSitterBoard sitterBoard;

    public final StringPath sitterImg = createString("sitterImg");

    public final NumberPath<Integer> sitterImgCode = createNumber("sitterImgCode", Integer.class);

    public QSitterBoardImage(String variable) {
        this(SitterBoardImage.class, forVariable(variable), INITS);
    }

    public QSitterBoardImage(Path<? extends SitterBoardImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSitterBoardImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSitterBoardImage(PathMetadata metadata, PathInits inits) {
        this(SitterBoardImage.class, metadata, inits);
    }

    public QSitterBoardImage(Class<? extends SitterBoardImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.sitterBoard = inits.isInitialized("sitterBoard") ? new QSitterBoard(forProperty("sitterBoard"), inits.get("sitterBoard")) : null;
    }

}

