package ext.ziang.common.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ptc.core.lwc.server.PersistableAdapter;
import com.ptc.core.meta.common.SearchOperationIdentifier;

import wt.fc.Persistable;
import wt.iba.definition.litedefinition.AttributeDefDefaultView;
import wt.iba.value.DefaultAttributeContainer;
import wt.iba.value.IBAHolder;
import wt.log4j.LogR;
import wt.session.SessionHelper;
import wt.util.WTException;

public class MbaUtil {

    private static final Logger log = LogR.getLogger(IbaUtil.class.getName());

    /**
     * 获取局部属性
     * 
     * @param ibaHolder
     * @return
     */
    public static Map<String, Object> findAllMBAValue(IBAHolder ibaHolder) {
        Map<String, Object> mapping = new HashMap<>();
        boolean enforce = true;
        try {
            enforce = wt.session.SessionServerHelper.manager.setAccessEnforced(false);
            PersistableAdapter adapter = new PersistableAdapter((Persistable)ibaHolder, null, SessionHelper.getLocale(),
                new SearchOperationIdentifier());
            DefaultAttributeContainer attributeContainer = (DefaultAttributeContainer)ibaHolder.getAttributeContainer();
            AttributeDefDefaultView[] attributeDefinitions = attributeContainer.getAttributeDefinitions();
            System.out.println("attributeDefinitions = " + attributeDefinitions);
        } catch (WTException e) {
            log.error("查询失败", e);
        } finally {
            wt.session.SessionServerHelper.manager.setAccessEnforced(enforce);
        }
        return null;
    }
}
