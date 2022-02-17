package hello.aop.proxyvs;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

@Slf4j
public class ProxyCastingTest {

    @Test
    void jdkProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(false); // JDK 동적 프록시

        // 프록시 캐스팅 성공
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

        //JDK 동적 프록시를 구현 클래스로 캐시팅 시도 실패, public class ClassCastException extends RuntimeException {


        Assertions.assertThrows(ClassCastException.class, () -> {
            MemberServiceImpl memberServiceImpl = (MemberServiceImpl) memberServiceProxy;
        });

    }

    @Test
    void cgLibProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true); // JDK 동적 프록시

        // 프록시 캐스팅 성공
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

        log.info("target = {}", memberServiceProxy.getClass());
        //CGLIB 프록시를 구현 클래스로 캐스팅 성공
        MemberServiceImpl memberServiceImpl = (MemberServiceImpl) memberServiceProxy;
    }
}
