/**
 * Copyright (c) 2015-2016, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.common.base.util;

import org.apache.commons.codec.binary.Hex;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 高频方法集合类
 */
public class ToolUtil {
    //手机正则
    private static final String regExp = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,1-9]))\\d{8}$";
    //密码正则,必须字母开头，6-12位
//    private static final String regex = "^(?![^a-zA-Z]+$)(?!\\D+$).{6,12}$";
    private static final String regex = "^[\\w]{6,12}";

    private static final String phoneExp = "^1\\d{10}$";

    /**
     * 获取随机位数的字符串
     *
     * @author fengshuonan
     * @Date 2017/8/24 14:09
     */
    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
    public static int compareVersion(String dbVersion, String appVersion) {
        //判断版本
        if (dbVersion.equals(appVersion)) {
            return 0;//与服务器版本相同
        }
        String[] version1Array = dbVersion.split("[.]");
        String[] version2Array = appVersion.split("[.]");
        int index = 0;
        int minLen = Math.min(version1Array.length, version2Array.length);
        long diff = 0;

        while (index < minLen
                && (diff = Long.parseLong(version1Array[index])
                - Long.parseLong(version2Array[index])) == 0) {
            index++;
        }
        if (diff == 0) {
            for (int i = index; i < version1Array.length; i++) {
                if (Long.parseLong(version1Array[i]) > 0) {
                    return 1; //数据库版本更大
                }
            }

            for (int i = index; i < version2Array.length; i++) {
                if (Long.parseLong(version2Array[i]) > 0) {
                    return -1;//传入版本更大
                }
            }
            return 0;
        } else {
            return diff > 0 ? 1 : -1;
        }
    }
    /**
     * @description: 生成随机数字
     * @author: LX
     */
    public static String getRandomNum(int length) { // length表示生成字符串的长度
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * @description: 生成随机数字 除了0
     * @author: LX
     */
    public static String getRandomNumWithoutZero(int length) { // length表示生成字符串的长度
        String base = "123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * @description: 生成随机数字
     * @author: LX
     */
    public static Integer get32RandomNum() {
        return new Random().nextInt(Integer.MAX_VALUE);
    }

    /**
     * @description: 明文密码加密, 返回密码与颜值
     * @author: LX
     */

    public static Map<String, String> pwdEncrypt(String pwd) {
        Map<String, String> result = new HashMap<>();
        String salt;
        String password;
        try {
            byte[] salts = MD5EncodeUtil.getSalt();
            //密码盐值加密
            salt = Hex.encodeHexString(salts);
            //获取加密后的密码
            password = MD5EncodeUtil.encodePassword(pwd, salt);
            result.put("salt", salt);
            result.put("pwd", password);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.put("salt", "");
            result.put("pwd", "");
            return result;
        }
    }

    /**
     * @Description： map 转 & 参数拼接
     */
    public static String mapCover(SortedMap<Object, Object> parameters) {
        StringBuilder sb = new StringBuilder();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v)) {
                sb.append(k + "=" + v + "&");
            }
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    /**
     * @description: 明文密码加密, 返回密码
     * @author: LX
     */

    public static String pwdEncryptOnlyPwd(String pwd) {
        String salt;
        String password = "";
        try {
            byte[] salts = MD5EncodeUtil.getSalt();
            //密码盐值加密
            salt = Hex.encodeHexString(salts);
            //获取加密后的密码
            password = MD5EncodeUtil.encodePassword(pwd, salt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return password;
    }


    /**
     * @description: 密码加密, 通过明文密码与颜值
     * @author: LX
     */

    public static String pwdEncrypt(String pwd, String salt) {
        String password = "";
        try {
            //获取加密后的密码
            password = MD5EncodeUtil.encodePassword(pwd, salt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return password;
    }

    /**
     * 手机正则匹配
     */
    public static boolean isPhone(String phone) {
        Pattern compilePhone = Pattern.compile(phoneExp);
        Matcher matcherPhone = compilePhone.matcher(phone);
        return matcherPhone.find();
    }

    /**
     * 手机正则匹配
     */
    public static boolean phoneReg(String phone) {
        Pattern compilePhone = Pattern.compile(regExp);
        Matcher matcherPhone = compilePhone.matcher(phone);
        return matcherPhone.find();
    }

    /**
     * 手机正则匹配
     */
    public static boolean phoneRegTwo(String phone) {
        if (StrKit.isEmpty(phone)) return false;
        if (phone.length() < 11) return false;
        if (!phone.startsWith("1")) return false;
        return true;
    }


    /**
     * 密码正则匹配
     */
    public static boolean passwordReg(String ps) {
        Pattern compilePsw = Pattern.compile(regex);
        Matcher matcherPsw = compilePsw.matcher(ps);

        return matcherPsw.find();
    }

    /**
     * 判断一个对象是否是时间类型
     *
     * @author stylefeng
     * @Date 2017/4/18 12:55
     */
    public static String dateType(Object o) {
        if (o instanceof Date) {
            return DateUtil.getDay((Date) o);
        } else {
            return o.toString();
        }
    }

    /**
     * @author: LX
     * @description: html转义
     */
    public static String htmlCover(String html) {
        String end = "";
        if (html.length() == 0) return "";
        end = html.replace("&amp;amp;", "&");
        end = html.replace("&amp;ldquo;", "“");
        end = html.replace("&amp;rdquo;", "”");
        end = html.replace("&ldquo;", "“");
        end = html.replace("&rdquo;", "”");
        end = end.replace("&amp;", "&");
        end = end.replace("& lt;", "<");
        end = end.replace("&lt;", "<");
        end = end.replace("& gt;", ">");
        end = end.replace("&gt;", ">");
        end = end.replace("& quot;", "\"");
        end = end.replace("&quot;", "\"");
        end = end.replace("& #40;", "(");
        end = end.replace("& #41;", ")");
        return end;
    }

    /**
     * UUID去除-
     *
     * @return String
     */
    public static String replaceUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取异常的具体信息
     *
     * @author fengshuonan
     * @Date 2017/3/30 9:21
     * @version 2.0
     */
    public static String getExceptionMsg(Exception e) {
        StringWriter sw = new StringWriter();
        try {
            e.printStackTrace(new PrintWriter(sw));
        } finally {
            try {
                sw.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return sw.getBuffer().toString().replaceAll("\\$", "T");
    }

    /**
     * 比较两个对象是否相等。<br>
     * 相同的条件有两个，满足其一即可：<br>
     * 1. obj1 == null && obj2 == null; 2. obj1.equals(obj2)
     *
     * @param obj1 对象1
     * @param obj2 对象2
     * @return 是否相等
     */
    public static boolean equals(Object obj1, Object obj2) {
        return (obj1 != null) ? (obj1.equals(obj2)) : (obj2 == null);
    }

    /**
     * 计算对象长度，如果是字符串调用其length函数，集合类调用其size函数，数组调用其length属性，其他可遍历对象遍历计算长度
     *
     * @param obj 被计算长度的对象
     * @return 长度
     */
    public static int length(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length();
        }
        if (obj instanceof Collection) {
            return ((Collection<?>) obj).size();
        }
        if (obj instanceof Map) {
            return ((Map<?, ?>) obj).size();
        }

        int count;
        if (obj instanceof Iterator) {
            Iterator<?> iter = (Iterator<?>) obj;
            count = 0;
            while (iter.hasNext()) {
                count++;
                iter.next();
            }
            return count;
        }
        if (obj instanceof Enumeration) {
            Enumeration<?> enumeration = (Enumeration<?>) obj;
            count = 0;
            while (enumeration.hasMoreElements()) {
                count++;
                enumeration.nextElement();
            }
            return count;
        }
        if (obj.getClass().isArray() == true) {
            return Array.getLength(obj);
        }
        return -1;
    }

    /**
     * 对象中是否包含元素
     *
     * @param obj     对象
     * @param element 元素
     * @return 是否包含
     */
    public static boolean contains(Object obj, Object element) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof String) {
            if (element == null) {
                return false;
            }
            return ((String) obj).contains(element.toString());
        }
        if (obj instanceof Collection) {
            return ((Collection<?>) obj).contains(element);
        }
        if (obj instanceof Map) {
            return ((Map<?, ?>) obj).values().contains(element);
        }

        if (obj instanceof Iterator) {
            Iterator<?> iter = (Iterator<?>) obj;
            while (iter.hasNext()) {
                Object o = iter.next();
                if (equals(o, element)) {
                    return true;
                }
            }
            return false;
        }
        if (obj instanceof Enumeration) {
            Enumeration<?> enumeration = (Enumeration<?>) obj;
            while (enumeration.hasMoreElements()) {
                Object o = enumeration.nextElement();
                if (equals(o, element)) {
                    return true;
                }
            }
            return false;
        }
        if (obj.getClass().isArray() == true) {
            int len = Array.getLength(obj);
            for (int i = 0; i < len; i++) {
                Object o = Array.get(obj, i);
                if (equals(o, element)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 对象是否不为空(新增)
     *
     * @param o String,List,Map,Object[],int[],long[]
     * @return
     */
    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }

    /**
     * 对象是否为空
     *
     * @param o String,List,Map,Object[],int[],long[]
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object o) {
        if (o == null) {
            return true;
        }
        if (o instanceof String) {
            if (o.toString().trim().equals("")) {
                return true;
            }
        } else if (o instanceof List) {
            if (((List) o).size() == 0) {
                return true;
            }
        } else if (o instanceof Map) {
            if (((Map) o).size() == 0) {
                return true;
            }
        } else if (o instanceof Set) {
            if (((Set) o).size() == 0) {
                return true;
            }
        } else if (o instanceof Object[]) {
            if (((Object[]) o).length == 0) {
                return true;
            }
        } else if (o instanceof int[]) {
            if (((int[]) o).length == 0) {
                return true;
            }
        } else if (o instanceof long[]) {
            if (((long[]) o).length == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 对象组中是否存在 Empty Object
     *
     * @param os 对象组
     * @return
     */
    public static boolean isOneEmpty(Object... os) {
        for (Object o : os) {
            if (isEmpty(o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 对象组中是否全是 Empty Object
     *
     * @param os
     * @return
     */
    public static boolean isAllEmpty(Object... os) {
        for (Object o : os) {
            if (!isEmpty(o)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否为数字
     *
     * @param obj
     * @return
     */
    public static boolean isNum(Object obj) {
        try {
            Integer.parseInt(obj.toString());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 如果为空, 则调用默认值
     *
     * @param str
     * @return
     */
    public static Object getValue(Object str, Object defaultValue) {
        if (isEmpty(str)) {
            return defaultValue;
        }
        return str;
    }

    /**
     * 格式化文本
     *
     * @param template 文本模板，被替换的部分用 {} 表示
     * @param values   参数值
     * @return 格式化后的文本
     */
    public static String format(String template, Object... values) {
        return StrKit.format(template, values);
    }

    /**
     * 格式化文本
     *
     * @param template 文本模板，被替换的部分用 {key} 表示
     * @param map      参数值对
     * @return 格式化后的文本
     */
    public static String format(String template, Map<?, ?> map) {
        return StrKit.format(template, map);
    }

    /**
     * 强转->string,并去掉多余空格
     *
     * @param str
     * @return
     */
    public static String toStr(Object str) {
        return toStr(str, "");
    }

    /**
     * 强转->string,并去掉多余空格
     *
     * @param str
     * @param defaultValue
     * @return
     */
    public static String toStr(Object str, String defaultValue) {
        if (null == str) {
            return defaultValue;
        }
        return str.toString().trim();
    }

    /**
     * 强转->int
     *
     * @param obj
     * @return
     */
//	public static int toInt(Object value) {
//		return toInt(value, -1);
//	}

    /**
     * 强转->int
     *
     * @param obj
     * @param defaultValue
     * @return
     */
//	public static int toInt(Object value, int defaultValue) {
//		return Convert.toInt(value, defaultValue);
//	}

    /**
     * 强转->long
     *
     * @param obj
     * @return
     */
//	public static long toLong(Object value) {
//		return toLong(value, -1);
//	}

    /**
     * 强转->long
     *
     * @param obj
     * @param defaultValue
     * @return
     */
//	public static long toLong(Object value, long defaultValue) {
//		return Convert.toLong(value, defaultValue);
//	}
//
//	public static String encodeUrl(String url) {
//		return URLKit.encode(url, CharsetKit.UTF_8);
//	}
//
//	public static String decodeUrl(String url) {
//		return URLKit.decode(url, CharsetKit.UTF_8);
//	}

    /**
     * map的key转为小写
     *
     * @param map
     */
    public static Map<String, Object> caseInsensitiveMap(Map<String, Object> map) {
        Map<String, Object> tempMap = new HashMap<>();
        for (String key : map.keySet()) {
            tempMap.put(key.toLowerCase(), map.get(key));
        }
        return tempMap;
    }

    /**
     * 获取map中第一个数据值
     *
     * @param <K> Key的类型
     * @param <V> Value的类型
     * @param map 数据源
     * @return 返回的值
     */
    public static <K, V> V getFirstOrNull(Map<K, V> map) {
        V obj = null;
        for (Entry<K, V> entry : map.entrySet()) {
            obj = entry.getValue();
            if (obj != null) {
                break;
            }
        }
        return obj;
    }

    /**
     * 创建StringBuilder对象
     *
     * @return StringBuilder对象
     */
    public static StringBuilder builder(String... strs) {
        final StringBuilder sb = new StringBuilder();
        for (String str : strs) {
            sb.append(str);
        }
        return sb;
    }

    /**
     * 创建StringBuilder对象
     *
     * @return StringBuilder对象
     */
    public static void builder(StringBuilder sb, String... strs) {
        for (String str : strs) {
            sb.append(str);
        }
    }

    /**
     * 去掉指定后缀
     *
     * @param str    字符串
     * @param suffix 后缀
     * @return 切掉后的字符串，若后缀不是 suffix， 返回原字符串
     */
    public static String removeSuffix(String str, String suffix) {
        if (isEmpty(str) || isEmpty(suffix)) {
            return str;
        }

        if (str.endsWith(suffix)) {
            return str.substring(0, str.length() - suffix.length());
        }
        return str;
    }

    /**
     * 当前时间
     *
     * @author stylefeng
     * @Date 2017/5/7 21:56
     */
    public static String currentTime() {
        return DateUtil.getTime();
    }

    /**
     * 首字母大写
     *
     * @author stylefeng
     * @Date 2017/5/7 22:01
     */
    public static String firstLetterToUpper(String val) {
        return StrKit.firstCharToUpperCase(val);
    }

    /**
     * 首字母小写
     *
     * @author stylefeng
     * @Date 2017/5/7 22:02
     */
    public static String firstLetterToLower(String val) {
        return StrKit.firstCharToLowerCase(val);
    }

    /**
     * 判断是否是windows操作系统
     *
     * @author stylefeng
     * @Date 2017/5/24 22:34
     */
    public static Boolean isWinOs() {
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取临时目录
     *
     * @author stylefeng
     * @Date 2017/5/24 22:35
     */
    public static String getTempPath() {
        return System.getProperty("java.io.tmpdir");
    }

    /**
     * 把一个数转化为int
     *
     * @author fengshuonan
     * @Date 2017/11/15 下午11:10
     */
    public static Integer toInt(Object val) {
        if (val instanceof Double) {
            BigDecimal bigDecimal = new BigDecimal((Double) val);
            return bigDecimal.intValue();
        } else {
            return Integer.valueOf(val.toString());
        }

    }

    /**
     * 获取项目路径
     */
    public static String getWebRootPath(String filePath) {
        try {
            String path = ToolUtil.class.getClassLoader().getResource("").toURI().getPath();
            path = path.replace("/WEB-INF/classes/", "");
            path = path.replace("/target/classes/", "");
            path = path.replace("file:/", "");
            if (ToolUtil.isEmpty(filePath)) {
                return path;
            } else {
                return path + "/" + filePath;
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param str 待转换字符串
     * @return 转换后字符串
     * @throws UnsupportedEncodingException exception
     * @Description 将字符串中的emoji表情转换成可以在utf-8字符集数据库中保存的格式（表情占4个字节，需要utf8mb4字符集）
     */
    public static String emojiConvert(String str) throws UnsupportedEncodingException {
        String patternString = "([\\x{10000}-\\x{10ffff}\ud800-\udfff])";

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            try {
                matcher.appendReplacement(
                        sb,
                        "[["
                                + URLEncoder.encode(matcher.group(1),
                                "UTF-8") + "]]");
            } catch (UnsupportedEncodingException e) {
                System.err.println("emoji表情转换失败");
                throw e;
            }
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * @param str 转换后的字符串
     * @return 转换前的字符串
     * @throws exception
     * @Description 还原utf8数据库中保存的含转换后emoji表情的字符串
     */
    public static String emojiRecovery(String str) throws UnsupportedEncodingException {
        String patternString = "\\[\\[(.*?)\\]\\]";

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(str);

        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            try {
                matcher.appendReplacement(sb,
                        URLDecoder.decode(matcher.group(1), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                System.err.println("emoji表情转换失败");
                throw e;
            }
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 对象转map 包括父类的所有不为空的字段
     *
     * @param object
     * @return
     */
    public static Map<String, String> objectToMap(Object object) {
        try {
            if (object == null) {
                return null;
            }
            Map<String, String> map = new HashMap<>();

            List<Field> fields = new ArrayList<>();
            Class clz = object.getClass();
            while (clz != null) {
                fields.addAll(Arrays.asList(clz.getDeclaredFields()));
                clz = clz.getSuperclass();
            }
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.get(object) == null) {
                    continue;
                }
                map.put(field.getName(), field.get(object).toString());
            }
            return map;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @description: 获取本机内网IP
     * @author: LX
     */
    public static String getLocalIP() {
        try {
            InetAddress address = InetAddress.getLocalHost();
            String hostAddress = address.getHostAddress();
            return hostAddress.trim();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static LocalDate dateToLocalDate(Date dateTime) {
        Instant instant = dateTime.toInstant();
        ZoneId zoneId = ZoneId.of("Asia/Shanghai");
        return instant.atZone(zoneId).toLocalDate();
    }

    /**
     * 针对php的md5
     * @param str
     * @return
     */
    public static String getMD5(String str) {
        try {
            /** 创建MD5加密对象 */
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            /** 进行加密 */
            md5.update(str.getBytes("UTF-8"));
            /** 获取加密后的字节数组 */
            byte[] md5Bytes = md5.digest();
            String res = "";
            for (int i = 0; i < md5Bytes.length; i++) {
                int temp = md5Bytes[i] & 0xFF;
                if (temp <= 0XF) { // 转化成十六进制不够两位，前面加零
                    res += "0";
                }
                res += Integer.toHexString(temp);
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}