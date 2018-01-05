package com.atguigu.server;

import java.util.List;

import javax.jws.WebService;

import com.atguigu.bean.T_MALL_ADDRESS;

@WebService
public interface AddressServer {

	public void save_address(T_MALL_ADDRESS address);

	public List<T_MALL_ADDRESS> query_address_by_user_id(Integer user_id);

	public T_MALL_ADDRESS query_address_by_id(Integer id);
}
