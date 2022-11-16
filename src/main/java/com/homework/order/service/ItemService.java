package com.homework.order.service;

import com.homework.order.dto.ItemDto;
import com.homework.order.entity.Item;
import com.homework.order.exception.CustomException;
import com.homework.order.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public Item saveItem(ItemDto itemDto) {
        return itemRepository.save(Item.of(itemDto));
    }

    public Item getItem(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new CustomException(HttpStatus.CONFLICT, "itemId not found."));

    }

    public List getItems(List<Long> checkedItemIdList) {
        return itemRepository.findAllById(checkedItemIdList);
    }
}
