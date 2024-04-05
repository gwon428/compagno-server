package domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOnedayClass is a Querydsl query type for OnedayClass
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOnedayClass extends EntityPathBase<OnedayClass> {

    private static final long serialVersionUID = 918760792L;

    public static final QOnedayClass onedayClass = new QOnedayClass("onedayClass");

    public final NumberPath<Integer> adcAccompaying = createNumber("adcAccompaying", Integer.class);

    public final StringPath odcContent = createString("odcContent");

    public final DateTimePath<java.util.Date> odcLastDate = createDateTime("odcLastDate", java.util.Date.class);

    public final NumberPath<Integer> odcNum = createNumber("odcNum", Integer.class);

    public final SimplePath<java.security.Timestamp> odcRegiDate = createSimple("odcRegiDate", java.security.Timestamp.class);

    public final DateTimePath<java.util.Date> odcStartDate = createDateTime("odcStartDate", java.util.Date.class);

    public final StringPath odcTitle = createString("odcTitle");

    public final NumberPath<User> user = createNumber("user", User.class);

    public QOnedayClass(String variable) {
        super(OnedayClass.class, forVariable(variable));
    }

    public QOnedayClass(Path<? extends OnedayClass> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOnedayClass(PathMetadata metadata) {
        super(OnedayClass.class, metadata);
    }

}

