package com.homework.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.homework.order.dto.OrderDto;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false, updatable = false)
    private Long id;

    private String email;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private LocalDateTime orderTime;

    @JsonIgnore
    @BatchSize(size = 20)
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItemList = new ArrayList<>();

    private Long totalPrice = 0L;


    public static Order of(OrderDto orderDto, List<OrderItem> orderItems) {
        Order order = new Order();
        order.email = orderDto.getEmail();
        order.orderStatus = OrderStatus.PLACED;
        order.orderTime = LocalDateTime.now();

        for(OrderItem item : orderItems){
            order.addOrderItem(item);
        }
        return order;

    }

    public Order addOrderItem(OrderItem orderItem) {
        this.orderItemList.add(orderItem);
        this.totalPrice += orderItem.getItemPrice() * orderItem.getCount();
        orderItem.setOrder(this);
        return this;
    }

    public void setOrderStatus(OrderStatus orderStatus){
        this.orderStatus = orderStatus;
    }

    @Override
    public boolean equals(Object obj) {
        boolean isSame = false;
        if(obj != null && obj instanceof Order) {
            if(this.id == ((Order) obj).getId()
                    && this.email == ((Order) obj).getEmail()
                    && this.totalPrice == ((Order) obj).getTotalPrice()){
                isSame = true;
            }
        }
        return isSame;
    }
}
