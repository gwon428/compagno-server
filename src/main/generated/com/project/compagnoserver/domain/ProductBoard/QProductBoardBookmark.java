package com.project.compagnoserver.domain.ProductBoard;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProductBoardBookmark is a Querydsl query type for ProductBoardBookmark
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductBoardBookmark extends EntityPathBase<ProductBoardBookmark> {

    private static final long serialVersionUID = -1777430387L;

    public static final QProductBoardBookmark productBoardBookmark = new QProductBoardBookmark("productBoardBookmark");

    public final NumberPath<Integer> productBoardCode = createNumber("productBoardCode", Integer.class);

    public final NumberPath<Integer> productBookmarkCode = createNumber("productBookmarkCode", Integer.class);

    public final StringPath userId = createString("userId");

    public QProductBoardBookmark(String variable) {
        super(ProductBoardBookmark.class, forVariable(variable));
    }

    public QProductBoardBookmark(Path<? extends ProductBoardBookmark> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductBoardBookmark(PathMetadata metadata) {
        super(ProductBoardBookmark.class, metadata);
    }

}

