package hello.advanced.trace.teemplate;

import hello.advanced.trace.teemplate.code.AbstractTemplate;
import hello.advanced.trace.teemplate.code.SubClassLogic1;
import hello.advanced.trace.teemplate.code.SubClassLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class TemplateMethodTest {

    @Test
    void templateMethodV0() {
        logic1();
    }

    private void logic1() {
        long startTime = System.currentTimeMillis();
        log.info("# business execution 1");

        long endTime = System.currentTimeMillis();
        log.info("resultTime {}", endTime - startTime);

    }

    private void logic2() {
        long startTime = System.currentTimeMillis();
        log.info("# business execution 2");

        long endTime = System.currentTimeMillis();
        log.info("resultTime {}", endTime - startTime);

    }

    @Test
    void templateMethodV(){
        AbstractTemplate template1 = new SubClassLogic1();
        template1.execute();
        AbstractTemplate template2 = new SubClassLogic2();
        template2.execute();
    }

    @Test
    void templateMethodV2() {
        AbstractTemplate template1 = new AbstractTemplate() {

            @Override
            protected void call() {
                log.info("비지니스로직 1 실행");
            }

        };
        template1.execute();

        AbstractTemplate template2 = new AbstractTemplate() {

            @Override
            protected void call() {
                log.info("비지니스로직 2 실행");
            }
        };
        template2.execute();
    }
}
