package com.project.compagnoserver.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.project.compagnoserver.domain.RegisterPet.RegisterPet;
import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRegisterPet is a Querydsl query type for RegisterPet
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRegisterPet extends EntityPathBase<RegisterPet> {

    private static final long serialVersionUID = 1663980741L;

    public static final QRegisterPet registerPet = new QRegisterPet("registerPet");

    public final NumberPath<Integer> regiBoardCode = createNumber("regiBoardCode", Integer.class);

    public final StringPath regiInstAddr = createString("regiInstAddr");

    public final StringPath regiInstName = createString("regiInstName");

    public final StringPath regiInstOwner = createString("regiInstOwner");

    public final StringPath regiInstPhone = createString("regiInstPhone");

    public QRegisterPet(String variable) {
        super(RegisterPet.class, forVariable(variable));
    }

    public QRegisterPet(Path<? extends RegisterPet> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRegisterPet(PathMetadata metadata) {
        super(RegisterPet.class, metadata);
    }

}

