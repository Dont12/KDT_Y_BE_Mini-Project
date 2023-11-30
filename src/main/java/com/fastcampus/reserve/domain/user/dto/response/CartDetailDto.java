package com.fastcampus.reserve.domain.user.dto.response;

import com.fastcampus.reserve.domain.product.room.Room;

public record CartDetailDto(
    Long productId,
    Long roomId,
    String productName,
    String imageUrl,
    String address,
    String roomName,
    int baseGuestCount,
    int maxGuestCount,
    int price,
    String checkInTime,
    String checkOutTime,
    int stock
) {

    public static CartDetailDto from(Room room) {
        return new CartDetailDto(
            room.getProductId(),
            room.getId(),
            room.getProductName(),
            room.getImageUrl(),
            room.getAddress(),
            room.getName(),
            room.getBaseGuestCount(),
            room.getMaxGuestCount(),
            room.getPrice(),
            room.getCheckInTime(),
            room.getCheckOutTime(),
            room.getStock()
        );
    }
}
