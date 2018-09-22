package com.miller.code.gererator.service;

/**
 * Created by miller on 2018/9/22
 */
public interface GenService {

    void generatorCode(String tableName, String modelName,String author);

    void generatorCodeByMybatis(String tableName, String modelName);
}
