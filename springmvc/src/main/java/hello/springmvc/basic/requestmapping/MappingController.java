package hello.springmvc.basic.requestmapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class MappingController {

    @RequestMapping({"/hello-basic", "/hello-go"})
    public String helloBasic() {
        log.info("hellobasic");
        return "ok";

    }

    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable String userId){
        log.info("mappingPath {}", userId);
        return "ok";
    }

    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable String userId, @PathVariable Long
            orderId) {
        log.info("mappingPath userId={}, orderId={}", userId, orderId);
        return "ok";
    }

    @PostMapping(value = "/mapping-consume", consumes = "application/json") public String mappingConsumes() {
        log.info("mappingConsumes");
        return "ok";
    }

    @PostMapping(value = "/mapping-produce", produces = "text/html") public String mappingProduces() {
        log.info("mappingProduces");
        return "ok";
    }

}
