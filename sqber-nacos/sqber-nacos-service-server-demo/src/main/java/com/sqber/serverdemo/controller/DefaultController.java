package com.sqber.serverdemo.controller;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DefaultController {

    @NacosInjected
    private NamingService namingService;

    @GetMapping("/")
    public String index() {
        return "server index";
    }

    public String test(String serviceName) throws NacosException {
        List<Instance> allInstances = namingService.getAllInstances(serviceName);
        return "ok";
    }

}
