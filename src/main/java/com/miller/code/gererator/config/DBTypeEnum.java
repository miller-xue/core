package com.miller.code.gererator.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by miller on 2018/9/19
 * @author Miller_xue
 *
 * TODO 后续会扩展数据库类型 目前仅限于mysql代码生成
 */

@AllArgsConstructor
@Getter
public enum DBTypeEnum {
    /**
     * mysql数据库
     */
    MYSQL("Mysql", "com.mysql.jdbc.Driver"),

    ;

    /**
     * 数据库名
     */
    private String name;

    /**
     * 数据库连接地址
     */
    private String dirverClassName;

    /**
     * 根据数据库名称找出对应对象
     *
     * @param name 数据库名称
     * @return 对象
     */
    public static DBTypeEnum getByName(@NonNull String name) {
        for (DBTypeEnum dbTypeEnum : values()) {
            if (dbTypeEnum.getName().equalsIgnoreCase(name)) {
                return dbTypeEnum;
            }
        }
        return null;
    }
}
