package com.example.dao.typeinfo;

import com.example.model.TypeInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TypeInfoDao {
    List<TypeInfo> queryAllTypeInfo();

    TypeInfo querySingleTypeInfoById(@Param("id") String id);

    TypeInfo querySingleTypeInfoByName(@Param("name") String name);

    void updateTypeInfo(@Param("id") String id,@Param("sort") String sort,@Param("name") String name);

    void insertTypeInfo(@Param("sort") String sort,@Param("name") String name);
}
