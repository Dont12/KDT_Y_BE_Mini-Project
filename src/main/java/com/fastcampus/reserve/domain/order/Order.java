package com.fastcampus.reserve.domain.order;

import com.fastcampus.reserve.domain.BaseTimeEntity;
import com.fastcampus.reserve.domain.order.orderitem.OrderItem;
import com.fastcampus.reserve.domain.order.payment.Payment;
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

    @Column(length = 100)
    private String reserveName;

    @Column(length = 50)
    private String reservePhone;

    @Column(length = 100)
    private String userName;

    @Column(length = 50)
    private String userPhone;

    @Enumerated(EnumType.STRING)
    private Payment payment;

    @OneToMany(
            fetch = FetchType.LAZY, mappedBy = "order",
            cascade = CascadeType.PERSIST, orphanRemoval = true
    )
    private final List<OrderItem> orderItems = new ArrayList<>();

    @Builder
    private Order(
            Long userId,
            String reserveName,
            String reservePhone,
            String userName,
            String userPhone,
            Payment payment
    ) {
        this.userId = userId;
        this.reserveName = reserveName;
        this.reservePhone = reservePhone;
        this.userName = userName;
        this.userPhone = userPhone;
        this.payment = payment;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
    }
}
