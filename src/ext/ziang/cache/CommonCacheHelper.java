package ext.ziang.cache;

import java.rmi.RemoteException;

public class CommonCacheHelper {
    public static CommonCache cache = null;

    static {
        try {
            cache = new CommonCache();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public static void put(Object key, Object value) {
        cache.put(key, value);
    }

    public static Object get(Object key) {
        return cache.get(key);
    }
}
