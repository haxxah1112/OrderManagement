package com.homework.order.entity;

import com.homework.order.dto.ItemDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name = "item")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String itemName;

    private Long price;

    private Long stockQuantity;

    private String useYn;


    public static Item of(ItemDto itemDto) {
       return Item.builder()
               .itemName(itemDto.getItemName())
               .price(itemDto.getPrice())
               .stockQuantity(itemDto.getStockQuantity())
               .useYn("Y")
               .build();

    }

    public void minusStockQuantity(Long count) {
        this.stockQuantity -= count;
    }

}
