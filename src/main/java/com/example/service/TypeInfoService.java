package com.example.service;

import com.example.dao.typeinfo.TypeInfoDao;
import com.example.model.TypeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeInfoService {
    @Autowired
    private TypeInfoDao typeInfoDao;

    public List<TypeInfo> queryAllTypeInfo(){
        return typeInfoDao.queryAllTypeInfo();
    }

    public TypeInfo querySingleTypeInfoById(String id){
        return typeInfoDao.querySingleTypeInfoById(id);
    }

    public TypeInfo querySingleTypeInfoByName(String name){
        return typeInfoDao.querySingleTypeInfoByName(name);
    }

    public void updateTypeInfo(String id,String sort,String name){
        typeInfoDao.updateTypeInfo(id,sort,name);
    }

    public void insertTypeInfo(String sort,String name){
        typeInfoDao.insertTypeInfo(sort,name);
    }
}
