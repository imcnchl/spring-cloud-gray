package cn.caohongliang.gray.common.util;

//public class RedisClient {
//	private static StringRedisTemplate redisTemplate;
//	/**
//	 * 默认过期时间: 3天
//	 */
//	private static final long DEFAULT_TIMEOUT = 3 * 24 * 60 * 60;
//
//	public static void setValue(String key, Object value) {
//		setValue(key, value, DEFAULT_TIMEOUT);
//	}
//
//	public static void setValue(String key, Object value, long timeout) {
//		String json = null;
//		try {
//			if (value == null) {
//				Logger.info("数据为空, 不设置到redis中: key={}, timeout={}", key, timeout);
//				return;
//			}
//			json = GsonUtils.toJson(value);
//			valueOps().set(key, json, timeout, TimeUnit.SECONDS);
//		} catch (Throwable e) {
//			Logger.error("redis异常: key={}, value={}, timeout={}", e, key, json, timeout);
//		}
//	}
//
//	public static boolean setIfAbsent(String key, Object value) {
//		return setIfAbsent(key, value, DEFAULT_TIMEOUT);
//	}
//
//	public static boolean setIfAbsent(String key, Object value, long timeout) {
//		String json = null;
//		try {
//			if (value == null) {
//				Logger.info("数据为空, 不设置到redis中: key={}, timeout={}", key, timeout);
//				return false;
//			}
//			json = GsonUtils.toJson(value);
//			Boolean absent = valueOps().setIfAbsent(key, json, timeout, TimeUnit.SECONDS);
//			if (absent == null) {
//				Logger.info("setIfAbsent返回空, 默认false");
//				return false;
//			}
//			return absent;
//		} catch (Throwable e) {
//			Logger.error("redis异常: key={}, value={}, timeout={}", e, key, json, timeout);
//			return false;
//		}
//	}
//
//	public static boolean setIfPresent(String key, Object value) {
//		return setIfPresent(key, value, DEFAULT_TIMEOUT);
//	}
//
//	public static boolean setIfPresent(String key, Object value, long timeout) {
//		String json = null;
//		try {
//			if (value == null) {
//				Logger.info("数据为空, 不设置到redis中: key={}, timeout={}", key, timeout);
//				return false;
//			}
//			json = GsonUtils.toJson(value);
//			Boolean absent = valueOps().setIfPresent(key, json, timeout, TimeUnit.SECONDS);
//			if (absent == null) {
//				Logger.info("setIfPresent返回空, 默认false");
//				return false;
//			}
//			return absent;
//		} catch (Throwable e) {
//			Logger.error("redis异常: key={}, value={}, timeout={}", e, key, json, timeout);
//			return false;
//		}
//	}
//
//	public static StringRedisTemplate redisTemplate() {
//		if (redisTemplate == null) {
//			synchronized (RedisClient.class) {
//				if (redisTemplate == null) {
//					redisTemplate = SpringUtils.getBean(StringRedisTemplate.class);
//				}
//			}
//		}
//
//		return redisTemplate;
//	}
//
//	public static ValueOperations<String, String> valueOps() {
//		return redisTemplate().opsForValue();
//	}
//
//	public static ListOperations<String, String> listOps() {
//		return redisTemplate().opsForList();
//	}
//
//	public static SetOperations<String, String> setOps() {
//		return redisTemplate().opsForSet();
//	}
//
//	public static ZSetOperations<String, String> zSetOps() {
//		return redisTemplate().opsForZSet();
//	}
//}
