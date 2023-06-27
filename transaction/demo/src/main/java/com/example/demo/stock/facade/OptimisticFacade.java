package com.example.demo.stock.facade;

import com.example.demo.stock.OptimisticLockStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OptimisticFacade {

    private final OptimisticLockStockService service;

    public void decrease(Long id, Long quantity) throws InterruptedException {
        while (true) {
            try {
                service.decrease(id, quantity);
                break;
            } catch (Exception e) {
                Thread.sleep(10);
            }
        }
    }
}
