package com.oliver.rest.rpc.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.rholder.retry.*;
import com.oliver.rest.exception.BusinessException;
import com.oliver.rest.rpc.config.RpcConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author Oliver Wang
 * @description 调度工具
 * @created by IntelliJ IDEA 2018.3.3
 * @date Create at 2019/4/17
 * @since
 */
@Component
@Slf4j
public abstract class ServiceRpcClientImpl implements IServiceRpcClient {
    /**
     * RPC异常的错误码
     */
    private static final String RPC_ERROR_CODE = "001";

    protected RpcConfig rpcConfig;

    @Autowired
    RestTemplate restTemplate;

    /**
     * 设置连接属性的abstract方法
     *
     * @param config
     */
    public abstract void setRpcConfig(RpcConfig config);

    @Override
    public Optional<String> getRequest(String uri, Object requestVo) {
        return doRequest(uri, requestVo, HttpMethod.GET);
    }

    @Override
    public Optional<String> postRequest(String uri, Object requestVo) {
        return doRequest(uri, requestVo, HttpMethod.POST);
    }

    @Override
    public Optional<String> getRequest(String uri, Object requestVo, Object... urlParams) {
        return doRequest(uri,requestVo,HttpMethod.GET,urlParams);
    }

    @Override
    public Optional<String> postRequest(String uri, Object requestVo, Object... urlParams) {
        return doRequest(uri,requestVo,HttpMethod.POST,urlParams);
    }

    @Override
    public <T> T fromData(String jsonStr, Class<T> clazz) {
        Objects.requireNonNull(jsonStr);
        log.info("fromData开始格式化json串[{}]为对象{}", jsonStr, clazz.getName());

        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        Objects.requireNonNull(jsonObject.get("info"));

        Optional<T> optionalT = rpcConfig.fromJson(jsonObject.get("info").toString(), clazz);
        log.info("fromData格式化json串[{}]为对象{}", jsonStr,optionalT);

        if (optionalT.isPresent()){
            return optionalT.get();
        }else {
            throw new BusinessException(RPC_ERROR_CODE+"003","fromData格式化相应对象为null");
        }
    }

    /**
     * 使用spring-restTemplate-exchange方法发起请求
     *
     * @param uri
     * @param requestVo
     * @param method
     * @return
     */
    private Optional<String> doRequest(String uri, Object requestVo, HttpMethod method, Object... urlParams) {
        log.info("start doRequest:[uri = {},request = {},method = {}]", uri, JSON.toJSON(requestVo), method.toString());
        try {
            Optional<String> optional = requestWithRetry(uri, requestVo, method, urlParams);
            log.info("doRequest has a response [{}]",optional.toString());
            return optional;
        }catch (ExecutionException ex) {
            log.info("调uri={}业务异常:",uri,ex);
            throw new BusinessException(RPC_ERROR_CODE+"001",ex.getMessage());
        } catch (RetryException rex) {
            log.info("调uri={}网络异常:",uri,rex);
            throw new BusinessException(RPC_ERROR_CODE+"002",rex.getMessage());
        }
    }

    /**
     * Retry to request
     *
     * @param uri
     * @param requestVo
     * @param method
     * @param urlParams
     * @return
     */
    private Optional<String> requestWithRetry(String uri, Object requestVo, HttpMethod method,
                                              Object... urlParams) throws ExecutionException, RetryException {
        //获取接口访问路径
        String url = rpcConfig.getUrl(uri);
        //请求头和实体类封装
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<Object> entity = new HttpEntity<>(requestVo, headers);

        /**
         * 请求入口执行RPC逻辑
         */
        Callable<String> callable = () -> {
            ResponseEntity<String> response = restTemplate.exchange(url, method, entity, String.class, urlParams);
            log.info("restful response:[url={},response={}]", url, response.getBody());
            return response.getBody();
        };

        /**
         * 定义重试策略
         */
        Retryer<String> retryer = RetryerBuilder.<String>newBuilder()
                //返回体为空
                .retryIfResult(Objects::isNull)
                //数据库连接异常
                .retryIfExceptionOfType(SQLException.class)
                //运行时异常
                .retryIfRuntimeException()
                //最大值为5的斐波那切数列等待间隔
                .withWaitStrategy(WaitStrategies.fibonacciWait(5, TimeUnit.SECONDS))
                //尝试5次结束retry
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))
                //设置监听器
                .withRetryListener(new MyRetryListener())
                .build();

        //retry to request
        String result;
        try {
            result = retryer.call(callable);
            //???? result 会为空还是抛了异常????????
            log.info("requestWithRetry final result = {}", result);
        } catch (ExecutionException e) {
            log.warn("Callable执行异常:", e);
            throw e;
        } catch (RetryException e) {
            log.warn("重试过程中发生异常结束重试:", e);
            throw e;
        }
        return Optional.ofNullable(result);
    }

}


@Slf4j
class MyRetryListener implements RetryListener {

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
