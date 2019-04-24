package com.oliver.rest.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oliver.rest.response.BaseResponseVo;
import com.oliver.rest.rpc.client.IServiceRpcClient;
import com.oliver.rest.service.ICrmService;
import com.oliver.rest.vo.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Oliver Wang
 * @description
 * @created by IntelliJ IDEA 2018.3.3
 * @date Create at 2019/4/23
 * @since
 */
@Service
@Slf4j
public class CrmServiceImpl implements ICrmService {

    @Autowired
    @Qualifier("bigDataRpcClient")
    IServiceRpcClient iServiceRpcClient;

    @Override
    public BaseResponseVo<Person> getPersonById(Long id) {
        log.info("getPersonById:input data [{}]",id);
        Optional<String> optionalS = iServiceRpcClient.getRequest("/bi/person?id={id}",null,id);

        return new BaseResponseVo<>(optionalS.map((x)-> iServiceRpcClient.fromData(x,Person.class)).orElse(null));
    }

    @Override
    public BaseResponseVo<Person> getPerson(Long id, String name) {
        log.info("getPersonById:input data [{},{}]",id,name);
        Optional<String> optionalS = iServiceRpcClient.getRequest("/bi/person/{id}/one?name={name}",null,id,name);

        return new BaseResponseVo<>(optionalS.map((x)-> iServiceRpcClient.fromData(x,Person.class)).orElse(null));
    }

    @Override
    public BaseResponseVo<Person> privatePerson(Long id, int age, String name) {
        log.info("getPersonById:input data [{},{},{}]",id,age,name);
        Optional<String> optionalS = iServiceRpcClient.postRequest("/bi/person/{id}/{age}/one?name={name}",null,id,age,name);

        return new BaseResponseVo<>(optionalS.map((x)-> iServiceRpcClient.fromData(x,Person.class)).orElse(null));
    }

    @Override
    public BaseResponseVo<Boolean> isGoodMan(Person person,int type) {
        log.info("getPersonById:input data [{}]", JSON.toJSONString(person));
        Optional<String> optionalS = iServiceRpcClient.postRequest("/bi/person/verify/{type}",person,type);

        return new BaseResponseVo<>(optionalS.map(JSONObject::parseObject)
                .map(x->Boolean.parseBoolean(x.getString("info")))
                .orElse(false));
    }
}
