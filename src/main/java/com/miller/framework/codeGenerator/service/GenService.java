package com.miller.framework.codeGenerator.service;

/**
 * Created by miller on 2018/9/22
 */
public interface GenService {

    /**
     * 生成Controller Service ServiceImpl Model Mapper Mapper.xml 代码
     *
     * @param childPkg  子包
     * @param tableName 表名
     * @param modelName model名 （强制自定义modelName）
     * @param author    作者
     */
    void generatorCode(String childPkg, String tableName, String modelName, String author);

}
