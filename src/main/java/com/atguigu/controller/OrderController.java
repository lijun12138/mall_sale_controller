package com.atguigu.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.atguigu.bean.OBJECT_T_MALL_FLOW;
import com.atguigu.bean.OBJECT_T_MALL_ORDER;
import com.atguigu.bean.T_MALL_ADDRESS;
import com.atguigu.bean.T_MALL_ORDER_INFO;
import com.atguigu.bean.T_MALL_SHOPPINGCART;
import com.atguigu.bean.T_MALL_USER_ACCOUNT;
import com.atguigu.exception.OverSaleException;
import com.atguigu.server.AddressServer;
import com.atguigu.service.OrderService;
import com.atguigu.service.ShoppingCartService;

@Controller
@SessionAttributes("order")
public class OrderController {

	@Autowired
	private AddressServer addressServer;

	@Autowired
	private OrderService orderService;

	@Autowired
	private ShoppingCartService shoppingCartService;

	@RequestMapping("order_pay_success")
	public String order_pay_success() {
		return "sale_order_pay_success";
	}

	@RequestMapping("order_pay_fail/{message}")
	public String order_pay_fail(@PathVariable("message") String message,ModelMap map) {
		map.put("error_message", message);
		return "sale_order_pay_fail";
	}

	@RequestMapping("order_pay")
	public String order_pay(@ModelAttribute("order") OBJECT_T_MALL_ORDER order) {

		// 支付改库存业务
		try {
			orderService.order_pay(order);
			return "redirect:/order_pay_success.do";
		} catch (OverSaleException e) {
			return "redirect:/order_pay_fail/"+e.getMessage()+".do/";
		}
	}

	@RequestMapping("goto_cashire")
	public String goto_cashire() {
		return "sale_cashire";
	}

	@RequestMapping("save_order")
	public String save_order(HttpSession session, @ModelAttribute("order") OBJECT_T_MALL_ORDER order,
			Integer address_id) {
		// 查询选中地址的地址信息
		T_MALL_ADDRESS address = addressServer.query_address_by_id(address_id);

		// 保存订单信息
		orderService.save_ordre(order, address);

		// 同步session中的数据
		List<T_MALL_SHOPPINGCART> list_cart = shoppingCartService.query_list_cart_by_user_id(order.getYh_id());
		session.setAttribute("list_cart_session", list_cart);

		return "redirect:/goto_cashire.do";
	}

	@RequestMapping("goto_check_order")
	public String goto_check_order(HttpSession session, BigDecimal total_price, ModelMap map) {

		T_MALL_USER_ACCOUNT user = (T_MALL_USER_ACCOUNT) session.getAttribute("user");
		List<T_MALL_SHOPPINGCART> list_cart = (List<T_MALL_SHOPPINGCART>) session.getAttribute("list_cart_session");

		// 调用webservice服务查询地址信息
		List<T_MALL_ADDRESS> addresses = addressServer.query_address_by_user_id(user.getId());

		// 1.获得库存地址
		OBJECT_T_MALL_ORDER order = new OBJECT_T_MALL_ORDER();
		order.setJdh(0);
		order.setZje(total_price);
		order.setYh_id(user.getId());

		Set<String> kcdzs = new HashSet<>();
		for (T_MALL_SHOPPINGCART t_MALL_SHOPPINGCART : list_cart) {
			kcdzs.add(t_MALL_SHOPPINGCART.getKcdz());
		}

		// 2.根据库存地址分别保存到物流信息中
		List<OBJECT_T_MALL_FLOW> list_flow = new ArrayList<>();
		for (String kcdz : kcdzs) {
			OBJECT_T_MALL_FLOW flow = new OBJECT_T_MALL_FLOW();
			flow.setPsfsh("超级快递");
			flow.setYh_id(user.getId());
			flow.setMqdd(kcdz);

			List<T_MALL_ORDER_INFO> list_info = new ArrayList<>();

			// 3.将选中的商品信息保存到物流信息表中
			for (T_MALL_SHOPPINGCART t_MALL_SHOPPINGCART : list_cart) {
				if (t_MALL_SHOPPINGCART.getShfxz().equals("1") && kcdz.equals(t_MALL_SHOPPINGCART.getKcdz())) {
					T_MALL_ORDER_INFO info = new T_MALL_ORDER_INFO();
					info.setShp_tp(t_MALL_SHOPPINGCART.getShp_tp());
					info.setSku_id(t_MALL_SHOPPINGCART.getSku_id());
					info.setSku_jg(t_MALL_SHOPPINGCART.getSku_jg());
					info.setSku_kcdz(t_MALL_SHOPPINGCART.getKcdz());
					info.setSku_mch(t_MALL_SHOPPINGCART.getSku_mch());
					info.setSku_shl(t_MALL_SHOPPINGCART.getTjshl());
					info.setGwch_id(t_MALL_SHOPPINGCART.getId());
					list_info.add(info);
				}
			}
			flow.setList_info(list_info);
			list_flow.add(flow);
		}
		order.setFlow_list(list_flow);

		map.put("order", order);
		map.put("addresses", addresses);

		return "sale_check_order";
	}
}
