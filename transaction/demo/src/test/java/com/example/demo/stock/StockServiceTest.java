package com.example.demo.stock;

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
class StockServiceTest {

    @Autowired
    private StockService stockService;

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
    void stock_decrease() {
        stockService.decrease(1L, 1L);

        Stock stock = stockRepository.findById(1L).get();
        assertEquals(stock.getQuantity(), 99);
    }

    @Test
    void at_the_same_time_100_request() throws InterruptedException {
        int thread = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(thread);
        for (int i = 0; i < thread; i++) {
            executorService.submit(() -> {
                try {
                    // race condition 발생
                    stockService.decrease(1L, 1L);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Stock stock = stockRepository.findById(1L).get();
        assertEquals(stock.getQuantity(), 0);
    }


    /**
     * 1개의 스레드만 접근할 수 있음
     * @Transaction의 문제가 잇음
     * 1.트랜잭션 시작 -> 2.재고 감소 -> 3.트랜잭션 종료
     * 트랜잭션 종료 되기 전에 다른 스레드에서 재고 감소를 접근할 수 있음..
     * @Transation 제거하면 가능
     * 단점은 ?
     * 멀티 서버일 때, 문제가 됨 syncronized는 각 프로세스 안에서만 보장이 가능하기 때문에, 다른 프로세스끼리 경합이 발생함.
     */
    @Test
    void at_the_same_time_100_sync_request() throws InterruptedException {
        int thread = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(thread);
        for (int i = 0; i < thread; i++) {
            executorService.submit(() -> {
                try {
                    stockService.decrease_synchronized(1L, 1L);
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