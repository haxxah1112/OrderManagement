package com.homework.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {

    private String itemName;

    private Long price;

    private Long stockQuantity;

    private String useYn;

}
