package com.project.compagnoserver.domain.Product;

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

    private static final long serialVersionUID = -1271148851L;

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

    public final StringPath userId = createString("userId");

    public QProductBoard(String variable) {
        super(ProductBoard.class, forVariable(variable));
    }

    public QProductBoard(Path<? extends ProductBoard> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductBoard(PathMetadata metadata) {
        super(ProductBoard.class, metadata);
    }

}

