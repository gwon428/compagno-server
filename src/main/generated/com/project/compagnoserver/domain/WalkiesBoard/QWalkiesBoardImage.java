package com.project.compagnoserver.domain.WalkiesBoard;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWalkiesBoardImage is a Querydsl query type for WalkiesBoardImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWalkiesBoardImage extends EntityPathBase<WalkiesBoardImage> {

    private static final long serialVersionUID = -273398844L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWalkiesBoardImage walkiesBoardImage1 = new QWalkiesBoardImage("walkiesBoardImage1");

    public final QWalkiesBoard walkiesBoard;

    public final StringPath walkiesBoardImage = createString("walkiesBoardImage");

    public final NumberPath<Integer> walkiesImageCode = createNumber("walkiesImageCode", Integer.class);

    public QWalkiesBoardImage(String variable) {
        this(WalkiesBoardImage.class, forVariable(variable), INITS);
    }

    public QWalkiesBoardImage(Path<? extends WalkiesBoardImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWalkiesBoardImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWalkiesBoardImage(PathMetadata metadata, PathInits inits) {
        this(WalkiesBoardImage.class, metadata, inits);
    }

    public QWalkiesBoardImage(Class<? extends WalkiesBoardImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.walkiesBoard = inits.isInitialized("walkiesBoard") ? new QWalkiesBoard(forProperty("walkiesBoard"), inits.get("walkiesBoard")) : null;
    }

}

