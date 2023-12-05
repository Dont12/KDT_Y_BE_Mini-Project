package com.fastcampus.reserve.domain.order;

import static com.fastcampus.reserve.common.response.ErrorCode.ALREADY_ORDER_ROOM;
import static com.fastcampus.reserve.common.response.ErrorCode.NOT_EXIST_REGISTER_ORDER;

import com.fastcampus.reserve.common.exception.CustomException;
import com.fastcampus.reserve.domain.RedisService;
import com.fastcampus.reserve.domain.order.dto.ReserveDateDto;
import com.fastcampus.reserve.domain.order.dto.request.PaymentDto;
import com.fastcampus.reserve.domain.order.dto.request.RegisterOrderDto;
import com.fastcampus.reserve.domain.order.dto.response.OrderHistoriesDto;
import com.fastcampus.reserve.domain.order.dto.response.OrderInfoDto;
import com.fastcampus.reserve.domain.order.dto.response.RegisterOrderInfoDto;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private static final long INIT_ORDER_EXPIRED_TIME = 600;

    private final RedisService redisService;
    private final OrderCommand orderCommand;
    private final OrderReader orderReader;
    private final RegisterOrderFactory registerOrderFactory;

    @Transactional
    public String registerOrder(RegisterOrderDto dto) {
        var registerOrder = registerOrderFactory.create(dto);
        var orderToken = UUID.randomUUID().toString();
        redisService.set(orderToken, registerOrder, INIT_ORDER_EXPIRED_TIME);
        return orderToken;
    }

    public List<String> getPaymentLockNames(String orderToken) {
        var registerOrder = getRegisterOrder(orderToken);
        return getReserveDates(registerOrder).stream()
                .map(ReserveDateDto::getLockName)
                .toList();
    }

    @Transactional
    public Long payment(PaymentDto dto) {
        var registerOrder = getRegisterOrder(dto.orderToken());
        var reserveDates = getReserveDates(registerOrder);
        if (!orderReader.isPayment(reserveDates)) {
            throw new CustomException(ALREADY_ORDER_ROOM);
        }
        return orderCommand.payment(registerOrder, dto);
    }

    public RegisterOrderInfoDto findRegisterOrder(String orderToken) {
        var registerOrder = getRegisterOrder(orderToken);
        return RegisterOrderInfoDto.from(orderToken, registerOrder);
    }

    public OrderHistoriesDto findOrderHistories(Long userId, Pageable pageable) {
        var orders = orderReader.findAllWithOrderItem(userId, pageable);
        return OrderHistoriesDto.from(orders);
    }

    public OrderInfoDto findOrder(Long orderId) {
        var order = orderReader.findByIdWithOrderItem(orderId);
        return OrderInfoDto.from(order);
    }

    public int calcOrderedStock(Long roomId, LocalDate checkInDate, LocalDate checkOutDate) {
        return orderReader.calcOrderedStock(roomId, checkInDate, checkOutDate);
    }

    private List<ReserveDateDto> getReserveDates(RegisterOrder registerOrder) {
        return registerOrder.orderItems().stream()
                .flatMap(orderItem -> {
                    LocalDate checkInDate = orderItem.getCheckInDate();
                    long days = ChronoUnit.DAYS.between(
                            checkInDate,
                            orderItem.getCheckOutDate()
                    );
                    return getReserveDate(orderItem.getRoomId(), checkInDate, days);
                })
                .toList();
    }

    private static Stream<ReserveDateDto> getReserveDate(
            Long roomId,
            LocalDate checkInDate,
            long days
    ) {
        return LongStream.range(0, days)
                .mapToObj(i -> ReserveDateDto.builder()
                        .roomId(roomId)
                        .date(checkInDate.plusDays(i))
                        .build()
                );
    }

    private RegisterOrder getRegisterOrder(String orderToken) {
        return redisService.get(orderToken, RegisterOrder.class)
                .orElseThrow(() -> new CustomException(NOT_EXIST_REGISTER_ORDER));
    }
}
