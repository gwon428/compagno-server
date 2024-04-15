package com.project.compagnoserver.domain.RegisterPet;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRegisterPetFaq is a Querydsl query type for RegisterPetFaq
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRegisterPetFaq extends EntityPathBase<RegisterPetFaq> {

    private static final long serialVersionUID = 1412758115L;

    public static final QRegisterPetFaq registerPetFaq = new QRegisterPetFaq("registerPetFaq");

    public final StringPath regiFaqAnswer = createString("regiFaqAnswer");

    public final NumberPath<Integer> regiFaqCode = createNumber("regiFaqCode", Integer.class);

    public final StringPath regiFaqQuestion = createString("regiFaqQuestion");

    public final StringPath regiFaqStatus = createString("regiFaqStatus");

    public QRegisterPetFaq(String variable) {
        super(RegisterPetFaq.class, forVariable(variable));
    }

    public QRegisterPetFaq(Path<? extends RegisterPetFaq> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRegisterPetFaq(PathMetadata metadata) {
        super(RegisterPetFaq.class, metadata);
    }

}

