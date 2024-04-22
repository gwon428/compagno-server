package com.project.compagnoserver.domain.OneDayClass;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QClassBoardReview is a Querydsl query type for ClassBoardReview
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClassBoardReview extends EntityPathBase<ClassBoardReview> {

    private static final long serialVersionUID = -614622231L;

    public static final QClassBoardReview classBoardReview = new QClassBoardReview("classBoardReview");

    public final NumberPath<Integer> odcCode = createNumber("odcCode", Integer.class);

    public final NumberPath<Integer> odcReviewCode = createNumber("odcReviewCode", Integer.class);

    public final StringPath odcReviewContent = createString("odcReviewContent");

    public final NumberPath<Integer> odcReviewCount = createNumber("odcReviewCount", Integer.class);

    public final DateTimePath<java.sql.Timestamp> odcReviewDate = createDateTime("odcReviewDate", java.sql.Timestamp.class);

    public final StringPath odcReviewTitle = createString("odcReviewTitle");

    public final StringPath userId = createString("userId");

    public QClassBoardReview(String variable) {
        super(ClassBoardReview.class, forVariable(variable));
    }

    public QClassBoardReview(Path<? extends ClassBoardReview> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClassBoardReview(PathMetadata metadata) {
        super(ClassBoardReview.class, metadata);
    }

}

