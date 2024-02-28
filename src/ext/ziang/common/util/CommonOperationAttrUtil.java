package ext.ziang.common.util;

import cn.hutool.core.util.StrUtil;
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
import wt.iba.definition.ReferenceDefinition;
import wt.iba.definition.UnitDefinition;
import wt.inf.container.WTContainerHelper;
import wt.inf.container.WTContainerRef;
import wt.org.WTOrganization;
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

    /**
     * 主要
     *
     * @param args 参数
     * @throws WTException WT异常
     */
    public static void main(String[] args) throws WTException {
        createReusableAttribute(
                "testCreateAttr",
                null,
                "wt.iba.definition.StringDefinition",
                "test",
                "测试创建属性",
                "OR:wt.iba.definition.AttributeOrganizer:156004",
                null);
        //   TODO
        //                          CreateAttributeFormProcessor 创建属性关联
        //                          NewConstraintFormProcessor 创建枚举关联
        //                          CreateEntryFormProcessor 创建枚举关联根枚举
    }

    /**
     * 创建 可重用属性
     *
     * @param internalName      内部名称  ->   字符串
     * @param logicalIdentifier 逻辑标识符 ->   字符串
     * @param dataType          数据类型  ->  内部名称
     * @param description       描述   ->  字符串
     * @param displayName       显示名称 ->  字符串
     * @param oid               oid -> 父对象oid
     * @param qtyOfMeasure      测量数量 -> 当数据类型为：测量单位时
     * @return {@link ReusableAttributeReadView}
     * @throws WTException WT异常
     */
    public static ReusableAttributeReadView createReusableAttribute(String internalName, String logicalIdentifier, String dataType, String description, String displayName, String oid, String qtyOfMeasure) throws WTException {
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

            if (UnitDefinition.class.getName().equals(dataType)) {
                if (StrUtil.isBlank(qtyOfMeasure)) {
                    qtyOfMeasure = "Acceleration";
                }
            }

            String orgName = null;
            if (ReferenceDefinition.class.getName().equals(dataType)) {
                orgName = WTOrganization.class.getName();
            }

            // 获取父级对象oid 父级对象类型 AttributeOrganizer 可以编写高级查询查询
            ObjectIdentifier parentIdentifier = OidHelper.getOid(oid);
            ReusableAttributeWriteView reusableAttribute = createReusableAttribute(internalName, dataType, qtyOfMeasure, orgName, description, displayName, internalName, logicalIdentifier, parentIdentifier);
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
     * @param internalName      内部名称
     * @param dataType          数据类型
     * @param description       描述
     * @param displayName       显示名称
     * @param internalName1     内部名称1
     * @param logicalIdentifier 逻辑标识符
     * @param parentIdentifier  父标识符
     * @param qtyOfMeasure      测量数量
     * @param orgName           组织名称
     * @return {@link ReusableAttributeWriteView}
     * @throws WTException WT异常
     */
    private static ReusableAttributeWriteView createReusableAttribute(String internalName, String dataType, String qtyOfMeasure, String orgName, String description, String displayName, String internalName1, String logicalIdentifier, ObjectIdentifier parentIdentifier) throws WTException {
        WTContainerRef containerRef = WTContainerHelper.service.getExchangeRef();
        ReusableAttributeWriteView attributeWriteView = new ReusableAttributeWriteView(internalName, containerRef, parentIdentifier, (Collection) null,
                dataType, orgName, (Map) null,
                false, (ReadViewIdentifier) null);
        ObjectIdentifier objectIdentifier = attributeWriteView.getOid();
        attributeWriteView.setProperty(createPropertyValueReadView(objectIdentifier, "internalName", internalName));
        attributeWriteView.setProperty(createPropertyValueReadView(objectIdentifier, "hierarchyDisplayName", internalName1));
        attributeWriteView.setProperty(createPropertyValueReadView(objectIdentifier, "displayName", displayName));
        attributeWriteView.setProperty(createPropertyValueReadView(objectIdentifier, "logicalIdentifier", logicalIdentifier));
        attributeWriteView.setProperty(createPropertyValueReadView(objectIdentifier, "description", description));
        attributeWriteView.setProperty(createPropertyValueReadView(objectIdentifier, "referenceClass", qtyOfMeasure));
        return attributeWriteView;
    }

    /**
     * 创建属性值读取视图
     *
     * @param objectIdentifier 对象标识符
     * @param referenceClass   引用类
     * @param qtyOfMeasure     测量数量
     * @return {@link PropertyValueReadView}
     */
    private static PropertyValueReadView createPropertyValueReadView(ObjectIdentifier objectIdentifier, String referenceClass, String qtyOfMeasure) {
        PropertyDefinitionReadView readView = new PropertyDefinitionReadView(AbstractAttributeDefinition.class.getName(),
                referenceClass, String.class.getName());
        PropertyValueReadView propertyValueReadView = new PropertyValueReadView(objectIdentifier, readView,
                qtyOfMeasure, (Map) null, (ObjectIdentifier) null,
                false, (ReadViewIdentifier) null, false);
        return propertyValueReadView;
    }


}
