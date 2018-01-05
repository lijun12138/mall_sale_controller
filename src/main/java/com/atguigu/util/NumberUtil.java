package com.atguigu.util;

import java.math.BigDecimal;
import java.util.List;

import com.atguigu.bean.T_MALL_SHOPPINGCART;

public class NumberUtil {

	public static BigDecimal get_total_price(List<T_MALL_SHOPPINGCART> list_cart) {
		BigDecimal total_price = new BigDecimal("0");
		if(list_cart!=null) {
			for (T_MALL_SHOPPINGCART t_MALL_SHOPPINGCART : list_cart) {
				if(t_MALL_SHOPPINGCART.getShfxz().equals("1")) {
					total_price=total_price.add(new BigDecimal(t_MALL_SHOPPINGCART.getHj()+""));
				}
			}
		}
		return total_price;
	}
	
}
