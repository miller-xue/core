package com.miller.code.gererator.service.impl;

import com.miller.code.gererator.config.CodeGeneratorConfig;
import com.miller.code.gererator.config.TemplateEnum;
import com.miller.code.gererator.service.CodeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miller on 2018/9/20
 * @author Miller
 */
@Slf4j
public class ModelAndMapperGenerator implements CodeGenerator {

    private CodeGeneratorConfig config;

    @Override
    public void genCode(TemplateEnum templateEnum, String tableName, String modelName, CodeGeneratorConfig config) {
        this.config = config;
        Context initConfig = initConfig(tableName, modelName);
        List<String> warnings = null;
        MyBatisGenerator generator = null;
        try {
            Configuration cfg = new Configuration();
            cfg.addContext(initConfig);
            cfg.validate();

            DefaultShellCallback callback = new DefaultShellCallback(true);
            warnings = new ArrayList<String>();
            generator = new MyBatisGenerator(cfg, callback, warnings);
            generator.generate(null);
        } catch (Exception e) {
            throw new RuntimeException("Model 和  Mapper 生成失败!", e);
        }

        if (generator == null || generator.getGeneratedJavaFiles().isEmpty() || generator.getGeneratedXmlFiles().isEmpty()) {
            throw new RuntimeException("Model 和  Mapper 生成失败, warnings: " + warnings);
        }

        log.info(modelName, "{}.java 生成成功!");
        log.info(modelName, "{}Mapper.java 生成成功!");
        log.info(modelName, "{}Mapper.xml 生成成功!");
    }


    /**
     * 增加 Mapper 插件
     * @param context
     */
    private void addMapperPlugin(Context context) {
        PluginConfiguration pluginConfiguration = new PluginConfiguration();
        pluginConfiguration.setConfigurationType("tk.mybatis.mapper.generator.MapperPlugin");
        pluginConfiguration.addProperty("mappers", "com.miller.core.Mapper");
        context.addPluginConfiguration(pluginConfiguration);
    }

    /**
     * Mybatis 代码自动生成基本配置
     * @return
     */
    public Context initMybatisGeneratorContext() {
        Context context = new Context(ModelType.FLAT);
        context.setId("Potato");
        context.setTargetRuntime("MyBatis3Simple");
        context.addProperty(PropertyRegistry.CONTEXT_BEGINNING_DELIMITER, "`");
        context.addProperty(PropertyRegistry.CONTEXT_ENDING_DELIMITER, "`");

        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setConnectionURL(config.getJdbcUrl());
        jdbcConnectionConfiguration.setUserId(config.getJdbcUsername());
        jdbcConnectionConfiguration.setPassword(config.getJdbcPassword());
        jdbcConnectionConfiguration.setDriverClass(config.getDbEnum().getDirverClassName());
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);


        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        // TODO 没加上module 名称
        sqlMapGeneratorConfiguration.setTargetProject(CodeGeneratorConfig.PROJECT_PATH + CodeGeneratorConfig.RESOURCES_PATH);
        sqlMapGeneratorConfiguration.setTargetPackage("mapper");
        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);

        // 增加 mapper 插件
        addMapperPlugin(context);

        return context;
    }


    /**
     * 完善初始化环境
     * @param tableName 表名
     * @param modelName 自定义实体类名, 为null则默认将表名下划线转成大驼峰形式
     */
    private Context initConfig(String tableName, String modelName) {
        Context context = null;
        try {
            context = initMybatisGeneratorContext();
            JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
            // TODO 没加上module name
            javaModelGeneratorConfiguration.setTargetProject(CodeGeneratorConfig.PROJECT_PATH + CodeGeneratorConfig.JAVA_PATH);
            javaModelGeneratorConfiguration.setTargetPackage(config.getBasePackage() + ".model");
            context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

            JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
            // TODO 加上module name
            javaClientGeneratorConfiguration.setTargetProject(CodeGeneratorConfig.PROJECT_PATH + CodeGeneratorConfig.JAVA_PATH);
            javaClientGeneratorConfiguration.setTargetPackage(config.getBasePackage() + ".mapper");
            javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
            context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);

            TableConfiguration tableConfiguration = new TableConfiguration(context);
            tableConfiguration.setTableName(tableName);
            tableConfiguration.setDomainObjectName(modelName);
            tableConfiguration.setGeneratedKey(new GeneratedKey("id", config.getDbEnum().getName(), true, null));
            context.addTableConfiguration(tableConfiguration);
        } catch (Exception e) {
            throw new RuntimeException("ModelAndMapperGenerator 初始化环境异常!", e);
        }
        return context;
    }
}
