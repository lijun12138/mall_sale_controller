package com.atguigu.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;

import net.sf.json.JSONArray;

public class JsonUtil {

	public static <T> String obj_to_json(T t) {

		Gson gson = new Gson();
		String json = gson.toJson(t);
		try {
			json = URLEncoder.encode(json, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return json;

	}

	public static <T> T json_to_obj(String json, Class<T> t) {
		if(StringUtils.isBlank(json)) {
			return null;
		}
		
		try {
			json = URLDecoder.decode(json, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		Gson gson = new Gson();
		T obj = gson.fromJson(json, t);

		return obj;

	}

	public static <T> String list_to_json(T t) {

		Gson gson = new Gson();
		String json = gson.toJson(t);
		try {
			json = URLEncoder.encode(json, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return json;

	}

	public static <T> List<T> json_to_list(String json, Class<T> t) {

		if (StringUtils.isBlank(json)) {
			return null;
		}

		try {
			json = URLDecoder.decode(json, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		JSONArray jsonArray = JSONArray.fromObject(json);
		@SuppressWarnings("unchecked")
		List<T> list =(List<T>)JSONArray.toCollection(jsonArray, t);
		
		return list;

	}

}
