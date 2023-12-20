package com.mrlee.ktcafe.home.order.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderOptionGroup is a Querydsl query type for OrderOptionGroup
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderOptionGroup extends EntityPathBase<OrderOptionGroup> {

    private static final long serialVersionUID = 1093857931L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderOptionGroup orderOptionGroup = new QOrderOptionGroup("orderOptionGroup");

    public final com.mrlee.ktcafe.common.QBaseTime _super = new com.mrlee.ktcafe.common.QBaseTime(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath name = createString("name");

    public final ListPath<OrderOption, QOrderOption> orderOptions = this.<OrderOption, QOrderOption>createList("orderOptions", OrderOption.class, QOrderOption.class, PathInits.DIRECT2);

    public final QOrderProduct orderProduct;

    public QOrderOptionGroup(String variable) {
        this(OrderOptionGroup.class, forVariable(variable), INITS);
    }

    public QOrderOptionGroup(Path<? extends OrderOptionGroup> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderOptionGroup(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderOptionGroup(PathMetadata metadata, PathInits inits) {
        this(OrderOptionGroup.class, metadata, inits);
    }

    public QOrderOptionGroup(Class<? extends OrderOptionGroup> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.orderProduct = inits.isInitialized("orderProduct") ? new QOrderProduct(forProperty("orderProduct"), inits.get("orderProduct")) : null;
    }

}

