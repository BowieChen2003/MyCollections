package com.ccb.backend.aspect;

import com.ccb.backend.annotation.AutoFill;
import com.ccb.backend.context.BaseContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Component
public class AutoFillAspect {

    // 定义切入点
    @Pointcut("execution(* com.ccb.backend.mapper.*.*(..)) && @annotation(com.ccb.backend.annotation.AutoFill)")
    public void autoFillPointCut() {}

    // 定义通知
    // 使用前置通知，为公共字段赋值
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint){

        // 1、获取当前拦截方法的操作类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);
        String operationType = autoFill.value(); // 获取操作类型

        // 2、获取当前方法的参数-实体对象
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return;
        }
        Object entity = args[0];

        // 3、准备赋值数据
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();

        // 4、根据操作类型为实体对象的字段赋值
        if("INSERT".equals(operationType)){
            try{
                Method setCreateTime = entity.getClass().getDeclaredMethod("setCreateTime", LocalDateTime.class);
//                Method setUpdateUser = entity.getClass().getDeclaredMethod("setUpdateUser", Long.class);
                setCreateTime.invoke(entity, now);
//                setUpdateUser.invoke(entity, currentId);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


}
