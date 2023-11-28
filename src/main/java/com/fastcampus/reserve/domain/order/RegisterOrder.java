package com.fastcampus.reserve.domain.order;

import static com.fastcampus.reserve.common.utils.ValidateUtils.emptyCheck;
import static com.fastcampus.reserve.common.utils.ValidateUtils.nullCheck;

import com.fastcampus.reserve.domain.order.orderitem.OrderItem;
import java.util.List;
import lombok.Builder;

@Builder
public record RegisterOrder(
        Long userId,
        String name,
        String phone,
        List<OrderItem> orderItems
) {

    public RegisterOrder {
        nullCheck(name, phone);
        emptyCheck(orderItems);
    }

    public int calcTotalPrice() {
        return orderItems.stream()
                .mapToInt(OrderItem::getPrice)
                .sum();
    }
}
