package com.atguigu.service;

import java.util.List;

import com.atguigu.bean.T_MALL_SHOPPINGCART;

public interface ShoppingCartService {

	List<T_MALL_SHOPPINGCART> query_list_cart_by_user_id(int id);

	void add_cart(T_MALL_SHOPPINGCART cart);

	void updata_cart(T_MALL_SHOPPINGCART t_MALL_SHOPPINGCART);

	T_MALL_SHOPPINGCART query_cart_by_sku_id(int sku_id);

	void delete_cart(T_MALL_SHOPPINGCART t_MALL_SHOPPINGCART);

}
