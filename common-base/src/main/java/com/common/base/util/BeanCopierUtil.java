package com.common.base.util;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.Converter;

import java.util.*;

/**
 * java 实体类属性值复制工具
 */
public class BeanCopierUtil {
	private static Map<String, BeanCopier> beanCopierMap = new HashMap<String, BeanCopier>();
    private static String generateKey(Class<?> source,Class<?> target){  
        return source.toString() + target.toString();  
    }  
	/**
	 * 将source中与target同名字段的值复制过去
	 * @param source 复制源
	 * @param target 复制目标
	 */
	public static void copy(Object source, Object target){
		Class<?> sc = source.getClass();
		Class<?> tc = target.getClass();
		if (sc.equals(Long.class) && tc.equals(Long.class)){
			
		}
        String beanKey = generateKey(source.getClass(),target.getClass());  
        BeanCopier copier = null;  
        if (!beanCopierMap.containsKey(beanKey)) {  
            copier = BeanCopier.create(source.getClass(), target.getClass(), true);  
            beanCopierMap.put(beanKey, copier);  
        }else {  
            copier = beanCopierMap.get(beanKey);  
        }  
        copier.copy(source, target, new ConverterImpl());  
	}
	
	private static class ConverterImpl implements Converter {

		@Override
		public Object convert(Object value, @SuppressWarnings("rawtypes") Class target, Object context) {
			if (value == null) {
				return null;
			}
			Class<?> source = value.getClass();//源属性类型
			
			/*由于无法获取集合类型的泛型参数类型, 为避免程序运行异常, 集合类型的属性复制手动处理*/
			if (Set.class.isAssignableFrom(target)) {
				return new HashSet<>();
			}
			if (List.class.isAssignableFrom(target)) {
				return new ArrayList<>();
			}
			if (source.equals(target)) {
				return value;
			}
			/* 数字类型转换 */
			if (Number.class.isAssignableFrom(source)) {
				Number number = (Number)value;
				if (Short.class.equals(target)) {
					return number.shortValue();
				}
				if (Integer.class.equals(target)) {
					return number.intValue();
				}
				if (Long.class.equals(target)) {
					return number.longValue();
				}
				if (Double.class.equals(target)) {
					return number.doubleValue();
				}
				if (Float.class.equals(target)) {
					return number.floatValue();
				}
			}
			//类型为一致, 不进行复制
			if (!source.equals(target)) {
				return null;
			}
			return value;
		}
		
	}
}
