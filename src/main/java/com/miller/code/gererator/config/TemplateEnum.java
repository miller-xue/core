package com.miller.code.gererator.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by miller on 2018/9/20
 * @author Miller
 */
@AllArgsConstructor
public enum TemplateEnum {
    /**
     * 控制层模板
     */
    CONTROLLER("controller.ftl","controller","Controller.java"),
    SERVICE("service.ftl","service","Service.java"),
    SERVICE_IMPL("service_impl.ftl","service.impl","ServiceImpl.java"),
    ;
    /**
     * 模板名称
     */
    @Getter
    private String name;

    /**
     * 模板生成后所在包
     */
    @Getter
    private String path;

    /**
     * 模板生成文件后缀
     */
    private String suffix;

    /**
     * 获取文件名称,
     * @param modelName 模板名称
     * @return 文件名称
     */
    public String getFileName(String modelName) {
       return modelName + suffix;
    }
}
