package com.oliver.rest.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Oliver Wang
 * @description 自定义业务异常
 * @created by IntelliJ IDEA 2018.3.3
 * @date Create at 2019/4/23
 * @since
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BusinessException extends RuntimeException {
    private String code;
    private String msg;
    private int level;

    /**
     * 无异常级别的业务异常
     *
     * @param codex
     * @param msg
     */
    public BusinessException(String codex, String msg){
        this.code = codex;
        this.msg = msg;
    }

}
