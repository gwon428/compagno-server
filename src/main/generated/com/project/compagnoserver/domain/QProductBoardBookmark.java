package com.project.compagnoserver.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.project.compagnoserver.domain.Product.ProductBoardBookmark;
import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductBoardBookmark is a Querydsl query type for ProductBoardBookmark
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductBoardBookmark extends EntityPathBase<ProductBoardBookmark> {

    private static final long serialVersionUID = 21971556L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductBoardBookmark productBoardBookmark = new QProductBoardBookmark("productBoardBookmark");

    public final NumberPath<Integer> productBoardCode = createNumber("productBoardCode", Integer.class);

    public final NumberPath<Integer> productBookmarkCode = createNumber("productBookmarkCode", Integer.class);

    public final QUser user;

    public QProductBoardBookmark(String variable) {
        this(ProductBoardBookmark.class, forVariable(variable), INITS);
    }

    public QProductBoardBookmark(Path<? extends ProductBoardBookmark> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductBoardBookmark(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductBoardBookmark(PathMetadata metadata, PathInits inits) {
        this(ProductBoardBookmark.class, metadata, inits);
    }

    public QProductBoardBookmark(Class<? extends ProductBoardBookmark> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

