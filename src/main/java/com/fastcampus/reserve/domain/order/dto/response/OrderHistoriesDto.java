package com.fastcampus.reserve.domain.order.dto.response;

import com.fastcampus.reserve.domain.order.Order;
import java.util.List;
import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Builder
public record OrderHistoriesDto(
        int size,
        int pageNumber,
        int totalPages,
        long totalElements,
        List<OrderHistoryDto> orderHistories
) {

    public static OrderHistoriesDto from(Page<Order> orders) {
        Pageable pageable = orders.getPageable();

        return OrderHistoriesDto.builder()
                .size(pageable.getPageSize())
                .pageNumber(pageable.getPageNumber() + 1)
                .totalPages(orders.getTotalPages())
                .totalElements(orders.getTotalElements())
                .orderHistories(getOrderHistories(orders.getContent()))
                .build();
    }

    private static List<OrderHistoryDto> getOrderHistories(List<Order> orders) {
        return orders.stream()
                .map(OrderHistoryDto::from)
                .toList();
    }
}
