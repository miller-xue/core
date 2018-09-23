package com.miller.code.gererator;

import com.miller.code.gererator.service.impl.GenServiceImpl;

/**
 * Created by miller on 2018/9/19
 * @author Miller
 */
public class CodeGeneratorManager {

    public static void main(String[] args) {
        GenServiceImpl genService = new GenServiceImpl();
        genService.generatorCodeByMybatis("user","User");
        genService.generatorCode("user", "User", "miller");
    }



}
