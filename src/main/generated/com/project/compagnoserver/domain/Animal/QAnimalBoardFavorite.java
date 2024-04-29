package com.project.compagnoserver.domain.Animal;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAnimalBoardFavorite is a Querydsl query type for AnimalBoardFavorite
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAnimalBoardFavorite extends EntityPathBase<AnimalBoardFavorite> {

    private static final long serialVersionUID = -478852469L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAnimalBoardFavorite animalBoardFavorite = new QAnimalBoardFavorite("animalBoardFavorite");

    public final QAnimalBoard animalBoard;

    public final NumberPath<Integer> animalFavoriteCode = createNumber("animalFavoriteCode", Integer.class);

    public final DateTimePath<java.util.Date> animalFavoriteDate = createDateTime("animalFavoriteDate", java.util.Date.class);

    public final com.project.compagnoserver.domain.user.QUser user;

    public QAnimalBoardFavorite(String variable) {
        this(AnimalBoardFavorite.class, forVariable(variable), INITS);
    }

    public QAnimalBoardFavorite(Path<? extends AnimalBoardFavorite> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAnimalBoardFavorite(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAnimalBoardFavorite(PathMetadata metadata, PathInits inits) {
        this(AnimalBoardFavorite.class, metadata, inits);
    }

    public QAnimalBoardFavorite(Class<? extends AnimalBoardFavorite> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.animalBoard = inits.isInitialized("animalBoard") ? new QAnimalBoard(forProperty("animalBoard"), inits.get("animalBoard")) : null;
        this.user = inits.isInitialized("user") ? new com.project.compagnoserver.domain.user.QUser(forProperty("user")) : null;
    }

}

