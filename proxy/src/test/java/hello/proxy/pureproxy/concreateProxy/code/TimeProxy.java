package hello.proxy.pureproxy.concreateProxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeProxy extends ConcreteLogic {

    private ConcreteLogic concreteLogic;

    public TimeProxy(ConcreteLogic concreteLogic) {
        this.concreteLogic = concreteLogic;
    }

    @Override
    public String operation() {
        log.info("TimeProxy 실행");
        long startTime = System.currentTimeMillis();
        String operation = concreteLogic.operation();
        long endTime = System.currentTimeMillis();

        log.info("타임 데코레이터 종료, resultTime = {}", startTime - endTime);
        return operation;
    }
}
