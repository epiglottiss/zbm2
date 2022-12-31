package com.example.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class LockAopAspect {

    //around는 시작과 끝에 둘러 싸면서 원하는 동작 부여
    @Around("@annotation(com.example.account.aop.AccountLock)")
    public Object aroundMethod(
            ProceedingJoinPoint pjp
    ) throws Throwable {
        // lock 취득 시도

        try{
            return pjp.proceed();
        }finally {
            // lock 해제
        }
    }

}
