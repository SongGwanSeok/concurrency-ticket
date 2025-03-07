package com.aengdulab.ticket.service.aop;

import com.aengdulab.ticket.service.LockService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(1)
@Component
@RequiredArgsConstructor
public class NamedLockAop {

    private final LockService lockService;

    @Around("@annotation(com.aengdulab.ticket.service.aop.NamedLock)")
    public Object handleLock(ProceedingJoinPoint joinPoint) throws Throwable {
        String lockKey = joinPoint.getSignature().getName();
        boolean isLockAcquired = lockService.getLock(lockKey);

        if (!isLockAcquired) {
            throw new IllegalStateException("락 획득에 실패했습니다 락: " + lockKey);
        }

        try {
            return joinPoint.proceed();
        } finally {
            lockService.releaseLock(lockKey);
        }
    }
}
