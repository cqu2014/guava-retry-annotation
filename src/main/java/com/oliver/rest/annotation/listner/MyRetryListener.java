package com.oliver.rest.annotation.listner;

import com.alibaba.fastjson.JSON;
import com.github.rholder.retry.Attempt;
import com.github.rholder.retry.RetryListener;
import com.oliver.rest.annotation.core.BaseRetryListener;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Oliver Wang
 * @description
 * @created by IntelliJ IDEA 2018.3.3
 * @date Create at 2019/4/24
 * @since
 */
@Slf4j
public class MyRetryListener extends BaseRetryListener {

    @Override
    public <String> void onRetry(Attempt<String> attempt) {
        log.info("[retry]time=" + attempt.getAttemptNumber());
        if (attempt.hasException()) {
            log.error("retry exception", attempt.getExceptionCause());
        }
        if (attempt.hasResult()) {
            if (attempt.getResult() == null) {
                log.info("retry return data is null");
            } else {
                log.info("retry return data is:{}", JSON.toJSONString(attempt.getResult()));
            }
        }
    }
}