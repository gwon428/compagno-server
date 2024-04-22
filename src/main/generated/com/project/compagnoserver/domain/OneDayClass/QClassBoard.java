package com.project.compagnoserver.domain.OneDayClass;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QClassBoard is a Querydsl query type for ClassBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClassBoard extends EntityPathBase<ClassBoard> {

    private static final long serialVersionUID = -675995343L;

    public static final QClassBoard classBoard = new QClassBoard("classBoard");

    public final ComparablePath<Character> odcAccompaying = createComparable("odcAccompaying", Character.class);

    public final NumberPath<Integer> odcCode = createNumber("odcCode", Integer.class);

    public final StringPath odcContent = createString("odcContent");

    public final DateTimePath<java.util.Date> odcLastDate = createDateTime("odcLastDate", java.util.Date.class);

    public final DateTimePath<java.sql.Timestamp> odcRegiDate = createDateTime("odcRegiDate", java.sql.Timestamp.class);

    public final DateTimePath<java.util.Date> odcStartDate = createDateTime("odcStartDate", java.util.Date.class);

    public final StringPath odcTitle = createString("odcTitle");

    public QClassBoard(String variable) {
        super(ClassBoard.class, forVariable(variable));
    }

    public QClassBoard(Path<? extends ClassBoard> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClassBoard(PathMetadata metadata) {
        super(ClassBoard.class, metadata);
    }

}

