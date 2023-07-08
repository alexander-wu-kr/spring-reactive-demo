package com.example.reactivedemo.dto;

import com.example.reactivedemo.model.Stock;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockRequest {

    @JsonProperty("stockName")
    private String name;
    private BigDecimal price;
    private String currency;

    public Stock toModel() {
        return Stock.builder()
            .name(this.name)
            .price(this.price)
            .currency(this.currency)
            .build();
    }
}
