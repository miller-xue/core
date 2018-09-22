package com.miller.code.gererator.util;

import com.miller.code.gererator.config.GenConfig;
import com.miller.code.gererator.config.GenConstants;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by miller on 2018/9/22
 * 代码生成工具类
 */
public class GenUtils
{

    /**
     * FreeMaker 模板配置
     */
    private static Configuration templateConf = null;


    static {
        initFreeMaker();
    }


    /**
     * 初始化FreeMaker模板配置
     */
    public static void initFreeMaker() {
        try {
            templateConf = new Configuration(Configuration.VERSION_2_3_23);
            templateConf.setDirectoryForTemplateLoading(new File(GenConstants.TEMPLATE_FILE_PATH));
            templateConf.setDefaultEncoding("UTF-8");
            templateConf.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Freemarker 模板环境初始化异常!", e);
        }
    }

    /**
     * 获取指定模板名称
     * @param templateName
     * @return
     * @throws FileNotFoundException
     */
    public static Template getTemplate(String templateName) throws FileNotFoundException {
        try {
            return templateConf.getTemplate(templateName);
        } catch (IOException e) {
            throw new FileNotFoundException("未找到指定模板" + templateName);
        }
    }

    /**
     * 写入模板文件到本地指定目录
     * @param template 模板对象
     * @param data 数据
     * @param filePath 本地路径
     * @throws IOException
     * @throws TemplateException
     */
    public static void writeTemplate(Template template, Object data, File filePath) throws IOException, TemplateException {
        template.process(data, new FileWriter(filePath));
    }





    /**
     * 获取文件基础路径
     * @param templateFolder
     * @return
     */
    public static File getBasePath(String modelName , String templateFolder) {
        // 1.获取基础路径(绝对项目路径)
        StringBuilder path = new StringBuilder(GenConstants.getJavaPath());

        /*// 2.如果未聚合项目获取module名
        if (!StringUtils.isBlank(GenConfig.MODULE_NAME)) {
            path.append(File.separator).append(GenConfig.MODULE_NAME);
        }

        // 3.拼接java路径
        path.append(GenConstants.JAVA_PATH);*/


        // 4.基础包名， com.miller.seckill
        path.append(File.separator).append(GenConfig.BASE_PACKAGE.replace(".", File.separator));

        //TODO 可以考虑加上子包。childModuleName

        // 5.模型名称
        path.append(File.separator).append(StringUtils.uncapitalize(modelName));

        // 6.模板生成的指定子包
        path.append(File.separator).append(templateFolder.replace(".", File.separator));

        File file = new File(path.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }
}
