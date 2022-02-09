package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
@Slf4j
public class AspectV2 {

    @Pointcut("execution(* hello.aop.order..*(..))") // pointCut
    private void allOrder(){} // pointCut signature

    @Around("allOrder()") // pointCut
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable { // advice
        log.info("[log] {}", joinPoint.getSignature()); // joinpoint sigature
        return joinPoint.proceed();
    }
}
