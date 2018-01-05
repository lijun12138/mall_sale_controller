package com.atguigu.bean;

import java.util.List;

public class OBJECT_T_MALL_SKU_DETAIL extends T_MALL_SKU {

	private T_MALL_PRODUCT spu;
	private List<T_MALL_PRODUCT_IMAGE> spu_img;
	private List<T_MALL_SKU_AV_NAME> sku_av_name;

	public T_MALL_PRODUCT getSpu() {
		return spu;
	}

	public void setSpu(T_MALL_PRODUCT spu) {
		this.spu = spu;
	}

	public List<T_MALL_PRODUCT_IMAGE> getSpu_img() {
		return spu_img;
	}

	public void setSpu_img(List<T_MALL_PRODUCT_IMAGE> spu_img) {
		this.spu_img = spu_img;
	}

	public List<T_MALL_SKU_AV_NAME> getSku_av_name() {
		return sku_av_name;
	}

	public void setSku_av_name(List<T_MALL_SKU_AV_NAME> sku_av_name) {
		this.sku_av_name = sku_av_name;
	}

}
