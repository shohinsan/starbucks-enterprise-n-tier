package com.example.starbucksworker;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "STARBUCKS_ORDER", indexes=@Index(name = "altIndex", columnList = "register", unique = true))
@Data
@RequiredArgsConstructor
public class StarbucksOrder {
    private @Id
    @GeneratedValue
    Long id;
    @Column(nullable = false)
    private String drink;
    @Column(nullable = false)
    private String milk;
    @Column(nullable = false)
    private String size;
    private double total;
    private String status;
    private String register;
}