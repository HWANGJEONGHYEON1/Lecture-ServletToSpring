package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
@Slf4j
public class AspectV6Advice {


//    @Around("hello.aop.order.aop.Pointcuts.allOrder()") // pointCut
//    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable { // advice
//        log.info("[log] {}", joinPoint.getSignature()); // joinpoint sigature
//        return joinPoint.proceed();
//    }
//
//    //hello.aop.order 패키지와 하위 패키지 이면서 클래스이름 패턴이 *Service인 것
//    @Around("hello.aop.order.aop.Pointcuts.orderAndService()")
//    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
//        try {
//            // @Before
//            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
//            Object proceed = joinPoint.proceed();
//            //@AfterReturning
//            log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
//            return proceed;
//        } catch (Exception e) {
//            // @AfterThrowing
//            log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
//            throw e;
//        } finally {
//            // @After
//            log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
//        }
//    }

    @Before("hello.aop.order.aop.Pointcuts.orderAndService()")
    public void before(JoinPoint joinPoint) {
        log.info("[before] {}", joinPoint.getSignature());
    }

    @AfterReturning(value = "hello.aop.order.aop.Pointcuts.orderAndService()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        log.info("[afterReturning] {}, result = {}", joinPoint.getSignature(), result);
    }

    @AfterThrowing(value = "hello.aop.order.aop.Pointcuts.orderAndService()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Exception ex) {
        log.info("[AfterThrowing] {}, ex = {}", joinPoint.getSignature(), ex);
    }

    @After(value = "hello.aop.order.aop.Pointcuts.orderAndService()")
    public void after(JoinPoint joinPoint) {
        log.info("[After] {}", joinPoint.getSignature());
    }

}
