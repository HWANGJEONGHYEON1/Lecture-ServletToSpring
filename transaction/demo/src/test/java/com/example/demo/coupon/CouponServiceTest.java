package com.example.demo.coupon;

import com.example.demo.stock.repository.RedisLockRepository;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CouponServiceTest {

    @Autowired
    RedisLockRepository redisLockRepository;

    @Test
    void issueCoupon() throws InterruptedException {

        RedisTemplate<String, String> redisTemplate = redisLockRepository.getRedisTemplate();
        // CouponService를 생성합니다.
        CouponService couponService = new CouponService(redisLockRepository);

        // 2000명의 요청을 생성합니다.

        ExecutorService executorService = Executors.newFixedThreadPool(2000);
        for (int i = 0; i < 2000; i++) {
            int finalI = i;
            executorService.submit(() -> {
                // 쿠폰을 발급합니다.
                couponService.issueCoupon("user" + finalI);
            });
        }

        // 모든 요청이 완료될 때까지 기다립니다.
        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.MINUTES);

        // 쿠폰이 모두 발급되었는지 확인합니다.
        assertEquals(0, Integer.parseInt(redisTemplate.opsForValue().get("coupon_stock")));
    }
}