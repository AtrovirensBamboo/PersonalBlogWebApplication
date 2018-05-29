package com.example.common.interceptor;

import com.example.model.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.regex.Pattern;

//拦截器有HandlerInterceptor接口，HandlerInterceptorAdapter具体类，具体类只需选择需要重写的方法，接口要实现三个方法,
//拦截器所在处理流程节点详见java api文档
public class SubmitInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) {
        //1 获取url内容
        String url = request.getRequestURI();
        String urlPattern = "(.*userInfo.*)|(.*userinfo.*)";
        //2 使用正则表达式判断url是否包含userInfo(!!!注意，不是jsp文件名，是映射url路径名)
        boolean isMatch = Pattern.matches(urlPattern,url);
        if (isMatch){
            //2.1 如果包含，则判断是否存在session，
            HttpSession session = request.getSession();
            User user = (User)session.getAttribute("admin");
            //如果没有session则返回登录界面
            if (user != null){
                return true;
            } else try {
                request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
            //注意JDK1.7之后的多异常捕捉方式
            } catch (IOException|ServletException e) {
                e.printStackTrace();
            }
        }else {
            //如果不是需要登录的界面，则返回true
            return true;
        }
        return false;
    }
}
