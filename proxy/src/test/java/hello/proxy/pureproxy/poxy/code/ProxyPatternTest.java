package hello.proxy.pureproxy.poxy.code;

import org.junit.jupiter.api.Test;

public class ProxyPatternTest {

    @Test
    void noProxyTest() {
        RealProject realProject = new RealProject();
        ProxyPatternClient client = new ProxyPatternClient(realProject);

        client.execute();
        client.execute();
        client.execute();
    }

    @Test
    void cacheProxyTest() {
        RealProject realProject = new RealProject();
        CacheProxy cacheProxy = new CacheProxy(realProject);
        ProxyPatternClient client = new ProxyPatternClient(cacheProxy);
        client.execute();
        client.execute();
        client.execute();
    }
}
