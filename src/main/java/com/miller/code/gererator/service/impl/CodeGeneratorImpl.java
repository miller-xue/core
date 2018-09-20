package com.miller.code.gererator.service.impl;

import com.miller.code.gererator.config.CodeGeneratorConfig;
import com.miller.code.gererator.config.FreemakerUtil;
import com.miller.code.gererator.config.TemplateEnum;
import com.miller.code.gererator.service.CodeGenerator;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by miller on 2018/9/20
 * @author Miller
 */
@Slf4j
public class CodeGeneratorImpl implements CodeGenerator {


    @Override
    public void genCode(TemplateEnum templateEnum, String tableName, String modelName, CodeGeneratorConfig config) {
        try {
            Template template = FreemakerUtil.getTemplate(templateEnum);
            File basePath = getBasePath(config, templateEnum);
            String fileName = getFileName(templateEnum,modelName);
            File filePath = new File(basePath, fileName);
            if (filePath.exists() && filePath.isFile()) {
                log.error("文件已存在: " + fileName);
                return;
            }
            FreemakerUtil.writeTemplate(template,getInitData(config,modelName),filePath);
            log.info("生成代码成功" + fileName);
        } catch (Exception e) {
            log.error("生成代码失败 错误信息：", e);
        }

    }

    public static Map<String, Object> getInitData(CodeGeneratorConfig config, String modelName) {
        Map<String, Object> data = new HashMap<>();
        data.put("now", new Date());
        data.put("author", config.getAuthor());
        data.put("modelName", modelName);
        data.put("basePackage", config.getBasePackage());
        return data;
    }

    /**
     * 获得生成后的文件全名
     * @param templateEnum
     * @return
     */
    public static String getFileName(TemplateEnum templateEnum,String modelName) {
        return templateEnum.getFileName(modelName);
    }


    public static File getBasePath(CodeGeneratorConfig config, TemplateEnum templateEnum) {
        // 获取基础路径
        StringBuilder path = new StringBuilder(CodeGeneratorConfig.PROJECT_PATH);
        // 如果未聚合项目获取module名
        if (!StringUtils.isBlank(config.getModuleName())) {
            path.append(File.separator).append(config.getModuleName());
        }
        path.append(CodeGeneratorConfig.JAVA_PATH);

        // 基础包名， com.miller.seckill
        path.append(File.separator).append(config.getBasePackage().replace(".", File.separator));
        // 模板生成的指定子包
        path.append(File.separator).append(templateEnum.getPkg().replace(".", File.separator));

        File file = new File(path.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }
}
