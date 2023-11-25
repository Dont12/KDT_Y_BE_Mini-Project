package com.fastcampus.reserve.domain.product.room;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoom is a Querydsl query type for Room
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoom extends EntityPathBase<Room> {

    private static final long serialVersionUID = 1828683933L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoom room = new QRoom("room");

    public final com.fastcampus.reserve.domain.QBaseTimeEntity _super = new com.fastcampus.reserve.domain.QBaseTimeEntity(this);

    public final NumberPath<Integer> baseGuestCount = createNumber("baseGuestCount", Integer.class);

    public final TimePath<java.time.LocalTime> checkInTime = createTime("checkInTime", java.time.LocalTime.class);

    public final TimePath<java.time.LocalTime> checkOutTime = createTime("checkOutTime", java.time.LocalTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<RoomImage, QRoomImage> images = this.<RoomImage, QRoomImage>createList("images", RoomImage.class, QRoomImage.class, PathInits.DIRECT2);

    public final NumberPath<Integer> maxGuestCount = createNumber("maxGuestCount", Integer.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final com.fastcampus.reserve.domain.product.QProduct product;

    public final QRoomFacilities roomFacilities;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QRoom(String variable) {
        this(Room.class, forVariable(variable), INITS);
    }

    public QRoom(Path<? extends Room> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoom(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoom(PathMetadata metadata, PathInits inits) {
        this(Room.class, metadata, inits);
    }

    public QRoom(Class<? extends Room> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new com.fastcampus.reserve.domain.product.QProduct(forProperty("product")) : null;
        this.roomFacilities = inits.isInitialized("roomFacilities") ? new QRoomFacilities(forProperty("roomFacilities")) : null;
    }

}

