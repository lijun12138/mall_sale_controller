package com.atguigu.server;

import javax.jws.WebService;

import com.atguigu.bean.T_MALL_USER_ACCOUNT;

@WebService
public interface UserServer {
	public T_MALL_USER_ACCOUNT login(T_MALL_USER_ACCOUNT user);
}
