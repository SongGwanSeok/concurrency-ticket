package com.aengdulab.ticket.aop;

import com.aengdulab.ticket.repository.LockRepository;
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

    private final LockRepository lockRepository;

    @Around("@annotation(com.aengdulab.ticket.aop.NamedLock)")
    public Object handleLock(ProceedingJoinPoint joinPoint) throws Throwable {
        String lockKey = joinPoint.getSignature().getName();
        boolean isLockAcquired = lockRepository.getLock(lockKey);

        if (!isLockAcquired) {
            throw new IllegalStateException("락 획득에 실패했습니다 락: " + lockKey);
        }

        try {
            return joinPoint.proceed();
        } finally {
            lockRepository.releaseLock(lockKey);
        }
    }
}
