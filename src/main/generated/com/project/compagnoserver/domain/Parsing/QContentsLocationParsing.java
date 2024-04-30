package com.project.compagnoserver.domain.Parsing;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QContentsLocationParsing is a Querydsl query type for ContentsLocationParsing
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QContentsLocationParsing extends EntityPathBase<ContentsLocationParsing> {

    private static final long serialVersionUID = -461312980L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QContentsLocationParsing contentsLocationParsing = new QContentsLocationParsing("contentsLocationParsing");

    public final NumberPath<Integer> locationCode = createNumber("locationCode", Integer.class);

    public final StringPath locationName = createString("locationName");

    public final NumberPath<Integer> locationParentCode = createNumber("locationParentCode", Integer.class);

    public final QContentsLocationParsing parent;

    public QContentsLocationParsing(String variable) {
        this(ContentsLocationParsing.class, forVariable(variable), INITS);
    }

    public QContentsLocationParsing(Path<? extends ContentsLocationParsing> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QContentsLocationParsing(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QContentsLocationParsing(PathMetadata metadata, PathInits inits) {
        this(ContentsLocationParsing.class, metadata, inits);
    }

    public QContentsLocationParsing(Class<? extends ContentsLocationParsing> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.parent = inits.isInitialized("parent") ? new QContentsLocationParsing(forProperty("parent"), inits.get("parent")) : null;
    }

}

