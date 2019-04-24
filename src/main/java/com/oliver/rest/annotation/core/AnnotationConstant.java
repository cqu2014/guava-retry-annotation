package com.oliver.rest.annotation.core;

import com.github.rholder.retry.WaitStrategies;
import com.github.rholder.retry.WaitStrategy;

import java.util.concurrent.TimeUnit;


/**
 * @Author Oliver Wang
 * @Description 定义注解使用的常量
 * @Created by IntelliJ IDEA 2018.3.3
 * @Date Create at 2019/4/24
 * @Since
 */
public interface AnnotationConstant {

    WaitStrategy NO_WAIT_STRATEGY = WaitStrategies.fixedWait(2, TimeUnit.SECONDS);
}
