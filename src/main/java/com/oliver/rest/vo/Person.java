package com.oliver.rest.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Oliver Wang
 * @description 人
 * @created by IntelliJ IDEA 2018.3.3
 * @date Create at 2019/4/23
 * @since
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person implements Serializable {
    private Long id;
    private String name;
    private int age;
}
