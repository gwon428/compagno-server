package com.project.compagnoserver.domain.LostBoard;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLostBoard is a Querydsl query type for LostBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLostBoard extends EntityPathBase<LostBoard> {

    private static final long serialVersionUID = 1947166079L;

    public static final QLostBoard lostBoard = new QLostBoard("lostBoard");

    public final ListPath<LostBoardImage, QLostBoardImage> images = this.<LostBoardImage, QLostBoardImage>createList("images", LostBoardImage.class, QLostBoardImage.class, PathInits.DIRECT2);

    public final NumberPath<Integer> lostAnimalAge = createNumber("lostAnimalAge", Integer.class);

    public final StringPath lostAnimalColor = createString("lostAnimalColor");

    public final StringPath lostAnimalFeature = createString("lostAnimalFeature");

    public final StringPath lostAnimalGender = createString("lostAnimalGender");

    public final StringPath lostAnimalImage = createString("lostAnimalImage");

    public final StringPath lostAnimalKind = createString("lostAnimalKind");

    public final StringPath lostAnimalName = createString("lostAnimalName");

    public final NumberPath<Integer> lostAnimalRFID = createNumber("lostAnimalRFID", Integer.class);

    public final NumberPath<Integer> lostBoardCode = createNumber("lostBoardCode", Integer.class);

    public final DateTimePath<java.util.Date> lostDate = createDateTime("lostDate", java.util.Date.class);

    public final StringPath lostLocation = createString("lostLocation");

    public final DateTimePath<java.sql.Timestamp> lostRegiDate = createDateTime("lostRegiDate", java.sql.Timestamp.class);

    public final NumberPath<Integer> lostViewCount = createNumber("lostViewCount", Integer.class);

    public final StringPath userId = createString("userId");

    public final StringPath userImg = createString("userImg");

    public final StringPath userNickname = createString("userNickname");

    public final StringPath userPhone = createString("userPhone");

    public QLostBoard(String variable) {
        super(LostBoard.class, forVariable(variable));
    }

    public QLostBoard(Path<? extends LostBoard> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLostBoard(PathMetadata metadata) {
        super(LostBoard.class, metadata);
    }

}

