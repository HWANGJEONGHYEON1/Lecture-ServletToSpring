package hello.proxy.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeDecorator implements Component {
    private Component component;

    public TimeDecorator(Component component) {
        this.component = component;
    }

    @Override
    public String operation() {
        log.info("타임 데코레이터 실행");
        long startTime = System.currentTimeMillis();
        String operation = component.operation();
        long endTime = System.currentTimeMillis();

        log.info("타임 데코레이터 종료, resultTime = {}", startTime - endTime);
        return operation;
    }
}
