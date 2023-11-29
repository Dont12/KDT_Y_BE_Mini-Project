package com.fastcampus.reserve.domain.order;

import static com.fastcampus.reserve.common.CreateUtils.createOrder;
import static com.fastcampus.reserve.common.CreateUtils.createOrderItem;
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
import com.fastcampus.reserve.domain.order.dto.response.OrderHistoriesDto;
import com.fastcampus.reserve.domain.order.dto.response.OrderInfoDto;
import com.fastcampus.reserve.domain.order.dto.response.RegisterOrderInfoDto;
import com.fastcampus.reserve.domain.order.orderitem.OrderItem;
import com.fastcampus.reserve.domain.order.payment.Payment;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

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
    @Mock
    private OrderReader orderReader;

    @Test
    @DisplayName("숙소 예약 신청 성공")
    void registerOrder() {
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

    @Test
    @DisplayName("주문 내역 조회")
    void findOrderHistories() {
        // given
        PageRequest pageable = PageRequest.of(0, 10);
        List<Order> orderHistories = LongStream.range(0, 10)
                .mapToObj(i -> {
                    Order order = getOrder(i);
                    OrderItem orderItem = createOrderItem();
                    ReflectionTestUtils.setField(orderItem, "id", i);
                    orderItem.registerOrder(order);
                    return order;
                })
                .toList();

        when(orderReader.findAllWithOrderItem(pageable))
                .thenReturn(new PageImpl<>(orderHistories, pageable, 10));

        // when
        OrderHistoriesDto result = orderService.findOrderHistories(pageable);

        // then
        assertThat(result.size()).isEqualTo(10);
    }

    @Test
    @DisplayName("주문 내역 상세 조회")
    void findOrder() {
        // given
        Long orderId = -1L;

        Order order = getOrder(orderId);
        order.addOrderItem(createOrderItem());

        when(orderReader.findByIdWithOrderItem(orderId))
                .thenReturn(order);

        // when
        OrderInfoDto result = orderService.findOrder(orderId);

        // then
        assertThat(result).isNotNull();
    }

    private Order getOrder(long i) {
        Order order = createOrder();
        ReflectionTestUtils.setField(order, "id", i);
        ReflectionTestUtils.setField(
                order,
                "createdDate",
                LocalDateTime.of(2023, 11, 25, 15, 30)
        );
        return order;
    }
}
