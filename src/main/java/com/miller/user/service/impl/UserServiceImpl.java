package com.miller.user.service.impl;


import org.springframework.stereotype.Service;
import com.miller.core.BaseAbstractService;
import com.miller.user.model.User;
import com.miller.user.service.UserService;
import com.miller.user.mapper.UserMapper;

/**
 *
 * Created by miller on 2018-09-23
 */
@Service
public class UserServiceImpl extends BaseAbstractService<User,UserMapper> implements UserService {


}
