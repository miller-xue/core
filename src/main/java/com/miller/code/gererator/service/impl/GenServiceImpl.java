package com.miller.code.gererator.service.impl;

import com.miller.code.gererator.config.GenConfig;
import com.miller.code.gererator.config.GenConstants;
import com.miller.code.gererator.config.TemplateEnum;
import com.miller.code.gererator.service.GenService;
import com.miller.code.gererator.util.GenUtils;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.*;

/**
 * Created by miller on 2018/9/22
 */
@Slf4j
public class GenServiceImpl implements GenService {

    /**
     * 生成Controller BaseService ServiceImpl 代码
     * @param tableName
     * @param modelName
     */
    @Override
    public void generatorCode(String tableName, String modelName,String author) {
        try {
            TemplateEnum[] templateEnums = TemplateEnum.values();
            for (TemplateEnum templateEnum : templateEnums) {
                Template template = GenUtils.getTemplate(templateEnum.getName());
                // 1.获得基础路径
                File basePath = GenUtils.getBasePath(modelName, templateEnum.getPath());
                // 2.获得文件名称
                String fileName = templateEnum.getFileName(modelName);
                // 3.生成文件全路径
                File filePath = new File(basePath, fileName);
                // 4.判断文件是否存在,存在不创建
                if (filePath.exists() && filePath.isFile()) {
                    log.error("文件已存在: " + fileName);
                    break;
                }
                // 4.写入本地
                GenUtils.writeTemplate(template, getInitData(modelName,author), filePath);
                log.info("生成代码成功" + fileName);
            }
        } catch (Exception e) {
            log.error("生成代码失败：" + modelName);
        }

    }

    public Map<String, Object> getInitData(String modelName,String author) {
        Map<String, Object> data = new HashMap<>();
        data.put("now", new Date());
        data.put("author", author);
        data.put("modelName", modelName);
        data.put("basePackage", GenConfig.BASE_PACKAGE);
        return data;
    }


    /**
     * 根据Mybatis 代码生成生成Model BaseMapper BaseMapper.xml
     * @param tableName
     * @param modelName
     */
    @Override
    public void generatorCodeByMybatis(String tableName, String modelName) {
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
            throw new RuntimeException("Model 和  BaseMapper 生成失败!", e);
        }

        if (generator == null || generator.getGeneratedJavaFiles().isEmpty() || generator.getGeneratedXmlFiles().isEmpty()) {
            throw new RuntimeException("Model 和  BaseMapper 生成失败, warnings: " + warnings);
        }

        log.info(modelName, ".java 生成成功!");
        log.info(modelName, "Mapper.java 生成成功!");
        log.info(modelName, "Mapper.xml 生成成功!");
    }


    /**
     * 增加 BaseMapper 插件
     * @param context
     */
    private void addMapperPlugin(Context context) {
        PluginConfiguration pluginConfiguration = new PluginConfiguration();
        pluginConfiguration.setConfigurationType("tk.mybatis.mapper.generator.MapperPlugin");
        pluginConfiguration.addProperty("mappers", "com.miller.core.BaseMapper");
        context.addPluginConfiguration(pluginConfiguration);
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

            // 1.model配置路径
            JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
            // TODO 没加上module name
            javaModelGeneratorConfiguration.setTargetProject(GenConstants.getJavaPath());

            javaModelGeneratorConfiguration.setTargetPackage(GenConfig.BASE_PACKAGE + "." + StringUtils.uncapitalize(modelName) + ".model");
            context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

            // 2.Mapper配置
            JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
            javaClientGeneratorConfiguration.setTargetProject(GenConstants.getJavaPath());
            javaClientGeneratorConfiguration.setTargetPackage(GenConfig.BASE_PACKAGE + "." + StringUtils.uncapitalize(modelName) + ".mapper");
            javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
            context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);


            TableConfiguration tableConfiguration = new TableConfiguration(context);
            tableConfiguration.setTableName(tableName);
            tableConfiguration.setDomainObjectName(modelName);
            tableConfiguration.setGeneratedKey(new GeneratedKey("id", GenConfig.DB_TYPE.getName(), true, null));
            context.addTableConfiguration(tableConfiguration);
        } catch (Exception e) {
            throw new RuntimeException("ModelAndMapperGenerator 初始化环境异常!", e);
        }
        return context;
    }


    /**
     * Mybatis 代码自动生成基本配置
     * @return
     */
    public Context initMybatisGeneratorContext() {
        Context context = new Context(ModelType.FLAT);

        // 1.基础配置
        context.setId("Potato");
        context.setTargetRuntime("MyBatis3Simple");
        context.addProperty(PropertyRegistry.CONTEXT_BEGINNING_DELIMITER, "`");
        context.addProperty(PropertyRegistry.CONTEXT_ENDING_DELIMITER, "`");

        // 2.JDBC基本配置
        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setConnectionURL(GenConfig.JDBC_URL);
        jdbcConnectionConfiguration.setUserId(GenConfig.JDBC_USERNAME);
        jdbcConnectionConfiguration.setPassword(GenConfig.JDBC_PASSWORD);
        jdbcConnectionConfiguration.setDriverClass(GenConfig.DB_TYPE.getDirverClassName());
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);


        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetProject(GenConstants.getResourcePath());
        sqlMapGeneratorConfiguration.setTargetPackage("mapper");
        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);

        // 增加 mapper 插件
        addMapperPlugin(context);

        return context;
    }

}
