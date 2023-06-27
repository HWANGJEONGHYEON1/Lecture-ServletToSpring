package com.example.demo.stock.facade;

import com.example.demo.stock.StockService;
import com.example.demo.stock.repository.LockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class NamedLockFacade {

    private final LockRepository lockRepository;
    private final StockService stockService;

    @Transactional
    public void decrease(Long id, Long quantity) {
        try {
            lockRepository.getLock(id.toString());
            stockService.decreaseNamedLock(id, quantity);
        } finally {
            lockRepository.releaseLock(id.toString());
        }
    }
}
