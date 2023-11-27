package com.fastcampus.reserve.domain.order.dto.response;

import com.fastcampus.reserve.domain.order.orderitem.OrderItem;
import com.fastcampus.reserve.domain.product.Product;
import com.fastcampus.reserve.domain.product.room.Room;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;

@Builder
public record RegisterOrderItemInfo(
        Long productId,
        String productName,
        Long roomId,
        String roomName,
        String imageUrl,
        int baseGuestCount,
        int maxGuestCount,
        LocalDate checkInDate,
        LocalTime checkInTime,
        LocalDate checkOutDate,
        LocalTime checkOutTime,
        int guestCount,
        int price
) {

    public static RegisterOrderItemInfo from(
            Product product,
            Room room,
            OrderItem orderItem
    ) {
        return RegisterOrderItemInfo.builder()
                .productId(product.getId())
                .productName(product.getName())
                .roomId(room.getId())
                .roomName(room.getName())
                .imageUrl(room.getFirstImage().orElse(null))
                .baseGuestCount(room.getBaseGuestCount())
                .maxGuestCount(room.getMaxGuestCount())
                .checkInDate(orderItem.getCheckInDate())
                .checkInTime(orderItem.getCheckInTime())
                .checkOutDate(orderItem.getCheckOutDate())
                .checkOutTime(orderItem.getCheckOutTime())
                .guestCount(orderItem.getGuestCount())
                .price(orderItem.getPrice())
                .build();
    }
}
