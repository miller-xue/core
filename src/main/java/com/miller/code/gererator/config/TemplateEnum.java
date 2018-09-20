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
/*
    SERVICE_IMPL("service_impl.ftl","service.impl","ServiceImpl.java"),
*/
    ;
    @Getter
    private String name;
    @Getter
    private String pkg;
    private String suffix;

    public String getFileName(String modelName) {
       return modelName + suffix;
    }
}
