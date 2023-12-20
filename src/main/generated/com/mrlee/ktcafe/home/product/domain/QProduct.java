package com.mrlee.ktcafe.home.product.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = -275687969L;

    public static final QProduct product = new QProduct("product");

    public final com.mrlee.ktcafe.common.QBaseTime _super = new com.mrlee.ktcafe.common.QBaseTime(this);

    public final StringPath addImage1 = createString("addImage1");

    public final StringPath addImage2 = createString("addImage2");

    public final NumberPath<Integer> basicPrice = createNumber("basicPrice", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath mainImage = createString("mainImage");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath name = createString("name");

    public final ListPath<ProductOptionGroup, QProductOptionGroup> productOptionGroups = this.<ProductOptionGroup, QProductOptionGroup>createList("productOptionGroups", ProductOptionGroup.class, QProductOptionGroup.class, PathInits.DIRECT2);

    public final EnumPath<Product.ProductType> productType = createEnum("productType", Product.ProductType.class);

    public QProduct(String variable) {
        super(Product.class, forVariable(variable));
    }

    public QProduct(Path<? extends Product> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProduct(PathMetadata metadata) {
        super(Product.class, metadata);
    }

}

