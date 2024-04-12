package com.project.compagnoserver.domain.ProductBoard;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProductBoardRecommend is a Querydsl query type for ProductBoardRecommend
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductBoardRecommend extends EntityPathBase<ProductBoardRecommend> {

    private static final long serialVersionUID = -313749435L;

    public static final QProductBoardRecommend productBoardRecommend = new QProductBoardRecommend("productBoardRecommend");

    public final NumberPath<Integer> productBoardCode = createNumber("productBoardCode", Integer.class);

    public final NumberPath<Integer> productRecommendCode = createNumber("productRecommendCode", Integer.class);

    public final DateTimePath<java.util.Date> productRecommendDate = createDateTime("productRecommendDate", java.util.Date.class);

    public final StringPath userId = createString("userId");

    public QProductBoardRecommend(String variable) {
        super(ProductBoardRecommend.class, forVariable(variable));
    }

    public QProductBoardRecommend(Path<? extends ProductBoardRecommend> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductBoardRecommend(PathMetadata metadata) {
        super(ProductBoardRecommend.class, metadata);
    }

}

