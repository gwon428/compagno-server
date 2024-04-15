package com.project.compagnoserver.domain.ProductBoard;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductBoardComment is a Querydsl query type for ProductBoardComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductBoardComment extends EntityPathBase<ProductBoardComment> {

    private static final long serialVersionUID = -1804026840L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductBoardComment productBoardComment = new QProductBoardComment("productBoardComment");

    public final QProductBoardComment parent;

    public final NumberPath<Integer> productBoardCode = createNumber("productBoardCode", Integer.class);

    public final NumberPath<Integer> productCommentCode = createNumber("productCommentCode", Integer.class);

    public final StringPath productCommentContent = createString("productCommentContent");

    public final ComparablePath<Character> productCommentDelete = createComparable("productCommentDelete", Character.class);

    public final DateTimePath<java.util.Date> productCommentRegiDate = createDateTime("productCommentRegiDate", java.util.Date.class);

    public final NumberPath<Integer> productParentCode = createNumber("productParentCode", Integer.class);

    public final com.project.compagnoserver.domain.user.QUser user;

    public QProductBoardComment(String variable) {
        this(ProductBoardComment.class, forVariable(variable), INITS);
    }

    public QProductBoardComment(Path<? extends ProductBoardComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductBoardComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductBoardComment(PathMetadata metadata, PathInits inits) {
        this(ProductBoardComment.class, metadata, inits);
    }

    public QProductBoardComment(Class<? extends ProductBoardComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.parent = inits.isInitialized("parent") ? new QProductBoardComment(forProperty("parent"), inits.get("parent")) : null;
        this.user = inits.isInitialized("user") ? new com.project.compagnoserver.domain.user.QUser(forProperty("user")) : null;
    }

}

