package com.example.demo.coupon;

import com.example.demo.stock.repository.RedisLockRepository;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;

@Getter
@Slf4j
public class CouponService {

    private final RedisLockRepository redisLockRepository;
    RedisTemplate<String, String> redisTemplate;
    private static final String COUPON_STOCK_KEY = "coupon_stock";
    private static final String COUPON_LOCK_KEY = "coupon_lock";

    public CouponService(RedisLockRepository redisLockRepository) {
        this.redisLockRepository = redisLockRepository;
        redisTemplate = redisLockRepository.getRedisTemplate();
        redisTemplate.opsForValue().set("coupon_stock", String.valueOf(500));
    }

    public void issueCoupon(String userId) {
        SetOperations<String, String> setOperations = redisTemplate.opsForSet();

        // Acquire the lock
        boolean lockAcquired = false;
        try {
            lockAcquired = redisTemplate.opsForValue().setIfAbsent(COUPON_LOCK_KEY, userId);
            if (!lockAcquired) {
                // Another server holds the lock, return false or try again
                return;
            }

            // Check if there are available coupons
            if (!setOperations.isMember(COUPON_STOCK_KEY, userId)) {
                // No more available coupons, return false or handle the case accordingly
                return;
            }

            // Decrease the coupon stock and assign it to the user
            redisTemplate.multi();
            setOperations.remove(COUPON_STOCK_KEY, userId);
            log.info("# redis : {}", redisTemplate.opsForValue().get(COUPON_STOCK_KEY));
            // Perform other operations related to assigning the coupon to the user
            redisTemplate.exec();

        } finally {
            // Release the lock
            if (lockAcquired) {
                redisTemplate.delete(COUPON_LOCK_KEY);
            }

        }
    }
}