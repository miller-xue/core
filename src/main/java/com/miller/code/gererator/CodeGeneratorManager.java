package com.miller.code.gererator;

import com.miller.code.gererator.config.CodeGeneratorConfig;

/**
 * Created by miller on 2018/9/19
 * @author Miller
 */
public class CodeGeneratorManager {

    private CodeGeneratorConfig config;

    public CodeGeneratorManager(CodeGeneratorConfig config) {
        this.config = config;
    }

    /**
     * @param tableName
     * @param modelName
     */
    public void genCodeByTableName(String tableName, String modelName){

    }

    private String getDefModelName(String tableName) {

    }
}
