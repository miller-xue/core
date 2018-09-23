package com.miller.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.miller.core.BaseController;
import com.miller.user.model.User;
import com.miller.user.service.UserService;

/**
 *
 * Created by miller on 2018-09-23.
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController<User, UserService> {


}
