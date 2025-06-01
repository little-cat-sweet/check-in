package com.hongyun.aspect;

import cn.hutool.core.date.StopWatch;
import com.hongyun.annotation.TimeLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
public class TimeLogAspect {

    @Pointcut("@annotation(com.hongyun.annotation.TimeLog) || @within(com.hongyun.annotation.TimeLog)")
    public void timeLogPointcut() {
    }

    @Around("timeLogPointcut()")
    public Object logTime(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        try {
            return joinPoint.proceed();
        } finally {
            stopWatch.stop();

            Signature signature = joinPoint.getSignature();
            String className = signature.getDeclaringType().getSimpleName();
            String methodName = signature.getName();

            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();

            TimeLog methodAnnotation = method.getAnnotation(TimeLog.class);
            TimeLog classAnnotation = joinPoint.getTarget().getClass().getAnnotation(TimeLog.class);

            String description = buildDescription(classAnnotation, methodAnnotation, className, methodName);

            long totalTimeMillis = stopWatch.getTotalTimeMillis();

            System.out.printf("[%s.%s] 耗时: %d ms  annotation info [%s] %n",
                    className, methodName, totalTimeMillis, description);
        }
    }

    private String buildDescription(TimeLog classAnnotation, TimeLog methodAnnotation,
                                    String className, String methodName) {
        if (methodAnnotation != null && !"".equals(methodAnnotation.value())) {
            return methodAnnotation.value();
        } else if (classAnnotation != null && !"".equals(classAnnotation.value())) {
            return classAnnotation.value() + "." + methodName;
        } else {
            return className + "." + methodName;
        }
    }
}
