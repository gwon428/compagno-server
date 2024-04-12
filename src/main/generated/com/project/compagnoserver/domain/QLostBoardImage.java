package com.project.compagnoserver.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLostBoardImage is a Querydsl query type for LostBoardImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLostBoardImage extends EntityPathBase<LostBoardImage> {

    private static final long serialVersionUID = 1851313776L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLostBoardImage lostBoardImage = new QLostBoardImage("lostBoardImage");

    public final QLostBoard lostBoardCode;

    public final StringPath lostImage = createString("lostImage");

    public final NumberPath<Integer> lostImageCode = createNumber("lostImageCode", Integer.class);

    public QLostBoardImage(String variable) {
        this(LostBoardImage.class, forVariable(variable), INITS);
    }

    public QLostBoardImage(Path<? extends LostBoardImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLostBoardImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLostBoardImage(PathMetadata metadata, PathInits inits) {
        this(LostBoardImage.class, metadata, inits);
    }

    public QLostBoardImage(Class<? extends LostBoardImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.lostBoardCode = inits.isInitialized("lostBoardCode") ? new QLostBoard(forProperty("lostBoardCode")) : null;
    }

}

