package com.project.compagnoserver.domain.ProductBoard;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductBoardImage is a Querydsl query type for ProductBoardImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductBoardImage extends EntityPathBase<ProductBoardImage> {

    private static final long serialVersionUID = 1317557604L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductBoardImage productBoardImage = new QProductBoardImage("productBoardImage");

    public final QProductBoard productBoard;

    public final StringPath productImage = createString("productImage");

    public final NumberPath<Integer> productImageCode = createNumber("productImageCode", Integer.class);

    public QProductBoardImage(String variable) {
        this(ProductBoardImage.class, forVariable(variable), INITS);
    }

    public QProductBoardImage(Path<? extends ProductBoardImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductBoardImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductBoardImage(PathMetadata metadata, PathInits inits) {
        this(ProductBoardImage.class, metadata, inits);
    }

    public QProductBoardImage(Class<? extends ProductBoardImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.productBoard = inits.isInitialized("productBoard") ? new QProductBoard(forProperty("productBoard"), inits.get("productBoard")) : null;
    }

}

