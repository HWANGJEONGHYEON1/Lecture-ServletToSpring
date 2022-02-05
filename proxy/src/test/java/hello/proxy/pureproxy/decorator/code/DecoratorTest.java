package hello.proxy.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class DecoratorTest {

    @Test
    void noDecorator() {
        Component realComponent = new RealComponent();
        DecoratorClient decoratorClient = new DecoratorClient(realComponent);
        decoratorClient.execute();
    }

    @Test
    void decorator1() {
        Component real = new RealComponent();
        Component messageDecorator = new MessageDecorator(real);
        DecoratorClient client = new DecoratorClient(messageDecorator);
        client.execute();
    }

    @Test
    void decorator2() {
        Component real = new RealComponent();
        Component messageDecorator = new MessageDecorator(real);
        Component timeDecorator = new TimeDecorator(messageDecorator);
        DecoratorClient client = new DecoratorClient(timeDecorator);
        client.execute();
    }
}
