package com.example.starbuckscashier;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public
class Command {
    private String action;
    private String message;
    private String stores;
    private String register;
    private String state;
    private String timestamp;
    private String hash;
    private String drink;
    private String milk;
    private String size;

}