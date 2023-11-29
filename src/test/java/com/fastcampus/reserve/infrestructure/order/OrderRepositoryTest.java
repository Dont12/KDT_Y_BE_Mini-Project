package com.fastcampus.reserve.infrestructure.order;

import static com.fastcampus.reserve.common.CreateUtils.createOrder;
import static com.fastcampus.reserve.common.CreateUtils.createOrderItem;
import static org.assertj.core.api.Assertions.assertThat;

import com.fastcampus.reserve.domain.order.Order;
import com.fastcampus.reserve.domain.order.orderitem.OrderItem;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@DisplayName("주문 DB 검증")
class OrderRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("주문과 주문 상품 조회")
    void findByIdWithOrderItem() {
        // given
        Order order = createOrder();
        OrderItem orderItem = createOrderItem();
        orderItem.registerOrder(order);
        saveEntity(order);

        // when
        Order result = orderRepository.findByIdWithOrderItem(order.getId()).orElse(null);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(order.getId());
        assertThat(result.getOrderItems().size()).isEqualTo(1);
    }

    private void saveEntity(Object entity) {
        entityManager.persist(entity);
        entityManager.flush();
        entityManager.clear();
    }
}
