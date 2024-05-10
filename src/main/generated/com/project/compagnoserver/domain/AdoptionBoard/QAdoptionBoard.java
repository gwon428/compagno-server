package com.project.compagnoserver.domain.AdoptionBoard;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAdoptionBoard is a Querydsl query type for AdoptionBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAdoptionBoard extends EntityPathBase<AdoptionBoard> {

    private static final long serialVersionUID = -1141891049L;

    public static final QAdoptionBoard adoptionBoard = new QAdoptionBoard("adoptionBoard");

    public final NumberPath<Integer> adopAnimalAge = createNumber("adopAnimalAge", Integer.class);

    public final StringPath adopAnimalColor = createString("adopAnimalColor");

    public final StringPath adopAnimalFeature = createString("adopAnimalFeature");

    public final StringPath adopAnimalFindplace = createString("adopAnimalFindplace");

    public final StringPath adopAnimalGender = createString("adopAnimalGender");

    public final StringPath adopAnimalImage = createString("adopAnimalImage");

    public final NumberPath<Integer> adopAnimalKg = createNumber("adopAnimalKg", Integer.class);

    public final StringPath adopAnimalKind = createString("adopAnimalKind");

    public final StringPath adopAnimalNeuter = createString("adopAnimalNeuter");

    public final NumberPath<Integer> adopBoardCode = createNumber("adopBoardCode", Integer.class);

    public final StringPath adopCenterName = createString("adopCenterName");

    public final StringPath adopCenterPhone = createString("adopCenterPhone");

    public final DateTimePath<java.sql.Timestamp> adopRegiDate = createDateTime("adopRegiDate", java.sql.Timestamp.class);

    public final NumberPath<Integer> adopViewCount = createNumber("adopViewCount", Integer.class);

    public final ListPath<AdoptionBoardImage, QAdoptionBoardImage> images = this.<AdoptionBoardImage, QAdoptionBoardImage>createList("images", AdoptionBoardImage.class, QAdoptionBoardImage.class, PathInits.DIRECT2);

    public final StringPath userId = createString("userId");

    public final StringPath userImg = createString("userImg");

    public final StringPath userNickname = createString("userNickname");

    public final StringPath userPhone = createString("userPhone");

    public QAdoptionBoard(String variable) {
        super(AdoptionBoard.class, forVariable(variable));
    }

    public QAdoptionBoard(Path<? extends AdoptionBoard> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAdoptionBoard(PathMetadata metadata) {
        super(AdoptionBoard.class, metadata);
    }

}

