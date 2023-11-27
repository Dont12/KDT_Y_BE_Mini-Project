package com.fastcampus.reserve.domain.order;

import static com.fastcampus.reserve.common.utils.ValidateUtils.emptyCheck;

import com.fastcampus.reserve.domain.order.orderitem.OrderItem;
import java.util.List;
import lombok.Builder;

@Builder
public record RegisterOrder(
        Long userId,
        List<OrderItem> orderItems
) {

    public RegisterOrder {
        emptyCheck(orderItems);
    }

    public int getTotalPrice() {
        return orderItems.stream()
                .mapToInt(OrderItem::getPrice)
                .sum();
    }
}
