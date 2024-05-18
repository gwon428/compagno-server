package com.project.compagnoserver.domain.SitterBoard;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSitterBoard is a Querydsl query type for SitterBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSitterBoard extends EntityPathBase<SitterBoard> {

    private static final long serialVersionUID = 1725293185L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSitterBoard sitterBoard = new QSitterBoard("sitterBoard");

    public final com.project.compagnoserver.domain.Animal.QAnimalCategory animalCategoryCode;

    public final ListPath<SitterBoardBookmark, QSitterBoardBookmark> bookmark = this.<SitterBoardBookmark, QSitterBoardBookmark>createList("bookmark", SitterBoardBookmark.class, QSitterBoardBookmark.class, PathInits.DIRECT2);

    public final ListPath<SitterBoardImage, QSitterBoardImage> images = this.<SitterBoardImage, QSitterBoardImage>createList("images", SitterBoardImage.class, QSitterBoardImage.class, PathInits.DIRECT2);

    public final com.project.compagnoserver.domain.Parsing.QLocationParsing location;

    public final NumberPath<Integer> sitterBoardCode = createNumber("sitterBoardCode", Integer.class);

    public final QSitterCategory sitterCategory;

    public final StringPath sitterContent = createString("sitterContent");

    public final DateTimePath<java.util.Date> sitterRegiDate = createDateTime("sitterRegiDate", java.util.Date.class);

    public final StringPath sitterTitle = createString("sitterTitle");

    public final DateTimePath<java.util.Date> sitterUpdateDate = createDateTime("sitterUpdateDate", java.util.Date.class);

    public final NumberPath<Integer> sitterViewCount = createNumber("sitterViewCount", Integer.class);

    public final com.project.compagnoserver.domain.user.QUser user;

    public QSitterBoard(String variable) {
        this(SitterBoard.class, forVariable(variable), INITS);
    }

    public QSitterBoard(Path<? extends SitterBoard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSitterBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSitterBoard(PathMetadata metadata, PathInits inits) {
        this(SitterBoard.class, metadata, inits);
    }

    public QSitterBoard(Class<? extends SitterBoard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.animalCategoryCode = inits.isInitialized("animalCategoryCode") ? new com.project.compagnoserver.domain.Animal.QAnimalCategory(forProperty("animalCategoryCode")) : null;
        this.location = inits.isInitialized("location") ? new com.project.compagnoserver.domain.Parsing.QLocationParsing(forProperty("location"), inits.get("location")) : null;
        this.sitterCategory = inits.isInitialized("sitterCategory") ? new QSitterCategory(forProperty("sitterCategory")) : null;
        this.user = inits.isInitialized("user") ? new com.project.compagnoserver.domain.user.QUser(forProperty("user")) : null;
    }

}

