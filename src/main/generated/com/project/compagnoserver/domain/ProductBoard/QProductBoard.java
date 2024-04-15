package com.project.compagnoserver.domain.ProductBoard;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductBoard is a Querydsl query type for ProductBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductBoard extends EntityPathBase<ProductBoard> {

    private static final long serialVersionUID = -1568231881L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductBoard productBoard = new QProductBoard("productBoard");

    public final NumberPath<Integer> animalCategoryCode = createNumber("animalCategoryCode", Integer.class);

    public final ListPath<ProductBoardImage, QProductBoardImage> images = this.<ProductBoardImage, QProductBoardImage>createList("images", ProductBoardImage.class, QProductBoardImage.class, PathInits.DIRECT2);

    public final NumberPath<Integer> productBoardCode = createNumber("productBoardCode", Integer.class);

    public final StringPath productBoardContent = createString("productBoardContent");

    public final NumberPath<Float> productBoardGrade = createNumber("productBoardGrade", Float.class);

    public final DateTimePath<java.util.Date> productBoardRegiDate = createDateTime("productBoardRegiDate", java.util.Date.class);

    public final StringPath productBoardTitle = createString("productBoardTitle");

    public final NumberPath<Integer> productBoardViewCount = createNumber("productBoardViewCount", Integer.class);

    public final StringPath productCategory = createString("productCategory");

    public final StringPath productMainImage = createString("productMainImage");

    public final StringPath productName = createString("productName");

    public final NumberPath<Integer> productPrice = createNumber("productPrice", Integer.class);

    public final com.project.compagnoserver.domain.user.QUser user;

    public QProductBoard(String variable) {
        this(ProductBoard.class, forVariable(variable), INITS);
    }

    public QProductBoard(Path<? extends ProductBoard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductBoard(PathMetadata metadata, PathInits inits) {
        this(ProductBoard.class, metadata, inits);
    }

    public QProductBoard(Class<? extends ProductBoard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.project.compagnoserver.domain.user.QUser(forProperty("user")) : null;
    }

}

