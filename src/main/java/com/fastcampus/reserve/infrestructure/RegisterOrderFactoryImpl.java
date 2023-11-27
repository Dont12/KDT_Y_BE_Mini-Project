package com.fastcampus.reserve.infrestructure;

import com.fastcampus.reserve.domain.order.RegisterOrder;
import com.fastcampus.reserve.domain.order.RegisterOrderFactory;
import com.fastcampus.reserve.domain.order.dto.request.RegisterOrderDto;
import com.fastcampus.reserve.domain.order.dto.request.RegisterOrderItemDto;
import com.fastcampus.reserve.domain.order.orderitem.OrderItem;
import com.fastcampus.reserve.domain.product.Product;
import com.fastcampus.reserve.domain.product.ProductReader;
import com.fastcampus.reserve.domain.product.room.Room;
import com.fastcampus.reserve.domain.product.room.RoomReader;
import com.fastcampus.reserve.domain.user.UserReader;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegisterOrderFactoryImpl implements RegisterOrderFactory {

    private final UserReader userReader;
    private final ProductReader productReader;
    private final RoomReader roomReader;

    public RegisterOrder create(RegisterOrderDto dto) {
        var user = userReader.findById(dto.userId());
        List<OrderItem> orderItems = dto.registerOrderItems().stream()
                .map(registerOrderItem -> {
                    var product = productReader.findByIdWithImage(registerOrderItem.productId());
                    var room = roomReader.findByIdWithImage(registerOrderItem.roomId());
                    return createOrderItem(registerOrderItem, product, room);
                })
                .collect(Collectors.toList());
        return RegisterOrder.builder()
                .userId(user.getId())
                .orderItems(orderItems)
                .build();
    }

    private OrderItem createOrderItem(
            RegisterOrderItemDto registerOrderItem,
            Product product,
            Room room
    ) {
        return OrderItem.builder()
                .productId(product.getId())
                .productName(product.getName())
                .roomId(room.getId())
                .roomName(room.getName())
                .imageUrl(room.getFirstImage().orElse(null))
                .guestCount(registerOrderItem.guestCount())
                .price(registerOrderItem.price())
                .checkInTime(registerOrderItem.checkInTime())
                .checkInDate(registerOrderItem.checkInDate())
                .checkOutTime(registerOrderItem.checkOutTime())
                .checkOutDate(registerOrderItem.checkOutDate())
                .build();
    }
}
