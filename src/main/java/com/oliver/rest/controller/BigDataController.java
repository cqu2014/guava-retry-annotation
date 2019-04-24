package com.oliver.rest.controller;

import com.oliver.rest.contants.ConstantData;
import com.oliver.rest.response.BaseResponseVo;
import com.oliver.rest.vo.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author Oliver Wang
 * @description 大数据RPC接口
 * @created by IntelliJ IDEA 2018.3.3
 * @date Create at 2019/4/23
 * @since
 */
@RestController
@RequestMapping(value = "/bi")
@Slf4j
public class BigDataController {

    /**
     * 访问地址 /bi/person?id=666
     *
     * @param id
     * @return
     */
    @GetMapping("/person")
    public BaseResponseVo<Person> getPerson(@RequestParam Long id){
        Person person = Person.builder()
                .id(id)
                .age(30)
                .name("Oliver Wang")
                .build();

        return new BaseResponseVo<>(person);
    }

    /**
     * 访问地址 /bi/person/123/one?name=XXXX
     *
     * @param id
     * @param name
     * @return
     */
    @GetMapping("/person/{id}/one")
    public BaseResponseVo<Person> person(@PathVariable Long id,@RequestParam String name){
        Person person = Person.builder()
                .id(id)
                .age(30)
                .name(name)
                .build();

        return new BaseResponseVo<>(person);
    }

    /**
     * /bi/person/123/23/one?name=XXXX
     *
     * @param id
     * @param age
     * @param name
     * @return
     */
    @PostMapping("/person/{id}/{age}/one")
    public BaseResponseVo<Person> privatePerson(@PathVariable Long id,@PathVariable int age,@RequestParam String name){
        Person person = Person.builder()
                .id(id)
                .age(age)
                .name(name)
                .build();

        return new BaseResponseVo<>(person);
    }

    /**
     * /person/verify
     *
     * @param person
     * @return
     */
    @PostMapping("/person/verify")
    public BaseResponseVo<Boolean> isGoodMan(@RequestBody Person person){
        boolean isGoodMan = true;
        if (Objects.isNull(person)){
            isGoodMan = false;
        }else  if (person.getAge() > ConstantData.BAD_MAN_AGE) {
            isGoodMan = false;
        }
        //return new BaseResponseVo<>(isGoodMan);

        return null;
    }

}
