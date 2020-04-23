package com.yzw.doubledemo.kit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class BeanShellKitTest {

    @Test
    public void test(){
        //定义java代码字符串
        String javaCode = "int b = 3*c;" +
                "int b = a+b;";
        //定义参数
        Map map = new HashMap();
        map.put("a", 2);
        map.put("c", 5);
        //调用执行方法
        Object result = BeanShellKit.executeJavaCode(javaCode, map, "b");
        //打印结果
        System.out.println("计算结果：b="+result);

    }

}
