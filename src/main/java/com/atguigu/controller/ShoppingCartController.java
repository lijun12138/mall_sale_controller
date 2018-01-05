package com.atguigu.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.bean.T_MALL_SHOPPINGCART;
import com.atguigu.bean.T_MALL_USER_ACCOUNT;
import com.atguigu.service.ShoppingCartService;
import com.atguigu.util.JsonUtil;
import com.atguigu.util.NumberUtil;

@Controller
public class ShoppingCartController {

	@Autowired
	private ShoppingCartService shoppingCartService;

	@SuppressWarnings("unchecked")
	@RequestMapping("add_cart")
	public String add_cart(@CookieValue(value = "list_cart_cookie", required = false) String list_cart_cookie,
			T_MALL_SHOPPINGCART cart, HttpSession session, HttpServletResponse response) {
		// List<T_MALL_SHOPPINGCART> list_cart = JsonUtil.json_to_list(list_cart_cookie,
		// T_MALL_SHOPPINGCART.class);
		cart.setHj(cart.getTjshl() * cart.getSku_jg());
		List<T_MALL_SHOPPINGCART> list_cart = new ArrayList<>();

		T_MALL_USER_ACCOUNT user = (T_MALL_USER_ACCOUNT) session.getAttribute("user");
		// 用户未登录
		if (user == null) {
			if (StringUtils.isBlank(list_cart_cookie)) {
				// cookie中没有购物车项，只需要进行添加到cookie中即可
				list_cart.add(cart);
			} else {
				// 将cookie中的json字符串转换为购物车项的集合
				list_cart = JsonUtil.json_to_list(list_cart_cookie, T_MALL_SHOPPINGCART.class);
				// 判断当前添加的购物车是否是在cookie中重复
				boolean b = is_new_cart(list_cart, cart);
				// 是新的购物车返回true
				if (b) {
					// 不重复，是新的购物车，添加
					list_cart.add(cart);
				} else {
					// 重复，不是新的购物车，更新
					for (T_MALL_SHOPPINGCART t_MALL_SHOPPINGCART : list_cart) {
						if (t_MALL_SHOPPINGCART.getSku_id() == cart.getSku_id()) {
							// 更新cookie中重复的购物车项
							t_MALL_SHOPPINGCART.setTjshl(t_MALL_SHOPPINGCART.getTjshl() + cart.getTjshl());
							t_MALL_SHOPPINGCART.setHj(t_MALL_SHOPPINGCART.getTjshl() * t_MALL_SHOPPINGCART.getSku_jg());
						}
					}
				}
			}
			// 将新的cookie更新到客户端浏览器
			Cookie cookie = new Cookie("list_cart_cookie", JsonUtil.list_to_json(list_cart));
			cookie.setMaxAge(60 * 60 * 24 * 7);
			response.addCookie(cookie);

		} else {
			// //用户已登录
			// T_MALL_SHOPPINGCART cart_db =
			// shoppingCartService.query_cart_by_sku_id(cart.getSku_id());
			// if(cart_db==null) {
			// //说明是重复的，需要更新
			// cart.setTjshl(cart_db.getTjshl() + cart.getTjshl());
			// cart.setHj(cart.getTjshl() * cart.getSku_jg());
			// shoppingCartService.updata_cart(cart);
			// }else {
			// cart.setYh_id(user.getId());
			// //说明不是重复的，添加即可
			// shoppingCartService.add_cart(cart);
			// }

			// 用户已登录，将用户对应的购物车项取出
			list_cart = (List<T_MALL_SHOPPINGCART>) session.getAttribute("list_cart_session");
			// 判断session中的购物车是否是空的
			if (list_cart == null || list_cart.size() == 0) {
				// sessoin中没有数据，直接添加
				shoppingCartService.add_cart(cart);
				// 同步session
				list_cart = new ArrayList<>();
				// 将当前购物车项放入session中
				list_cart.add(cart);
				session.setAttribute("list_cart_session", list_cart);
			} else {
				// 判断当前对象是否是重复的
				boolean b = is_new_cart(list_cart, cart);
				if (b) {
					// 是新的购物车项
					// 添加到db中
					shoppingCartService.add_cart(cart);
					list_cart.add(cart);
				} else {
					// 不是新的购物车项
					for (T_MALL_SHOPPINGCART t_MALL_SHOPPINGCART : list_cart) {
						if (t_MALL_SHOPPINGCART.getSku_id() == cart.getSku_id()) {
							// 同步session中的数据
							t_MALL_SHOPPINGCART.setTjshl(t_MALL_SHOPPINGCART.getTjshl() + cart.getTjshl());
							t_MALL_SHOPPINGCART.setHj(t_MALL_SHOPPINGCART.getTjshl() * t_MALL_SHOPPINGCART.getSku_jg());
							// 更新数据库中的数据
							shoppingCartService.updata_cart(t_MALL_SHOPPINGCART);
						}
					}
				}
			}
		}
		return "redirect:/sale_cart_success.do";
	}

	public boolean is_new_cart(List<T_MALL_SHOPPINGCART> list_cart, T_MALL_SHOPPINGCART cart) {
		boolean b = true;
		for (T_MALL_SHOPPINGCART t_MALL_SHOPPINGCART : list_cart) {
			if (t_MALL_SHOPPINGCART.getSku_id() == cart.getSku_id()) {
				// 只要有相同的sku_id就不是新的购物车
				b = false;
			}
		}
		return b;
	}

	@RequestMapping("sale_cart_success")
	public String sale_cart_success() {
		return "sale_cart_success";
	}

	@RequestMapping("mini_cart")
	public String mini_cart(@CookieValue(value = "list_cart_cookie", required = false) String list_cart_cookie,
			HttpSession session, ModelMap map) {

		List<T_MALL_SHOPPINGCART> list_cart = new ArrayList<>();

		T_MALL_USER_ACCOUNT user = (T_MALL_USER_ACCOUNT) session.getAttribute("user");
		if (user == null) {
			// 用户未登录，将cookie中的购物车集合放入迷你购物车中
			list_cart = JsonUtil.json_to_list(list_cart_cookie, T_MALL_SHOPPINGCART.class);
		} else {
			list_cart = shoppingCartService.query_list_cart_by_user_id(user.getId());
		}
		map.put("total_price", NumberUtil.get_total_price(list_cart));
		map.put("list_cart", list_cart);
		return "sale_mini_cart_list";
	}

	@RequestMapping("goto_cart_list")
	public String goto_cart_list(@CookieValue(value = "list_cart_cookie", required = false) String list_cart_cookie,
			HttpSession session, ModelMap map) {

		List<T_MALL_SHOPPINGCART> list_cart = new ArrayList<>();

		T_MALL_USER_ACCOUNT user = (T_MALL_USER_ACCOUNT) session.getAttribute("user");
		if (user == null) {
			// 用户未登录，将cookie中的购物车集合放入购物车中
			list_cart = JsonUtil.json_to_list(list_cart_cookie, T_MALL_SHOPPINGCART.class);
		} else {
			list_cart = (List<T_MALL_SHOPPINGCART>)session.getAttribute("list_cart_session");
		}
		map.put("total_price", NumberUtil.get_total_price(list_cart));
		map.put("list_cart", list_cart);
		return "sale_cart_list";
	}

	@RequestMapping("change_status")
	public String change_status(HttpServletResponse response, T_MALL_SHOPPINGCART cart,
			@CookieValue(value = "list_cart_cookie", required = false) String list_cart_cookie, HttpSession session,
			ModelMap map) {

		List<T_MALL_SHOPPINGCART> list_cart = new ArrayList<>();

		T_MALL_USER_ACCOUNT user = (T_MALL_USER_ACCOUNT) session.getAttribute("user");
		if (user == null) {
			// 用户未登录，将cookie中的购物车集合放入购物车中
			list_cart = JsonUtil.json_to_list(list_cart_cookie, T_MALL_SHOPPINGCART.class);
		} else {
			// 用户已登录，将数据库中的购物车集合放入购物车中
			list_cart = shoppingCartService.query_list_cart_by_user_id(user.getId());
		}

		for (T_MALL_SHOPPINGCART t_MALL_SHOPPINGCART : list_cart) {
			if (t_MALL_SHOPPINGCART.getSku_id() == cart.getSku_id()) {
				t_MALL_SHOPPINGCART.setShfxz(cart.getShfxz());
				if (user == null) {
					// 将新的cookie更新到客户端浏览器
					Cookie cookie = new Cookie("list_cart_cookie", JsonUtil.list_to_json(list_cart));
					cookie.setMaxAge(60 * 60 * 24 * 7);
					response.addCookie(cookie);
				} else {
					shoppingCartService.updata_cart(t_MALL_SHOPPINGCART);
				}
			}
		}
		map.put("total_price", NumberUtil.get_total_price(list_cart));
		map.put("list_cart", list_cart);
		return "sale_cart_list_inner";
	}

	@RequestMapping("change_tjshl")
	public String change_tjshl(HttpServletResponse response, T_MALL_SHOPPINGCART cart,
			@CookieValue(value = "list_cart_cookie", required = false) String list_cart_cookie, HttpSession session,
			ModelMap map) {

		List<T_MALL_SHOPPINGCART> list_cart = new ArrayList<>();

		T_MALL_USER_ACCOUNT user = (T_MALL_USER_ACCOUNT) session.getAttribute("user");
		if (user == null) {
			// 用户未登录，将cookie中的购物车集合放入购物车中
			list_cart = JsonUtil.json_to_list(list_cart_cookie, T_MALL_SHOPPINGCART.class);
		} else {
			// 用户已登录，将数据库中的购物车集合放入购物车中
			list_cart = shoppingCartService.query_list_cart_by_user_id(user.getId());
		}

		for (T_MALL_SHOPPINGCART t_MALL_SHOPPINGCART : list_cart) {
			if (t_MALL_SHOPPINGCART.getSku_id() == cart.getSku_id()) {
				t_MALL_SHOPPINGCART.setTjshl(cart.getTjshl());
				t_MALL_SHOPPINGCART.setHj(t_MALL_SHOPPINGCART.getSku_jg() * t_MALL_SHOPPINGCART.getTjshl());
				if (user == null) {
					// 将新的cookie更新到客户端浏览器
					Cookie cookie = new Cookie("list_cart_cookie", JsonUtil.list_to_json(list_cart));
					cookie.setMaxAge(60 * 60 * 24 * 7);
					response.addCookie(cookie);
				} else {
					shoppingCartService.updata_cart(t_MALL_SHOPPINGCART);
				}
			}
		}
		map.put("total_price", NumberUtil.get_total_price(list_cart));
		map.put("list_cart", list_cart);
		return "sale_cart_list_inner";
	}

	@RequestMapping("delete_cart")
	public String delete_cart(HttpServletResponse response, T_MALL_SHOPPINGCART cart,
			@CookieValue(value = "list_cart_cookie", required = false) String list_cart_cookie, HttpSession session,
			ModelMap map) {

		List<T_MALL_SHOPPINGCART> list_cart = new ArrayList<>();

		T_MALL_USER_ACCOUNT user = (T_MALL_USER_ACCOUNT) session.getAttribute("user");
		if (user == null) {
			// 用户未登录，将cookie中的购物车集合放入购物车中
			list_cart = JsonUtil.json_to_list(list_cart_cookie, T_MALL_SHOPPINGCART.class);
		} else {
			// 用户已登录，将数据库中的购物车集合放入购物车中
			list_cart = shoppingCartService.query_list_cart_by_user_id(user.getId());
		}
		T_MALL_SHOPPINGCART delete_cart = null;
		for (T_MALL_SHOPPINGCART t_MALL_SHOPPINGCART : list_cart) {
			if (t_MALL_SHOPPINGCART.getSku_id() == cart.getSku_id()) {
				delete_cart = t_MALL_SHOPPINGCART;
				if (user == null) {
					// 将新的cookie更新到客户端浏览器
					Cookie cookie = new Cookie("list_cart_cookie", JsonUtil.list_to_json(list_cart));
					cookie.setMaxAge(60 * 60 * 24 * 7);
					response.addCookie(cookie);
				} else {
					shoppingCartService.delete_cart(t_MALL_SHOPPINGCART);
				}
			}
		}
		list_cart.remove(delete_cart);
		map.put("total_price", NumberUtil.get_total_price(list_cart));
		map.put("list_cart", list_cart);
		return "sale_cart_list_inner";
	}

}
