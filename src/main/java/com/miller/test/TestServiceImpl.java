package com.miller.test;

import com.miller.core.BaseAbstractService;
import org.springframework.stereotype.Service;

/**
 * Created by miller on 2018/9/22
 */
@Service
public class TestServiceImpl extends BaseAbstractService<Test,TestMapper> implements TestService {
}
