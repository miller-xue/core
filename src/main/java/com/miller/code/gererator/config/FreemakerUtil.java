package com.miller.code.gererator.config;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
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
    public static Template getTemplate(TemplateEnum  templateEnum) throws FileNotFoundException {
        try {
            return config.getTemplate(templateEnum.getName());
        } catch (IOException e) {
            throw new FileNotFoundException("未找到指定模板" + templateEnum.getName() );
        }
    }


    @AllArgsConstructor
    public enum TemplateEnum {
        /**
         * 控制层模板
         */
        CONTROLLER("controller.ftl"),
        ;
        private String name;

        public String getName() {
            return name;
        }
    }

}
