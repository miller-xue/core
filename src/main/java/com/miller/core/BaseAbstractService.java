package com.miller.core;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by miller on 2018/9/18
 */
public class BaseAbstractService<T, M extends BaseMapper<T>> implements BaseService<T> {

    public BaseAbstractService() {
        // 获取currentMapper
        // 获取modelCLass
    }

    /**
     * 当前服务层的mapper
     */
    @Autowired
    private M currentMapper;

    /**
     * 当前泛型真实类型的Class
     */
    private Class<T> modelClass;


    protected M getCurrentMapper() {
        return this.currentMapper;
    }

    @Override
    public int save(T model) {
        return 1;
    }

    @Override
    public int save(List<T> models) {
        return 0;
    }

    @Override
    public int deleteById(Integer id) {
        return 0;
    }

    @Override
    public int deleteByIds(String ids) {
        return 0;
    }

    @Override
    public int update(T model) {
        return 0;
    }

    @Override
    public T findById(Integer id) {
        return null;
    }
}
