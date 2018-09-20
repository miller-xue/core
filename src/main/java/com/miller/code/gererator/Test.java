package com.miller.code.gererator;

import com.google.common.base.CaseFormat;

/**
 * Created by miller on 2018/9/20
 */
public class Test {
    public static void main(String[] args) {
        String sys_user_t = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, "sys_user_t");
        System.out.println(sys_user_t);
    }
}
