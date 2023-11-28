package com.fastcampus.reserve.domain.order;

import static com.fastcampus.reserve.common.CreateUtils.createRegisterOrder;
import static com.fastcampus.reserve.common.CreateUtils.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fastcampus.reserve.domain.RedisService;
import com.fastcampus.reserve.domain.order.dto.request.PaymentDto;
import com.fastcampus.reserve.domain.order.dto.request.RegisterOrderDto;
import com.fastcampus.reserve.domain.order.dto.request.RegisterOrderItemDto;
import com.fastcampus.reserve.domain.order.dto.response.RegisterOrderInfoDto;
import com.fastcampus.reserve.domain.order.payment.Payment;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("주문 검증")
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private RedisService redisService;
    @Mock
    private RegisterOrderFactory registerOrderFactory;

    @Mock
    private OrderCommand orderCommand;

    @Test
    @DisplayName("숙소 예약 신청 성공")
    void registerOrder() {
        // given
        RegisterOrderItemDto registerOrderItemDto = new RegisterOrderItemDto(
                -1L,
                -1L,
                LocalDate.now(),
                "15:00",
                LocalDate.now().plusDays(1),
                "12:00",
                2,
                120000
        );
        RegisterOrderDto request = new RegisterOrderDto(-1L, List.of(registerOrderItemDto));
        RegisterOrder registerOrder = request.toEntity(createUser());

        when(registerOrderFactory.create(request))
                .thenReturn(registerOrder);
        doNothing().when(redisService)
                .set(any(String.class), any(RegisterOrder.class), any(Long.class));

        // when
        String result = orderService.registerOrder(request);

        // then
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("예약 결제 성공")
    void payment() {
        // given
        PaymentDto request = new PaymentDto(
                "OrderToken",
                "UserName",
                "010-0000-0000",
                139000,
                Payment.CARD
        );

        RegisterOrder registerOrder = createRegisterOrder();

        when(redisService.get("OrderToken", RegisterOrder.class))
                .thenReturn(Optional.of(registerOrder));
        when(orderCommand.payment(any(RegisterOrder.class), any(PaymentDto.class)))
                .thenReturn(-1L);

        // when
        Long result = orderService.payment(request);

        // then
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("예약 신청 조회")
    void findRegisterOrder() {
        // given
        String orderToken = "orderToken";

        when(redisService.get(orderToken, RegisterOrder.class))
                .thenReturn(Optional.of(createRegisterOrder()));

        // when
        RegisterOrderInfoDto result = orderService.findRegisterOrder(orderToken);

        // then
        assertThat(result).isNotNull();
    }
}
