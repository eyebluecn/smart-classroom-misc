package com.smart.classroom.misc.utility.util;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Random;

public class NumberUtil {


    /**
     * 判断一个字符串是否为整数
     *
     * @param value 待检测的字符串
     * @return true=>是整数 false=>不是整数
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (Throwable throwable) {
            return false;
        }
    }


    /**
     * 判断一个BigDecimal是否为整数
     *
     * @param bigDecimal 待检测的字符串
     * @return true=>是整数 false=>不是整数
     */
    public static boolean isInteger(BigDecimal bigDecimal) {
        return bigDecimal.stripTrailingZeros().scale() <= 0;
    }


    /**
     * [a,b)的一个随机整数
     */
    public static int random(int start, int end) {

        if (start > end) {
            int temp = start;
            start = end;
            end = temp;
        }

        Random r = new Random();

        return r.nextInt(end - start) + start;
    }


    /**
     * 比较两个泛型Number的大小
     */
    public static int numberCompare(Number number1, Number number2) {

        return new BigDecimal(number1.toString()).compareTo(new BigDecimal(number2.toString()));

    }

    public static int min(int a, int b) {
        return Math.min(a, b);

    }


    /**
     * 都为null 为true ,相等为true
     */
    public static Boolean equals(Number number1,Number number2) {

        if (Objects.isNull(number1) && Objects.isNull(number2)) {
            return true;
        } else if (Objects.nonNull(number1) && number1.equals(number2)) {
            return true;
        } else{
            return false;
        }
    }

}
