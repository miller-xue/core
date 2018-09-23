package com.miller.code.gererator.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by miller on 2018/9/20
 *
 * @author Miller
 */
@AllArgsConstructor
public enum TemplateEnum {
    /**
     * 控制层模板
     */
    CONTROLLER("controller.ftl", "controller", "Controller","java"),
    SERVICE("service.ftl", "service", "Service","java"),
    SERVICE_IMPL("service_impl.ftl", "service.impl", "ServiceImpl","java"),
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
    private String targetPkg;

    /**
     * Class类名后缀
     */
    @Getter
    private String suffix;

    /**
     * 文件扩展名,生成文件格式
     */
    private String extensionName;

    /**
     * 获取文件名称,
     *
     * @param modelName 模板名称
     * @return 文件名称
     */
    public String getFileName(String modelName) {
        return new StringBuilder(modelName).append(suffix).append(".").append(extensionName).toString();
    }

    public static Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        for (TemplateEnum templateEnum : values()) {
            Map<String, String> obj = new HashMap<>();
            obj.put("targetPkg", templateEnum.targetPkg);
            obj.put("suffix", templateEnum.suffix);
            result.put(templateEnum.name(), obj);
        }
        return result;
    }

    public static void main(String[] args) {
        toMap();
    }
}
