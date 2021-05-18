package hello.springmvc.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogTestController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/log-test")
    public String loggerTest() {
        String name = "Spring";
        System.out.println("name = " + name);

        log.trace("trace = {}", name);
        log.debug("debug = {}", name);
        log.warn("warn = {}", name);
        log.error("error = {}", name);
        log.info("info = {}", name);


        return name;
    }
}
