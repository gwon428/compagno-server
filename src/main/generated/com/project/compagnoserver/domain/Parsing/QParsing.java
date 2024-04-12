package com.project.compagnoserver.domain.Parsing;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QParsing is a Querydsl query type for Parsing
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QParsing extends EntityPathBase<Parsing> {

    private static final long serialVersionUID = 393726491L;

    public static final QParsing parsing = new QParsing("parsing");

    public final StringPath addr = createString("addr");

    public final StringPath fee = createString("fee");

    public final StringPath latitude = createString("latitude");

    public final StringPath longtitude = createString("longtitude");

    public final StringPath mainCate = createString("mainCate");

    public final NumberPath<Integer> mainCateCode = createNumber("mainCateCode", Integer.class);

    public final StringPath mainregCate = createString("mainregCate");

    public final NumberPath<Integer> mainregCode = createNumber("mainregCode", Integer.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> num = createNumber("num", Integer.class);

    public final StringPath parking = createString("parking");

    public final StringPath phone = createString("phone");

    public final StringPath roadAddr = createString("roadAddr");

    public final StringPath subCate = createString("subCate");

    public final NumberPath<Integer> subCateCode = createNumber("subCateCode", Integer.class);

    public final StringPath subregCate = createString("subregCate");

    public final StringPath url = createString("url");

    public QParsing(String variable) {
        super(Parsing.class, forVariable(variable));
    }

    public QParsing(Path<? extends Parsing> path) {
        super(path.getType(), path.getMetadata());
    }

    public QParsing(PathMetadata metadata) {
        super(Parsing.class, metadata);
    }

}

