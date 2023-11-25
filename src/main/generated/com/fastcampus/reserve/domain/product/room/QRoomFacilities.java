package com.fastcampus.reserve.domain.product.room;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRoomFacilities is a Querydsl query type for RoomFacilities
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QRoomFacilities extends BeanPath<RoomFacilities> {

    private static final long serialVersionUID = -1162862850L;

    public static final QRoomFacilities roomFacilities = new QRoomFacilities("roomFacilities");

    public final BooleanPath roomAirCondition = createBoolean("roomAirCondition");

    public final BooleanPath roomBath = createBoolean("roomBath");

    public final BooleanPath roomBathFacility = createBoolean("roomBathFacility");

    public final BooleanPath roomCable = createBoolean("roomCable");

    public final BooleanPath roomCook = createBoolean("roomCook");

    public final BooleanPath roomHairdryer = createBoolean("roomHairdryer");

    public final BooleanPath roomHomeTheater = createBoolean("roomHomeTheater");

    public final BooleanPath roomInternet = createBoolean("roomInternet");

    public final BooleanPath roomPc = createBoolean("roomPc");

    public final BooleanPath roomRefrigerator = createBoolean("roomRefrigerator");

    public final BooleanPath roomSofa = createBoolean("roomSofa");

    public final BooleanPath roomTable = createBoolean("roomTable");

    public final BooleanPath roomToiletries = createBoolean("roomToiletries");

    public final BooleanPath roomTv = createBoolean("roomTv");

    public QRoomFacilities(String variable) {
        super(RoomFacilities.class, forVariable(variable));
    }

    public QRoomFacilities(Path<? extends RoomFacilities> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRoomFacilities(PathMetadata metadata) {
        super(RoomFacilities.class, metadata);
    }

}

