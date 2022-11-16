package com.homework.order.service;

import com.homework.order.dto.OrderDto;
import com.homework.order.dto.OrderItemDto;
import com.homework.order.dto.OrderResponse;
import com.homework.order.entity.Item;
import com.homework.order.entity.Order;
import com.homework.order.entity.OrderItem;
import com.homework.order.entity.OrderStatus;
import com.homework.order.exception.CustomException;
import com.homework.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final ItemService itemService;

    @Transactional
    public Order saveOrder(OrderDto orderDto) {
        List<Long> checkedItemIdList = orderDto.getOrderItemList()
                .stream()
                .map(OrderItemDto::getItemId)
                .collect(toList());

        List<Item> itemList = itemService.getItems(checkedItemIdList);

        for(Item item:itemList){
            if("Y".equals(item.getUseYn())) {
                if(item.getStockQuantity()<=0){
                    throw new CustomException(HttpStatus.BAD_REQUEST, item.getId()+"의 재고가 없습니다.");
                } else {
                    item.minusStockQuantity();
                }
            }else{
                throw new CustomException(HttpStatus.BAD_REQUEST, item.getId()+"를 구매할 수 없습니다.");
            }
        }


        List<OrderItem> orderItems = orderDto.getOrderItemList()
                .stream()
                .map(OrderItem::of)
                .collect(toList());

        Order savedOrder = orderRepository.save(Order.of(orderDto, orderItems));


        return savedOrder;
    }

    @Transactional
    public Order updateOrder(Long orderId, OrderStatus orderStatus) {
        Order updatedOrder = getOrder(orderId);

        updatedOrder.setOrderStatus(orderStatus);

        return updatedOrder;
    }

    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(HttpStatus.CONFLICT, "orderId not found."));

    }

    public SliceImpl<OrderResponse> getOrders(Pageable pageable) {
        SliceImpl<Order> orders = orderRepository.findAllByOrderByIdDesc(pageable);

        List<OrderResponse> orderList = orders.getContent()
                .stream()
                .map(OrderResponse::of)
                .collect(Collectors.toList());

        return new SliceImpl<>(orderList, pageable, orders.hasNext());
    }
}
