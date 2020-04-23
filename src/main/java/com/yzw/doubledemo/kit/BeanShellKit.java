package com.yzw.doubledemo.kit;

import bsh.Interpreter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author  yzw
 * @date    2020/04/23
 */
public class BeanShellKit {

    /**
     * 执行一段Java代码字符串，传入一些变量，获取一个变量的值
     *
     * @param javaCode       java代码字符串
     * @param inParameterMap 输入的值变量，key-value形式
     * @param outParamName   输出的变量名
     * @return 返回指定输出变量的值
     */
    public static Object executeJavaCode(String javaCode, Map<String, Object> inParameterMap, String outParamName) {
        Interpreter inter = new Interpreter();
        //遍历参数，写入inter
        for (Map.Entry<String, Object> entry : inParameterMap.entrySet()) {
            try {
                inter.set(entry.getKey(), entry.getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            //执行eval方法，进行计算
            inter.eval(javaCode);
            //返回结果
            return inter.get(outParamName);
        } catch (Exception e) {
            e.printStackTrace();
            //捕获异常，打印代码字符串
            System.out.println("javaCode:\n"+javaCode);
            return null;
        }
    }

}
