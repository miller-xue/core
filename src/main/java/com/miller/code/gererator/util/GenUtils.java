package com.miller.code.gererator.util;

import com.miller.code.gererator.config.GenConfig;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.util.StringUtils;


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
            templateConf.setDirectoryForTemplateLoading(new File(GenConfig.TEMPLATE_FILE_PATH));
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
     * 获取文件基础路径包路径
     * @param modelName 模型名称
     * @param targetPkg 文件生成后包
     * @return
     */
    public static File getBasePath(String modelName , String targetPkg) {
        // 1.获得java文件的绝对路径,如过为聚合项目,则为聚合项目全路径
        StringBuilder path = new StringBuilder(GenConfig.getJavaPath()).append(File.separator);

        // 2.获得生成后代码所在包名     TODO 可以考虑加上子包。childModuleName
        String pkg = getTargetPkg(modelName, targetPkg);

        path.append(pkg.replace(".", File.separator));
        File file = new File(path.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     * 根据model名称和目标文件夹生成包名
     * @param modelName 模型名
     * @param targetPkg 目标文件夹
     * @return basePackage.modelName.targetFolder
     * 基础包 + 模型名 + 模板生成目标文件夹
     */
    public static String getTargetPkg(String modelName, String targetPkg) {
        return new StringBuilder(GenConfig.BASE_PACKAGE)
                .append(".").append(StringUtils.uncapitalize(modelName))
                .append(".").append(targetPkg).toString();
    }
}
