package com.oliver.rest.rpc.client;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

/**
 * @author Oliver Wang
 * @description 错误响应处理类
 * @created by IntelliJ IDEA 2018.3.3
 * @date Create at 2019/4/17
 * @since
 */
public class ResponseErrorHandler extends DefaultResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return super.hasError(response);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException{
        super.handleError(response);
    }
}
