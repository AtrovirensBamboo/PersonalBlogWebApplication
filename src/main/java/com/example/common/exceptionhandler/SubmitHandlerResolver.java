package com.example.common.exceptionhandler;

import com.alibaba.fastjson.JSON;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class SubmitHandlerResolver implements HandlerExceptionResolver {

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,Object handler,
                                         Exception exception){
        //1 打印信息到控制台
        exception.printStackTrace();
        String message = "未知错误";
        //判断该错误是否是自定义的错误
        if (exception instanceof SubmitException) {
            message = exception.getMessage();
        }
        //2 判断请求类型
        //2.1 如果是json请求，返回json数据
        String url = request.getRequestURI();
        if (url.contains("json")){
            Map<String,String> stringStringHashMap = new HashMap<>();
            stringStringHashMap.put("status","failed");
            stringStringHashMap.put("message",message);
            //使用fastjson api将map转换为json数据
            String json = JSON.toJSONString(stringStringHashMap);
            try {
                request.setCharacterEncoding("UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.setContentType("application/json;charset=utf-8");
            try {
                response.getWriter().write(json);
                response.getWriter().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //返回空视图表示已经手动返回响应
            return new ModelAndView();
        }
        //2.2 如果是action请求，跳转至错误界面
        if (url.contains("action")){
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("message",message);
            modelAndView.setViewName("error");
            return modelAndView;
        }
        return null;
    }
}
