package com.atguigu.util;

import java.util.Calendar;
import java.util.Date;

public class MyDateUtil {

	public static Date get_date(Integer day) {
		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.DATE, day);
		
		Date time = calendar.getTime();

		return time;
	}

}
