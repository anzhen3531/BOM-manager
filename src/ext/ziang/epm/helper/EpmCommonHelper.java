package ext.ziang.epm.helper;

import ext.ziang.epm.bean.CreateEpmBean;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wt.content.ApplicationData;
import wt.content.ContentRoleType;
import wt.content.ContentServerHelper;
import wt.epm.*;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.collections.WTValuedHashMap;
import wt.fc.collections.WTValuedMap;
import wt.folder.Folder;
import wt.folder.FolderHelper;
import wt.inf.container.WTContainerRef;
import wt.part.QuantityUnit;
import wt.pdmlink.PDMLinkProduct;
import wt.pom.Transaction;
import wt.query.KeywordExpression;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionServerHelper;
import wt.util.WTException;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class EpmCommonHelper {

    /**
     * 日志工具
     */
    private static final Logger log = LoggerFactory.getLogger(EpmCommonHelper.class);

    /**
     * 编写接口
     *
     * @param bean
     * @throws WTException
     * @throws PropertyVetoException
     * @throws IOException
     */
    public static void createEPM(CreateEpmBean bean) throws WTException, PropertyVetoException, IOException {
        EPMDocument epmDocument = EPMDocument.newEPMDocument();
        EPMDocumentMaster master = (EPMDocumentMaster) epmDocument.getMaster();
        master.setAuthoringApplication(EPMAuthoringAppType.toEPMAuthoringAppType(bean.getCadType()));
        master.setOwnerApplication(EPMApplicationType.toEPMApplicationType("EPM"));
        if (StringUtils.isNotBlank(bean.getFileType())) {
            epmDocument.setDocType(EPMDocumentType.toEPMDocumentType(bean.getFileType()));
        }
        if (StringUtils.isNotBlank(bean.getUnit())) {
            master.setDefaultUnit(QuantityUnit.toQuantityUnit(bean.getUnit()));
        }
        if (StringUtils.isNotBlank(bean.getFileName())) {
            master.setCADName(bean.getFileName());
        }
        if (StringUtils.isNotBlank(bean.getName())) {
            master.setName(bean.getName());
        } else {
            master.setName(bean.getFileName());
        }
        if (StringUtils.isNotBlank(bean.getNumber())) {
            master.setName(bean.getNumber());
        } else {
            master.setNumber(bean.getFileName());
        }
        if (StringUtils.isNotBlank(bean.getDescription())) {
            epmDocument.setDescription(bean.getDescription());
        }
        InputStream inputStream = bean.getInputStream();
        PDMLinkProduct wtLibraryByName = findWTLibraryByName(bean.getContainerName());
        WTContainerRef containerReference = WTContainerRef.newWTContainerRef(wtLibraryByName);
        epmDocument.setContainerReference(containerReference);
        if (StringUtils.isNotBlank(bean.getPath())) {
            Folder folder;
            if (bean.getPath().startsWith("/Default")) {
                folder = FolderHelper.service.getFolder(bean.getPath(), containerReference);
            } else {
                folder = FolderHelper.service.getFolder("/Default", containerReference);
            }
            WTValuedMap map = new WTValuedHashMap();
            map.put(epmDocument, folder);
            FolderHelper.assignLocations(map);
        } else {
            Folder folder = FolderHelper.service.getFolder("/Default", containerReference);
            WTValuedMap map = new WTValuedHashMap();
            map.put(epmDocument, folder);
            FolderHelper.assignLocations(map);
        }
        epmDocument = (EPMDocument) PersistenceHelper.manager.save(epmDocument);
        wt.pom.Transaction tx = new Transaction();
        try {
            tx.start();
            ApplicationData appdata = ApplicationData.newApplicationData(epmDocument);
            appdata.setRole(ContentRoleType.PRIMARY);
            appdata.setFileName("{$CAD_NAME}");
            appdata.setCategory("GENERAL");
            ContentServerHelper.service.updateContent(epmDocument, appdata, inputStream);
            tx.commit();
            tx = null;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (Objects.nonNull(tx)) {
                tx.rollback();
            }
        }
    }

    /**
     * 根据容器得到容器的文件夹
     *
     * @return 产品库
     */
    public static PDMLinkProduct findWTLibraryByName(String cProductFamily) {
        PDMLinkProduct product = null;
        boolean access = SessionServerHelper.manager.setAccessEnforced(false);
        try {
            QuerySpec qs = new QuerySpec(PDMLinkProduct.class);
            SearchCondition searchCondition = new SearchCondition(new KeywordExpression("A0.NAMECONTAINERINFO"), SearchCondition.EQUAL,
                    new KeywordExpression("'" + cProductFamily + "'"));
            qs.appendWhere(searchCondition, new int[]{0});
            QueryResult qr = PersistenceHelper.manager.find(qs);
            if (qr.hasMoreElements()) {
                product = (PDMLinkProduct) qr.nextElement();
            }
        } catch (WTException e) {
            log.error(e.getMessage(), e);
        } finally {
            SessionServerHelper.manager.setAccessEnforced(access);
        }
        return product;
    }

}
