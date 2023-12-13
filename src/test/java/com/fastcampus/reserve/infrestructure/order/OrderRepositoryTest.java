package com.fastcampus.reserve.infrestructure.order;

import static com.fastcampus.reserve.common.CreateUtils.createOrder;
import static com.fastcampus.reserve.common.CreateUtils.createOrderItem;
import static org.assertj.core.api.Assertions.assertThat;

import com.fastcampus.reserve.domain.order.Order;
import com.fastcampus.reserve.domain.order.orderitem.OrderItem;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

@DataJpaTest
@DisplayName("주문 DB 검증")
class OrderRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("주문과 주문 상품 페이징 조회")
    void findAllWithOrderItem() {
        // given
        IntStream.range(0, 10)
                .forEach(i -> {
                    if (i % 2 == 0) {
                        saveOrder();
                        return;
                    }
                    Order order = createOrder();
                    addOrderItem(order);
                    addOrderItem(order);
                    saveEntity(order);
                });

        PageRequest pageable = PageRequest.of(0, 10);
        LocalDateTime recentMonthAgo = LocalDateTime.now().minusMonths(6);

        // when
        Page<Order> result = orderRepository.findAllWithOrderItem(
                -1L,
                recentMonthAgo,
                pageable
        );

        // then
        assertThat(result.getSize()).isEqualTo(10);
    }

    @Test
    @DisplayName("주문과 주문 상품 조회")
    void findByIdWithOrderItem() {
        // given
        Order order = saveOrder();

        // when
        Order result = orderRepository.findByIdWithOrderItem(order.getId()).orElse(null);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(order.getId());
        assertThat(result.getOrderItems().size()).isEqualTo(1);
    }

    private Order saveOrder() {
        Order order = createOrder();
        addOrderItem(order);
        saveEntity(order);
        return order;
    }

    private void addOrderItem(Order order) {
        OrderItem orderItem = createOrderItem();
        orderItem.registerOrder(order);
    }

    private void saveEntity(Object entity) {
        entityManager.persist(entity);
        entityManager.flush();
        entityManager.clear();
    }
}
