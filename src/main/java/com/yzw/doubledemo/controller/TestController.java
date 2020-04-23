package com.yzw.doubledemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "test")
public class TestController {

    @RequestMapping(value = "test")
    public String test(){
        return "测试成功";
    }

}
