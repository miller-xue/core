package com.miller.code.gererator.config;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by miller on 2018/9/19
 * @author Miller
 */
@Slf4j
public class FreemakerUtil {

    private FreemakerUtil() {
    }

    private static Configuration config = null;

    /**
     * 初始化FreeMaker模板配置
     */
    static {
        try {
            config = new Configuration(Configuration.VERSION_2_3_23);
            config.setDirectoryForTemplateLoading(new File(CodeGeneratorConfig.TEMPLATE_FILE_PATH));
            config.setDefaultEncoding("UTF-8");
            config.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Freemarker 模板环境初始化异常!", e);
        }
    }

    /**
     * 获取指定模板名称
     * @param templateEnum
     * @return
     * @throws FileNotFoundException
     */
    public static Template getTemplate(TemplateEnum templateEnum) throws FileNotFoundException {
        try {
            return config.getTemplate(templateEnum.getName());
        } catch (IOException e) {
            throw new FileNotFoundException("未找到指定模板" + templateEnum.getName() );
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
}
