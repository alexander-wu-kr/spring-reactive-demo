package com.example.reactivedemo.service;

import com.example.reactivedemo.dto.StockRequest;
import com.example.reactivedemo.dto.StockResponse;
import com.example.reactivedemo.exception.StockCreationException;
import com.example.reactivedemo.exception.StockNotFoundException;
import com.example.reactivedemo.repository.StocksRepository;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class StocksService {

    private StocksRepository stocksRepository;

    public Mono<StockResponse> getOneStock(String id) {
        return stocksRepository.findById(id)
            .map(StockResponse::fromModel)
            .switchIfEmpty(
                Mono.error(new StockNotFoundException("Stock not found with id:" + id))
            )
            .doFirst(() -> System.out.println("Retrieving stock with id: " + id))
            .doOnNext(stock -> System.out.println("Stock found: " + stock))
            .doOnError(ex -> System.out.println("Something went wrong when trying to retrieve stock: " + id + ex.getMessage()))
            .doOnTerminate(() -> System.out.println("Finalized retrieving stock"))
            .doFinally(signalType -> System.out.println("Finalized retrieving stock with signal type" + signalType));
    }

    public Flux<StockResponse> getAllStocks(BigDecimal priceGreaterThan) {
        return stocksRepository.findAll()
            .filter(stock -> stock.getPrice().compareTo(priceGreaterThan) > 0)
            .map(StockResponse::fromModel)
            .doFirst(() -> System.out.println("Retrieving all stocks"))
            .doOnNext(stock -> System.out.println("Stock found: " + stock))
            .doOnError(ex -> System.out.println("Something went wrong when trying to retrieve all stocks: " + ex.getMessage()))
            .doOnTerminate(() -> System.out.println("Finalized retrieving stocks"))
            .doFinally(signalType -> System.out.println("Finalized retrieving stock with signal type" + signalType));
    }

    public Mono<StockResponse> createStock(StockRequest stockRequest) {
        return Mono.just(stockRequest)
            .map(StockRequest::toModel)
            .flatMap(stock -> stocksRepository.save(stock))
            .map(StockResponse::fromModel)
//            .onErrorReturn(StockResponse.builder().build()); // method 1
//            .onErrorResume(ex -> {
//                System.out.println("Exception thrown while creating new stock. " + ex.getMessage());
//                return Mono.just(StockResponse.builder().build());
//            }); // method 2
            .onErrorMap(ex -> new StockCreationException(ex.getMessage()));
    }
}
