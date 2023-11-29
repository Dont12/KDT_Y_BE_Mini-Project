package com.fastcampus.reserve.interfaces.order.dto.response;

import java.util.List;

public record OrderHistoriesResponse(
        int size,
        int pageNumber,
        int totalPages,
        long totalElements,
        List<OrderHistoryResponse> orderHistories
) {

}
