package ext.ziang.common.util;

import com.ptc.windchill.enterprise.templateutil.ActionValidator;
import wt.access.AccessControlHelper;
import wt.access.AccessPermission;
import wt.doc.WTDocument;
import wt.fc.Persistable;
import wt.fc.ReferenceFactory;
import wt.fc.WTReference;
import wt.folder.Folder;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.part.WTPart;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.wip.CheckoutLink;
import wt.vc.wip.WorkInProgressException;
import wt.vc.wip.WorkInProgressHelper;
import wt.vc.wip.Workable;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

/**
 * 工具实用程序
 *
 * @author anzhen
 * @date 2024/03/30
 */
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


    /**
     * 返回检出部件的工作副本，如果部件已经检出，则返回已经存在的工作副本
     *
     * @param workable     需要检出的对象
     * @param checkOutNote 检出说明
     * @return Workable
     * @throws WTException
     * @throws WTPropertyVetoException
     * @throws WorkInProgressException
     */
    public static Workable checkout(Workable workable, String checkOutNote) throws WTException, WTPropertyVetoException {
        Workable workingCopy = null;
        if (WorkInProgressHelper.isWorkingCopy(workable)) {
            workingCopy = workable;
            return workingCopy;
        }
        if (WorkInProgressHelper.isCheckedOut(workable)) {
            workingCopy = WorkInProgressHelper.service.workingCopyOf(workable);
            return workingCopy;
        }

        String name = "";
        String number = "";
        if (workable instanceof WTPart) {
            WTPart wtpart = (WTPart) workable;
            name = wtpart.getName();
            number = wtpart.getNumber();
        } else if (workable instanceof WTDocument) {
            WTDocument wtdoc = (WTDocument) workable;
            name = wtdoc.getName();
            number = wtdoc.getNumber();
        }
        if (!AccessControlHelper.manager.hasAccess(workable, AccessPermission.MODIFY)) {
            throw new WTException("当前用户不具有对象[编号:" + number + "，名称:" + name + "]的检出权限。");
        }

        // 判断是否有检出权限)
        if (ActionValidator.isCheckOutValid(workable)) {
            Folder checkOutFolder = null;
            CheckoutLink checkOutLink = null;
            try {
                checkOutFolder = WorkInProgressHelper.service.getCheckoutFolder();
                checkOutLink = WorkInProgressHelper.service.checkout(workable, checkOutFolder, checkOutNote);
                workingCopy = checkOutLink.getWorkingCopy();
                if (!WorkInProgressHelper.isWorkingCopy(workingCopy)) {
                    workingCopy = WorkInProgressHelper.service.workingCopyOf(workingCopy);
                }
            } catch (WTPropertyVetoException | WTException e) {
                throw e;
            }
        } else {
            throw new WTException("当前用户不具有对象[编号:" + number + "，名称:" + name + "]的检出权限。");
        }
        return workingCopy;
    }

    /**
     * 检入对象
     *
     * @param workable
     * @param checkInNote
     * @return Workable
     * @throws WTException
     * @throws WTPropertyVetoException
     */
    public static Workable checkin(Workable workable, String checkInNote) throws WTException, WTPropertyVetoException {
        // 判断是否需要检入
        if (!WorkInProgressHelper.isWorkingCopy(workable)) {
            workable = WorkInProgressHelper.service.workingCopyOf(workable);
        }
        if (WorkInProgressHelper.isCheckedOut(workable)) {
            // 如果处于检出状态
            workable = WorkInProgressHelper.service.checkin(workable, checkInNote);
        }
        return workable;
    }
}
