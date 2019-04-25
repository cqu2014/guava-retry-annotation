package com.oliver.rest.annotation.aspect;

import com.alibaba.fastjson.JSON;
import com.github.rholder.retry.RetryException;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.oliver.rest.annotation.Retry;
import com.oliver.rest.annotation.core.BaseRetryListener;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * @author Oliver Wang
 * @description @Retryer注解过滤器
 * @created by IntelliJ IDEA 2018.3.3
 * @date Create at 2019/4/24
 * @since
 */
@Aspect
@Component
@Slf4j
public class RetryerHandlerAspect {
    @Pointcut("@annotation(com.oliver.rest.annotation.Retry)")
    public void retryPointCut(){}

    @Around("retryPointCut()&&@annotation(retry)")
    public Object around(ProceedingJoinPoint pjp, Retry retry) throws Exception {
        Signature signature = pjp.getSignature();

        //获取到所有的参数值的数组
        Object[] params = pjp.getArgs();
        MethodSignature methodSignature = (MethodSignature) signature;
        //获取到方法的所有参数名称的字符串数组
        String[] parameterNames = methodSignature.getParameterNames();
        log.info("切面around拦截到方法{}.{},参数name列表{}对应的value列表:{}",signature.getDeclaringTypeName(),signature.getName(),parameterNames,params);

        log.info("其注解参数:{}",retry);

        /**
         * 请求入口
         */
        Callable<Object> callable = () -> {
            try {
                Object result = pjp.proceed();
                log.info("{}执行结果{}",signature.getName(),JSON.toJSON(result));

                return result;
            } catch (Throwable throwable) {
                log.warn("{}执行异常:",signature.getName(),throwable);
                throw new  ExecutionException(signature.getName()+"执行异常",throwable);
            }
        };

        /**
         * 定义重试策略
         *
         *
         */
        RetryerBuilder<Object> retryerBuilder = RetryerBuilder.newBuilder()
                .withWaitStrategy(retry.waitStrategy().getWaitStrategy())
                .withStopStrategy(retry.stopStrategy().getStopStrategy());
        /**
         * 返回值不能为null
         */
        if (retry.requireNonNull()){
            retryerBuilder.retryIfResult(Objects::isNull);
        }

        /**
         * RuntimeException()时进行重试
         */
        if (retry.retryIfRuntimeException()){
            retryerBuilder.retryIfRuntimeException();
        }

        /**
         * 添加重试异常类型
         */
        for (Class clazz: retry.exceptionType()){
           retryerBuilder.retryIfExceptionOfType(clazz);
       }

        /**
         * 添加监听器列表
         */
        for(Class<? extends BaseRetryListener> listener:retry.retryListeners()){
            try {
                retryerBuilder.withRetryListener(listener.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                log.warn("添加监听器异常：",e);
            }
        }

        Retryer<Object> retryPolicy = retryerBuilder.build();
        try {
            Object result =  retryPolicy.call(callable);
            log.info("{} final result = {}",signature.getName(),JSON.toJSON(result));

            return result;
        } catch (ExecutionException e) {
            log.warn("Callable执行异常:", e);
            throw e;
        } catch (RetryException e) {
            log.warn("重试过程中发生异常结束重试:", e);
            throw e;
        }


    }
}
