package com.oliver.rest.controller;

import com.oliver.rest.service.ICrmService;
import com.oliver.rest.vo.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Oliver Wang
 * @description 大会员系统接口
 * @created by IntelliJ IDEA 2018.3.3
 * @date Create at 2019/4/23
 * @since
 */
@RestController
@RequestMapping("/crm")
@Slf4j
public class CrmController {
    @Autowired
    ICrmService iCrmService;

    @GetMapping("/one")
    public Object one(){
        //访问 /bi/person?id={id}
        return iCrmService.getPersonById(12345L);
    }

    @GetMapping("/two")
    public Object two(){
        return iCrmService.getPerson(898989L,"金二狗");
    }

    @GetMapping("/three")
    public Object three(){
        return iCrmService.privatePerson(9999L,19,"ergou");
    }

    @GetMapping("/four")
    public Object four(){
        return iCrmService.isGoodMan(Person.builder()
                .age(16)
                .id(100L)
                .name("徐二狗")
                .build());
    }
}
