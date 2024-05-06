package ext.ziang.docTag;

import java.util.HashMap;

import ext.ziang.common.helper.cache.CommonSingleCache;
import ext.ziang.common.util.CommonLog;

/**
 * 学习 JCA 标签实用程序
 *
 * @author anzhen
 * @date 2024/05/06
 */
public class LearnJcaTagUtil {

	/**
	 * 获取文档标签映射
	 *
	 * @return {@link HashMap}<{@link String}, {@link String}>
	 */
	public static HashMap<String, String> getDocTagMap() {
		return null;
	}

	public static void initAttr() {
		HashMap<Object, Object> jcaTagMap = new HashMap<>();
		jcaTagMap.put("className", "ext.ziang.docTag.LearnJcaTagUtil");
		jcaTagMap.put("cache", "ext.ziang.common.helper.cache.CommonSingleCache");
		jcaTagMap.put("test", "test");
		CommonLog.log("jcaTagMap", jcaTagMap);
		jcaTagMap.forEach((key, value) -> {
			CommonLog.log("key", key);
			CommonLog.log("value", value);
			CommonSingleCache.put(key, value);
		});
	}

	public static HashMap<Object, Object> getExamplePropertyPanelData() {
		return CommonSingleCache.getAllValue();
	}

	public static Object[] getKeys() {
		return CommonSingleCache.getAllKeys();
	}
}
