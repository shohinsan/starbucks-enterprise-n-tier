package com.example.starbuckscashier;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, reason="Invalid Request")  // 500
public class CashierServerError extends RuntimeException { }

// Compare the input hash with the calculated hash. If they don't match, throw the CashierServerError exception.
// Calculate the difference between the input timestamp and the current timestamp.
// Check if the time difference is more than 1000 seconds. If it is, throw the CashierServerError exception.