package ext.ziang.docTag;

import java.rmi.RemoteException;
import java.util.HashMap;

import ext.ziang.cache.CommonPartCache;
import ext.ziang.common.helper.cache.CommonSingleCache;
import ext.ziang.common.util.LoggerHelper;
import wt.method.RemoteAccess;

/**
 * 学习 JCA 标签实用程序
 *
 * @author anzhen
 * @date 2024/05/06
 */
public class LearnJcaTagUtil implements RemoteAccess {

    public static CommonPartCache commonPartCache = null;

    static {
        try {
            commonPartCache = new CommonPartCache();
            initAttr();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 初始化 attr
     */
    public static void initAttr() throws RemoteException {
        HashMap<Object, Object> jcaTagMap = new HashMap<>();
        jcaTagMap.put("className", "ext.ziang.docTag.LearnJcaTagUtil");
        jcaTagMap.put("cache", "ext.ziang.common.helper.cache.CommonSingleCache");
        jcaTagMap.put("test", "test");
        LoggerHelper.log("jcaTagMap", jcaTagMap);
        jcaTagMap.forEach((key, value) -> {
            LoggerHelper.log("key", key);
            LoggerHelper.log("value", value);
            commonPartCache.put(key, value);
        });
    }

    /**
     * 获取示例属性面板数据
     *
     * @return {@link HashMap}<{@link Object}, {@link Object}>
     */
    public static HashMap<Object, Object> getExamplePropertyPanelData() {
        HashMap<Object, Object> jcaTagMap = new HashMap<>();
        jcaTagMap.put("className", "ext.ziang.docTag.LearnJcaTagUtil");
        jcaTagMap.put("cache", "ext.ziang.common.helper.cache.CommonSingleCache");
        jcaTagMap.put("test", "test");
        for (Object key : getKeys()) {
            System.out.println("key = " + key);
        }
        System.out.println("commonPartCache.get(\"className\") = " + commonPartCache.get("className"));
        System.out.println("commonPartCache.get(\"cache) = " + commonPartCache.get("cache"));
        System.out.println("commonPartCache.get(\"test\") = " + commonPartCache.get("test"));
        return jcaTagMap;
    }

    /**
     * 获取密钥
     *
     * @return {@link Object[]}
     */
    public static Object[] getKeys() {
        return CommonSingleCache.getAllKeys();
    }

    public static LearnJcaTagUtil newLearnJcaTagUtil() throws RemoteException {
        initAttr();
        return new LearnJcaTagUtil();
    }
}
