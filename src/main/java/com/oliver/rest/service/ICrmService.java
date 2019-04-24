package com.oliver.rest.service;

import com.oliver.rest.response.BaseResponseVo;
import com.oliver.rest.vo.Person;

/**
 * @Author Oliver Wang
 * @Description 会员营销接口
 * @Created by IntelliJ IDEA 2018.3.3
 * @Date Create at 2019/4/23
 * @Since
 */

public interface ICrmService {
    /**
     * 根据id获取person
     *
     * @param id
     * @return
     */
    BaseResponseVo<Person> getPersonById(Long id);

    /**
     *  定制化person对象
     *
     * @param id
     * @param name
     * @return
     */
    BaseResponseVo<Person> getPerson(Long id,String name);

    /**
     * 定制person
     *
     * @param id
     * @param age
     * @param name
     * @return
     */
    BaseResponseVo<Person> privatePerson(Long id,int age,String name);

    /**
     * 判断是否为好人
     *
     * @param person
     * @return
     */
    BaseResponseVo<Boolean> isGoodMan(Person person,int type);
}
