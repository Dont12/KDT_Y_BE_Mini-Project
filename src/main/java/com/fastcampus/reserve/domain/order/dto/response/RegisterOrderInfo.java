package com.fastcampus.reserve.domain.order.dto.response;

import com.fastcampus.reserve.domain.order.RegisterOrder;
import com.fastcampus.reserve.domain.product.Product;
import com.fastcampus.reserve.domain.product.room.Room;
import com.fastcampus.reserve.domain.user.User;
import java.util.List;
import lombok.Builder;

@Builder
public record RegisterOrderInfo(
        String orderToken,
        Integer totalPrice,
        String imageUrl,
        String name,
        String phone,
        List<RegisterOrderItemInfo> registerOrderItems
) {

    public static RegisterOrderInfo from(
            String orderToken,
            RegisterOrder registerOrder,
            Product product,
            Room room,
            User user
    ) {
        return RegisterOrderInfo.builder()
                .orderToken(orderToken)
//                .totalPrice(registerOrder.getTotalPrice())
//                .imageUrl(product.getFirstImage().orElse(null))
                .name(user.getNickname())
                .phone(user.getPhone())
                .registerOrderItems(createRegisterOrderItemInfo(registerOrder, product, room))
                .build();
    }

    private static List<RegisterOrderItemInfo> createRegisterOrderItemInfo(
            RegisterOrder registerOrder,
            Product product,
            Room room
    ) {
        return registerOrder.orderItems().stream()
                .map(orderItem -> RegisterOrderItemInfo.from(
                        product,
                        room,
                        orderItem
                ))
                .toList();
    }
}
