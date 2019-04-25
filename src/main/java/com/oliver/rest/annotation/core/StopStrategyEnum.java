package com.oliver.rest.annotation.core;

import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.StopStrategy;

/**
 * @Author Oliver Wang
 * @Description 重试策略-停止策略
 * @Created by IntelliJ IDEA 2018.3.3
 * @Date Create at 2019/4/24
 * @Since
 */
public enum StopStrategyEnum {
    /**
     * 重试3次之后停止
     */
    STOP_AFTER_ATTEMPT_THREE(StopStrategies.stopAfterAttempt(3)),
    ;
    private final StopStrategy stopStrategy;

    public StopStrategy getStopStrategy(){
        return this.stopStrategy;
    }

    StopStrategyEnum(StopStrategy stopStrategy) {
        this.stopStrategy = stopStrategy;
    }
}
