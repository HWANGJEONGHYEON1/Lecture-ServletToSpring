package hello.proxy.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

@Slf4j
public class BasicTest {

    @Test
    void basicConfig() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BasicConfig.class);
        A beanA = applicationContext.getBean("beanA", A.class);
        beanA.helloA();

        // B 빈은 등록되지 않음
        Assertions.assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(B.class));
    }

    static class A {
        public void helloA() {
            log.info("Hello A");
        }
    }

    static class B {
        public void helloB() {
            log.info("Hello B");
        }
    }

    static class BasicConfig {
        @Bean(name = "beanA")
        public A a() {
            return new A();
        }
    }
}
