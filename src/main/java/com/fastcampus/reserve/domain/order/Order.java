package com.fastcampus.reserve.domain.order;

import com.fastcampus.reserve.domain.BaseTimeEntity;
import com.fastcampus.reserve.domain.order.orderitem.OrderItem;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "orders")
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    private StatusType status;

    @Column(length = 100)
    private String userName;

    @Column(length = 50)
    private String userPhone;

    @OneToMany(
            fetch = FetchType.LAZY, mappedBy = "order",
            cascade = CascadeType.PERSIST, orphanRemoval = true
    )
    private final List<OrderItem> orderItems = new ArrayList<>();

    @Builder
    private Order(
            Long userId,
            StatusType statusType,
            String userName,
            String userPhone
    ) {
        this.userId = userId;
        this.status = statusType;
        this.userName = userName;
        this.userPhone = userPhone;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
    }

    public Order init(Long userId) {
        return Order.builder()
                .userId(userId)
                .statusType(StatusType.INIT)
                .build();
    }

    public void payment(
            String userName,
            String userPhone
    ) {
        this.userName = userName;
        this.userPhone = userPhone;
    }

    @Getter
    @RequiredArgsConstructor
    public enum StatusType {
        INIT("예약 신청"),
        COMPLETE("결제 완료"),
        ;

        private final String description;
    }
}
