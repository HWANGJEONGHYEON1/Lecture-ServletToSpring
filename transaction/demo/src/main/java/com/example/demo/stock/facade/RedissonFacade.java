package com.example.demo.stock.facade;

import com.example.demo.stock.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@RequiredArgsConstructor
public class RedissonFacade {

    private final RedissonClient redisClient;
    private final StockService stockService;

    public void decrease(Long key, Long quantity) {
        RLock lock = redisClient.getLock(key.toString());

        try {
            boolean available = lock.tryLock(5, 1, TimeUnit.SECONDS);

            if (!available) {
                log.info("lock 획들 실패");
                return ;
            }
            stockService.decrease(key, quantity);
        } catch (InterruptedException e) {
            System.out.println("e = " + e);
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
