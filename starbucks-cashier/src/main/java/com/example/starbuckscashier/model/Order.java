package com.example.starbuckscashier.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Data
public class Order {
    private String drink;
    private String milk;
    private String size;
    private double total;
    private String status;
    private String register;

    // Add other code, constructors, and setters here

    public String getDrink() {
        return drink;
    }

    public String getMilk() {
        return milk;
    }

    public String getSize() {
        return size;
    }

    public double getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }

    public String getRegister() {
        return register;
    }

}
