package com.fastcampus.reserve.domain.product;

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

    private static final long serialVersionUID = -1712146690L;

    public static final QProduct product = new QProduct("product");

    public final com.fastcampus.reserve.domain.QBaseTimeEntity _super = new com.fastcampus.reserve.domain.QBaseTimeEntity(this);

    public final StringPath address = createString("address");

    public final EnumPath<Product.CategoryType> category = createEnum("category", Product.CategoryType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<ProductImage, QProductImage> images = this.<ProductImage, QProductImage>createList("images", ProductImage.class, QProductImage.class, PathInits.DIRECT2);

    public final StringPath latitude = createString("latitude");

    public final StringPath longitude = createString("longitude");

    public final StringPath name = createString("name");

    public final ListPath<com.fastcampus.reserve.domain.product.room.Room, com.fastcampus.reserve.domain.product.room.QRoom> rooms = this.<com.fastcampus.reserve.domain.product.room.Room, com.fastcampus.reserve.domain.product.room.QRoom>createList("rooms", com.fastcampus.reserve.domain.product.room.Room.class, com.fastcampus.reserve.domain.product.room.QRoom.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public final StringPath zipCode = createString("zipCode");

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

