package com.miller.framework.sys.cart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.miller.framework.core.common.BaseController;
import com.miller.framework.sys.cart.model.Cart;
import com.miller.framework.sys.cart.service.CartService;

/**
 *
 * Created by miller on 2018-09-25.
 */
@Controller
@RequestMapping("/cart")
public class CartController extends BaseController<Cart, CartService> {


}
