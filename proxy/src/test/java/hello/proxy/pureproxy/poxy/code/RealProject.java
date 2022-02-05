package hello.proxy.pureproxy.poxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RealProject implements Subject {
    @Override
    public String operation() {
        log.info("실재 객체 호출");
        sleep(1000);
        return "data";
    }

    private void sleep(int mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
