package com.example.reactivedemo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockCreationException extends RuntimeException {
    private String message;
}
