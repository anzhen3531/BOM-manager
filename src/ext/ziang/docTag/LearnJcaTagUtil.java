package ext.ziang.docTag;

import ext.ziang.cache.CommonCacheHelper;
import ext.ziang.cache.CommonPartCache;
import ext.ziang.common.util.LoggerHelper;
import wt.method.RemoteAccess;

import java.rmi.RemoteException;
import java.util.HashMap;

/**
 * 学习 JCA 标签实用程序
 *
 * @author anzhen
 * @date 2024/05/06
 */
public class LearnJcaTagUtil implements RemoteAccess {

    static {
        initAttr();
    }

    /**
     * 初始化 attr
     */
    public static void initAttr() {
        HashMap<Object, Object> jcaTagMap = new HashMap<>();
        jcaTagMap.put("className", "ext.ziang.docTag.LearnJcaTagUtil");
        jcaTagMap.put("cache", "ext.ziang.common.helper.cache.CommonSingleCache");
        jcaTagMap.put("test", "test");
        LoggerHelper.log("jcaTagMap", jcaTagMap);
        jcaTagMap.forEach((key, value) -> {
            LoggerHelper.log("key", key);
            LoggerHelper.log("value", value);
            CommonCacheHelper.put(key, value);
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
        System.out.println("commonPartCache.get(\"className\") = " + CommonCacheHelper.get("className"));
        System.out.println("commonPartCache.get(\"cache) = " + CommonCacheHelper.get("cache"));
        System.out.println("commonPartCache.get(\"test\") = " + CommonCacheHelper.get("test"));
        return jcaTagMap;
    }

    public static LearnJcaTagUtil newLearnJcaTagUtil() throws RemoteException {
        initAttr();
        return new LearnJcaTagUtil();
    }
}
