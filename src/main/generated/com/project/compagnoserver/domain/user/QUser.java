package com.project.compagnoserver.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1903303721L;

    public static final QUser user = new QUser("user");

    public final StringPath userEmail = createString("userEmail");

    public final StringPath userEnrollDate = createString("userEnrollDate");

    public final StringPath userId = createString("userId");

    public final StringPath userImg = createString("userImg");

    public final StringPath userNickname = createString("userNickname");

    public final StringPath userPersonName = createString("userPersonName");

    public final StringPath userPhone = createString("userPhone");

    public final NumberPath<Integer> userPoint = createNumber("userPoint", Integer.class);

    public final StringPath userPwd = createString("userPwd");

    public final DateTimePath<java.util.Date> userQuitDate = createDateTime("userQuitDate", java.util.Date.class);

    public final StringPath userRole = createString("userRole");

    public final StringPath userStatus = createString("userStatus");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

