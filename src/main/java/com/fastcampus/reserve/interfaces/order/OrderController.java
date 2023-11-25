package com.fastcampus.reserve.interfaces.order;

import com.fastcampus.reserve.application.order.OrderFacade;
import com.fastcampus.reserve.common.response.CommonResponse;
import com.fastcampus.reserve.interfaces.order.dto.request.RegisterOrderRequest;
import com.fastcampus.reserve.interfaces.order.dto.response.RegisterOrderResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
            @RequestBody @Valid RegisterOrderRequest request
    ) {
        // todo: Spring Security Authentication 정보에서 User ID 조회야 한다.
        Long userId = 1L;
        var orderToken = orderFacade.registerOrder(mapper.of(request, userId));
        return CommonResponse.ok(mapper.of(orderToken));
    }
}
