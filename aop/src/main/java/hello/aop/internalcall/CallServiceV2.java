package hello.aop.internalcall;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CallServiceV2 {

    private final ApplicationContext applicationContext; // 너무거대함
    private final ObjectProvider<CallServiceV2> objectProvider;


    public void external() {
        log.info("call external");
//        CallServiceV2 bean = applicationContext.getBean(CallServiceV2.class);
        CallServiceV2 callServiceV2 = objectProvider.getObject();
        callServiceV2.internal();
    }

    public void internal() {
        log.info("call internal");
    }
}
