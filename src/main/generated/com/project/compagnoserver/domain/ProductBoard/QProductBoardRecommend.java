package com.project.compagnoserver.domain.ProductBoard;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductBoardRecommend is a Querydsl query type for ProductBoardRecommend
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductBoardRecommend extends EntityPathBase<ProductBoardRecommend> {

    private static final long serialVersionUID = -313749435L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductBoardRecommend productBoardRecommend = new QProductBoardRecommend("productBoardRecommend");

    public final NumberPath<Integer> productBoardCode = createNumber("productBoardCode", Integer.class);

    public final NumberPath<Integer> productRecommendCode = createNumber("productRecommendCode", Integer.class);

    public final DateTimePath<java.util.Date> productRecommendDate = createDateTime("productRecommendDate", java.util.Date.class);

    public final com.project.compagnoserver.domain.QUser user;

    public QProductBoardRecommend(String variable) {
        this(ProductBoardRecommend.class, forVariable(variable), INITS);
    }

    public QProductBoardRecommend(Path<? extends ProductBoardRecommend> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductBoardRecommend(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductBoardRecommend(PathMetadata metadata, PathInits inits) {
        this(ProductBoardRecommend.class, metadata, inits);
    }

    public QProductBoardRecommend(Class<? extends ProductBoardRecommend> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.project.compagnoserver.domain.QUser(forProperty("user")) : null;
    }

}

