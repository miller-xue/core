package com.miller.core;

import java.util.List;

/**
 * Created by miller on 2018/9/18
 */
public interface Service<T> {

    int save(T model);//持久化

    int save(List<T> models);//批量持久化

    int deleteById(Integer id);//通过主鍵刪除

    int deleteByIds(String ids);//批量刪除 eg：ids -> “1,2,3,4”

    int update(T model);//更新

    T findById(Integer id);//通过ID查找
}
