package com.fastcampus.reserve.domain.order.orderitem;

import com.fastcampus.reserve.domain.BaseTimeEntity;
import com.fastcampus.reserve.domain.order.Order;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class OrderItem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Long roomId;

    @Column(nullable = false)
    private Integer guestCount;

    @Column(nullable = false)
    private Integer price;

    private VisitType visit;
    private Long cartId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Builder
    public OrderItem(
            Long productId,
            Long roomId,
            Integer guestCount,
            Integer price,
            VisitType visitType,
            Long cartId
    ) {
        this.productId = productId;
        this.roomId = roomId;
        this.guestCount = guestCount;
        this.price = price;
        this.visit = visitType;
        this.cartId = cartId;
    }

    public enum VisitType {
        WALK, CAR
    }
}
