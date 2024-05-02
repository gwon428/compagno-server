package com.project.compagnoserver.domain.Parsing;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLocationParsing is a Querydsl query type for LocationParsing
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLocationParsing extends EntityPathBase<LocationParsing> {

    private static final long serialVersionUID = -619970138L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLocationParsing locationParsing = new QLocationParsing("locationParsing");

    public final NumberPath<Integer> locationCode = createNumber("locationCode", Integer.class);

    public final StringPath locationName = createString("locationName");

    public final NumberPath<Integer> locationParentCode = createNumber("locationParentCode", Integer.class);

    public final QLocationParsing parent;

    public QLocationParsing(String variable) {
        this(LocationParsing.class, forVariable(variable), INITS);
    }

    public QLocationParsing(Path<? extends LocationParsing> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLocationParsing(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLocationParsing(PathMetadata metadata, PathInits inits) {
        this(LocationParsing.class, metadata, inits);
    }

    public QLocationParsing(Class<? extends LocationParsing> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.parent = inits.isInitialized("parent") ? new QLocationParsing(forProperty("parent"), inits.get("parent")) : null;
    }

}

