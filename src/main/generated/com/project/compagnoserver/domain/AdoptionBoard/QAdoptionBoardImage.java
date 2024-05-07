package com.project.compagnoserver.domain.AdoptionBoard;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAdoptionBoardImage is a Querydsl query type for AdoptionBoardImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAdoptionBoardImage extends EntityPathBase<AdoptionBoardImage> {

    private static final long serialVersionUID = 10162052L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAdoptionBoardImage adoptionBoardImage = new QAdoptionBoardImage("adoptionBoardImage");

    public final QAdoptionBoard adopBoardCode;

    public final StringPath adopImage = createString("adopImage");

    public final NumberPath<Integer> adopImageCode = createNumber("adopImageCode", Integer.class);

    public QAdoptionBoardImage(String variable) {
        this(AdoptionBoardImage.class, forVariable(variable), INITS);
    }

    public QAdoptionBoardImage(Path<? extends AdoptionBoardImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAdoptionBoardImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAdoptionBoardImage(PathMetadata metadata, PathInits inits) {
        this(AdoptionBoardImage.class, metadata, inits);
    }

    public QAdoptionBoardImage(Class<? extends AdoptionBoardImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.adopBoardCode = inits.isInitialized("adopBoardCode") ? new QAdoptionBoard(forProperty("adopBoardCode")) : null;
    }

}

