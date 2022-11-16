package com.homework.order.service;

import com.homework.order.dto.OrderDto;
import com.homework.order.dto.OrderItemDto;
import com.homework.order.entity.Item;
import com.homework.order.entity.Order;
import com.homework.order.entity.OrderStatus;
import com.homework.order.repository.ItemRepository;
import com.homework.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderService orderService;


    private Item item;
    private Order order;

    @BeforeEach
    void setUp() {
        Item savedItem = Item.builder()
                .itemName("item1")
                .price(6000L)
                .stockQuantity(500L)
                .useYn("Y")
                .build();

        item = itemRepository.save(savedItem);

        List<OrderItemDto> orderItemDto = Arrays.asList(OrderItemDto.builder()
                .itemId(item.getId())
                .count(2L)
                .itemPrice(6000L)
                .build());

        OrderDto orderDto = OrderDto.builder()
                .email("test@test.com")
                .orderItemList(orderItemDto)
                .build();

        order = orderService.saveOrder(orderDto);
    }

    @Test
    @DisplayName("주문의 상태값을 접수로 변경한다.")
    void acceptOrder() {
        //given
        Order acceptOrder = orderRepository.findById(order.getId())
                .orElse(null);

        //when
        Order updateOrder = orderService.updateOrder(acceptOrder.getId(), OrderStatus.ACCEPT);

        //then
        assertThat(updateOrder.getOrderStatus()).isEqualTo(OrderStatus.ACCEPT);
    }

    @Test
    @DisplayName("주문의 상태값을 완료로 변경한다.")
    void completeOrder() {
        //given
        Order completeOrder = orderRepository.findById(order.getId())
                .orElse(null);

        //when
        Order order = orderService.updateOrder(completeOrder.getId(), OrderStatus.COMPLETE);

        //then
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.COMPLETE);
    }

    @Test
    @DisplayName("주문에 대한 상세정보를 조회한다.")
    void getOrder() {
        //given

        //when
        Order getOrder = orderRepository.findById(order.getId())
                .orElse(null);

        //then
        assertEquals(order.getId(), getOrder.getId());
    }

    @Test
    @DisplayName("주문 목록을 조회한다.")
    @Transactional
    void getOrders() {
        //given

        //when
        List<Order> getOrders = orderRepository.findAll();

        //then
        assertThat(getOrders).contains(order);
    }
}
