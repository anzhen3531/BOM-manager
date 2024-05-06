package ext.ziang.cache;

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
}
