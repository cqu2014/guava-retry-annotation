package com.oliver.rest.rpc.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Oliver Wang
 * @description 调用服务方访问地址
 * @created by IntelliJ IDEA 2018.3.3
 * @date Create at 2019/4/17
 * @since
 */
@ConfigurationProperties(prefix = "service.url")
@Component("bigDataRpcProperties")
@Data
public class BigDataRpcProperties {
    /**
     * 包含:port
     */
    private String host;
    private String user;
    private String password;

}
