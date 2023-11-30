package com.fastcampus.reserve.interfaces.product.dto.response;

import java.util.List;

public record RoomDetailResponse(
    Long id,
    String name,
    int basicGuestCount,
    int maxGuestCount,
    int price,
    String checkInTime,
    String checkOutTime,
    List<String> imageUrls,
    String reserveDate,
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

}
