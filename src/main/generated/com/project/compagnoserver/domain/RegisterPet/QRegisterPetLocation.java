package com.project.compagnoserver.domain.RegisterPet;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRegisterPetLocation is a Querydsl query type for RegisterPetLocation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRegisterPetLocation extends EntityPathBase<RegisterPetLocation> {

    private static final long serialVersionUID = -1303463800L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRegisterPetLocation registerPetLocation = new QRegisterPetLocation("registerPetLocation");

    public final NumberPath<Integer> locationCode = createNumber("locationCode", Integer.class);

    public final StringPath locationName = createString("locationName");

    public final NumberPath<Integer> locationParentCode = createNumber("locationParentCode", Integer.class);

    public final QRegisterPetLocation parent;

    public QRegisterPetLocation(String variable) {
        this(RegisterPetLocation.class, forVariable(variable), INITS);
    }

    public QRegisterPetLocation(Path<? extends RegisterPetLocation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRegisterPetLocation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRegisterPetLocation(PathMetadata metadata, PathInits inits) {
        this(RegisterPetLocation.class, metadata, inits);
    }

    public QRegisterPetLocation(Class<? extends RegisterPetLocation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.parent = inits.isInitialized("parent") ? new QRegisterPetLocation(forProperty("parent"), inits.get("parent")) : null;
    }

}

