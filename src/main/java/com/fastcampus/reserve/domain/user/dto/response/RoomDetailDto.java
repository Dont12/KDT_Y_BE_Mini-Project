package com.fastcampus.reserve.domain.user.dto.response;

import com.fastcampus.reserve.domain.product.room.Room;

public record RoomDetailDto(
    Long productId,
    Long roomId,
    String productName,
    String imageUrl,
    String address,
    int baseGuestCount,
    int maxGuestCount,
    int price,
    String checkInTime,
    String checkOutTime,
    int stock
) {

    public static RoomDetailDto from(Room room) {
        return new RoomDetailDto(
            room.getProductId(),
            room.getId(),
            room.getProductName(),
            room.getImageUrl(),
            room.getAddress(),
            room.getBaseGuestCount(),
            room.getMaxGuestCount(),
            room.getPrice(),
            room.getCheckInTime(),
            room.getCheckOutTime(),
            room.getStock()
        );
    }
}
