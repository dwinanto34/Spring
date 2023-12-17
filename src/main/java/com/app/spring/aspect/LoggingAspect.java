package com.app.spring.aspect;

import com.app.spring.entity.Product;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

//    AOP - Aspect object-oriented - Provides cross-cutting concerns related
//    Commonly used for Logging, auditing, handling exception, etc
@Aspect
@Component
// @Order to determine the execution
@Order(1)
public class LoggingAspect {

//    Pointcut expression
//    @Before("execution(public com.app.spring.entity.Product findById(String))")
//    @Before("execution(public com.app.spring.entity.Product com.app.spring.service.ProductService.findById(String))")
//    * for wildcard and .. to match any parameters, etc
    @Pointcut("execution(public com.app.spring.entity.Product com.app.spring.service.ProductService.find*(String))")
    private void forFindProductById() {}

    @Pointcut("execution(public void com.app.spring.service.ProductService.save(..))")
    private void forSaveProduct() {}

    @Pointcut("forFindProductById() || forSaveProduct()")
    private void forProductService() {}

//    @Before("forFindProductById()")
    @Before("forProductService()")
    public void performLoggingAdvice(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();

        System.out.println("Logging advice 1");
    }

    @Before("forFindProductById()")
    public void performOtherLoggingAdvice() {
        System.out.println("Logging advice 2");
    }

    @AfterReturning(
            pointcut = "forFindProductById()",
            returning = "result"
    )
    public void afterReturningFindProductAdvice(JoinPoint joinPoint, Product result) {
        System.out.println("Logging after returning");
        System.out.println(result);
        result.setId("Override the ID");
    }

    @AfterThrowing(
            pointcut = "forFindProductById()",
            throwing = "exception"
    )
    public void afterThrowingFindProductAdvice(JoinPoint joinPoint, Throwable exception) {
        System.out.println("Logging after throwing");
        System.out.println(exception);
    }

//    Run after the method
//    code to run regardless of method outcome (success or failure)
    @Before("forProductService()")
    public void afterPerformLoggingAdvice(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();

        System.out.println("Logging advice after 1");
    }

//    Run before and after the method
    @Around("forProductService()")
    public Object aroundPerformLoggingAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//        Must run proceed, else the code will be stuck
        Object result = proceedingJoinPoint.proceed();
        System.out.println("Logging advice around 1");
        return result;
    }
}
