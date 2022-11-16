package com.homework.order.dto;

import com.homework.order.entity.Item;
import com.homework.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {

    private Long itemId;

    private Long count;

    private Long itemPrice;


}
