package com.oliver.rest.annotation.core;

import com.github.rholder.retry.WaitStrategies;
import com.github.rholder.retry.WaitStrategy;

import java.util.concurrent.TimeUnit;

/**
 * @Author Oliver Wang
 * @Description 重试等待策略
 * @Created by IntelliJ IDEA 2018.3.3
 * @Date Create at 2019/4/24
 * @Since
 */
public enum WaitStrategyEnum {
    /**
     * 固定长度的等待策略
     */
    FIXED_WAIT(WaitStrategies.fixedWait(2, TimeUnit.SECONDS)),
    ;
    /**
     * 等待策略
     */
    private final WaitStrategy waitStrategy;

    public WaitStrategy getCode() {
        return waitStrategy;
    }

    WaitStrategyEnum(WaitStrategy waitStrategy) {
        this.waitStrategy = waitStrategy;
    }
}
