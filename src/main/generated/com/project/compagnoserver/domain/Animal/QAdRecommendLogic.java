package com.project.compagnoserver.domain.Animal;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAdRecommendLogic is a Querydsl query type for AdRecommendLogic
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAdRecommendLogic extends EntityPathBase<AdRecommendLogic> {

    private static final long serialVersionUID = 1533247648L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAdRecommendLogic adRecommendLogic = new QAdRecommendLogic("adRecommendLogic");

    public final QAnimalCategory animalCategory;

    public final NumberPath<Integer> logicCode = createNumber("logicCode", Integer.class);

    public final NumberPath<Double> totalScore = createNumber("totalScore", Double.class);

    public final com.project.compagnoserver.domain.user.QUser user;

    public QAdRecommendLogic(String variable) {
        this(AdRecommendLogic.class, forVariable(variable), INITS);
    }

    public QAdRecommendLogic(Path<? extends AdRecommendLogic> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAdRecommendLogic(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAdRecommendLogic(PathMetadata metadata, PathInits inits) {
        this(AdRecommendLogic.class, metadata, inits);
    }

    public QAdRecommendLogic(Class<? extends AdRecommendLogic> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.animalCategory = inits.isInitialized("animalCategory") ? new QAnimalCategory(forProperty("animalCategory")) : null;
        this.user = inits.isInitialized("user") ? new com.project.compagnoserver.domain.user.QUser(forProperty("user")) : null;
    }

}

