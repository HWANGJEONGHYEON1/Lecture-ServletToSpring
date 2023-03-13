package tobyspring.helloboot;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
public class HelloController {

    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping(value = "/hello")
    @ResponseBody
    public String hello(String name) {

        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException();
        }
        return helloService.sayHello(name);
    }
}
