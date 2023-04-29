package com.example.starbuckscashier.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.text.DecimalFormat;
import java.util.Random;
@RequiredArgsConstructor

@Data
public class Order {
    private String drink;
    private String milk;
    private String size;
    private double total;
    private String status;
    private String register;


}
