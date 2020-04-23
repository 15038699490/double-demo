package com.yzw.doubledemo.kit;

import bsh.Interpreter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author  YuanZhiWei
 * @date    2020/04/23
 */
public class BeanShellKit {

    public static void main(String[] args) throws Exception {
        String javaCode = "System.out.println(\"abc\");" +
                "int b = 3;" +
                "int b = a+b;";
        Map map = new HashMap();
        map.put("a", 2);
        Object result = executeJavaCode(javaCode, map, "b");
        System.out.println(result);
    }

    /**
     * 执行一段Java代码，传入一些变量，获取一个变量的值
     *
     * @param javaCode       java代码字符串
     * @param inParameterMap 输入的值变量，key-value形式
     * @param outParamName   输出的变量名
     * @return 返回指定输出变量的值
     */
    public static Object executeJavaCode(String javaCode, Map<String, Object> inParameterMap, String outParamName) {
        Interpreter inter = new Interpreter();
        for (Map.Entry<String, Object> entry : inParameterMap.entrySet()) {
            try {
                inter.set(entry.getKey(), entry.getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            inter.eval(javaCode);
            return inter.get(outParamName);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("javaCode:\n"+javaCode);
            return null;
        }
    }

}
