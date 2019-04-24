package com.oliver.rest.rpc.config.retry;

import com.github.rholder.retry.*;
import com.google.common.base.Predicates;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author Oliver Wang
 * @description guava-retry实例
 * @created by IntelliJ IDEA 2018.3.3
 * @date Create at 2019/4/22
 * @since
 */
@Slf4j
public class ExponentialBackoff {
    public static void main(String[] args) {
        //guava-retry入口
        Callable<Boolean> callable = () -> {
            // do something useful here
            log.info("call...");
            throw new RuntimeException();
        };

        Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()
                .retryIfResult(Predicates.isNull())
                .retryIfExceptionOfType(IOException.class)
                .retryIfRuntimeException()
                .withWaitStrategy(WaitStrategies.fixedWait(3, TimeUnit.SECONDS))
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))
                .build();
        try {
            retryer.call(callable);
        } catch (RetryException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}
