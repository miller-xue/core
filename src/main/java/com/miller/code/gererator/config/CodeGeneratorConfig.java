package com.miller.code.gererator.config;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by miller on 2018/9/19
 * @author Miller
 */

@Getter
@Setter
public class CodeGeneratorConfig {

    private String jdbcUrl;

    private String jdbcUsername;

    private String jdbcPassword;

    private DBEnum dbEnum;

    /**
     * 如果项目为分模块项目,输入模块名
     */
    private String moduleName;

    /**
     * 项目基础包名,列入 com.miller, com.miller.seckill, com.miller.sell
     */
    private String basePackage;
    /**
     * 项目作者
     */
    private String author;

    public CodeGeneratorConfig(String jdbcUrl, String jdbcUsername, String jdbcPassword, DBEnum dbEnum, String basePackage, String author) {
        this(jdbcUrl, jdbcUsername, jdbcPassword, dbEnum, null, basePackage, author);
    }


    /**
     * 聚合多模块项目
     * @param jdbcUrl
     * @param jdbcUsername
     * @param jdbcPassword
     * @param dbEnum
     * @param moduleName
     * @param basePackage
     */
    public CodeGeneratorConfig(String jdbcUrl, String jdbcUsername, String jdbcPassword, DBEnum dbEnum, String moduleName, String basePackage, String author) {
        this.jdbcUrl = jdbcUrl;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
        this.dbEnum = dbEnum;
        this.moduleName = moduleName;
        this.basePackage = basePackage;
        this.author = author;
    }

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
    public  static String RESOURCES_PATH = "/src/main/resources";

    /**
     * 模板文件相对路径 /开头为绝对路径
     */
    public  static String TEMPLATE_FILE_PATH = "src/main/resources/generator";

}
