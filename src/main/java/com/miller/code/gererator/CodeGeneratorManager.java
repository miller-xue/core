package com.miller.code.gererator;

import com.miller.code.gererator.config.CodeGeneratorConfig;
import com.miller.code.gererator.config.DBEnum;
import com.miller.code.gererator.config.TemplateEnum;
import com.miller.code.gererator.service.impl.CodeGeneratorImpl;
import com.miller.code.gererator.service.impl.ModelAndMapperGenerator;

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
    public void genCode(String tableName, String modelName) {
        TemplateEnum[] values = TemplateEnum.values();
        for (TemplateEnum templateEnum : values) {
            new CodeGeneratorImpl().genCode(templateEnum, tableName, modelName, config);
        }
    }

    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://192.168.163.148:3306/mmall?characterEncoding=utf-8";
        String username = "miller";
        String password = "miller";
        String basePackage = "com.miller.seckill";
        String author = "miller";
        CodeGeneratorConfig config = new CodeGeneratorConfig(jdbcUrl, username, password, DBEnum.MYSQL, basePackage, author);
        CodeGeneratorManager manager = new CodeGeneratorManager(config);
        manager.genCode("user", "User");



        ModelAndMapperGenerator modelAndMapperGenerator = new ModelAndMapperGenerator();
        modelAndMapperGenerator.genCode(null, "user", "User", config);
    }
}
