package com.oliver.rest.rpc.client.bigdata;

import com.oliver.rest.rpc.client.ServiceRpcClientImpl;
import com.oliver.rest.rpc.config.RpcConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author Oliver Wang
 * @description 调用大数据API方法
 * @created by IntelliJ IDEA 2018.3.3
 * @date Create at 2019/4/23
 * @since
 */
@Component("bigDataRpcClient")
public class BigDataRpcClient extends ServiceRpcClientImpl {
    @Override
    @Autowired
    @Qualifier("bigDataRpcConfig")
    public void setRpcConfig(RpcConfig config) {
        this.rpcConfig = config;
    }
}
