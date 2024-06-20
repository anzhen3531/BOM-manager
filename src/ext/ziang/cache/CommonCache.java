package ext.ziang.cache;

import wt.cache.CacheManager;

import java.rmi.RemoteException;

public class CommonCache extends CacheManager {
    public CommonCache() throws RemoteException {
    }

    private static volatile CommonCache cache = null;

    static CommonCache getCommonPartCache() throws RemoteException {
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
