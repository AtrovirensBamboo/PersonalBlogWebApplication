package com.example.common;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

//公共的工具类
public final class SubmitValidator {
    //表单校验，返回错误信息
    public static  HashMap<String,ArrayList<String>> getErrorMessage(Errors errors){
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
    //校验list中的值是否都满足正则表达式,返回布尔值
    public static  Boolean listVerify(List<String> list, String pattern){
        boolean signal = false;

        for (String value:list){
            signal = Pattern.matches(pattern,value);
        }
        return signal;
    }
    //校验两个list中的值是否都相等，并返回不相等的值得索引的HashSet
    public static HashSet<Integer> listVerify(List<String> listSaved, List<String> listToBeSaved){
        int length = listSaved.size();
        HashSet<Integer> resultSet = new HashSet<>();

        for (int i = 0; i < length; i++){
            if (listSaved.get(i).equals(listToBeSaved.get(i))) {
                continue;
            }else {
                resultSet.add(i);
            }
        }
        return resultSet;
    }
}
