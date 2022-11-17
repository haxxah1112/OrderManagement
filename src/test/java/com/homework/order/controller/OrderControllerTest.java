package com.homework.order.controller;


import com.google.gson.Gson;
import com.homework.order.dto.OrderDto;
import com.homework.order.dto.OrderItemDto;
import com.homework.order.entity.Item;
import com.homework.order.entity.Order;
import com.homework.order.repository.ItemRepository;
import com.homework.order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
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
    @DisplayName("주문의 상태값을 접수로 변경한다.")
    public void acceptOrder() throws Exception {
        //given
        Long orderId = order.getId();

        //when
        ResultActions actions = mockMvc.perform(patch("/api/orders/{orderId}/accept", String.valueOf(orderId))
                .accept(MediaType.APPLICATION_JSON)
        );

        //then
        actions.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("order-patch-accept",
                        pathParameters(
                                parameterWithName("orderId").description("주문 고유번호")
                        ),
                        responseFields(
                                fieldWithPath("code").description("결과코드"),
                                fieldWithPath("message").description("메시지"),
                                fieldWithPath("data").description("데이터"),
                                fieldWithPath("data.id").description("주문 고유번호"),
                                fieldWithPath("data.email").description("주문한 회원 이메일"),
                                fieldWithPath("data.orderStatus").description("주문 상태"),
                                fieldWithPath("data.orderTime").description("주문 시간"),
                                fieldWithPath("data.totalPrice").description("총 주문 금액"),
                                fieldWithPath("data.createdAt").description("주문 생성일"),
                                fieldWithPath("data.modifiedAt").description("주문 수정일")
                        )
                ));

    }

    @Test
    @DisplayName("주문의 상태값을 완료로 변경한다.")
    public void completeOrder() throws Exception {

        //given
        Long orderId = order.getId();

        //when
        ResultActions actions = mockMvc.perform(patch("/api/orders/{orderId}/complete", String.valueOf(orderId))
                .accept(MediaType.APPLICATION_JSON)
        );

        //then
        actions.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("order-patch-complete",
                        pathParameters(
                                parameterWithName("orderId").description("주문 고유번호")
                        ),
                        responseFields(
                                fieldWithPath("code").description("결과코드"),
                                fieldWithPath("message").description("메시지"),
                                fieldWithPath("data").description("데이터"),
                                fieldWithPath("data.id").description("주문 고유번호"),
                                fieldWithPath("data.email").description("주문한 회원 이메일"),
                                fieldWithPath("data.orderStatus").description("주문 상태"),
                                fieldWithPath("data.orderTime").description("주문 시간"),
                                fieldWithPath("data.totalPrice").description("총 주문 금액"),
                                fieldWithPath("data.createdAt").description("주문 생성일"),
                                fieldWithPath("data.modifiedAt").description("주문 수정일")
                        )
                ));

    }

    @Test
    @DisplayName("주문에 대한 상세정보를 조회한다.")
    public void getOrder() throws Exception {

        //given
        Long orderId = order.getId();

        //when
        ResultActions actions = mockMvc.perform(get("/api/orders/{orderId}", String.valueOf(orderId))
                .accept(MediaType.APPLICATION_JSON)
        );

        //then
        actions.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("order-get",
                        pathParameters(
                                parameterWithName("orderId").description("주문 고유번호")
                        ),
                        responseFields(
                                fieldWithPath("code").description("결과코드"),
                                fieldWithPath("message").description("메시지"),
                                fieldWithPath("data").description("데이터"),
                                fieldWithPath("data.id").description("주문 고유번호"),
                                fieldWithPath("data.email").description("주문한 회원 이메일"),
                                fieldWithPath("data.orderStatus").description("주문 상태"),
                                fieldWithPath("data.orderTime").description("주문 시간"),
                                fieldWithPath("data.totalPrice").description("총 주문 금액"),
                                fieldWithPath("data.createdAt").description("주문 생성일"),
                                fieldWithPath("data.modifiedAt").description("주문 수정일")
                        )
                ));

    }

    @Test
    @DisplayName("주문 목록을 조회한다.")
    public void getOrders() throws Exception {

        //given
        Pageable pageable = PageRequest.of(0, 10);
        String json = new Gson().toJson(pageable);


        //when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/api/orders")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON));


        //then
            actions.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("order-gets",
                        requestParameters(
                                parameterWithName("page").optional().description("페이지 번호"),
                                parameterWithName("size").optional().description("페이지 사이즈")
                       ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("결과코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("메시지"),
                                fieldWithPath("data").description("데이터"),
                                fieldWithPath("data.content[].id").type(JsonFieldType.NUMBER).description("주문 고유번호"),
                                fieldWithPath("data.content[].email").type(JsonFieldType.STRING).description("주문한 회원 이메일"),
                                fieldWithPath("data.content[].orderStatus").type(JsonFieldType.STRING).description("주문 상태"),
                                fieldWithPath("data.content[].orderTime").type(JsonFieldType.STRING).description("주문 시간"),
                                fieldWithPath("data.content[].orderItemList[]").type(JsonFieldType.ARRAY).description("주문 상품 목록"),
                                fieldWithPath("data.content[].orderItemList[].id").type(JsonFieldType.NUMBER).description("주문 상품 고유번호").ignored(),
                                fieldWithPath("data.content[].orderItemList[].itemId").type(JsonFieldType.NUMBER).description("주문 상품 고유번호").ignored(),
                                fieldWithPath("data.content[].orderItemList[].orderId").type(JsonFieldType.NUMBER).description("주문 상품 고유번호").ignored(),
                                fieldWithPath("data.content[].orderItemList[].count").type(JsonFieldType.NUMBER).description("주문 상품 고유번호").ignored(),
                                fieldWithPath("data.content[].orderItemList[].itemPrice").type(JsonFieldType.NUMBER).description("주문 상품 고유번호").ignored(),
                                fieldWithPath("data.content[].totalPrice").description("총 주문 금액"),
                                fieldWithPath("data.pageable").ignored(),
                                fieldWithPath("data.pageable.sort").ignored(),
                                fieldWithPath("data.pageable.sort.empty").ignored(),
                                fieldWithPath("data.pageable.sort.sorted").ignored(),
                                fieldWithPath("data.pageable.sort.unsorted").ignored(),
                                fieldWithPath("data.pageable.offset").ignored(),
                                fieldWithPath("data.pageable.pageNumber").ignored(),
                                fieldWithPath("data.pageable.pageSize").ignored(),
                                fieldWithPath("data.pageable.paged").ignored(),
                                fieldWithPath("data.pageable.unpaged").ignored(),
                                fieldWithPath("data.size").ignored(),
                                fieldWithPath("data.number").ignored(),
                                fieldWithPath("data.sort").ignored(),
                                fieldWithPath("data.sort.sorted").ignored(),
                                fieldWithPath("data.sort.empty").ignored(),
                                fieldWithPath("data.sort.empty").ignored(),
                                fieldWithPath("data.sort.unsorted").ignored(),
                                fieldWithPath("data.first").ignored(),
                                fieldWithPath("data.last").ignored(),
                                fieldWithPath("data.numberOfElements").ignored(),
                                fieldWithPath("data.empty").ignored()
                            )
                ));

    }
}
