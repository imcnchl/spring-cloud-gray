package cn.caohongliang.gray.core.util.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.lang.reflect.Type;

/**
 * json工具类
 *
 * @author caohongliang
 */
public class GsonUtils {
	private static final Gson gson = new GsonBuilder()
			.registerTypeAdapterFactory(new GsonTypeAdapterFactory())
			.addSerializationExclusionStrategy(GsonExclusionStrategies.serializationExclusionStrategy)
			.addDeserializationExclusionStrategy(GsonExclusionStrategies.deserializationExclusionStrategy)
			.disableHtmlEscaping()
			.create();

	/**
	 * 将对象转为json
	 *
	 * @param obj 实例
	 * @return json格式的字符串
	 */
	public static String toJson(Object obj) {
		return gson.toJson(obj);
	}

	/**
	 * 将json字符串转成对应的实例
	 *
	 * @param json json字符串
	 * @param c    类型
	 * @param <T>  需要转的类型
	 * @return 对应的实例
	 */
	public static <T> T fromJson(String json, Class<T> c) {
		return gson.fromJson(json, c);
	}

	/**
	 * 将json字符串转成对应的实例
	 *
	 * @param json    json字符串
	 * @param typeOfT 类型
	 * @param <T>     需要转的类型
	 * @return 对应的实例
	 */
	public static <T> T fromJson(String json, Type typeOfT) {
		return gson.fromJson(json, typeOfT);
	}

	/**
	 * 将json字符串转成对应的实例
	 *
	 * @param json json字符串
	 * @param c    类型
	 * @param <T>  需要转的类型
	 * @return 对应的实例
	 */
	public static <T> T fromJson(JsonElement json, Class<T> c) {
		return gson.fromJson(json, c);
	}

	/**
	 * 将json字符串转成对应的实例
	 *
	 * @param json    json字符串
	 * @param typeOfT 类型
	 * @param <T>     需要转的类型
	 * @return 对应的实例
	 */
	public static <T> T fromJson(JsonElement json, Type typeOfT) {
		return gson.fromJson(json, typeOfT);
	}

	/**
	 * json格式的字符串转成jsonElement
	 *
	 * @param json
	 * @return
	 */
	public static JsonElement parseAsJson(String json) {
		return JsonParser.parseString(json);
	}
}
