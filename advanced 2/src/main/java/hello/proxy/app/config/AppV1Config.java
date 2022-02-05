package hello.proxy.app.config;

import hello.proxy.app.v1.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppV1Config {

    @Bean
    public OrderController orderControllerV1() {
        return new OrderControllerImpl(orderServiceV1());
    }
    @Bean
    public OrderService orderServiceV1() {
        return new OrderServiceImpl(orderRepositoryV1());
    }
    @Bean
    public OrderRepository orderRepositoryV1() {
        return new OrderRepositoryImpl();
    }
}
