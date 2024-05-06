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
	 * 初始化 attr
	 */
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

	/**
	 * 获取示例属性面板数据
	 *
	 * @return {@link HashMap}<{@link Object}, {@link Object}>
	 */
	public static HashMap<Object, Object> getExamplePropertyPanelData() {
		return CommonSingleCache.getAllValue();
	}

	/**
	 * 获取密钥
	 *
	 * @return {@link Object[]}
	 */
	public static Object[] getKeys() {
		return CommonSingleCache.getAllKeys();
	}
}
