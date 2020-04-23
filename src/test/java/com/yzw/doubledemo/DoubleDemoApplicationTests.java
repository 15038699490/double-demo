package com.yzw.doubledemo;

import com.yzw.doubledemo.kit.BeanShellKit;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class DoubleDemoApplicationTests {

    @Test
    void doubleTest() throws Exception {
        String codeStr = "res=(a/b+c)*d*100";
        Map map = new HashMap();
        map.put("a",45646565145.12);
        map.put("b",4567891321.1);
        map.put("c",-7.12653);
        map.put("d",1756446.5562);
        double res = Double.valueOf(BeanShellKit.executeJavaCode(codeStr,map,"res").toString());
        System.out.println("使用BigDecimal处理后的数据："+BigDecimal.valueOf(res).toString()+"-----------原始数据："+res);
        
        //方式1
        DecimalFormat df = new DecimalFormat("#.00000000000000000");
        System.out.println("DecimalFormat方式处理结果："+df.format(res));

        //方式2
        System.out.println("String方式处理结果："+String.format("%.9f",res));

        //方式3
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(11);
        System.out.println("NumberFormat方式处理结果："+nf.format(res));

        //下面是我整理的对数字的精确度的处理方法
        System.out.println("自定义方式处理结果:"+precision(res,"000000000"));
    }

    /**
     * 小数精度
     * @param object            参数
     * @param precisionValue    十进制小数位精度 "0","00",...
     * @return String
     */
    public static String precision(Object object, String precisionValue){
        BigDecimal round = null;

        switch (object.getClass().getName()){
            case "java.math.BigDecimal":
                round = (BigDecimal) object;
                break;
            case "java.lang.Integer":
                int value = ((Integer) object).intValue();
                round = BigDecimal.valueOf(value);
                break;
            case "java.lang.Double":
                double d = ((Double) object).doubleValue();
                round = BigDecimal.valueOf(d);
                break;
            case "java.lang.Float":
                float f = ((Float) object).floatValue();
                round = BigDecimal.valueOf(f);
                break;
            case "java.lang.Long":
                long l = ((Long) object).longValue();
                round = BigDecimal.valueOf(l);
                break;
            case "java.lang.Byte":
                byte b = ((Byte) object).byteValue();
                round = BigDecimal.valueOf(b);
                break;
            case "java.lang.Short":
                short s = ((Short) object).shortValue();
                round = BigDecimal.valueOf(s);
                break;
            case "java.lang.String":
                String str1 = object.toString();
                round = new BigDecimal(str1);
                break;
            case "java.lang.StringBuffer":
                String str2 = object.toString();
                round = new BigDecimal(str2);
                break;
            case "java.lang.StringBuilder":
                String str3 = object.toString();
                round = new BigDecimal(str3);
                break;
            default:
                return "FALSE";
        }

        //起初是使用的if判断，后来考虑到在三个判断条件以上的话switch效率更高。
        //所以改用switch
        //但是用if的话可以使用instanceof，这样看起来更加舒服。
        //具体取舍看自己吧
//        if (object instanceof BigDecimal) {
//            round = (BigDecimal) object;
//        } else if (object instanceof Integer) {
//            int value = ((Integer) object).intValue();
//            round = BigDecimal.valueOf(value);
//        } else if (object instanceof Double) {
//            double d = ((Double) object).doubleValue();
//            round = BigDecimal.valueOf(d);
//        } else if (object instanceof Float) {
//            float f = ((Float) object).floatValue();
//            round = BigDecimal.valueOf(f);
//        } else if (object instanceof Long) {
//            long l = ((Long) object).longValue();
//            round = BigDecimal.valueOf(l);
//        } else if (object instanceof Byte) {
//            byte l = ((Byte) object).byteValue();
//            round = BigDecimal.valueOf(l);
//        } else if (object instanceof Short) {
//            short l = ((Short) object).shortValue();
//            round = BigDecimal.valueOf(l);
//        } else if (object instanceof String || object instanceof StringBuffer || object instanceof StringBuilder) {
//            String s = object.toString();
//            round = new BigDecimal(s);
//        } else {
//            return "FALSE";
//        }

        String string = null;
        DecimalFormat format = new DecimalFormat("####################." + precisionValue);
        //是否小于1
        if (round.doubleValue() < 1) {
            //是否是负数
            if (round.signum() == -1) {
                //是否大于-1
                if (round.doubleValue() > -1) {//这是-0.*
                    String str1 = format.format(round).substring(0, 1);
                    String str2 = format.format(round).substring(1, format.format(round).length());
                    string = str1 + "0" + str2;
                } else {
                    string = format.format(round);
                }
            } else {
                //不是负数，加零在前面
                string = "0" + format.format(round);
            }
        } else {
            string = format.format(round);
        }
        return string;
    }

    @Test
    public void testa(){

        //同样一个数值，使用new BigDecimal()方法明显造成了数值的失真
        System.out.println(new BigDecimal(456465.45654654321635278465497931));
        //而BigDecimal.valueOf()方法会对数值进行当前类型的最大精确度来四舍五入
        System.out.println(BigDecimal.valueOf(456465.45654654321635278465497931));

        //下面是double类型运算的数值失真
        System.out.println(0.5*3);
        System.out.println(0.1*3);

        //我们来比较一下使用BigDecimal的方法进行的运算和我们平时的运算
        BigDecimal big = BigDecimal.valueOf(31.4);
        BigDecimal big2 = BigDecimal.valueOf(10);

        System.out.println(31.4/10);
        System.out.println(big.divide(big2));
    }
}
