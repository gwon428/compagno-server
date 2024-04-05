package domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNoticeBoardImage is a Querydsl query type for NoticeBoardImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNoticeBoardImage extends EntityPathBase<NoticeBoardImage> {

    private static final long serialVersionUID = 871686935L;

    public static final QNoticeBoardImage noticeBoardImage = new QNoticeBoardImage("noticeBoardImage");

    public final NumberPath<Integer> noticeBoardCode = createNumber("noticeBoardCode", Integer.class);

    public final StringPath noticeImage = createString("noticeImage");

    public final NumberPath<Integer> noticeImageCode = createNumber("noticeImageCode", Integer.class);

    public QNoticeBoardImage(String variable) {
        super(NoticeBoardImage.class, forVariable(variable));
    }

    public QNoticeBoardImage(Path<? extends NoticeBoardImage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNoticeBoardImage(PathMetadata metadata) {
        super(NoticeBoardImage.class, metadata);
    }

}

