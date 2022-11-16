package com.homework.order.repository;

import com.homework.order.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    SliceImpl<Order> findAllByOrderByIdDesc(Pageable pageable);
}
