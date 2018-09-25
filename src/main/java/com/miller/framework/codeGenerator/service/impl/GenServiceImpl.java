package com.miller.framework.codeGenerator.service.impl;



import com.miller.framework.codeGenerator.config.GenConfig;
import com.miller.framework.codeGenerator.config.TemplateEnum;
import com.miller.framework.codeGenerator.service.GenService;
import com.miller.framework.codeGenerator.util.GenUtils;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;
import java.io.File;
import java.util.*;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by miller on 2018/9/22
 */
@Slf4j
public class GenServiceImpl implements GenService {


    @Override
    public void generatorCode(String childPkg, @NonNull String tableName, @NonNull String modelName, @NonNull String author) {

        // 强制模块首字母大写
        modelName = StringUtils.capitalize(modelName);
        // 1.生成模板代码
        generatorCodeByTemplate(childPkg, modelName, author);

        // 2.生成mybatis代码
        generatorCodeByMybatis(childPkg, tableName, modelName);
    }

    private void generatorCodeByTemplate(String childPkg, String modelName, String author) {
        try {
            TemplateEnum[] templateEnums = TemplateEnum.values();
            for (TemplateEnum templateEnum : templateEnums) {
                Template template = GenUtils.getTemplate(templateEnum.getName());
                // 1.获得基础路径
                File basePath = GenUtils.getBasePath(childPkg, modelName, templateEnum.getTargetPkg());
                // 2.获得文件名称
                String fileName = templateEnum.getFileName(modelName);

                // 3.生成文件全路径
                File filePath = new File(basePath, fileName);
                // 4.判断文件是否存在,存在不创建
                if (filePath.exists() && filePath.isFile()) {
                    log.error("文件已存在: " + fileName);
                    continue;
                }
                // 4.写入本地
                GenUtils.writeTemplate(template, getInitData(childPkg, modelName, author), filePath);
                log.info("生成代码成功" + fileName);
            }
        } catch (Exception e) {
            log.error("生成代码失败：" + modelName);
        }
    }

    private Map<String, Object> getInitData(String childPkg, String modelName,String author) {
        Map<String, Object> data = new HashMap<>();
        // 1. 当前时间
        data.put("now", new Date());
        // 2. 作者
        data.put("author", author);
        // 3. 模型名首字母为大写
        data.put("modelName", modelName);
        // 4. 基础包
        data.put("basePackage", GenConfig.BASE_PACKAGE + (StringUtils.isBlank(childPkg) ? "" : "." + childPkg));
        data.put("templateEnum", TemplateEnum.toMap());
        return data;
    }


    /**
     * 根据Mybatis 代码生成生成Model BaseMapper BaseMapper.xml
     *
     * @param tableName
     * @param modelName
     */
    private void generatorCodeByMybatis(String childPkg, String tableName, String modelName) {

        Context initConfig = initConfig(childPkg, tableName, modelName);
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
            throw new RuntimeException("Model 和  BaseMapper 生成失败!" , e);
        }

        if (generator == null || generator.getGeneratedJavaFiles().isEmpty() || generator.getGeneratedXmlFiles().isEmpty()) {
            throw new RuntimeException("Model 和  BaseMapper 生成失败, warnings: " + warnings);
        }

        log.info(modelName + ".java 生成成功!");
        log.info(modelName + "Mapper.java 生成成功!");
        log.info(modelName + "Mapper.xml 生成成功!");
    }


    /**
     * 增加 BaseMapper 插件
     * @param context
     */
    private void addMapperPlugin(Context context) {
        PluginConfiguration pluginConfiguration = new PluginConfiguration();
        pluginConfiguration.setConfigurationType("tk.mybatis.mapper.generator.MapperPlugin");
        pluginConfiguration.addProperty("mappers", "com.miller.framework.core.common.BaseMapper");
        context.addPluginConfiguration(pluginConfiguration);
    }


    /**
     * 完善初始化环境
     *
     * @param tableName 表名
     * @param modelName 自定义实体类名, 为null则默认将表名下划线转成大驼峰形式
     */
    private Context initConfig(String childPkg, String tableName, String modelName) {
        Context context = null;
        try {
            context = initMybatisGeneratorContext();

            // 1.model配置路径
            JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
            javaModelGeneratorConfiguration.setTargetProject(GenConfig.getJavaPath());

            javaModelGeneratorConfiguration.setTargetPackage(GenUtils.getTargetPkg(childPkg, modelName, GenConfig.MODEL_PACKAGE));
            context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

            // 2.Mapper配置
            JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
            javaClientGeneratorConfiguration.setTargetProject(GenConfig.getJavaPath());
            javaClientGeneratorConfiguration.setTargetPackage(GenUtils.getTargetPkg(childPkg, modelName, GenConfig.MAPPER_PACKAGE));
            javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
            context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);


            TableConfiguration tableConfiguration = new TableConfiguration(context);
            tableConfiguration.setTableName(tableName);
            tableConfiguration.setDomainObjectName(modelName);
            tableConfiguration.setGeneratedKey(new GeneratedKey("id" , GenConfig.DB_TYPE.getName(), true, null));
            context.addTableConfiguration(tableConfiguration);
        } catch (Exception e) {
            throw new RuntimeException("ModelAndMapperGenerator 初始化环境异常!" , e);
        }
        return context;
    }


    /**
     * Mybatis 代码自动生成基本配置
     * @return
     */
    private Context initMybatisGeneratorContext() {
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

        // 3.SqlMapper配置
        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetProject(GenConfig.getResourcePath());
        sqlMapGeneratorConfiguration.setTargetPackage(GenConfig.SQL_MAPPER_PACKAGE);
        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);

        // 增加 mapper 插件
        addMapperPlugin(context);

        return context;
    }
}
