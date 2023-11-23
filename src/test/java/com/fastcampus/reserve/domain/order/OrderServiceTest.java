package com.fastcampus.reserve.domain.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import com.fastcampus.reserve.domain.RedisService;
import com.fastcampus.reserve.domain.order.dto.request.RegisterOrderDto;
import com.fastcampus.reserve.domain.order.dto.request.RegisterOrderItemDto;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
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

    @Test
    void registerOrder_성공() {
        // given
        RegisterOrderItemDto registerOrderItemDto = new RegisterOrderItemDto(
                -1L,
                -1L,
                LocalDate.now(),
                LocalTime.of(15, 0),
                LocalDate.now().plusDays(1),
                LocalTime.of(12, 0),
                2,
                120000
        );
        RegisterOrderDto request = new RegisterOrderDto(-1L, List.of(registerOrderItemDto));

        doNothing().when(redisService)
                .set(any(String.class), any(RegisterOrder.class), any(Long.class));

        // when
        String result = orderService.registerOrder(request);

        // then
        assertThat(result).isNotNull();
    }
}
