package domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProductBoardImage is a Querydsl query type for ProductBoardImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductBoardImage extends EntityPathBase<ProductBoardImage> {

    private static final long serialVersionUID = -2044313190L;

    public static final QProductBoardImage productBoardImage = new QProductBoardImage("productBoardImage");

    public final NumberPath<Integer> productBoardCode = createNumber("productBoardCode", Integer.class);

    public final StringPath productImage = createString("productImage");

    public final NumberPath<Integer> productImageCode = createNumber("productImageCode", Integer.class);

    public QProductBoardImage(String variable) {
        super(ProductBoardImage.class, forVariable(variable));
    }

    public QProductBoardImage(Path<? extends ProductBoardImage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductBoardImage(PathMetadata metadata) {
        super(ProductBoardImage.class, metadata);
    }

}

