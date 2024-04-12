package com.project.compagnoserver.domain.Note;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNote is a Querydsl query type for Note
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNote extends EntityPathBase<Note> {

    private static final long serialVersionUID = 1541899287L;

    public static final QNote note = new QNote("note");

    public final BooleanPath deletedByReceiver = createBoolean("deletedByReceiver");

    public final BooleanPath deletedBySender = createBoolean("deletedBySender");

    public final ListPath<NoteFIle, QNoteFIle> files = this.<NoteFIle, QNoteFIle>createList("files", NoteFIle.class, QNoteFIle.class, PathInits.DIRECT2);

    public final NumberPath<Integer> noteCode = createNumber("noteCode", Integer.class);

    public final StringPath noteContent = createString("noteContent");

    public final DateTimePath<java.util.Date> noteRegiDate = createDateTime("noteRegiDate", java.util.Date.class);

    public final StringPath noteTitle = createString("noteTitle");

    public final StringPath receiver = createString("receiver");

    public final StringPath sender = createString("sender");

    public QNote(String variable) {
        super(Note.class, forVariable(variable));
    }

    public QNote(Path<? extends Note> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNote(PathMetadata metadata) {
        super(Note.class, metadata);
    }

}

