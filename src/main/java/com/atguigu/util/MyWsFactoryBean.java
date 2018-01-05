package com.atguigu.util;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.FactoryBean;

public class MyWsFactoryBean<T> implements FactoryBean<T>{

	private String url;
	private Class<T> t;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Class<T> getT() {
		return t;
	}

	public void setT(Class<T> t) {
		this.t = t;
	}

	public static <T> T  getMyWs(String url , Class<T> t){
		JaxWsProxyFactoryBean jax = new JaxWsProxyFactoryBean();
		
		jax.setAddress(url);
		jax.setServiceClass(t);
		T ws = (T)jax.create();
		return ws;
	}

	@Override
	public T getObject() throws Exception {
		return getMyWs(this.url,this.t);
	}

	@Override
	public Class<?> getObjectType() {
		return this.t;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}
