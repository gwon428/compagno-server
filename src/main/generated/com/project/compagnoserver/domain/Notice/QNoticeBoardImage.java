package com.project.compagnoserver.domain.Notice;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNoticeBoardImage is a Querydsl query type for NoticeBoardImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNoticeBoardImage extends EntityPathBase<NoticeBoardImage> {

    private static final long serialVersionUID = 1532254028L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNoticeBoardImage noticeBoardImage = new QNoticeBoardImage("noticeBoardImage");

    public final QNoticeBoard noticeBoard;

    public final StringPath noticeImage = createString("noticeImage");

    public final NumberPath<Integer> noticeImageCode = createNumber("noticeImageCode", Integer.class);

    public QNoticeBoardImage(String variable) {
        this(NoticeBoardImage.class, forVariable(variable), INITS);
    }

    public QNoticeBoardImage(Path<? extends NoticeBoardImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNoticeBoardImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNoticeBoardImage(PathMetadata metadata, PathInits inits) {
        this(NoticeBoardImage.class, metadata, inits);
    }

    public QNoticeBoardImage(Class<? extends NoticeBoardImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.noticeBoard = inits.isInitialized("noticeBoard") ? new QNoticeBoard(forProperty("noticeBoard"), inits.get("noticeBoard")) : null;
    }

}

