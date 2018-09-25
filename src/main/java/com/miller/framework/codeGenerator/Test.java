package com.miller.framework.codeGenerator;

import com.miller.framework.codeGenerator.service.impl.GenServiceImpl;

/**
 * Created by miller on 2018/9/25
 */
public class Test {
    public static void main(String[] args) {
        GenServiceImpl genService = new GenServiceImpl();
        genService.generatorCode("cart" , "Cart" , "sys" , "miller");
    }
}
