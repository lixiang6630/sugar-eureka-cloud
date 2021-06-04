/**
 * Copyright (c) 2015-2016, Chill Zhuang 庄骞 (smallchill@163.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.common.base.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

	//一天毫秒数
	public static Long DAY_MILIS=1L*24 * 60 * 60 * 1000;

	/**
	 * 获取YYYY格式
	 *
	 * @return
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 获取YYYY格式
	 *
	 * @return
	 */
	public static String getYear(Date date) {
		return formatDate(date, "yyyy");
	}

	/**
	 * 获取YYYY-MM-DD格式
	 *
	 * @return
	 */
	public static String getDay() {
		return formatDate(new Date(), "yyyy-MM-dd");
	}

	/**
	 * 获取YYYY-MM-DD格式
	 *
	 * @return
	 */
	public static String getDay(Date date) {
		return formatDate(date, "yyyy-MM-dd");
	}

	/**
	 * 获取YYYYMMDD格式
	 *
	 * @return
	 */
	public static String getDays() {
		return formatDate(new Date(), "yyyyMMdd");
	}

	/**
	 * 获取YYYYMMDD格式
	 *
	 * @return
	 */
	public static String getDays(Date date) {
		return formatDate(date, "yyyyMMdd");
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 *
	 * @return
	 */
	public static String getTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss.SSS格式
	 *
	 * @return
	 */
	public static String getMsTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.SSS");
	}

	/**
	 * 获取YYYYMMDDHHmmss格式
	 *
	 * @return
	 */
	public static String getAllTime() {
		return formatDate(new Date(), "yyyyMMddHHmmss");
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 *
	 * @return
	 */
	public static String getTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	public static String formatDate(Date date, String pattern) {
		String formatDate = null;
		if (StringUtils.isNotBlank(pattern)) {
			formatDate = DateFormatUtils.format(date, pattern);
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}

	/**
	 * @Title: compareDate
	 * @Description:(日期比较，如果s>=e 返回true 否则返回false)
	 * @param s
	 * @param e
	 * @return boolean
	 * @throws
	 * @author luguosui
	 */
	public static boolean compareDate(String s, String e) {
		if (parseDate(s) == null || parseDate(e) == null) {
			return false;
		}
		return parseDate(s).getTime() >= parseDate(e).getTime();
	}

	/**
	 * 格式化日期
	 *
	 * @return
	 */
	public static Date parseDate(String date) {
		return parse(date,"yyyy-MM-dd");
	}

	/**
	 * 格式化日期
	 *
	 * @return
	 */
	public static Date parseTime(String date) {
		return parse(date,"yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 格式化日期
	 *
	 * @return
	 */
	public static Date parse(String date, String pattern) {
		try {
			return DateUtils.parseDate(date,pattern);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 格式化日期
	 *
	 * @return
	 */
	public static String format(Date date, String pattern) {
		return DateFormatUtils.format(date, pattern);
	}

	/**
	 * 把日期转换为Timestamp
	 *
	 * @param date
	 * @return
	 */
	public static Timestamp format(Date date) {
		return new Timestamp(date.getTime());
	}

	/**
	 * 校验日期是否合法
	 *
	 * @return
	 */
	public static boolean isValidDate(String s) {
		return parse(s, "yyyy-MM-dd HH:mm:ss") != null;
	}

	/**
	 * 校验日期是否合法
	 *
	 * @return
	 */
	public static boolean isValidDate(String s, String pattern) {
        return parse(s, pattern) != null;
	}

	public static int getDiffYear(String startTime, String endTime) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			int years = (int) (((fmt.parse(endTime).getTime() - fmt.parse(
					startTime).getTime()) / (1000 * 60 * 60 * 24)) / 365);
			return years;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return 0;
		}
	}

	/**
	 * <li>功能描述：时间相减得到天数
	 *
	 * @param beginDateStr
	 * @param endDateStr
	 * @return long
	 * @author Administrator
	 */
	public static long getDaySub(String beginDateStr, String endDateStr) {
		long day = 0;
		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd");
		Date beginDate = null;
		Date endDate = null;

		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
		// System.out.println("相隔的天数="+day);

		return day;
	}

	/**
	 * 得到n天之后的日期
	 *
	 * @param days
	 * @return
	 */
	public static String getAfterDayDate(String days) {
		int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdfd.format(date);

		return dateStr;
	}

	public static Date addMonth(Date date ,int months) {
		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.setTime(date);
		canlendar.add(Calendar.MONTH, months);
		Date rtDate = canlendar.getTime();
		return rtDate;
	}

	public static Date addHour(Date date ,int hours) {
		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.setTime(date);
		canlendar.add(Calendar.HOUR, hours);
		Date rtDate = canlendar.getTime();
		return rtDate;
	}

	public static Date addMinite(Date date ,int minites) {
		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.setTime(date);
		canlendar.add(Calendar.MINUTE, minites);
		Date rtDate = canlendar.getTime();
		return rtDate;
	}

	/**
	 * 得到n天之后的日期
	 */
	public static Date dateAdd(int  days) {
		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, days); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();
		return date;
	}

	/**
	 * 得到n天之后的日期
	 */
	public static Date dateAdd(Date date ,int  days) {
		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.setTime(date);
		canlendar.add(Calendar.DATE, days); // 日期减 如果不够减会将月变动
		Date rtDate = canlendar.getTime();
		return rtDate;
	}

	public static Date getFirstDayOfMonth(Date date)
	{
		Calendar canlendar = Calendar.getInstance();
		canlendar.setTime(date);
		canlendar.set(Calendar.DAY_OF_MONTH, 1);
		Date rtDate = canlendar.getTime();
		return rtDate;
	}

	/**
	 * 得到n天之后是周几
	 *
	 * @param days
	 * @return
	 */
	public static String getAfterDayWeek(String days) {
		int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdf = new SimpleDateFormat("E");
		String dateStr = sdf.format(date);

		return dateStr;
	}

	/**
	 * 格式化Oracle Date
	 * @param value
	 * @return
	 */
//	public static String buildDateValue(Object value){
//		if(Func.isOracle()){
//			return "to_date('"+ value +"','yyyy-mm-dd HH24:MI:SS')";
//		}else{
//			return Func.toStr(value);
//		}
//	}
	/**
	 * 将持续时间(毫秒)转换成易读格式
	 * @param duration 持续时间(毫秒)
	 * @return X 小时 X 分 X 秒 X 毫秒
	 */
	public static String format(long duration) {
		StringBuilder sb = new StringBuilder();
		long hour = 0, minute = 0, seconds = 0;
		seconds = duration / 1000;
		duration = duration % 1000;
		
		if (seconds > 0) {
			minute = seconds / 60;
			seconds = seconds % 60;
		}
		
		if (minute > 0) {
			hour = minute / 60;
			minute = minute % 60;
		}
		if (hour > 0) {
			sb.append(hour).append(" 小时 ");
		}
		if (sb.length() > 0 || minute > 0) {
			sb.append(minute).append(" 分 ");
		}
		if (sb.length() > 0 || seconds > 0) {
			sb.append(seconds).append(" 秒 ");
		}
		if (sb.length() > 0 || duration > 0) {
			sb.append(duration).append(" 毫秒 ");
		}
		
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(getTodayLastSubNow());
	}
	
	public static long getTodayLastSubNow() {
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        return calendar.getTimeInMillis() - System.currentTimeMillis();
	}

	//两个日期的天数差
	public static long getDayDiff(Date date1, Date date2)
	{
		long timea =date1.getTime();
		long timeend = date2.getTime();
		long diff = (timeend - timea) / DAY_MILIS;
		long day=Math.abs(diff);
		return day;
	}

	//今天剩余描述
	public static long todayLeftSnd() {
		Date now = new Date();
		String dayBiginStr = DateUtil.format(now,"yyyy-MM-dd");
		Date todayDate = DateUtil.parseDate(dayBiginStr);
		Date nextDate= DateUtil.dateAdd(todayDate,1);
		long lestSnd = (nextDate.getTime()-now.getTime())/1000;
		return lestSnd;
	}
}
