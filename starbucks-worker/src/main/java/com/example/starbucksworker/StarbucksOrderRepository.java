package com.example.starbucksworker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StarbucksOrderRepository extends JpaRepository<StarbucksOrder, Long> {
    List<StarbucksOrder> findByRegister(String register);
}