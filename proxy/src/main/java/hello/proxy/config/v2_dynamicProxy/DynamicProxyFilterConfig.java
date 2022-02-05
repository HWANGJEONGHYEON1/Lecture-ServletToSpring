package hello.proxy.config.v2_dynamicProxy;

import hello.proxy.app.v1.*;
import hello.proxy.config.v2_dynamicProxy.handler.LogTraceBasicHandler;
import hello.proxy.config.v2_dynamicProxy.handler.LogTraceFilterHandler;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Proxy;

@Configuration
public class DynamicProxyFilterConfig {

    private static final String[] PATTERNS = {"request*", "order*", "save*"};

    @Bean
    public OrderController orderControllerV1(LogTrace logTrace) {
        OrderController orderController = new OrderControllerImpl(orderServiceV1(logTrace));
        OrderController proxy = (OrderController) Proxy.newProxyInstance(OrderController.class.getClassLoader(),
                new Class[]{OrderController.class},
                new LogTraceFilterHandler(orderController, logTrace, PATTERNS));

        return proxy;
    }

    @Bean
    public OrderService orderServiceV1(LogTrace logTrace) {
        OrderService orderService = new OrderServiceImpl(orderRepositoryV1(logTrace));
        OrderService proxy = (OrderService) Proxy.newProxyInstance(OrderService.class.getClassLoader(),
                new Class[]{OrderService.class},
                new LogTraceFilterHandler(orderService, logTrace, PATTERNS));
        return proxy;
    }

    @Bean
    public OrderRepository orderRepositoryV1(LogTrace logTrace) {
        OrderRepository orderRepository = new OrderRepositoryImpl();
        OrderRepository proxy = (OrderRepository) Proxy.newProxyInstance(OrderRepository.class.getClassLoader(),
                new Class[]{OrderRepository.class},
                new LogTraceFilterHandler(orderRepository, logTrace, PATTERNS));

        return proxy;
    }
}
