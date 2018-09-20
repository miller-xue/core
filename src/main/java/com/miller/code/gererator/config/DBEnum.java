package com.miller.code.gererator.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by miller on 2018/9/19
 * @author Miller_xue
 *
 * TODO 后续会扩展数据库类型 目前仅限于mysql代码生成
 */

@AllArgsConstructor
@Getter
public enum DBEnum {
    /**
     * mysql数据库 TODO
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
}
