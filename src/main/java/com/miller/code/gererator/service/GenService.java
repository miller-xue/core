package com.miller.code.gererator.service;

/**
 * Created by miller on 2018/9/22
 */
public interface GenService {

    /**
     * 生成Controller Service ServiceImpl代码
     * @param tableName 表名
     * @param modelName model名 （强制自定义modelName）
     * @param author 作者
     */
    void generatorCode(String tableName, String modelName,String author);

    /**
     * 生成Model Mapper Mapper.xml 代码
     * @param tableName 表名
     * @param modelName model名
     */
    void generatorCodeByMybatis(String tableName, String modelName);
}
