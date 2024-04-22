package com.project.compagnoserver.domain.OneDayClass;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QClassBoardReviewComment is a Querydsl query type for ClassBoardReviewComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClassBoardReviewComment extends EntityPathBase<ClassBoardReviewComment> {

    private static final long serialVersionUID = -1879154122L;

    public static final QClassBoardReviewComment classBoardReviewComment = new QClassBoardReviewComment("classBoardReviewComment");

    public final NumberPath<Integer> odcCode = createNumber("odcCode", Integer.class);

    public final NumberPath<Integer> odcCommentCode = createNumber("odcCommentCode", Integer.class);

    public final StringPath odcCommentContext = createString("odcCommentContext");

    public final DateTimePath<java.sql.Timestamp> odcCommentDate = createDateTime("odcCommentDate", java.sql.Timestamp.class);

    public final NumberPath<Integer> odcParentCode = createNumber("odcParentCode", Integer.class);

    public final NumberPath<Integer> odcReviewCode = createNumber("odcReviewCode", Integer.class);

    public final StringPath userId = createString("userId");

    public QClassBoardReviewComment(String variable) {
        super(ClassBoardReviewComment.class, forVariable(variable));
    }

    public QClassBoardReviewComment(Path<? extends ClassBoardReviewComment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClassBoardReviewComment(PathMetadata metadata) {
        super(ClassBoardReviewComment.class, metadata);
    }

}

