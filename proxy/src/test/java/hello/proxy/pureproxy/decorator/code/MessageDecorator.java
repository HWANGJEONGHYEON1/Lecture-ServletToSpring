package hello.proxy.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageDecorator implements Component {

    private Component component;

    public MessageDecorator(Component component) {
        this.component = component;
    }

    @Override
    public String operation() {
        log.info("messageDecorator 실행");
        String result = component.operation();
        String decoration = "*****" + result + "*****";

        log.info("messageDecorator 적용 전 = {}, 후 = {}", result, decoration);
        return decoration;
    }
}
