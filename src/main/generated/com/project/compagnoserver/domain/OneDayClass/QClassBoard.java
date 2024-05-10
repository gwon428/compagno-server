package com.project.compagnoserver.domain.OneDayClass;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClassBoard is a Querydsl query type for ClassBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClassBoard extends EntityPathBase<ClassBoard> {

    private static final long serialVersionUID = -675995343L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClassBoard classBoard = new QClassBoard("classBoard");

    public final ListPath<ClassBoardMainImage, QClassBoardMainImage> images = this.<ClassBoardMainImage, QClassBoardMainImage>createList("images", ClassBoardMainImage.class, QClassBoardMainImage.class, PathInits.DIRECT2);

    public final ComparablePath<Character> odcAccompaying = createComparable("odcAccompaying", Character.class);

    public final NumberPath<Integer> odcCode = createNumber("odcCode", Integer.class);

    public final StringPath odcContent = createString("odcContent");

    public final DateTimePath<java.util.Date> odcLastDate = createDateTime("odcLastDate", java.util.Date.class);

    public final DateTimePath<java.util.Date> odcRegiDate = createDateTime("odcRegiDate", java.util.Date.class);

    public final DateTimePath<java.util.Date> odcStartDate = createDateTime("odcStartDate", java.util.Date.class);

    public final StringPath odcTitle = createString("odcTitle");

    public final com.project.compagnoserver.domain.user.QUser user;

    public QClassBoard(String variable) {
        this(ClassBoard.class, forVariable(variable), INITS);
    }

    public QClassBoard(Path<? extends ClassBoard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClassBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClassBoard(PathMetadata metadata, PathInits inits) {
        this(ClassBoard.class, metadata, inits);
    }

    public QClassBoard(Class<? extends ClassBoard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.project.compagnoserver.domain.user.QUser(forProperty("user")) : null;
    }

}

