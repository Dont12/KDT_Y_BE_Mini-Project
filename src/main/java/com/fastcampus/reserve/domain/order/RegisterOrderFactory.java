package com.fastcampus.reserve.domain.order;

import com.fastcampus.reserve.domain.order.dto.request.RegisterOrderDto;

public interface RegisterOrderFactory {

    RegisterOrder create(RegisterOrderDto registerOrder);
}
