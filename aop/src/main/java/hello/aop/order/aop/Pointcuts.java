package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
public class Pointcuts {

    @Pointcut("execution(* hello.aop.order..*(..))") // pointCut
    public void allOrder(){} // pointCut signature

    // class 이름 패턴이 *Service
    @Pointcut("execution(* *..*Service.*(..))")
    public void allService() {}


    //hello.aop.order 패키지와 하위 패키지 이면서 클래스이름 패턴이 *Service인 것
    @Pointcut("allOrder() && allService()")
    public void orderAndService() throws Throwable {}
}
