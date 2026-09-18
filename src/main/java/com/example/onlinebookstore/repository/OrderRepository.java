package com.example.onlinebookstore.repository;

import com.example.onlinebookstore.domain.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
