package com.miller.core;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by miller on 2018/9/18
 * @author Miller
 */
public class Controller<T , S extends Service<T>> {

    @Autowired
    private S currentService;

    /**
     * 当前泛型真实类型的Class
     */
    private Class<T> modelClass;

    protected S getCurrentService() {
        return this.currentService;
    }


}
