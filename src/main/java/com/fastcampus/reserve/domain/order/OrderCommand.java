package com.fastcampus.reserve.domain.order;

import com.fastcampus.reserve.domain.order.dto.request.PaymentDto;

public interface OrderCommand {

    Long payment(RegisterOrder registerOrder, PaymentDto dto);
}
