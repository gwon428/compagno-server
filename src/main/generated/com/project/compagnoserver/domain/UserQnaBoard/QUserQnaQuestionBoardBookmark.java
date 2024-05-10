package com.project.compagnoserver.domain.UserQnaBoard;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserQnaQuestionBoardBookmark is a Querydsl query type for UserQnaQuestionBoardBookmark
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserQnaQuestionBoardBookmark extends EntityPathBase<UserQnaQuestionBoardBookmark> {

    private static final long serialVersionUID = -950362873L;

    public static final QUserQnaQuestionBoardBookmark userQnaQuestionBoardBookmark = new QUserQnaQuestionBoardBookmark("userQnaQuestionBoardBookmark");

    public final StringPath userId = createString("userId");

    public final NumberPath<Integer> userQnaQuestionBoard = createNumber("userQnaQuestionBoard", Integer.class);

    public final NumberPath<Integer> userQuestionBookmarkCode = createNumber("userQuestionBookmarkCode", Integer.class);

    public QUserQnaQuestionBoardBookmark(String variable) {
        super(UserQnaQuestionBoardBookmark.class, forVariable(variable));
    }

    public QUserQnaQuestionBoardBookmark(Path<? extends UserQnaQuestionBoardBookmark> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserQnaQuestionBoardBookmark(PathMetadata metadata) {
        super(UserQnaQuestionBoardBookmark.class, metadata);
    }

}

