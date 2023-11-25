package com.fastcampus.reserve.domain.order;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrder is a Querydsl query type for Order
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrder extends EntityPathBase<Order> {

    private static final long serialVersionUID = -774769732L;

    public static final QOrder order = new QOrder("order1");

    public final com.fastcampus.reserve.domain.QBaseTimeEntity _super = new com.fastcampus.reserve.domain.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.fastcampus.reserve.domain.order.orderitem.OrderItem, com.fastcampus.reserve.domain.order.orderitem.QOrderItem> orderItems = this.<com.fastcampus.reserve.domain.order.orderitem.OrderItem, com.fastcampus.reserve.domain.order.orderitem.QOrderItem>createList("orderItems", com.fastcampus.reserve.domain.order.orderitem.OrderItem.class, com.fastcampus.reserve.domain.order.orderitem.QOrderItem.class, PathInits.DIRECT2);

    public final EnumPath<Order.StatusType> status = createEnum("status", Order.StatusType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final StringPath userName = createString("userName");

    public final StringPath userPhone = createString("userPhone");

    public QOrder(String variable) {
        super(Order.class, forVariable(variable));
    }

    public QOrder(Path<? extends Order> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrder(PathMetadata metadata) {
        super(Order.class, metadata);
    }

}

