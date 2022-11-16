package com.homework.order.repository;

import com.homework.order.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ItemRepository  extends JpaRepository<Item, Long> {

}
