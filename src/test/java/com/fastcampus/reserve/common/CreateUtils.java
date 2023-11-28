package com.fastcampus.reserve.common;

import com.fastcampus.reserve.domain.order.Order;
import com.fastcampus.reserve.domain.order.RegisterOrder;
import com.fastcampus.reserve.domain.order.orderitem.OrderItem;
import com.fastcampus.reserve.domain.order.payment.Payment;
import com.fastcampus.reserve.domain.user.User;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public final class CreateUtils {

    private CreateUtils() {
    }

    public static User createUser() {
        return User.builder()
                .email("user@gmail.com")
                .password("password")
                .nickname("reserveName")
                .phone("010-0000-0000")
                .build();
    }

    public static Order createOrder() {
        return Order.builder()
                .userId(-1L)
                .reserveName("reserveName")
                .reservePhone("010-0000-0000")
                .userName("userName")
                .userPhone("010-0000-0000")
                .payment(Payment.CARD)
                .build();
    }

    public static RegisterOrder createRegisterOrder() {
        return RegisterOrder.builder()
                .userId(-1L)
                .name("reserveName")
                .phone("010-0000-0000")
                .orderItems(List.of(createOrderItem()))
                .build();
    }

    public static OrderItem createOrderItem() {
        return OrderItem.builder()
                .productId(-1L)
                .productName("숙박 업소 이름")
                .roomId(-1L)
                .roomName("방 이름")
                .imageUrl("이미지 URL")
                .guestCount(2)
                .price(99000)
                .baseGuestCount(2)
                .maxGuestCount(4)
                .checkInDate(LocalDate.now())
                .checkInTime(LocalTime.of(15, 0))
                .checkOutDate(LocalDate.now().plusDays(1))
                .checkOutTime(LocalTime.of(12, 0))
                .build();
    }
}
