package ext.ziang.cache;

import wt.cache.CacheManager;

import java.rmi.RemoteException;

public class CommonPartCache extends CacheManager {
    public CommonPartCache() throws RemoteException {
    }

    private static volatile CommonPartCache cache = null;


    static CommonPartCache getCommonPartCache() throws RemoteException {
        if (cache == null) {
            synchronized (CommonPartCache.class) {
                if (cache == null) {
                    cache = new CommonPartCache();
                }
            }
        }
        return cache;
    }
}
