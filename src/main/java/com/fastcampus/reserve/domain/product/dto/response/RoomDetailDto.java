package com.fastcampus.reserve.domain.product.dto.response;

import com.fastcampus.reserve.domain.product.room.Room;
import java.time.LocalDate;
import java.util.List;

public record RoomDetailDto(
    Long id,
    String name,
    int basicGuestCount,
    int maxGuestCount,
    int price,
    String checkInTime,
    String checkOutTime,
    List<String> imageUrls,
    LocalDate reserveDate,
    int stock,
    String roomBathFacility,
    String roomBath,
    String roomHomeTheater,
    String roomAircondition,
    String roomTv,
    String roomPc,
    String roomCable,
    String roomInternet,
    String roomRefrigerator,
    String roomSofa,
    String roomCook,
    String roomTable,
    String roomHairdryer
) {

    public static RoomDetailDto from(
        Room room, LocalDate reserveDate, FacilityInfoDto facilityInfo
    ) {
        return new RoomDetailDto(
            room.getId(),
            room.getName(),
            room.getBaseGuestCount(),
            room.getMaxGuestCount(),
            room.getPrice(),
            room.getCheckInTime(),
            room.getCheckOutTime(),
            room.getImageUrls(),
            reserveDate,
            room.getStock(),
            facilityInfo.roombathfacility(),
            facilityInfo.roombath(),
            facilityInfo.roomhometheater(),
            facilityInfo.roomaircondition(),
            facilityInfo.roomtv(),
            facilityInfo.roompc(),
            facilityInfo.roomcable(),
            facilityInfo.roominternet(),
            facilityInfo.roomrefrigerator(),
            facilityInfo.roomsofa(),
            facilityInfo.roomcook(),
            facilityInfo.roomcable(),
            facilityInfo.roomhairdryer()
        );
    }

    public RoomDetailDto updateStock(int stock) {
        return new RoomDetailDto(
            this.id,
            this.name,
            this.basicGuestCount,
            this.maxGuestCount,
            this.price,
            this.checkInTime,
            this.checkOutTime,
            this.imageUrls,
            this.reserveDate,
            stock,
            this.roomBathFacility,
            this.roomBath,
            this.roomHomeTheater,
            this.roomAircondition,
            this.roomTv,
            this.roomPc,
            this.roomCable,
            this.roomInternet,
            this.roomRefrigerator,
            this.roomSofa,
            this.roomCook,
            this.roomTable,
            this.roomHairdryer
        );
    }
}
