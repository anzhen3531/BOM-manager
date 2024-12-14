package ext.ziang.common.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;

import com.ptc.windchill.enterprise.templateutil.ActionValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wt.access.AccessControlHelper;
import wt.access.AccessPermission;
import wt.doc.WTDocument;
import wt.fc.*;
import wt.fc.collections.WTValuedHashMap;
import wt.folder.Folder;
import wt.folder.FolderHelper;
import wt.inf.container.WTContained;
import wt.inf.container.WTContainer;
import wt.inf.container.WTContainerRef;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.org.WTPrincipal;
import wt.org.WTPrincipalReference;
import wt.part.WTPart;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.Iterated;
import wt.vc.IterationInfo;
import wt.vc.VersionReference;
import wt.vc._IterationInfo;
import wt.vc.wip.CheckoutLink;
import wt.vc.wip.WorkInProgressException;
import wt.vc.wip.WorkInProgressHelper;
import wt.vc.wip.Workable;

/**
 * 工具实用程序
 *
 * @author anzhen
 * @date 2024/03/30
 */
public class ToolUtils implements RemoteAccess {
    private static final Logger log = LoggerFactory.getLogger(IbaUtil.class);

    public static String getOROid(Persistable persistable) throws WTException {
        ReferenceFactory refFactory = new ReferenceFactory();
        return refFactory.getReferenceString(
            ObjectReference.newObjectReference((persistable.getPersistInfo().getObjectIdentifier())));
    }

    public static String getVROid(Iterated iterated) throws WTException {
        ReferenceFactory refFactory = new ReferenceFactory();
        return refFactory.getReferenceString(VersionReference.newVersionReference(iterated));
    }

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
                return (Persistable)RemoteMethodServer.getDefault().invoke("getObjectByOid", ToolUtils.class.getName(),
                    null, new Class[] {String.class}, new Object[] {oid});
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
                return (WTReference)RemoteMethodServer.getDefault().invoke("getReferenceByOid",
                    ToolUtils.class.getName(), null, new Class[] {String.class}, new Object[] {oid});
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
     * @param workable 需要检出的对象
     * @param checkOutNote 检出说明
     * @return Workable
     * @throws WTException
     * @throws WTPropertyVetoException
     * @throws WorkInProgressException
     */
    public static Workable checkout(Workable workable, String checkOutNote)
        throws WTException, WTPropertyVetoException {
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
            WTPart wtpart = (WTPart)workable;
            name = wtpart.getName();
            number = wtpart.getNumber();
        } else if (workable instanceof WTDocument) {
            WTDocument wtdoc = (WTDocument)workable;
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
     * @param workable 检入副本
     * @param checkInNote 检入说明
     * @return Workable 检入之后的新对象
     * @throws WTException WT异常
     * @throws WTPropertyVetoException WTFeproperty 否决例外
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

    /**
     * 获取或创建文件夹
     *
     * @param folderPath 文件夹路径
     * @param containerRef 容器编号
     * @return {@link Folder}
     */
    public static Folder getOrCreateFolder(String folderPath, WTContainerRef containerRef) {
        // 先查询Folder 是否存在
        Folder folder = null;
        if (containerRef != null) {
            try {
                // 只要能正常查询到会直接返回
                // 否则直接创建
                folder = FolderHelper.service.getFolder(folderPath, containerRef);
                return folder;
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (folder == null) {
                try {
                    folder = FolderHelper.service.createSubFolder(folderPath, containerRef);
                    return folder;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 分配文件夹
     *
     * @param container 容器
     * @param folderPath 文件夹路径
     * @param contained 包含
     * @throws WTPropertyVetoException WTFeproperty 否决例外
     * @throws WTException WT异常
     */
    public static void assignFolder(WTContainer container, String folderPath, WTContained contained)
        throws WTPropertyVetoException, WTException {
        ObjectIdentifier oid = container.getPersistInfo().getObjectIdentifier();
        WTContainerRef containerRef = WTContainerRef.newWTContainerRef(oid);
        Folder folder = getOrCreateFolder(folderPath, containerRef);
        contained.setContainerReference(containerRef);
        WTValuedHashMap map = new WTValuedHashMap();
        map.put(contained, folder);
        FolderHelper.assignLocations(map);
    }

    /**
     * 设置创建者和修改者
     *
     * @param persistable 对象
     * @param iterationInfo 版本信息
     * @param reference     参考
     */
    public static void setCreator(Persistable persistable, IterationInfo iterationInfo,
        WTPrincipalReference reference) {
        updatePrincipal(persistable, iterationInfo, reference, "setCreator");
    }

    public static void setModifier(Persistable persistable, IterationInfo iterationInfo,
        WTPrincipalReference reference) {
        updatePrincipal(persistable, iterationInfo, reference, "setModifier");
    }

    /**
     * 更新用户信息
     * 
     * @param revisionControlledObject 当前对象
     * @param iterationInfo 版本信息对象
     * @param reference 用户对象
     * @param methodName 方法名
     */
    public static void updatePrincipal(Persistable revisionControlledObject, IterationInfo iterationInfo,
        WTPrincipalReference reference, String methodName) {
        try {
            Class[] classWTPrinicipalReference = new Class[] {WTPrincipalReference.class};
            Method method = _IterationInfo.class.getDeclaredMethod(methodName, classWTPrinicipalReference);
            method.setAccessible(true);
            method.invoke(iterationInfo, new Object[] {reference});
            PersistenceServerHelper.manager.update(revisionControlledObject);
        } catch (Exception e) {
            log.error("updatePrincipal error {}", e);
        }
    }

}
