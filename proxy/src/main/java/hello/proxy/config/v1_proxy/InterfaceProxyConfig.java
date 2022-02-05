package hello.proxy.config.v1_proxy;

import hello.proxy.app.v1.*;
import hello.proxy.config.v1_proxy.interface_proxy.OrderControllerInterfaceProxy;
import hello.proxy.config.v1_proxy.interface_proxy.OrderRepositoryInterfaceProxy;
import hello.proxy.config.v1_proxy.interface_proxy.OrderServiceInterfaceProxy;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InterfaceProxyConfig {
    @Bean
    public OrderController orderControllerV1(LogTrace trace) {
        OrderControllerImpl orderController = new OrderControllerImpl(orderService(trace));
        return new OrderControllerInterfaceProxy(orderController, trace);
    }

    @Bean
    public OrderService orderService(LogTrace trace) {
        OrderServiceImpl orderService = new OrderServiceImpl(orderRepository(trace));
        return new OrderServiceInterfaceProxy(orderService, trace);
    }
    @Bean
    public OrderRepository orderRepository(LogTrace trace) {
        OrderRepositoryImpl orderRepository = new OrderRepositoryImpl();
        return new OrderRepositoryInterfaceProxy(orderRepository, trace);
    }
}
