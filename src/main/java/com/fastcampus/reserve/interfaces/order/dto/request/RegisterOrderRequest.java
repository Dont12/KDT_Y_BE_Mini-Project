package com.fastcampus.reserve.interfaces.order.dto.request;

import static com.fastcampus.reserve.common.utils.ValidateUtils.emptyCheck;

import jakarta.validation.Valid;
import java.util.List;

public record RegisterOrderRequest(
        @Valid
        List<RegisterOrderItemRequest> registerOrderItems
) {

    public RegisterOrderRequest {
        emptyCheck(registerOrderItems);
    }
}
