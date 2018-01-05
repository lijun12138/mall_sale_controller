package com.atguigu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.bean.MODEL_T_MALL_SKU_ATTR_VALUE;
import com.atguigu.bean.OBJECT_T_MALL_ATTR;
import com.atguigu.bean.OBJECT_T_MALL_SKU;
import com.atguigu.bean.OBJECT_T_MALL_SKU_DETAIL;
import com.atguigu.bean.OBJECT_T_MALL_SKU_KEYWORDS;
import com.atguigu.bean.T_MALL_SKU;
import com.atguigu.bean.T_MALL_SKU_ATTR_VALUE;
import com.atguigu.service.AttrService;
import com.atguigu.service.SearchService;
import com.atguigu.util.JsonUtil;
import com.atguigu.util.MyCacheUtil;
import com.atguigu.util.MyHttpGetUtil;
import com.atguigu.util.PropertiesUtil;

@Controller
public class SearchController {

	@Autowired
	private SearchService searchService;

	@Autowired
	private AttrService attrService;

	@RequestMapping("search_keywords")
	public String search_keywords(String keywords, ModelMap map) throws Exception {

		String json = MyHttpGetUtil.doGet(PropertiesUtil.getPath("keywords.properties", "sku_keywords")+keywords);
		List<OBJECT_T_MALL_SKU_KEYWORDS> list_sku = JsonUtil.json_to_list(json, OBJECT_T_MALL_SKU_KEYWORDS.class);
		map.put("list_sku", list_sku);
		map.put("keywords", keywords);
		
		return "sale_search_keywords";
	}

	
	@RequestMapping("sku_detail")
	public String sku_detail(Integer sku_id, Integer spu_id, ModelMap map) {

		OBJECT_T_MALL_SKU_DETAIL sku_spu_tm_value = searchService.query_sku_by_sku_shp(sku_id, spu_id);

		List<T_MALL_SKU> list_sku = searchService.query_sku_by_spu_id(spu_id);
		map.put("sku_spu_tm_value", sku_spu_tm_value);
		map.put("list_sku", list_sku);
		return "sale_search_detail";
	}

	@RequestMapping("goto_class_search")
	public String goto_class_search(Integer class_2_id, String class_2_name, ModelMap map) {
		// 根据二级分类查询商品属性和sku信息
		List<OBJECT_T_MALL_ATTR> attr_list = attrService.query_attr_list(class_2_id);

		String key = "class_2_" + class_2_id;
		List<OBJECT_T_MALL_SKU> list = MyCacheUtil.getList(key, OBJECT_T_MALL_SKU.class);
		// 查询Redis中的数据

		if (list == null || list.size() == 0) {
			list = searchService.query_sku(class_2_id);
			MyCacheUtil.setList(list, key);
		}

		map.put("class_2_id", class_2_id);
		map.put("class_2_name", class_2_name);
		map.put("sku_spu_tm_value", list);
		map.put("attr_list", attr_list);

		return "sale_class_search";
	}

	// @RequestMapping("sale_search_attr")
	// public String sale_search_attr(Integer class_2_id, String
	// class_2_name,Integer attr_id,Integer attr_value_id, ModelMap map) {
	//
	// List<OBJECT_T_MALL_SKU> sku_spu_tm_value =
	// searchService.query_sku_by_attr(class_2_id,attr_id,attr_value_id);
	//
	// map.put("class_2_id", class_2_id);
	// map.put("class_2_name", class_2_name);
	//
	// map.put("sku_spu_tm_value", sku_spu_tm_value);
	// return "sale_class_search";
	// }
	@RequestMapping("sale_search_attr")
	public String sale_search_attr(Integer class_2_id, MODEL_T_MALL_SKU_ATTR_VALUE sku_attr_value_list, ModelMap map) {

		List<T_MALL_SKU_ATTR_VALUE> list_av = sku_attr_value_list.getList_av();
		String key = "class_2_" + class_2_id;
		for (int i = 0; i < list_av.size(); i++) {
			int shxm_id = list_av.get(i).getShxm_id();
			int shxzh_id = list_av.get(i).getShxzh_id();
			key += "_" + shxm_id + "_" + shxzh_id;
		}
		List<OBJECT_T_MALL_SKU> sku_spu_tm_value = MyCacheUtil.getList(key, OBJECT_T_MALL_SKU.class);
		if (sku_spu_tm_value == null || sku_spu_tm_value.size() == 0) {
			sku_spu_tm_value = searchService.query_sku_by_attr(class_2_id, sku_attr_value_list.getList_av());
			MyCacheUtil.setList(sku_spu_tm_value, key);
		}

		map.put("class_2_id", class_2_id);

		/*List<OBJECT_T_MALL_SKU_CLASS> sku_spu_tm_value = new ArrayList<OBJECT_T_MALL_SKU_CLASS>();

		List<T_MALL_SKU_ATTR_VALUE> list_av = sku_attr_value_list.getList_av();

		String[] keys = new String[list_av.size()];
		String dstkey = "dst";
		for (int i = 0; i < list_av.size(); i++) {
			String key = "attr_" + class_2_id + "_" + list_av.get(i).getShxm_id() + "_" + list_av.get(i).getShxzh_id();
			keys[i] = key;

			dstkey = dstkey + key;
		}

		// 将属性id的key交叉起来
		Jedis jedis = JedisPoolUtil.getJedis();

		// 如果之前没有dstkey
		Boolean exists = jedis.exists(dstkey);
		if (!exists) {
			jedis.zinterstore(dstkey, keys);
		}

		sku_spu_tm_value = MyCacheUtil.getList(dstkey, OBJECT_T_MALL_SKU_CLASS.class);

		// mysql
		if (sku_spu_tm_value == null || sku_spu_tm_value.size() == 0) {
			yList<OBJECT_T_MALL_SKU> query_sku_by_attr = searchService.query_sku_by_attr(class_2_id, list_av);
		}

		map.put("class_2_id", class_2_id);

		map.put("sku_spu_tm_value", sku_spu_tm_value);
		return "sale_class_search_list";
*/
		map.put("sku_spu_tm_value", sku_spu_tm_value);
		return "sale_class_search_list";
	}

}
