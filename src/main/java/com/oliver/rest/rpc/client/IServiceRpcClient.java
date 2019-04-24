package com.oliver.rest.rpc.client;

import java.util.Optional;

/**
 * @Author Oliver Wang
 * @Description 调度工具
 * @Created by IntelliJ IDEA 2018.3.3
 * @Date Create at 2019/4/17
 * @Since
 */
public interface IServiceRpcClient {
    /**
     * Http get请求返回jsonStr
     *
     * @param uri
     * @param requestVo
     * @return
     */
    Optional<String> getRequest(String uri, Object requestVo);


    /**
     * Http post请求返回jsonStr
     *
     * @param uri
     * @param requestVo
     * @return
     */
    Optional<String> postRequest(String uri, Object requestVo);

    /**
     * 带有url参数的get请求
     *
     * @param uri
     * @param requestVo
     * @param urlParams
     * @return
     */
    Optional<String> getRequest(String uri,Object requestVo,Object... urlParams);

    /**
     * 带有url参数的get请求
     *
     * @param uri
     * @param requestVo
     * @param urlParams
     * @return
     */
    Optional<String> postRequest(String uri,Object requestVo,Object... urlParams);

    /**
     * json字符串传化为对象实例
     *
     * @param jsonStr
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T fromData(String jsonStr, Class<T> clazz);

}
