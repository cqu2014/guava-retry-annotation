package com.oliver.rest.rpc.config;

import com.oliver.rest.rpc.configuration.BigDataRpcProperties;
import com.oliver.rest.constants.ConstantData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author Oliver Wang
 * @description 组装访问地址
 * @created by IntelliJ IDEA 2018.3.3
 * @date Create at 2019/4/17
 * @since
 */
@Component("bigDataRpcConfig")
public class BigDataRpcConfig implements RpcConfig{
    @Autowired
    BigDataRpcProperties bigDataRpcProperties;

    @Override
    public String getUrl(String uri) {
        Objects.requireNonNull(uri,"ServiceRpcConfig访问路径不允许为null");

        if (!uri.startsWith(ConstantData.START_SPLIT_PATH)){
            uri = "/"+uri;
        }
        return bigDataRpcProperties.getHost()+uri;
    }
}
