package ext.ziang.common.util;

import java.util.HashMap;
import java.util.Map;

import com.ptc.core.lwc.client.commands.LWCCommands;
import com.ptc.core.meta.common.TypeIdentifier;
import org.apache.log4j.Logger;

import com.ptc.core.lwc.server.PersistableAdapter;
import com.ptc.core.meta.common.SearchOperationIdentifier;

import wt.fc.Persistable;
import wt.fc.WTObject;
import wt.iba.definition.litedefinition.AttributeDefDefaultView;
import wt.iba.value.DefaultAttributeContainer;
import wt.iba.value.IBAHolder;
import wt.log4j.LogR;
import wt.part.WTPart;
import wt.session.SessionHelper;
import wt.type.TypeDefinitionReference;
import wt.type.TypedUtility;
import wt.util.WTException;

public class MbaUtil {

    private static final Logger log = LogR.getLogger(IbaUtil.class.getName());

    /**
     * 获取局部属性
     * 
     * @param ibaHolder
     * @return
     */
    public static Map<String, Object> findAllMBAValue(WTPart wtPart) {
        Map<String, Object> mapping = new HashMap<>();
        boolean enforce = true;
        try {
            enforce = wt.session.SessionServerHelper.manager.setAccessEnforced(false);
            TypeDefinitionReference typeDefinitionReference = wtPart.getTypeDefinitionReference();
            // 获取当前类型所有的属性
            //
            TypeIdentifier typeIdentifier = TypedUtility.getTypeIdentifier(wtPart);
            System.out.println("typeIdentifier = " + typeIdentifier);
            String typeInternalName = typeIdentifier.getTypeInternalName();
            System.out.println("typeInternalName = " + typeInternalName);
            System.out.println("getTypename = " + typeIdentifier.getTypename());
            LWCCommands.getTypeAttributes("");
        } catch (WTException e) {
            log.error("查询失败", e);
        } finally {
            wt.session.SessionServerHelper.manager.setAccessEnforced(enforce);
        }
        return null;
    }
}
