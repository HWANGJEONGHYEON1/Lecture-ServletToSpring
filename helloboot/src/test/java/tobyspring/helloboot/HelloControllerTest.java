package tobyspring.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class HelloControllerTest {

    @Test
    void helloController() {
        HelloController helloController = new HelloController(name -> name);
        String ret = helloController.hello("Test");

        Assertions.assertThat(ret).isEqualTo("Test");
    }

    @Test
    void helloControllerException() {
        HelloController helloController = new HelloController(name -> name);
        String ret = helloController.hello("Test");

        Assertions.assertThat(ret).isEqualTo("Test");
    }
}
