package com.example.demo.facade;

import com.example.demo.stock.Stock;
import com.example.demo.stock.facade.OptimisticFacade;
import com.example.demo.stock.repository.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OptimisticFacadeTest {
    @Autowired
    private OptimisticFacade stockService;

    @Autowired
    private StockRepository stockRepository;

    @BeforeEach
    void setup() {
        Stock stock = new Stock(1L, 100L);
        stockRepository.saveAndFlush(stock);
    }

    @AfterEach
    void deleteAll() {
        stockRepository.deleteAll();
    }


    @Test
    void at_the_same_time_100_request() throws InterruptedException {
        int thread = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(thread);
        for (int i = 0; i < thread; i++) {
            executorService.submit(() -> {
                try {
                    stockService.decrease(1L, 1L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Stock stock = stockRepository.findById(1L).get();
        assertEquals(0, stock.getQuantity());
    }
}