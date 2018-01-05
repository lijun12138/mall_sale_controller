package com.atguigu.test;

import java.util.ArrayList;
import java.util.List;

import com.atguigu.bean.T_MALL_SHOPPINGCART;
import com.atguigu.util.JsonUtil;

public class JsonTest {
	public static void main(String[] args) {
		T_MALL_SHOPPINGCART t_MALL_SHOPPINGCART = new T_MALL_SHOPPINGCART();
		t_MALL_SHOPPINGCART.setSku_mch("不知道");
		String obj_to_json = JsonUtil.obj_to_json(t_MALL_SHOPPINGCART);
		System.out.println(obj_to_json);
		
		T_MALL_SHOPPINGCART json_to_obj = JsonUtil.json_to_obj(obj_to_json, T_MALL_SHOPPINGCART.class);
		System.out.println(json_to_obj);
		
		List<T_MALL_SHOPPINGCART> list_cart = new ArrayList<>();
		
		for (int i = 0; i < 5; i++) {
			T_MALL_SHOPPINGCART cart = new T_MALL_SHOPPINGCART();
			cart.setSku_mch("不知道"+i);
			list_cart.add(cart);
		}
		
		String list_to_json = JsonUtil.list_to_json(list_cart);
		System.out.println(list_to_json);
		
		List<T_MALL_SHOPPINGCART> json_to_list = JsonUtil.json_to_list(list_to_json, T_MALL_SHOPPINGCART.class);
		System.out.println(json_to_list);
	}
}
