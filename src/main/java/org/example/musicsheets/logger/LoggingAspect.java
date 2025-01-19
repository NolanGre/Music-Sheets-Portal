package org.example.musicsheets.logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("within(org.example.musicsheets.controllers..*)")
    private void anyControllerPointcut() {}

    @Before("anyControllerPointcut()")
    public void logBeforeMethod(JoinPoint joinPoint) {
        LOGGER.info("Entering method: {} with arguments: {}", joinPoint.getSignature().toShortString()  , joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "anyControllerPointcut()", returning = "result")
    public void logAfterReturningMethod(JoinPoint joinPoint, Object result) {
        LOGGER.debug("Method {} executed successfully, returned: {}", joinPoint.getSignature().toShortString(), result);
    }

    @AfterThrowing(pointcut = "anyControllerPointcut()", throwing = "error")
    public void logAfterThrowingMethod(JoinPoint joinPoint, Throwable error) {
        LOGGER.error("An error occurred in method: {} with message: {}", joinPoint.getSignature().toShortString(), error.getMessage(), error.getCause());
    }
}
