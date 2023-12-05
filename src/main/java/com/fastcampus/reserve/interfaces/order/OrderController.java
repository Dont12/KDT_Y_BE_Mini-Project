package com.fastcampus.reserve.interfaces.order;

import com.fastcampus.reserve.application.order.OrderFacade;
import com.fastcampus.reserve.common.response.CommonResponse;
import com.fastcampus.reserve.common.security.PrincipalDetails;
import com.fastcampus.reserve.interfaces.order.dto.request.PaymentRequest;
import com.fastcampus.reserve.interfaces.order.dto.request.RegisterOrderRequest;
import com.fastcampus.reserve.interfaces.order.dto.response.OrderHistoriesResponse;
import com.fastcampus.reserve.interfaces.order.dto.response.OrderInfoResponse;
import com.fastcampus.reserve.interfaces.order.dto.response.PaymentResponse;
import com.fastcampus.reserve.interfaces.order.dto.response.RegisterOrderInfoResponse;
import com.fastcampus.reserve.interfaces.order.dto.response.RegisterOrderResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/orders")
public class OrderController {

    private final OrderFacade orderFacade;
    private final OrderDtoMapper mapper;

    @PostMapping
    public CommonResponse<RegisterOrderResponse> registerOrder(
            @RequestBody @Valid RegisterOrderRequest request,
            @AuthenticationPrincipal PrincipalDetails principal
    ) {
        Long userId = principal.id();
        var orderToken = orderFacade.registerOrder(mapper.of(request, userId));
        return CommonResponse.ok(mapper.of(orderToken));
    }

    @PostMapping("/payment")
    public CommonResponse<PaymentResponse> payment(
            @RequestBody @Valid PaymentRequest request
    ) {
        var orderId = orderFacade.payment(mapper.of(request));
        return CommonResponse.ok(mapper.of(orderId));
    }

    @GetMapping
    public CommonResponse<RegisterOrderInfoResponse> getRegisterOrder(
            @RequestParam String orderToken
    ) {
        var response = orderFacade.findRegisterOrder(orderToken);
        return CommonResponse.ok(mapper.of(response));
    }

    @GetMapping("/history")
    public CommonResponse<OrderHistoriesResponse> getOrderHistories(
            Pageable pageable,
            @AuthenticationPrincipal PrincipalDetails principal
    ) {
        Long userId = principal.id();
        var response = orderFacade.findOrderHistories(userId, pageable);
        return CommonResponse.ok(mapper.of(response));
    }

    @GetMapping("/history/{id}")
    public CommonResponse<OrderInfoResponse> getOrder(
            @PathVariable Long id
    ) {
        var response = orderFacade.findOrder(id);
        return CommonResponse.ok(mapper.of(response));
    }
}
