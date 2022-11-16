package com.homework.order.entity;

import com.homework.order.dto.OrderItemDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "order_item")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private Long count;

    private Long itemPrice;

    public static OrderItem of(OrderItemDto orderItemDto) {
        return OrderItem.builder()
                .item(Item.builder().id(orderItemDto.getItemId()).build())
                .count(orderItemDto.getCount())
                .itemPrice(orderItemDto.getItemPrice())
                .build();

    }

    public void setOrder(Order order) {
        this.order = order;
    }

}
