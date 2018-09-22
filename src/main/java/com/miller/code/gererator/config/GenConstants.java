package com.miller.code.gererator.config;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by miller on 2018/9/22
 */
public class GenConstants {

    /**
     * 项目在硬盘上的基础路径
     */

    public static String PROJECT_PATH = System.getProperty("user.dir");
    /**
     * 生成java文件所存基础路径
     */
    public static String JAVA_PATH = "/src/main/java";

    /**
     * 生成资源文件所存路径
     */
    public static String RESOURCES_PATH = "/src/main/resources";

    /**
     * 模板文件相对路径 /开头为绝对路径
     */
    public static String TEMPLATE_FILE_PATH = "src/main/resources/generator";



    public static String getJavaPath() {
        if (StringUtils.isBlank(GenConfig.MODULE_NAME)) {
            return PROJECT_PATH + JAVA_PATH;
        }
        return PROJECT_PATH + "/" + GenConfig.MODULE_NAME + JAVA_PATH;
    }

    public static String getResourcePath() {
        if (StringUtils.isBlank(GenConfig.MODULE_NAME)) {
            return PROJECT_PATH + RESOURCES_PATH;
        }
        return PROJECT_PATH + "/" + GenConfig.MODULE_NAME + RESOURCES_PATH;
    }

}
