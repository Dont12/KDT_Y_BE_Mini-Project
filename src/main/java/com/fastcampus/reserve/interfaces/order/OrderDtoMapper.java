package com.fastcampus.reserve.interfaces.order;

import com.fastcampus.reserve.domain.order.dto.request.RegisterOrderDto;
import com.fastcampus.reserve.domain.order.dto.request.RegisterOrderItemDto;
import com.fastcampus.reserve.interfaces.order.dto.request.RegisterOrderItemRequest;
import com.fastcampus.reserve.interfaces.order.dto.request.RegisterOrderRequest;
import com.fastcampus.reserve.interfaces.order.dto.response.RegisterOrderResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface OrderDtoMapper {

    RegisterOrderItemDto of(RegisterOrderItemRequest request);

    RegisterOrderDto of(RegisterOrderRequest request, Long userId);

    RegisterOrderResponse of(String orderToken);
}
