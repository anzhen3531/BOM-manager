package ext.ziang.common.helper.cache;

import java.util.HashMap;

import ext.ziang.common.util.CommonLog;
import wt.util.Cache;
import wt.util.WTProperties;

/**
 * 通用单缓存
 *
 * @author anzhen
 * @date 2024/05/06
 */
public class CommonSingleCache {

	/**
	 * 缓存
	 */
	private static Cache cache;

	/**
	 * 实例缓存
	 */
	static {
		int size = 20;
		try {
			size = WTProperties.getLocalProperties().getProperty("PropertyUtils.cacheSize", 20);
		} catch (Exception e) {
			CommonLog.error("Unable to get local properties", e);
		}
		cache = new Cache(size);
	}

	/**
	 * 放
	 *
	 * @param key
	 *            钥匙
	 * @param value
	 *            价值
	 */
	public static void put(Object key, Object value) {
		cache.put(key, value);
	}

	/**
	 * 获取
	 *
	 * @param key
	 *            钥匙
	 * @return {@link Object}
	 */
	public static Object get(Object key) {
		return cache.get(key);
	}

	public static HashMap<Object, Object> getAllValue() {
		HashMap<Object, Object> map = new HashMap<>();
		for (Object key : cache.getKeys()) {
			map.put(key, cache.get(key));
		}
		return map;
	}

	public static Object[] getAllKeys() {
		return cache.getKeys();
	}
}
