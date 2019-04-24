package com.oliver.rest.rpc.config;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * @Author Oliver Wang
 * @Description 组装访问路径
 * @Created by IntelliJ IDEA 2018.3.3
 * @Date Create at 2019/4/17
 * @Since
 */
public interface RpcConfig {

    Logger log = LoggerFactory.getLogger(RpcConfig.class);

    /**
     * 获取url
     *
     * @param uri
     * @return
     */
    String getUrl(String uri);

    /**
     * http响应json字符串转化对象实例
     *
     * @param jsonStr
     * @param tClass
     * @param <T>
     * @return
     */
    default <T> Optional<T> fromJson(String jsonStr, Class<T> tClass) {
        if (StringUtils.isEmpty(jsonStr)) {
            return Optional.empty();
        }
        try {
            return Optional.ofNullable(
                    JSONObject.parseObject(jsonStr, tClass)
            );
        } catch (Exception e) {
            log.warn("fromJson转化{}发生异常", jsonStr, e);
            throw e;
        }
    }
}
