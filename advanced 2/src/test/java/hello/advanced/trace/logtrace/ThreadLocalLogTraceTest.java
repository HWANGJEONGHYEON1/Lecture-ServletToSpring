package hello.advanced.trace.logtrace;

import hello.advanced.trace.TraceStatus;
import org.junit.jupiter.api.Test;

class ThreadLocalLogTraceTest {

    ThreadLocalFieldLogTrace trace = new ThreadLocalFieldLogTrace();

    @Test
    void begin_end_level() {
        TraceStatus status1 = trace.begin("heelo1");
        TraceStatus status2 = trace.begin("heelo2");

        trace.end(status2);
        trace.end(status1);
    }

    @Test
    void begin_exception_level2() {
        TraceStatus status1 = trace.begin("heelo1");
        TraceStatus status2 = trace.begin("heelo2");
        trace.exception(status2, new IllegalStateException());
        trace.exception(status1, new IllegalStateException());
    }

}