package com.atguigu.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.bean.T_MALL_SHOPPINGCART;
import com.atguigu.bean.T_MALL_USER_ACCOUNT;
import com.atguigu.server.UserServer;
import com.atguigu.service.ShoppingCartService;
import com.atguigu.util.JsonUtil;

@Controller
public class LoginController {

	// @Autowired
	// private LoginMapper loginMapper;

	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private UserServer userServer;

	@RequestMapping("to_login")
	public String to_login() {
		return "sale_login";
	}

	@RequestMapping("login")
	public String login(@CookieValue(value = "list_cart_cookie", required = false) String list_cart_cookie,
			HttpSession session, T_MALL_USER_ACCOUNT user, HttpServletResponse response) {

		List<T_MALL_SHOPPINGCART> list_cart = new ArrayList<>();

		// 如果登录成功保存用户信息
		T_MALL_USER_ACCOUNT login = userServer.login(user);
		
		
		if (login == null) {
			return "redirect:/to_login.do";
		} else {
			if (StringUtils.isBlank(list_cart_cookie)) {
				// cookie中购物车是空的，只需要将数据库中对应用户的购物车查出来同步到session中
				list_cart = shoppingCartService.query_list_cart_by_user_id(login.getId());
			} else {
				// cookie中的数据不是空的
				List<T_MALL_SHOPPINGCART> list_cart_db = shoppingCartService.query_list_cart_by_user_id(login.getId());
				if (list_cart_db == null || list_cart_db.size() == 0) {
					// 说明数据库中用户对应的购物车项是空的，只需要将cookie中的数据保存到数据库，并同步到session中
					list_cart = JsonUtil.json_to_list(list_cart_cookie, T_MALL_SHOPPINGCART.class);

					for (T_MALL_SHOPPINGCART t_MALL_SHOPPINGCART : list_cart) {
						t_MALL_SHOPPINGCART.setYh_id(login.getId());
						shoppingCartService.add_cart(t_MALL_SHOPPINGCART);
					}
				} else {
					// 数据库中有数据,判断数据是否重复
					list_cart = JsonUtil.json_to_list(list_cart_cookie, T_MALL_SHOPPINGCART.class);
					for (T_MALL_SHOPPINGCART cart : list_cart) {
						// 设置一个标签，初始为true
						boolean b = true;
						for (T_MALL_SHOPPINGCART db : list_cart_db) {
							if (db.getSku_id() == cart.getSku_id()) {
								// 说明数据是重复的，更新数据
								db.setTjshl(db.getTjshl() + cart.getTjshl());
								db.setHj(db.getTjshl() * db.getSku_jg());
								shoppingCartService.updata_cart(db);
								// 将标签设置为false
								b = false;
							}
						}
						if (b) {
							// 为true时说明不是重复的，需要将cookie中的数据存入到数据库中
							cart.setYh_id(login.getId());
							shoppingCartService.add_cart(cart);
						}
					}
					list_cart = shoppingCartService.query_list_cart_by_user_id(login.getId());
				}
			}
			
			session.setAttribute("user", login);
			session.setAttribute("list_cart_session", list_cart);
			Cookie cookie2 = new Cookie("list_cart_cookie", "");

			Cookie cookie = null;
			try {
				cookie = new Cookie("yh_nch", URLEncoder.encode(login.getYh_nch(), "utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			cookie.setMaxAge(60 * 60 * 24);
			response.addCookie(cookie);
			response.addCookie(cookie2);
			return "redirect:/sale_index.do";
		}
	}

	@RequestMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/to_login.do";
	}

}
