package com.fastcampus.reserve.interfaces.order;

import com.fastcampus.reserve.domain.order.dto.request.PaymentDto;
import com.fastcampus.reserve.domain.order.dto.request.RegisterOrderDto;
import com.fastcampus.reserve.domain.order.dto.request.RegisterOrderItemDto;
import com.fastcampus.reserve.domain.order.dto.response.OrderHistoriesDto;
import com.fastcampus.reserve.domain.order.dto.response.OrderInfoDto;
import com.fastcampus.reserve.domain.order.dto.response.OrderItemInfoDto;
import com.fastcampus.reserve.domain.order.dto.response.RegisterOrderInfoDto;
import com.fastcampus.reserve.domain.order.dto.response.RegisterOrderItemInfoDto;
import com.fastcampus.reserve.interfaces.order.dto.request.PaymentRequest;
import com.fastcampus.reserve.interfaces.order.dto.request.RegisterOrderItemRequest;
import com.fastcampus.reserve.interfaces.order.dto.request.RegisterOrderRequest;
import com.fastcampus.reserve.interfaces.order.dto.response.OrderHistoriesResponse;
import com.fastcampus.reserve.interfaces.order.dto.response.OrderInfoResponse;
import com.fastcampus.reserve.interfaces.order.dto.response.OrderItemInfoResponse;
import com.fastcampus.reserve.interfaces.order.dto.response.PaymentResponse;
import com.fastcampus.reserve.interfaces.order.dto.response.RegisterOrderInfoResponse;
import com.fastcampus.reserve.interfaces.order.dto.response.RegisterOrderItemInfoResponse;
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

    PaymentDto of(PaymentRequest request);

    RegisterOrderResponse of(String orderToken);

    PaymentResponse of(Long orderId);

    RegisterOrderItemInfoResponse of(RegisterOrderItemInfoDto response);

    RegisterOrderInfoResponse of(RegisterOrderInfoDto response);

    OrderItemInfoResponse of(OrderItemInfoDto response);

    OrderInfoResponse of(OrderInfoDto response);

    OrderHistoriesResponse of(OrderHistoriesDto response);
}
