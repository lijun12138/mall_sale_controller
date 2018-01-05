package com.atguigu.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class MyCacheUtil {

	public static <T> List<T> getList(String key, Class<T> t) {
		List<T> list = new ArrayList<>();
		try {
			Jedis jedis = JedisPoolUtil.getJedis();
			Set<String> zrange = jedis.zrange(key, 0, -1);
			if (zrange != null && zrange.size() != 0) {
				for (String json : zrange) {
					T sku = JsonUtil.json_to_obj(json, t);
					list.add(sku);
				}
			}
		} catch (Exception e) {
			return null;
		}
		return list;
	}

	public static <T> void setList(List<T> list, String key) {
		try {
			Jedis jedis = JedisPoolUtil.getJedis();
			for (int i = 0; i < list.size(); i++) {
				jedis.zadd(key, i, JsonUtil.obj_to_json(list.get(i)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
