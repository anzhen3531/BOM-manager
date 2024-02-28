package ext.ziang.common.util;

import com.ptc.core.lwc.common.BaseDefinitionService;
import com.ptc.core.lwc.common.OidHelper;
import com.ptc.core.lwc.common.view.PropertyDefinitionReadView;
import com.ptc.core.lwc.common.view.PropertyValueReadView;
import com.ptc.core.lwc.common.view.ReadViewIdentifier;
import com.ptc.core.lwc.common.view.ReusableAttributeReadView;
import com.ptc.core.lwc.common.view.ReusableAttributeWriteView;
import com.ptc.core.meta.common.CorrectableException;
import wt.fc.ObjectIdentifier;
import wt.iba.definition.AbstractAttributeDefinition;
import wt.iba.definition.IBADefinitionException;
import wt.inf.container.WTContainerHelper;
import wt.inf.container.WTContainerRef;
import wt.services.ServiceFactory;
import wt.util.WTException;

import java.util.Collection;
import java.util.Map;

/**
 * 常用操作 attr util
 *
 * @author anzhen
 * @date 2024/02/29
 */
public class CommonOperationAttrUtil {
    private static final BaseDefinitionService BASE_DEF_SERVICE = (BaseDefinitionService) ServiceFactory.getService(BaseDefinitionService.class);

    public static void main(String[] args) throws WTException {
        createAttr("testCreateAttr",
                null,
                "wt.iba.definition.StringDefinition",
                "test",
                "测试创建属性",
                "OR:wt.iba.definition.AttributeOrganizer:156004");
    }

    /**
     * 创建 Attr
     *
     * @param internalName      内部名称
     * @param logicalIdentifier 逻辑标识符
     * @param dataType          数据类型
     * @param description       描述
     * @param displayName       显示名称
     * @param oid               oid
     * @throws WTException WT异常
     */
    public static ReusableAttributeReadView createAttr(String internalName, String logicalIdentifier, String dataType, String description, String displayName, String oid) throws WTException {
        ReusableAttributeReadView reusableAttributeReadView = null;
        if (oid == null) {
            throw new WTException("New reusable attribute needs to have a reusable organizer as parent!");
        } else {
            if (displayName == null || displayName.isEmpty()) {
                if (internalName.length() > 100) {
                    displayName = internalName.substring(0, 100);
                } else {
                    displayName = internalName;
                }
            }
            if (description == null || description.isEmpty()) {
                description = internalName;
            }
            ObjectIdentifier parentIdentifier = OidHelper.getOid(oid);
            ReusableAttributeWriteView reusableAttribute = createReusableAttribute(internalName, dataType, null, null,
                    description, displayName, internalName, logicalIdentifier, parentIdentifier);
            try {
                reusableAttributeReadView = BASE_DEF_SERVICE.createReusableAttribute(reusableAttribute);
                return reusableAttributeReadView;
            } catch (IBADefinitionException var19) {
                throw new CorrectableException(var19.getLocalizedMessage());
            }
        }
    }


    /**
     * 创建可重用属性
     *
     * @param var1 变量1
     * @param var2 变量2
     * @param var3 var3
     * @param var4 var4
     * @param var5 var5
     * @param var6 var6
     * @param var7 变量7
     * @param var8 var8
     * @param var9 var9
     * @return {@link ReusableAttributeWriteView}
     * @throws WTException WT异常
     */
    private static ReusableAttributeWriteView createReusableAttribute(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, ObjectIdentifier var9) throws WTException {
        WTContainerRef var10 = WTContainerHelper.service.getExchangeRef();
        ReusableAttributeWriteView var11 = new ReusableAttributeWriteView(var1, var10, var9, (Collection) null, var2, var4, (Map) null, false, (ReadViewIdentifier) null);
        ObjectIdentifier var12 = var11.getOid();
        var11.setProperty(createPropertyValueReadView(var12, "internalName", var1));
        var11.setProperty(createPropertyValueReadView(var12, "hierarchyDisplayName", var7));
        var11.setProperty(createPropertyValueReadView(var12, "displayName", var6));
        var11.setProperty(createPropertyValueReadView(var12, "logicalIdentifier", var8));
        var11.setProperty(createPropertyValueReadView(var12, "description", var5));
        var11.setProperty(createPropertyValueReadView(var12, "referenceClass", var3));
        return var11;
    }

    /**
     * 创建属性值读取视图
     *
     * @param var1 变量1
     * @param var2 变量2
     * @param var3 var3
     * @return {@link PropertyValueReadView}
     */
    private static PropertyValueReadView createPropertyValueReadView(ObjectIdentifier var1, String var2, String var3) {
        Object var4 = null;
        Object var5 = null;
        Object var7 = null;
        PropertyDefinitionReadView var9 = new PropertyDefinitionReadView(AbstractAttributeDefinition.class.getName(), var2, String.class.getName());
        PropertyValueReadView var10 = new PropertyValueReadView(var1, var9, var3, (Map) var4, (ObjectIdentifier) var5, false, (ReadViewIdentifier) var7, false);
        return var10;
    }
}
