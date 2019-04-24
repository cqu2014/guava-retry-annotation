package com.oliver.rest.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Oliver Wang
 * @description Rpc响应基类
 * @created by IntelliJ IDEA 2018.3.3
 * @date Create at 2019/4/22
 * @since
 */
@Data
@ToString
@AllArgsConstructor
public class BaseResponseVo<T> implements Serializable {
    private int code;
    private String msg;
    private T info;

    public BaseResponseVo(){
        code = 0;
        msg = "OK";
    }

    public BaseResponseVo(T data){
        code = 0;
        msg = "OK";
        info = data;
    }
}
