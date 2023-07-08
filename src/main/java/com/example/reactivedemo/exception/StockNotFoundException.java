package com.example.reactivedemo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockNotFoundException extends RuntimeException {
    private String message;
}
