package com.smart.classroom.misc.utility.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smart.classroom.misc.utility.exception.UtilException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonUtil {

    public final static String EMPTY_JSON_ARRAY = "[]";
    public final static String EMPTY_JSON_OBJECT = "{}";

    public final static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        //遇到没有定义的字段，反序列化不要报错。
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 时间格式化
        objectMapper.setDateFormat(new SimpleDateFormat(DateUtil.DEFAULT_FORMAT));
    }

    public static <T> T toObject(String content, Class<T> clazz) {

        try {
            return objectMapper.readValue(content, clazz);
        } catch (IOException e) {
            throw new UtilException("JSON信息有误：{} {}", content, ExceptionUtils.getRootCauseMessage(e));
        }
    }


    //从一个jsonString中去获取对应类型的对象
    public static <T> T toGenericObject(TypeReference<T> typeReference, String jsonString) {

        if (jsonString == null) {
            throw new UtilException("jsonString必须指定");
        }


        try {
            return objectMapper.readValue(jsonString, typeReference);
        } catch (IOException e) {
            throw new UtilException("JSON信息有误：{} {}", jsonString, ExceptionUtils.getRootCauseMessage(e));
        }

    }

    /**
     * 从一个jsonString中去获取对应含有泛型的对象。
     * eg.
     * final TxyResponse<AesKeyData> o1 = JsonUtil.toGenericObject(TxyResponse.class, AesKeyData.class, content);
     */
    public static <T> T toGenericObject(Class<?> baseClass, Class<?> parameterClass, String jsonString) {

        if (jsonString == null) {
            throw new UtilException("jsonString必须指定");
        }

        JavaType concreteType = objectMapper.getTypeFactory().constructParametricType(
                baseClass,
                parameterClass
        );

        try {
            return objectMapper.readValue(jsonString, concreteType);
        } catch (IOException e) {
            throw new UtilException("JSON信息有误：{} {}", jsonString, ExceptionUtils.getRootCauseMessage(e));
        }

    }

    //从一个jsonString中去获取List<Long>
    public static List<Long> toLongList(String jsonString) {


        if (jsonString == null) {
            return new ArrayList<>();
        }

        return toGenericObject(new TypeReference<List<Long>>() {
        }, jsonString);
    }

    //从一个jsonString中去获取List<String>
    public static List<String> toStringList(String jsonString) {
        if (StringUtils.isEmpty(jsonString)) {
            return new ArrayList<>();
        }

        return toGenericObject(new TypeReference<List<String>>() {
        }, jsonString);
    }


    //将对象转换成json字符串。
    public static String toJson(Object obj) {


        try {

            return objectMapper.writeValueAsString(obj);

        } catch (JsonProcessingException e) {
            throw new UtilException("转成JSON时出错：{}", ExceptionUtils.getRootCauseMessage(e));
        }
    }

    //将一个对象转换成 Map<String,Object>
    public static <T> Map<String, T> toMap(String jsonString, Class<T> clazz) {

        TypeReference<Map<String, T>> typeReference = new TypeReference<Map<String, T>>() {
        };

        return objectMapper.convertValue(jsonString, typeReference);
    }

    //从jsonString中解析出一个数组。
    public static <T> List<T> parseArray(String jsonString, Class<T> clazz) {

        if (jsonString == null) {
            return new ArrayList<>();
        }

        return toGenericObject(new TypeReference<List<T>>() {
        }, jsonString);

    }

}
