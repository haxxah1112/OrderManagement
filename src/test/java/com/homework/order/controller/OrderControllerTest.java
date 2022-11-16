package com.homework.order.controller;


import com.google.gson.Gson;
import com.homework.order.dto.OrderDto;
import com.homework.order.dto.OrderItemDto;
import com.homework.order.entity.Item;
import com.homework.order.entity.Order;
import com.homework.order.repository.ItemRepository;
import com.homework.order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
    public void acceptOrder() throws Exception {

        Long orderId = order.getId();

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/orders/"+orderId+"/accept"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void completeOrder() throws Exception {

        Long orderId = order.getId();

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/orders/"+orderId+"/complete"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void getOrder() throws Exception {

        Long orderId = order.getId();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders/"+orderId))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void getOrders() throws Exception {

        Pageable pageable = PageRequest.of(0, 5);
        String json = new Gson().toJson(pageable);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }
}
