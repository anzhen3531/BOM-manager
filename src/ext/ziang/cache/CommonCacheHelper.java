package ext.ziang.cache;

import java.rmi.RemoteException;

/**
 * 通用缓存帮助程序
 *
 * @author anzhen
 * @date 2024/06/20
 */
public class CommonCacheHelper {
    public static CommonCache cache;

    static {
        try {
            cache = CommonCache.getCommonCache();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将key和Value存放入缓存中
     *
     * @param key
     * @param value
     */
    public static void put(Object key, Object value) {
        cache.put(key, value);
    }

    /**
     * 通过key获取Value
     *
     * @param key key
     * @return {@link Object }
     */
    public static Object get(Object key) {
        return cache.get(key);
    }
}
