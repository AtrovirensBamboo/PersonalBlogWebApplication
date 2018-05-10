package com.example.common;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//公共的工具类
public class SubmitValidator {
    //表单校验，返回错误信息
    public static HashMap<String,ArrayList<String>> getErrorMessage(Errors errors){
        HashMap<String,ArrayList<String>> messageMap = new HashMap<>();
        List<FieldError> errorObjects;
        String key;
        String value;

        errorObjects = errors.getFieldErrors();
        for (FieldError errorObject:errorObjects){
            //获取错误属性名及消息

            key = errorObject.getField();
            value = errorObject.getDefaultMessage();

            //判断是否存在错误属性名的键值对
            if (messageMap.containsKey(key)) {
                //如果存在，则将错误消息加入值arrayList中
                messageMap.get(key).add(value);
            } else {
                //不存在则新建键值对
                messageMap.put(key,new ArrayList<String>());
                messageMap.get(key).add(value);
            }
        }
        return messageMap;
    }
}
