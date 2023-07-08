package com.example.reactivedemo.model;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
public class Stock {
    @Id
    private String id;
    private String name;

    @NonNull
    private BigDecimal price;
    private String currency;
}
