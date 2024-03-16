package com.kodeinc.authservice.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@Aspect
@Slf4j
public class RequestTracer {

  @Around(
      "execution(public * com.kodeinc.authservice.*.*.*(..)) "
          + "&& !execution(public * com.kodeinc.authservice.controllers.*.*(..))")
  public Object traceRequest(ProceedingJoinPoint joinPoint) throws Throwable {
    String methodName = joinPoint.getSignature().toString();
    String inputArgs = Arrays.toString(joinPoint.getArgs());
    log.debug(
        "request received in the method {}, with following arguments {}", methodName, inputArgs);
    Object output = joinPoint.proceed();
    log.trace(
        "request returned from method {}, with following arguments {}, with following output {}",
        methodName,
        inputArgs,
        output);
    return output;
  }
}
