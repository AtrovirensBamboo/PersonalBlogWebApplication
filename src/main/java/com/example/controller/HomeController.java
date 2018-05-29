package com.example.controller;

import com.example.common.SubmitValidator;
import com.example.model.SubmitResult;
import com.example.model.TypeInfo;
import com.example.service.TypeInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping(value = "/userinfo")
public class HomeController {
    private static final Logger logger=LogManager.getLogger(HomeController.class);
    @Autowired
    private TypeInfoService typeInfoService;

    //注销用户
    @RequestMapping(value = "/loginOut.action")
    public String loginOUt(HttpSession session){
        // 销毁session
        session.invalidate();
        return "index";
    }
    //返回文章分类列表页面
    @RequestMapping(value = "/typelist.action",method = RequestMethod.GET)
    public String queryList(Model model){
        logger.info("queryList called");

        List<TypeInfo> list = typeInfoService.queryAllTypeInfo();
        model.addAttribute("typeList",list);


        return "userinfo/typelist";
    }
    @RequestMapping(value = "/typechange.json",method = RequestMethod.POST)
    public @ResponseBody SubmitResult typeChange(@RequestParam(value = "idValueArray") ArrayList<String> idValueList,
                                                @RequestParam(value = "sortValueArray") ArrayList<String> sortValueList,
                                                @RequestParam(value = "nameValueArray") ArrayList<String> nameValueList
                                                 ){
        logger.info("typeChange called");

        ArrayList<String> idIndexNotEmptyList = new ArrayList<>();
        ArrayList<TypeInfo> tempList = new ArrayList<>();
        ArrayList<String> savedValueList = new ArrayList<>();
        ArrayList<String> toBeSavedValueList;
        HashSet<Integer> valueChangeIndex;
        int insertIndex = 0;
        String sortPattern = "^\\d{1,5}$";
        String namePattern = "^[a-zA-Z]{1,15}$";
        String idPattern = "^\\d*$";
        SubmitResult submitResult = new SubmitResult();
        TypeInfo uniqueTypeInfo;


        //1 校验所有的值是否合法，是则将signal的值设为true，否则返回错误
        //1.1 校验idValueList的值
        if (!SubmitValidator.listVerify(idValueList,idPattern)) {
            submitResult.setStatus("failed");
            submitResult.setMessage("请友好对待辛苦送至您面前的网页哟");
        }
        //1.2 校验sortValueList的值
        if (!SubmitValidator.listVerify(sortValueList,sortPattern)) {
            submitResult.setStatus("failed");
            submitResult.setMessage("请更改不合法的值");

            return submitResult;
        }
        //1.3 校验nameValueList的值
        if (!SubmitValidator.listVerify(nameValueList,namePattern)) {
            submitResult.setStatus("failed");
            submitResult.setStatus("请更改不合法的值");

            return submitResult;
        }
        //2 校验已有的文章分类的相关内容是否变化
        //2.1 找出idValue不为空字符串的索引
        for (String idValueNotEmpty:idValueList){
            if (idValueNotEmpty.isEmpty()){
                continue;
            }else {
                idIndexNotEmptyList.add(idValueNotEmpty);
            }
        }
        //2.1.1将所有不为空字符串的索引的idValue查询出
        for (String idIndexNotEmpty:idIndexNotEmptyList){
            tempList.add(typeInfoService.querySingleTypeInfoById(idIndexNotEmpty));
        }
        //2.2 校验sortValueList的值是否变化
        for (TypeInfo sortTypeInfo:tempList){
            savedValueList.add(sortTypeInfo.getSort());
        }
        toBeSavedValueList = new ArrayList<>(sortValueList.subList(0,tempList.size()));
        valueChangeIndex= SubmitValidator.listVerify(savedValueList,toBeSavedValueList);

        //2.3 校验nameValueList的值是否变化
        savedValueList.clear();
        for (TypeInfo nameTypeInfo:tempList){
            savedValueList.add(nameTypeInfo.getName());
        }
        toBeSavedValueList.clear();
        toBeSavedValueList = new ArrayList<>(nameValueList.subList(0,tempList.size()));
        valueChangeIndex.addAll(SubmitValidator.listVerify(savedValueList,toBeSavedValueList));

        //3 update值已变化的TypeInfo
        for (int index: valueChangeIndex) {
            typeInfoService.updateTypeInfo(idValueList.get(index),sortValueList.get(index),nameValueList.get(index));
        }

        //4 insert文章分类
        //4.1 查找需要insert的文章分类的起始索引
        for (String idValue:idValueList) {
            if (idValue.isEmpty()){
                insertIndex = idValueList.indexOf(idValue);
                break;
            }
        }
        //4.2 insert 文章分类
        //当只有一条insert信息的时候，idValueList为空，但是sortValueList和nameValueList的值不为空
        if (idValueList.isEmpty() && !sortValueList.isEmpty()) {
            typeInfoService.insertTypeInfo(sortValueList.get(0),nameValueList.get(0));
        } else {
            for (int i = insertIndex; i < idValueList.size() ; i++) {
                uniqueTypeInfo = typeInfoService.querySingleTypeInfoByName(nameValueList.get(i));
                if (uniqueTypeInfo == null){
                    typeInfoService.insertTypeInfo(sortValueList.get(i),nameValueList.get(i));
                }
            }
        }
        //5 设置返回值
        submitResult.setStatus("success");
        submitResult.setMessage("更新成功");
        return submitResult;
    }
}
