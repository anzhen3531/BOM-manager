package ext.ziang.common.util;

import wt.fc.Persistable;
import wt.fc.ReferenceFactory;
import wt.fc.WTReference;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.util.WTException;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

public class ToolUtils implements RemoteAccess {

    /**
     * 按 oid 获取对象
     *
     * @param oid oid
     * @return {@link Persistable}
     * @throws WTException WT异常
     */
    public static Persistable getObjectByOid(String oid) throws WTException {
        try {
            if (!RemoteMethodServer.ServerFlag) {
                return (Persistable) RemoteMethodServer.getDefault().invoke("getObjectByOid", ToolUtils.class.getName(), null, new Class[]{String.class}, new Object[]{oid});
            } else {
                WTReference wtreference = getReferenceByOid(oid);
                return wtreference == null ? null : wtreference.getObject();
            }
        } catch (RemoteException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 按 OID 获取引用
     *
     * @param oid oid
     * @return {@link WTReference}
     * @throws WTException WT异常
     */
    public static WTReference getReferenceByOid(String oid) throws WTException {
        try {
            if (!RemoteMethodServer.ServerFlag) {
                return (WTReference) RemoteMethodServer.getDefault().invoke("getReferenceByOid", ToolUtils.class.getName(), null, new Class[]{String.class}, new Object[]{oid});
            } else {
                ReferenceFactory referencefactory = new ReferenceFactory();
                return referencefactory.getReference(oid);
            }
        } catch (RemoteException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
