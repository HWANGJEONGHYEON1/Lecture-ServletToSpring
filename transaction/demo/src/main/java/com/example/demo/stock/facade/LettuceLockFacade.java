package com.example.demo.stock.facade;

import com.example.demo.stock.StockService;
import com.example.demo.stock.repository.RedisLockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class LettuceLockFacade {

    private final RedisLockRepository redisLockRepository;
    private final StockService stockService;

    @Transactional
    public void decrease(Long id, Long quantity) throws InterruptedException {
        while (!redisLockRepository.lock(id)) {
            Thread.sleep(100);
        }

        try {
            log.info("thread : {}", Thread.currentThread().getName());
            stockService.decrease(id, quantity);
        } finally {
            redisLockRepository.unlock(id);
        }
    }
}
