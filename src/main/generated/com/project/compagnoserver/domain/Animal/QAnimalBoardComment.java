package com.project.compagnoserver.domain.Animal;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAnimalBoardComment is a Querydsl query type for AnimalBoardComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAnimalBoardComment extends EntityPathBase<AnimalBoardComment> {

    private static final long serialVersionUID = 1039602576L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAnimalBoardComment animalBoardComment = new QAnimalBoardComment("animalBoardComment");

    public final QAnimalBoard animalBoard;

    public final NumberPath<Integer> animalCommentCode = createNumber("animalCommentCode", Integer.class);

    public final StringPath animalCommentContent = createString("animalCommentContent");

    public final DateTimePath<java.util.Date> animalCommentDate = createDateTime("animalCommentDate", java.util.Date.class);

    public final QAnimalBoardComment animalParent;

    public final NumberPath<Integer> animalParentCode = createNumber("animalParentCode", Integer.class);

    public final com.project.compagnoserver.domain.user.QUser user;

    public QAnimalBoardComment(String variable) {
        this(AnimalBoardComment.class, forVariable(variable), INITS);
    }

    public QAnimalBoardComment(Path<? extends AnimalBoardComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAnimalBoardComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAnimalBoardComment(PathMetadata metadata, PathInits inits) {
        this(AnimalBoardComment.class, metadata, inits);
    }

    public QAnimalBoardComment(Class<? extends AnimalBoardComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.animalBoard = inits.isInitialized("animalBoard") ? new QAnimalBoard(forProperty("animalBoard"), inits.get("animalBoard")) : null;
        this.animalParent = inits.isInitialized("animalParent") ? new QAnimalBoardComment(forProperty("animalParent"), inits.get("animalParent")) : null;
        this.user = inits.isInitialized("user") ? new com.project.compagnoserver.domain.user.QUser(forProperty("user")) : null;
    }

}

