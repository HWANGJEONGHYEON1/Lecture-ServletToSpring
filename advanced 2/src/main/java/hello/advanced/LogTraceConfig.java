package hello.advanced;

import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.logtrace.ThreadLocalFieldLogTrace;
import hello.proxy.app.config.AppV1Config;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;


@Import(AppV1Config.class)
@SpringBootApplication(scanBasePackages = "hello.proxy.app")
public class LogTraceConfig {

    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalFieldLogTrace();
    }
}
