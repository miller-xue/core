package com.miller.code.gererator.config;


import java.io.InputStream;
import java.util.Properties;

/**
 * Created by miller on 2018/9/19
 * @author Miller
 */
public class GenConfig{

    public static String JDBC_URL;

    public static String JDBC_USERNAME;

    public static String JDBC_PASSWORD;

    public static DBTypeEnum DB_TYPE;

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
        }
        return prop;
    }



    /**
     * 如果项目为分模块项目,输入模块名
     */
    public static String MODULE_NAME;

}
