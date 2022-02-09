package hello.proxy.config.v3_proxyFactory;

import hello.proxy.app.v1.*;
import hello.proxy.config.v3_proxyFactory.advice.LogTraceAdvice;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ProxyFactoryConfigV1 {

    @Bean
    public OrderController orderController(LogTrace logTrace) {
        OrderController orderController = new OrderControllerImpl(orderServiceV1(logTrace));
        ProxyFactory proxyFactory = new ProxyFactory(orderController);
        proxyFactory.addAdvisor(getAdvice(logTrace));
        OrderController proxy = (OrderController) proxyFactory.getProxy();
        log.info("ProxyFactory proxy = {}, target = {}", proxy.getClass(), orderController.getClass());
        return proxy;
    }

    @Bean
    public OrderService orderServiceV1(LogTrace logTrace) {
        OrderService orderService = new OrderServiceImpl(orderRepository(logTrace));
        ProxyFactory proxyFactory = new ProxyFactory(orderService);
        proxyFactory.addAdvisor(getAdvice(logTrace));
        OrderService proxy = (OrderService) proxyFactory.getProxy();
        log.info("ProxyFactory proxy = {}, target = {}", proxy.getClass(), orderService.getClass());
        return proxy;
    }

    @Bean
    public OrderRepository orderRepository(LogTrace logTrace) {
        OrderRepository orderRepository = new OrderRepositoryImpl();
        ProxyFactory proxyFactory = new ProxyFactory(orderRepository);
        proxyFactory.addAdvisor(getAdvice(logTrace));
        OrderRepository proxy = (OrderRepository) proxyFactory.getProxy();
        log.info("ProxyFactory proxy = {}, target = {}", proxy.getClass(), orderRepository.getClass());
        return proxy;
    }

    private Advisor getAdvice(LogTrace logTrace) {
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");

        LogTraceAdvice logTraceAdvice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, logTraceAdvice);
    }
}
