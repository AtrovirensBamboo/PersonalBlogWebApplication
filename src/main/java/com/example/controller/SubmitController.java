package com.example.controller;

import com.example.common.SubmitValidator;
import com.example.common.exceptionhandler.SubmitException;
import com.example.model.SubmitResult;
import com.example.model.User;
import com.example.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@org.springframework.stereotype.Controller
public class SubmitController {
    private static final Logger logger=LogManager.getLogger(SubmitController.class);
    @Autowired
    private UserService userService;

    //登录验证,使用java validation规范验证表单内容
    @RequestMapping(value = "/submit.json",method = RequestMethod.POST)
    public @ResponseBody SubmitResult submit(@ModelAttribute("userSubmit") @Valid User user, Errors errors,
                                             HttpSession session)throws SubmitException{

        User userQuery = userService.queryUser(user.getUsername(),user.getPassword());
        SubmitResult submitResult = new SubmitResult();

        logger.info("submit called");

        //1 表单验证，errors中存储了表单验证的错误信息
        if (errors.hasErrors()){
            //未通过表单验证返回"status":"failed"
            submitResult.setStatus("failed");
            if (SubmitValidator.getErrorMessage(errors).containsKey("username")) {
                throw new SubmitException("不合法的用户名");
            }
            if (SubmitValidator.getErrorMessage(errors).containsKey("password")) {
                throw new SubmitException("不合法的用户名");
            }
        }
        //2 数据库查询，是否存在该用户，
        if ( userQuery != null){
            //设置用户登录后的session
            session.setAttribute("admin",userQuery);
            //2.1 存在则返回"status":"success"
            submitResult.setStatus("success");
            return submitResult;
        }
        //2.2 不存在则返回"status":"failed"及用户名相关错误信息
        throw new SubmitException("不存在该用户");
    }
    @RequestMapping(value = "/userInfo.action")
    public String turnToUserInfo(){
        logger.info("turnToUserInfo called");

        return "home";
    }

}
