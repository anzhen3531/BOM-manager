package ext.ziang.common.util;

import ext.ziang.resource.NewTmlResource;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.iba.definition.litedefinition.*;
import wt.iba.definition.service.StandardIBADefinitionService;
import wt.iba.value.AttributeContainer;
import wt.iba.value.DefaultAttributeContainer;
import wt.iba.value.IBAHolder;
import wt.iba.value.IBAValueUtility;
import wt.iba.value.litevalue.*;
import wt.iba.value.service.IBAValueDBService;
import wt.iba.value.service.IBAValueHelper;
import wt.units.service.MeasurementSystemDefaultView;
import wt.util.*;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * IBA操作工具
 */
public class IbaUtil {
    private static final Logger log = LoggerFactory.getLogger(IbaUtil.class);
    private static final String NEWRESOURCE = NewTmlResource.class.getName();

    /**
     * 获取所有的IBA属性
     *
     * @param ibaNameList IBA Name
     * @param ibaHolder IBA属性持有者
     * @return
     */
    public static Map<String, List<Object>> findIBAValueByNameList(List<String> ibaNameList, IBAHolder ibaHolder) {
        Map<String, List<Object>> map = new ConcurrentHashMap<>();
        try {
            // 刷新最新的属性和约束条件
            ibaHolder = IBAValueHelper.service.refreshAttributeContainerWithoutConstraints(ibaHolder);
            DefaultAttributeContainer attributeContainer = (DefaultAttributeContainer)ibaHolder.getAttributeContainer();
            // 获取所有的属性
            AttributeDefDefaultView[] attributeDefinitions = attributeContainer.getAttributeDefinitions();
            if (ArrayUtils.isEmpty(attributeDefinitions)) {
                return map;
            }
            // 遍历所有的属性
            for (AttributeDefDefaultView attributeDefinition : attributeDefinitions) {
                // 通过属性获取值
                if (CollectionUtils.isNotEmpty(ibaNameList) && ibaNameList.contains(attributeDefinition.getName())) {
                    continue;
                }
                AbstractValueView[] attributeValues = attributeContainer.getAttributeValues(attributeDefinition);
                if (ArrayUtils.isEmpty(attributeValues)) {
                    map.put(attributeDefinition.getName(), null);
                    continue;
                }
                List<Object> valueList = new ArrayList<>(attributeValues.length);
                for (AbstractValueView attributeValue : attributeValues) {
                    valueList.add(IBAValueUtility.getLocalizedIBAValueDisplayString(attributeValue, Locale.CHINA));
                }
                map.put(attributeDefinition.getName(), valueList);
            }
        } catch (Exception e) {
            log.error("findIBAValueByNameList Exception Message:", e);
        }
        return map;
    }

    /**
     * 获取所有的IBA属性
     *
     * @param ibaHolder
     * @return
     */
    public static Map findAllIBAValue(IBAHolder ibaHolder, boolean isMergeMultipleValued) {
        Map<String, Object> result = new HashMap<>();
        Map<String, List<Object>> ibaValueByNameList = findIBAValueByNameList(null, ibaHolder);
        for (Map.Entry<String, List<Object>> entry : ibaValueByNameList.entrySet()) {
            List<Object> value = entry.getValue();
            if (isMergeMultipleValued) {
                String toString = listToString(value, ", ");
                result.put(entry.getKey(), toString);
            }
        }
        if (isMergeMultipleValued) {
            return result;
        } else {
            return ibaValueByNameList;
        }
    }

    /**
     * 获取所有的IBA属性
     *
     * @param ibaHolder
     * @return
     */
    public static Map<String, Object> findAllIBAValue(IBAHolder ibaHolder) {
        return findAllIBAValue(ibaHolder, true);
    }

    /**
     * 删除IBA属性
     * 
     * @param ibaHolder iba属性持有者
     * @param ibaName iba属性名称
     * @return 最新的IBA持有者
     * @throws WTException
     * @throws RemoteException
     */
    private static IBAHolder deleteIBAValue(IBAHolder ibaHolder, String ibaName) throws WTException, RemoteException {
        DefaultAttributeContainer attributeContainer = (DefaultAttributeContainer)ibaHolder.getAttributeContainer();
        if (attributeContainer == null) {
            return ibaHolder;
        } else {
            AttributeDefDefaultView defDefaultView = getAttributeDefDefaultView(ibaName);
            if (defDefaultView == null) {
                throw new WTException("No attribute container found for " + ibaName);
            } else {
                AbstractValueView[] valueViews = attributeContainer.getAttributeValues(defDefaultView);
                if (valueViews.length == 0) {
                    return ibaHolder;
                } else {
                    attributeContainer.deleteAttributeValue(valueViews[0]);
                    ibaHolder.setAttributeContainer(attributeContainer);
                    return ibaHolder;
                }
            }
        }
    }

    /**
     * 获取IBA属性视图
     * 
     * @param ibaName
     * @return
     * @throws RemoteException
     * @throws WTException
     */
    public static AttributeDefDefaultView getAttributeDefDefaultView(String ibaName)
        throws RemoteException, WTException {
        StandardIBADefinitionService var1 = new StandardIBADefinitionService();
        AttributeDefDefaultView var2 = var1.getAttributeDefDefaultViewByPath(ibaName);
        return var2;
    }

    /**
     * 
     * @param ibaHolder
     * @param isRefresh
     * @return
     * @throws WTException
     * @throws RemoteException
     */
    private static DefaultAttributeContainer getRefreshableAttributeContainer(IBAHolder ibaHolder, boolean isRefresh)
        throws WTException, RemoteException {
        DefaultAttributeContainer var2 = (DefaultAttributeContainer)ibaHolder.getAttributeContainer();
        if (var2 == null || PersistenceHelper.isPersistent(ibaHolder) && isRefresh) {
            ibaHolder = IBAValueHelper.service.refreshAttributeContainer(ibaHolder, (Object)null, (Locale)null,
                (MeasurementSystemDefaultView)null);
            var2 = (DefaultAttributeContainer)ibaHolder.getAttributeContainer();
        }

        return var2;
    }

    /**
     * 获取IBA属性值
     * 
     * @param ibaHolder 持有者对象
     * @param ibaName iba名称
     * @return
     * @throws WTException
     */
    public static String getStringIBAValue(IBAHolder ibaHolder, String ibaName) throws WTException {
        return getStringIBAValue(ibaHolder, ibaName, false);
    }

    /**
     *
     * @param ibaHolder 持有者对象
     * @param ibaName iba名称
     * @param var2
     * @return
     * @throws WTException
     */
    public static String getStringIBAValue(IBAHolder ibaHolder, String ibaName, boolean var2) throws WTException {
        try {
            DefaultAttributeContainer var3 = getRefreshableAttributeContainer(ibaHolder, var2);
            if (var3 == null) {
                return null;
            } else {
                AttributeDefDefaultView var4 = getAttributeDefDefaultView(ibaName);
                ArrayList var5 = new ArrayList();
                AbstractValueView[] var6 = var3.getAttributeValues(var4);
                int var7 = var6.length;

                for (int var8 = 0; var8 < var7; ++var8) {
                    AbstractValueView var9 = var6[var8];
                    if (var9.getState() != 2) {
                        var5.add(var9);
                    }
                }

                StringBuffer var11 = new StringBuffer();
                if (var5.size() > 1) {
                    Iterator var12 = var5.iterator();

                    while (var12.hasNext()) {
                        AbstractValueView var13 = (AbstractValueView)var12.next();
                        if (var13 instanceof StringValueDefaultView) {
                            var11.append(((StringValueDefaultView)var13).getValueAsString()).append('|');
                        } else if (var13 instanceof BooleanValueDefaultView) {
                            var11.append(((BooleanValueDefaultView)var13).getValueAsString()).append('|');
                        }
                    }

                    return var11.toString();
                } else if (var5.size() == 1) {
                    if (var5.get(0) instanceof StringValueDefaultView) {
                        return ((StringValueDefaultView)var5.get(0)).getValueAsString();
                    } else if (var5.get(0) instanceof BooleanValueDefaultView) {
                        return ((BooleanValueDefaultView)var5.get(0)).getValueAsString();
                    } else {
                        return ((AbstractValueView)var5.get(0)).toString();
                    }
                } else {
                    return "";
                }
            }
        } catch (RemoteException var10) {
            var10.printStackTrace();
            return null;
        }
    }

    private static IBAHolder setBooleanIBAValue(IBAHolder var0, String var1, String var2)
        throws WTException, WTPropertyVetoException, RemoteException {
        boolean var3 = false;
        var3 = Boolean.valueOf(var2);

        try {
            DefaultAttributeContainer var4 = getRefreshableAttributeContainer(var0, false);
            AttributeDefDefaultView var5 = getAttributeDefDefaultView(var1);
            if (!(var5 instanceof BooleanDefView)) {
                String var10 =
                    WTMessage.getLocalizedMessage(NEWRESOURCE, "EXCEPTION_IBA_NOT_BOOL", new Object[] {var1});
                throw new WTException(var10);
            }

            BooleanDefView var6 = (BooleanDefView)var5;
            AbstractValueView[] var7 = var4.getAttributeValues(var6);
            BooleanValueDefaultView var8;
            if (var7.length == 0) {
                var8 = new BooleanValueDefaultView(var6, var3);
                var4.addAttributeValue(var8);
            } else {
                var8 = (BooleanValueDefaultView)var7[0];
                var8.setValue(var3);
                var4.updateAttributeValue(var8);
            }

            var0.setAttributeContainer(var4);
        } catch (WTException var9) {
            var9.printStackTrace();
        }

        return var0;
    }

    private static IBAHolder setFloatIBAValue(IBAHolder var0, String var1, String var2)
        throws WTException, RemoteException, WTPropertyVetoException {
        var2 = var2.trim();
        double var3 = 0.0;
        if (var2 != null && !"".equals(var2)) {
            var3 = Double.valueOf(var2);
            DefaultAttributeContainer var5 = getRefreshableAttributeContainer(var0, false);
            AttributeDefDefaultView var6 = getAttributeDefDefaultView(var1);
            if (!(var6 instanceof FloatDefView)) {
                String var10 =
                    WTMessage.getLocalizedMessage(NEWRESOURCE, "EXCEPTION_IBA_NOT_FLOAT", new Object[] {var1});
                throw new WTException(var10);
            } else {
                FloatDefView var7 = (FloatDefView)var6;
                AbstractValueView[] var8 = var5.getAttributeValues(var7);
                FloatValueDefaultView var9;
                if (var8.length == 0) {
                    var9 = new FloatValueDefaultView(var7, var3, NumericToolkitUtil.countSigFigs(var2));
                    var5.addAttributeValue(var9);
                } else {
                    var9 = (FloatValueDefaultView)var8[0];
                    var9.setValue(var3);
                    var5.updateAttributeValue(var9);
                }

                var0.setAttributeContainer(var5);
                return var0;
            }
        } else {
            return var0;
        }
    }

    /**
     * 设置IBA属性
     * 
     * @param ibaHolder IBA持有者
     * @param ibaName 属性名
     * @param value 属性值
     * @return
     * @throws WTException
     * @throws RemoteException
     * @throws WTPropertyVetoException
     */
    public static IBAHolder setIBAValue(IBAHolder ibaHolder, String ibaName, Object value)
        throws WTException, RemoteException, WTPropertyVetoException {
        return setIBAValue(ibaHolder, ibaName, value, true);
    }

    /**
     * TODO 设置IBA属性 修改这个函数 通过属性的视图类型来设置对应的值 例如 一个字符类型的数据传递一个int数字是需要转换成字符串设置进去的 这样编写存在问题
     *
     * @param ibaHolder IBA持有者
     * @param ibaName 属性名
     * @param ibaValue 属性值
     * @param isUpdateHolder 是否更新容器
     * @return
     * @throws WTException
     * @throws RemoteException
     * @throws WTPropertyVetoException
     */
    public static IBAHolder setIBAValue(IBAHolder ibaHolder, String ibaName, Object ibaValue, boolean isUpdateHolder)
        throws WTException, RemoteException, WTPropertyVetoException {
        // 删除IBA属性值
        ibaHolder = deleteIBAValue(ibaHolder, ibaName);
        // 判断类型 并设置对应的IBA属性 TODO 缺少URL类型和其他的类型
        if (ibaValue instanceof Boolean) {
            ibaHolder = setBooleanIBAValue(ibaHolder, ibaName, ibaValue.toString());
        } else if (ibaValue instanceof String) {
            ibaHolder = setStringIBAValue(ibaHolder, ibaName, (String)ibaValue);
        } else if (ibaValue instanceof Float) {
            ibaHolder = setFloatIBAValue(ibaHolder, ibaName, ibaValue.toString());
        } else if (ibaValue instanceof Integer) {
            ibaHolder = setIntegerIBAValue(ibaHolder, ibaName, ibaValue.toString());
        } else {
            if (!(ibaValue instanceof Timestamp)) {
                throw new WTException(NEWRESOURCE, "EXCEPTION_UNSUPPORTED_TYPE",
                    new Object[] {ibaValue.getClass().getName()});
            }
            ibaHolder = setTimestampValue(ibaHolder, ibaName, (Timestamp)ibaValue);
        }
        if (isUpdateHolder && PersistenceHelper.isPersistent(ibaHolder)) {
            if (updateIBAHolder(ibaHolder)) {
                return ibaHolder;
            } else {
                throw new WTException(NEWRESOURCE, "EXCEPTION_UNABLE_TO_UPDATE_IBAHOLDER", (Object[])null);
            }
        } else {
            return ibaHolder;
        }
    }

    private static IBAHolder setIntegerIBAValue(IBAHolder var0, String var1, String var2)
        throws WTException, RemoteException, WTPropertyVetoException {
        var2 = var2.trim();
        boolean var3 = false;
        int var9 = Integer.parseInt(var2);
        DefaultAttributeContainer var4 = getRefreshableAttributeContainer(var0, false);
        AttributeDefDefaultView var5 = getAttributeDefDefaultView(var1);
        if (!(var5 instanceof IntegerDefView)) {
            throw new WTException(NEWRESOURCE, "EXCEPTION_IBA_NOT_INTEGER", new Object[] {var1});
        } else {
            IntegerDefView var6 = (IntegerDefView)var5;
            AbstractValueView[] var7 = var4.getAttributeValues(var6);
            IntegerValueDefaultView var8;
            if (var7.length == 0) {
                var8 = new IntegerValueDefaultView(var6, (long)var9);
                var4.addAttributeValue(var8);
            } else {
                var8 = (IntegerValueDefaultView)var7[0];
                var8.setValue((long)var9);
                var4.updateAttributeValue(var8);
            }

            var0.setAttributeContainer(var4);
            return var0;
        }
    }

    private static IBAHolder setTimestampValue(IBAHolder ibaHolder, String ibaName, Timestamp timestamp)
        throws RemoteException, WTException, WTPropertyVetoException {
        DefaultAttributeContainer attributeContainer = getRefreshableAttributeContainer(ibaHolder, false);
        AttributeDefDefaultView defDefaultView = getAttributeDefDefaultView(ibaName);
        if (!(defDefaultView instanceof TimestampDefView)) {
            throw new WTException(NEWRESOURCE, "EXCEPTION_IBA_NOT_TIMESTAMP", new Object[] {ibaName});
        } else {
            TimestampDefView defaultView = (TimestampDefView)defDefaultView;
            AbstractValueView[] valueViews = attributeContainer.getAttributeValues(defaultView);
            TimestampValueDefaultView valueDefaultView;
            if (valueViews.length == 0) {
                valueDefaultView = new TimestampValueDefaultView(defaultView, timestamp);
                attributeContainer.addAttributeValue(valueDefaultView);
            } else {
                valueDefaultView = (TimestampValueDefaultView)valueViews[0];
                valueDefaultView.setValue(timestamp);
                attributeContainer.updateAttributeValue(valueDefaultView);
            }
            ibaHolder.setAttributeContainer(attributeContainer);
            return ibaHolder;
        }
    }

    /**
     * 设置IBA属性 如果是多值则则只设置第一个位置的值
     * 
     * @param ibaHolder iba属性持有者
     * @param ibaName 属性名
     * @param value 值
     * @return 最新的持有者对象
     * @throws WTException
     * @throws WTPropertyVetoException
     * @throws RemoteException
     */
    public static IBAHolder setStringIBAValue(IBAHolder ibaHolder, String ibaName, String value)
        throws WTException, WTPropertyVetoException, RemoteException {
        return setStringIBAValue(ibaHolder, ibaName, value, 0);
    }

    /**
     * 设置IBA属性 如果是多值则 指定位置覆盖
     *
     * @param ibaHolder iba属性持有者
     * @param ibaName 属性名
     * @param value 值
     * @return 最新的持有者对象
     * @throws WTException
     * @throws WTPropertyVetoException
     * @throws RemoteException
     */
    public static IBAHolder setStringIBAValue(IBAHolder ibaHolder, String ibaName, String value, int index)
        throws WTException, WTPropertyVetoException, RemoteException {
        AttributeDefDefaultView defDefaultView = getAttributeDefDefaultView(ibaName);
        if (!(defDefaultView instanceof StringDefView)) {
            throw new WTException(NEWRESOURCE, "EXCEPTION_IBA_NOT_STRING", new Object[] {ibaName});
        } else {
            StringDefView defaultView = (StringDefView)defDefaultView;
            DefaultAttributeContainer attributeContainer = getRefreshableAttributeContainer(ibaHolder, false);
            AbstractValueView[] attributeValues = attributeContainer.getAttributeValues(defaultView);
            StringValueDefaultView valueDefaultView;
            if (attributeValues.length > 0) {
                valueDefaultView = (StringValueDefaultView)attributeValues[index];
                valueDefaultView.setValue(value);
                attributeContainer.updateAttributeValue(valueDefaultView);
            } else {
                valueDefaultView = new StringValueDefaultView(defaultView, value);
                attributeContainer.addAttributeValue(valueDefaultView);
            }
            ibaHolder.setAttributeContainer(attributeContainer);
            return ibaHolder;
        }
    }

    /**
     * 更新IBA容器
     * 
     * @param ibaHolder iba持有对象
     * @return
     */
    public static boolean updateIBAHolder(IBAHolder ibaHolder) {
        IBAValueDBService service = new IBAValueDBService();
        try {
            PersistenceServerHelper.manager.update((Persistable)ibaHolder);
            AttributeContainer attributeContainer = ibaHolder.getAttributeContainer();
            if (attributeContainer == null) {
                return false;
            } else {
                Object object = ((DefaultAttributeContainer)attributeContainer).getConstraintParameter();
                AttributeContainer container = service.updateAttributeContainer(ibaHolder, object, (Locale)null,
                    (MeasurementSystemDefaultView)null);
                ibaHolder.setAttributeContainer(container);
                return true;
            }
        } catch (WTException var5) {
            log.error("updateIBAHOlder: Couldn't update. " + var5);
            return false;
        }
    }

    /**
     *
     * @param list
     * @return
     */
    public static String listToString(List<Object> list, String symbol) {
        return list.stream().map(Object::toString).collect(Collectors.joining(symbol));
    }
}
