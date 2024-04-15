package com.project.compagnoserver.domain.Note;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNoteFIle is a Querydsl query type for NoteFIle
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNoteFIle extends EntityPathBase<NoteFIle> {

    private static final long serialVersionUID = 1441436179L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNoteFIle noteFIle = new QNoteFIle("noteFIle");

    public final QNote noteCode;

    public final NumberPath<Integer> noteFileCode = createNumber("noteFileCode", Integer.class);

    public final StringPath noteFileUrl = createString("noteFileUrl");

    public QNoteFIle(String variable) {
        this(NoteFIle.class, forVariable(variable), INITS);
    }

    public QNoteFIle(Path<? extends NoteFIle> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNoteFIle(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNoteFIle(PathMetadata metadata, PathInits inits) {
        this(NoteFIle.class, metadata, inits);
    }

    public QNoteFIle(Class<? extends NoteFIle> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.noteCode = inits.isInitialized("noteCode") ? new QNote(forProperty("noteCode")) : null;
    }

}

