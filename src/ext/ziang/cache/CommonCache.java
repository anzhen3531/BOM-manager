package ext.ziang.cache;

import wt.cache.CacheManager;

import java.rmi.RemoteException;

/**
 * 公共缓存
 *
 * @author anzhen
 * @date 2024/06/20
 */
public class CommonCache extends CacheManager {
    /**
     * 公用缓存
     *
     * @throws RemoteException 远程异常
     */
    CommonCache() throws RemoteException {
         super.createCache("CommonCache", 100, false);
    }

    private static volatile CommonCache cache = null;

    /**
     * 获取公共部件缓存
     *
     * @return {@link CommonCache }
     * @throws RemoteException 远程异常
     */
    public static CommonCache getCommonCache() throws RemoteException {
        if (cache == null) {
            synchronized (CommonCache.class) {
                if (cache == null) {
                    cache = new CommonCache();
                }
            }
        }
        return cache;
    }
}
