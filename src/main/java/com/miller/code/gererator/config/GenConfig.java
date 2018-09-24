package com.miller.code.gererator.config;


import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by miller on 2018/9/19
 * @author Miller
 */
public class GenConfig{

    /**
     * 项目在硬盘上的基础路径
     */

    private static String PROJECT_PATH = System.getProperty("user.dir");
    /**
     * 生成java文件所存基础路径
     */
    private static String JAVA_PATH = "/src/main/java";

    /**
     * 生成资源文件所存路径
     */
    private static String RESOURCES_PATH = "/src/main/resources";

    /**
     * 模板文件相对路径 /开头为绝对路径
     */
    public static String TEMPLATE_FILE_PATH = "src/main/resources/generator";

    /**
     * sqlMapper 生成后的指定包
     */
    public static String SQL_MAPPER_PACKAGE = "mapper";

    /**
     * mapper生成后的制定包
     */
    public static String MAPPER_PACKAGE = "dao";

    /**
     * model生成后的指定包
     */
    public static String MODEL_PACKAGE = "model";



    public static String JDBC_URL;

    public static String JDBC_USERNAME;

    public static String JDBC_PASSWORD;

    /**
     * 数据库类型
     */
    public static DBTypeEnum DB_TYPE;

    /**
     * 如果项目为分模块项目,输入模块名 TODO 模块名应该每次生成的时候输入,OR 系统常量进行选择
     */
    private static String MODULE_NAME;

    /**
     * 项目基础包名,列入 com.miller, com.miller.seckill, com.miller.sell
     */
    public static String BASE_PACKAGE;


    static {
        Properties prop = loadProperties();
        JDBC_URL = prop.getProperty("jdbc_url");
        JDBC_USERNAME = prop.getProperty("jdbc_username");
        JDBC_PASSWORD = prop.getProperty("jdbc_password");
        DB_TYPE = DBTypeEnum.getByName(prop.getProperty("db_name"));
        BASE_PACKAGE = prop.getProperty("base_package");
    }

    /**
     * 加载配置文件
     * @return
     */
    private static Properties loadProperties() {
        Properties prop = null;
        try {
            prop = new Properties();
            InputStream in = GenConfig.class.getClassLoader().getResourceAsStream("generatorConfig.properties");
            prop.load(in);
        } catch (Exception e) {
            throw new RuntimeException("加载配置文件异常!", e);
            //TODO 多模块项目配置文件路径可能会发生变化, 默认读取Resources 下 聚合项目读取核心包下
        }
        return prop;
    }

    /**
     * 获取java代码绝对根路径
     * @return
     */
    public static String getJavaPath() {
        return PROJECT_PATH + (StringUtils.isBlank(MODULE_NAME) ? "" : "/" + MODULE_NAME) + JAVA_PATH;
    }

    /**
     * 获取资源文件绝对根路径
     * @return
     */
    public static String getResourcePath() {
        return PROJECT_PATH + (StringUtils.isBlank(MODULE_NAME) ? "" : "/" + MODULE_NAME) + RESOURCES_PATH;

    }

}
