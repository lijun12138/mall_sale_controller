package com.atguigu.service;

import java.util.List;

import com.atguigu.bean.OBJECT_T_MALL_SKU;
import com.atguigu.bean.OBJECT_T_MALL_SKU_DETAIL;
import com.atguigu.bean.T_MALL_SKU;
import com.atguigu.bean.T_MALL_SKU_ATTR_VALUE;

public interface SearchService {

	List<OBJECT_T_MALL_SKU> query_sku(Integer class_2_id);

	List<OBJECT_T_MALL_SKU> query_sku_by_attr(Integer class_2_id, List<T_MALL_SKU_ATTR_VALUE> list_av);

	OBJECT_T_MALL_SKU_DETAIL query_sku_by_sku_shp(Integer sku_id, Integer spu_id);

	List<T_MALL_SKU> query_sku_by_spu_id(Integer spu_id);

}
