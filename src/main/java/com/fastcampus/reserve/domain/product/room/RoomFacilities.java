package com.fastcampus.reserve.domain.product.room;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomFacilities {

    private boolean roomBathFacility;
    private boolean roomBath;
    private boolean roomHomeTheater;
    private boolean roomAirCondition;
    private boolean roomTv;
    private boolean roomPc;
    private boolean roomCable;
    private boolean roomInternet;
    private boolean roomRefrigerator;
    private boolean roomToiletries;
    private boolean roomSofa;
    private boolean roomCook;
    private boolean roomTable;
    private boolean roomHairdryer;

    @Builder
    private RoomFacilities(
            boolean roomBathFacility,
            boolean roomBath,
            boolean roomHomeTheater,
            boolean roomAirCondition,
            boolean roomTv,
            boolean roomPc,
            boolean roomCable,
            boolean roomInternet,
            boolean roomRefrigerator,
            boolean roomToiletries,
            boolean roomSofa,
            boolean roomCook,
            boolean roomTable,
            boolean roomHairdryer
    ) {
        this.roomBathFacility = roomBathFacility;
        this.roomBath = roomBath;
        this.roomHomeTheater = roomHomeTheater;
        this.roomAirCondition = roomAirCondition;
        this.roomTv = roomTv;
        this.roomPc = roomPc;
        this.roomCable = roomCable;
        this.roomInternet = roomInternet;
        this.roomRefrigerator = roomRefrigerator;
        this.roomToiletries = roomToiletries;
        this.roomSofa = roomSofa;
        this.roomCook = roomCook;
        this.roomTable = roomTable;
        this.roomHairdryer = roomHairdryer;
    }
}
