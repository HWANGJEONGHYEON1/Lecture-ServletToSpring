package com.example.demo.stock;

import com.example.demo.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;


    @Transactional
    public void decrease(Long id, Long quantity) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow();

        stock.decrease(quantity);

        stockRepository.saveAndFlush(stock);
    }

    public synchronized void decrease_synchronized(Long id, Long quantity) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow();

        stock.decrease(quantity);

        stockRepository.saveAndFlush(stock);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void decreaseNamedLock(Long id, Long quantity) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow();

        stock.decrease(quantity);

        stockRepository.saveAndFlush(stock);
    }
}
