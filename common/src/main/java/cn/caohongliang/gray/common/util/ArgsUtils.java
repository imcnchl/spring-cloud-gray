package cn.caohongliang.gray.common.util;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * main方法的参数转换的工具类
 *
 * @author caohongliang
 */
public class ArgsUtils {

	/**
	 * args数组转成map
	 *
	 * @param args 参数数组
	 * @return map对象
	 */
	public static Map<String, String> getMap(String[] args) {
		return Stream.of(args)
				.map(arg -> {
					int index = arg.indexOf("=");
					if (index == -1) {
						return null;
					}
					return new String[]{arg.substring(0, index), arg.substring(index)};
				})
				.filter(Objects::nonNull)
				.collect(Collectors.toMap(k -> k[0], v -> v[1]));
	}

	/**
	 * 获取参数数组中key对应的值
	 *
	 * @param args 参数数组
	 * @param key  key
	 * @return 值
	 */
	public static String getString(String[] args, String key) {
		return getMap(args).get(key);
	}

	/**
	 * 获取参数数组中key对应的值
	 *
	 * @param args         参数数组
	 * @param key          key
	 * @param defaultValue 默认值
	 * @return 值
	 */
	public static String getString(String[] args, String key, String defaultValue) {
		String value = getString(args, key);
		return value != null ? value : defaultValue;
	}

	/**
	 * 获取参数数组中key对应的值
	 *
	 * @param args 参数数组
	 * @param key  key
	 * @return 值
	 */
	public static Integer getInteger(String[] args, String key) {
		String value = getMap(args).get(key);
		return value != null && !"".equals(value) ? Integer.valueOf(value) : null;
	}

	/**
	 * 获取参数数组中key对应的值
	 *
	 * @param args         参数数组
	 * @param key          key
	 * @param defaultValue 默认值
	 * @return 值
	 */
	public static Integer getInteger(String[] args, String key, int defaultValue) {
		Integer value = getInteger(args, key);
		return value != null ? value : defaultValue;
	}

	/**
	 * 获取参数数组中key对应的值
	 *
	 * @param args 参数数组
	 * @param key  key
	 * @return 值
	 */
	public static Boolean getBoolean(String[] args, String key) {
		String value = getMap(args).get(key);
		return value != null && !"".equals(value) ? Boolean.valueOf(value) : null;
	}

	/**
	 * 获取参数数组中key对应的值
	 *
	 * @param args         参数数组
	 * @param key          key
	 * @param defaultValue 默认值
	 * @return 值
	 */
	public static Boolean getBoolean(String[] args, String key, boolean defaultValue) {
		Boolean value = getBoolean(args, key);
		return value != null ? value : defaultValue;
	}
}
