package ext.ziang.common.helper.attr;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;

import com.google.gwt.thirdparty.guava.common.collect.Maps;
import com.ptc.core.lwc.client.commands.LWCCommands;
import com.ptc.core.lwc.client.util.AttributeTemplateFlavorHelper;
import com.ptc.core.lwc.client.util.NewConstraintInflator;
import com.ptc.core.lwc.client.util.PropertyDefinitionHelper;
import com.ptc.core.lwc.common.*;
import com.ptc.core.lwc.common.dynamicEnum.EnumerationConstraintsHelper;
import com.ptc.core.lwc.common.view.*;
import com.ptc.core.lwc.server.LWCBasicConstraint;
import com.ptc.core.lwc.server.LWCIBAAttDefinition;
import com.ptc.core.lwc.server.TypeDefinitionServiceHelper;
import com.ptc.core.lwc.server.cache.db.DBServiceHelper;
import com.ptc.core.meta.common.CorrectableException;
import com.ptc.core.meta.common.DiscreteSet;
import com.ptc.core.meta.container.common.impl.SingleValuedConstraint;
import com.ptc.netmarkets.model.NmOid;

import cn.hutool.core.util.StrUtil;
import com.ptc.windchill.csm.client.helpers.CSMTypeDefHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wt.doc.WTDocument;
import wt.doc.WTDocumentMaster;
import wt.doc.WTDocumentMasterIdentity;
import wt.fc.IdentityHelper;
import wt.fc.ObjectIdentifier;
import wt.fc.PersistenceHelper;
import wt.iba.definition.AbstractAttributeDefinition;
import wt.iba.definition.AttributeDefinition;
import wt.iba.definition.IBADefinitionException;
import wt.iba.definition.ReferenceDefinition;
import wt.iba.definition.UnitDefinition;
import wt.iba.definition.litedefinition.AttributeDefDefaultView;
import wt.iba.definition.service.IBADefinitionCache;
import wt.iba.definition.service.IBADefinitionService;
import wt.inf.container.WTContainerHelper;
import wt.inf.container.WTContainerIdentity;
import wt.inf.container.WTContainerRef;
import wt.org.WTOrganization;
import wt.pdmlink.PDMLinkProduct;
import wt.services.ServiceFactory;
import wt.session.SessionServerHelper;
import wt.units.service.QuantityOfMeasureDefaultView;
import wt.units.service.UnitsService;
import wt.util.WTException;
import wt.util.WTProperties;
import wt.util.WTPropertyVetoException;

/**
 * 常用操作 attr util
 *
 * @author anzhen
 * @date 2024/02/29
 */
public class AttributeOperationHelper {
    public static final Logger logger = LoggerFactory.getLogger(AttributeOperationHelper.class);
    /**
     * 基本 def 服务
     */
    private static final BaseDefinitionService BASE_DEF_SERVICE;

    /**
     * 类型 def service
     */
    private static final TypeDefinitionService TYPE_DEF_SERVICE;

    /**
     * IBA DEF 服务
     */
    private static final IBADefinitionService IBA_DEF_SERVICE;

    /**
     * 单位服务
     */
    private static final UnitsService UNITS_SERVICE;

    /**
     * iba def 缓存
     */
    private static final IBADefinitionCache IBA_DEF_CACHE;

    /**
     * 自动将单值约束添加到新全局属性
     */
    private static final boolean AUTO_ADD_SINGLE_VALUE_CONSTRAINT_TO_NEW_GLOBAL_ATT;

    static {
        TYPE_DEF_SERVICE = TypeDefinitionServiceHelper.service;
        BASE_DEF_SERVICE = (BaseDefinitionService) ServiceFactory.getService(BaseDefinitionService.class);
        IBA_DEF_SERVICE = (IBADefinitionService) ServiceFactory.getService(IBADefinitionService.class);
        IBA_DEF_CACHE = IBADefinitionCache.getIBADefinitionCache();
        UNITS_SERVICE = (UnitsService) ServiceFactory.getService(UnitsService.class);
        boolean falg = true;
        try {
            falg = WTProperties.getServerProperties().getProperty("com.ptc.core.lwc.autoAddSingleValuedConstraint", falg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 是否全局约束
        AUTO_ADD_SINGLE_VALUE_CONSTRAINT_TO_NEW_GLOBAL_ATT = falg;
    }

    /**
     * 主要
     *
     * @param args 参数
     * @throws WTException WT异常
     */
    public static void main(String[] args) throws WTException {
        createReusableAttribute("testCreateAttr", null, "wt.iba.definition.StringDefinition", "test", "测试创建属性", "OR:wt.iba.definition.AttributeOrganizer:156004", null);
        // TODO
        // CreateAttributeFormProcessor 创建属性关联 完成
        // NewConstraintFormProcessor 创建枚举关联 完成
        // CreateEntryFormProcessor 创建枚举关联根枚举 完成
    }

    /**
     * 创建 可重用属性
     *
     * @param internalName      内部名称 -> 字符串
     * @param logicalIdentifier 逻辑标识符 -> 字符串
     * @param dataType          数据类型 -> 内部名称
     * @param description       描述 -> 字符串
     * @param displayName       显示名称 -> 字符串
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
                reusableAttribute.getId();
                // 通过id获取属性视图
                // BASE_DEF_SERVICE.getReusableAttributeReadView()
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
        ReusableAttributeWriteView attributeWriteView = new ReusableAttributeWriteView(internalName, containerRef, parentIdentifier, (Collection) null, dataType, orgName, (Map) null, false, (ReadViewIdentifier) null);
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
     * 创建属性值读取视图 TODO 等待处理
     *
     * @param objectIdentifier 对象标识符
     * @param referenceClass   引用类
     * @param qtyOfMeasure     测量数量
     * @return {@link PropertyValueReadView}
     */
    private static PropertyValueReadView createPropertyValueReadView(ObjectIdentifier objectIdentifier, String referenceClass, String qtyOfMeasure) {
        PropertyDefinitionReadView readView = new PropertyDefinitionReadView(AbstractAttributeDefinition.class.getName(), referenceClass, String.class.getName());
        PropertyValueReadView propertyValueReadView = new PropertyValueReadView(objectIdentifier, readView, qtyOfMeasure, (Map) null, (ObjectIdentifier) null, false, (ReadViewIdentifier) null, false);
        return propertyValueReadView;
    }


//    /**
//     * TODO: 通过分类属性直接创建即可
//     *
//     * @param ibaInnerName
//     * @param lwcStructTemplateName
//     * @return
//     * @throws WTException
//     */
//    public static AttributeDefinitionReadView createAttributeDefinition(String ibaInnerName, String lwcStructTemplateName) throws WTException {
//        // 查询两个节点 直接读取即可
//        return null;
//    }


    /**
     * 创建分类属性和IBA属性映射
     *
     * @param innerName        内部名称
     * @param lwcDisplayName   LWC 显示名称
     * @param lwcDescription   LWC 描述
     * @param ibaSelectAttrOid 选择的可重用属性
     * @param classifyAttrOid  需要映射的分类属性
     * @return {@link AttributeDefinitionReadView}
     * @throws WTException WT异常
     */
    public static TypeDefinitionReadView createAttributeDefinitionAndConstraint(String innerName, String lwcDisplayName, String lwcDescription, String ibaSelectAttrOid, String classifyAttrOid) throws WTException, RemoteException {
        // IBA 属性可以自己去取def
        System.out.println("CommonOperationAttrUtil.createAttributeDefinition");
        System.out.println("innerName = " + innerName + ", lwcDisplayName = " + lwcDisplayName + ", lwcDescription = " + lwcDescription + ", ibaSelectAttrOid = " + ibaSelectAttrOid + ", classifyAttrOid = " + classifyAttrOid);
        String selectIbaClassTypeName = null;
        String ibaInternalName = LWCCommands.getIbaInternalName(ibaSelectAttrOid);
        NmOid nmOid = NmOid.newNmOid(classifyAttrOid);
        ObjectIdentifier identifier = nmOid.getOidObject();
        AttributeTemplateFlavor attributeType = AttributeTemplateFlavorHelper.getFlavor(nmOid);
        if (attributeType.equals(AttributeTemplateFlavor.LWCSTRUCT)) {
            if (innerName == null || innerName.isEmpty()) {
                innerName = "csmAttr_" + System.currentTimeMillis();
            }
            selectIbaClassTypeName = LWCIBAAttDefinition.class.getName();
        }
        System.out.println("attributeType = " + attributeType);
        String ibaDataTypeName;
        DatatypeReadView readView = null;
        // 判断类型是否包含文本翻译类型
        boolean flag = selectIbaClassTypeName.contains("LWCTranslatedTextAttDefinition");
        if (selectIbaClassTypeName.contains("LWCIBAAttDefinition")) {
            ibaDataTypeName = LWCCommands.getIbaDatatype(ibaSelectAttrOid);
            readView = BASE_DEF_SERVICE.getDatatypeView(ibaDataTypeName);
        } else if (flag) {
            ibaDataTypeName = String.class.getName();
            readView = BASE_DEF_SERVICE.getDatatypeView(ibaDataTypeName);
        } else {
            // 没有进入
            ibaDataTypeName = "";
        }
        if (selectIbaClassTypeName.contains("calculated")) {
            if (ibaDataTypeName.contains("AttributeTypeIdentifierSet")) {
                selectIbaClassTypeName = "com.ptc.core.lwc.server.LWCAttributeSetAttDefinition";
            } else {
                selectIbaClassTypeName = "com.ptc.core.lwc.server.LWCNonPersistedAttDefinition";
            }
        }
        System.out.println("selectIbaClassTypeName = " + selectIbaClassTypeName);
        AttributeDefDefaultView defDefaultView = null;
        String unitDefinitionClassName = null;
        if (selectIbaClassTypeName.contains("LWCIBAAttDefinition")) {
            try {
                // 通过路径查找属性
                defDefaultView = IBA_DEF_SERVICE.getAttributeDefDefaultViewByPath(ibaInternalName);
                if (defDefaultView == null) {
                    throw new WTException("Error retrieving IBA for \"" + ibaInternalName + "\"");
                }
            } catch (Exception e) {
                throw new WTException(e, "Error retrieving IBA for \"" + ibaInternalName + "\"");
            }
            AttributeDefinition definition = IBA_DEF_CACHE.getAttributeDefinition(ibaInternalName);
            if (definition instanceof UnitDefinition) {
                unitDefinitionClassName = ((UnitDefinition) definition).getQuantityOfMeasure().getName();
            } else {
                unitDefinitionClassName = null;
            }
        }
        System.out.println("unitDefinitionClassName = " + unitDefinitionClassName);
        QuantityOfMeasureDefaultView quantityOfMeasureDefaultView = null;
        if (selectIbaClassTypeName.contains("LWCIBAAttDefinition") && unitDefinitionClassName != null) {
            try {
                quantityOfMeasureDefaultView = UNITS_SERVICE.getQuantityOfMeasureDefaultView(unitDefinitionClassName);
            } catch (Exception e) {
                throw new WTException(e, "Error retrieving QoM for \"" + unitDefinitionClassName + "\"");
            }
            if (quantityOfMeasureDefaultView == null) {
                throw new WTException("Error retrieving QoM for \"" + unitDefinitionClassName + "\"");
            }
        }

        TypeDefinitionReadView typeDefReadView = TYPE_DEF_SERVICE.getTypeDefView(AttributeTemplateFlavorHelper.getFlavor(nmOid), identifier.getId());
        TypeDefinitionWriteView typeDefWriteView = typeDefReadView.getWritableView();
        // 判断是否存在当前视图
        AttributeDefinitionWriteView attrDefWriteView = new AttributeDefinitionWriteView(selectIbaClassTypeName,
                innerName, readView,
                defDefaultView,
                quantityOfMeasureDefaultView,
                (DisplayStyleReadView) null,
                (DisplayStyleReadView) null, (Collection) null, false, (Collection) null);
        System.out.println("attrDefWriteView = " + attrDefWriteView);
        attrDefWriteView.setTypeDefId(identifier);
        if (AUTO_ADD_SINGLE_VALUE_CONSTRAINT_TO_NEW_GLOBAL_ATT && defDefaultView != null) {
            ConstraintDefinitionWriteView singleValuedConstraint = addSingleValuedConstraint(attrDefWriteView);
            attrDefWriteView.setConstraintDefinition(singleValuedConstraint);
        }

        Set allPropertyDefViews = TYPE_DEF_SERVICE.getAllPropertyDefViews(selectIbaClassTypeName, typeDefReadView.getReadViewIdentifier(), readView);
        // 添加默认的属性
        if (allPropertyDefViews != null && !allPropertyDefViews.isEmpty()) {
            for (Object allPropertyDefView : allPropertyDefViews) {
                PropertyDefinitionReadView propertyDefReadView = (PropertyDefinitionReadView) allPropertyDefView;
                String propertyDefReadViewName = propertyDefReadView.getName();
                String classifyName = "lwc_" + propertyDefReadViewName;
                // 更新属性值数据
                System.out.println("classifyName = " + classifyName);
                if (propertyDefReadViewName.equals("displayName") || propertyDefReadViewName.equals("description")) {
                    String valueData;
                    if (propertyDefReadViewName.equals("displayName")) {
                        valueData = lwcDisplayName;
                    } else {
                        valueData = lwcDescription;
                    }
                    boolean isUpdateSuccess = PropertyDefinitionHelper.updatePropertyValue(propertyDefReadView, (ReadViewIdentifier) null, (PropertyValueWriteView) null, valueData, (Map) null, false);
                    if (isUpdateSuccess) {
                        PropertyValueWriteView propertyValueWriteView = new PropertyValueWriteView((ObjectIdentifier) null, propertyDefReadView, valueData, (Map) null, identifier, false, (ReadViewIdentifier) null, false);
                        attrDefWriteView.setProperty(propertyValueWriteView);
                    }
                }
            }
        }
        // 类型设置属性
        typeDefWriteView.setAttribute(attrDefWriteView);
        // 更新类型
        typeDefReadView = TYPE_DEF_SERVICE.updateTypeDef(typeDefWriteView);

        // 重新创建一个枚举的分类
        AttributeDefinitionReadView readViewAttributeByName = typeDefReadView.getAttributeByName(attrDefWriteView.getName());
        System.out.println("readViewAttributeByName = " + readViewAttributeByName);
        // 26537L 固定为枚举值
        AttributeDefinitionWriteView writableView = readViewAttributeByName.getWritableView();
        typeDefWriteView = typeDefReadView.getWritableView();
        // 默认设置枚举
        ConstraintDefinitionWriteView enumConstraint = createConstraintsWriteView(writableView.getName(),
                typeDefWriteView.getReadViewIdentifier(), 29611L);
        writableView.setConstraintDefinition(enumConstraint);
        IBA_DEF_SERVICE.updateAttributeDefinition(writableView.getIBARefView());
        System.out.println("更新完成");
        return typeDefReadView;
    }

    /**
     * 添加单值约束
     *
     * @param attributeDefView 属性定义视图
     * @throws WTException WT异常
     */
    private static ConstraintDefinitionWriteView addSingleValuedConstraint(AttributeDefinitionWriteView attributeDefView) throws WTException {
        // 设置单值 基本规则 约束
        ConstraintRuleDefinitionReadView readView = BASE_DEF_SERVICE.getConstraintRuleDefView(SingleValuedConstraint.class.getName(),
                LWCBasicConstraint.class.getName(),
                attributeDefView.getDatatype().getId(),
                (String) null);
        return new ConstraintDefinitionWriteView(readView,
                (ConstraintDefinitionReadView.RuleDataObject) null,
                attributeDefView.getName(),
                (Collection) null,
                attributeDefView.getTypeDefId(),
                (String) null);

    }

    /**
     * 创建约束
     * 创建枚举值
     * //
     * http://win-fv1tfp5mpk5.ziang.com/Windchill/ptc1/comp/csm.lwc.type.attribute.info?
     * // lwcMode=edit&u8=1&
     * //
     * <p>
     * lwcrv=-com.ptc.core.lwc.server.LWCStructEnumAttTemplate%253A112108-com.ptc.core.lwc.server.LWCIBAAttDefinition%253A116903
     * // &containerRef=OR%3Awt.inf.container.ExchangeContainer%3A5
     * // &setActiveLayout=contentPanel_twoPanes_typeadmin&portlet=component
     * // &wt.reqGrp=nzupiwl%3Bltfgos8r%3B4308%3Buv2bdi%3B2399
     * // &oid=OR%3Acom.ptc.core.lwc.server.LWCIBAAttDefinition%3A116903
     *
     * @param lwcrv  LWCRV型
     * @param typeId 类型 ID
     * @throws WTException WT异常
     */
    public static ConstraintDefinitionWriteView createConstraint(String lwcrv, Long typeId) throws WTException {
        if (StrUtil.isBlank(lwcrv)) {
            throw new WTException("cannot get attributeRv parameter from command bean");
        } else {
            System.out.println("lwcrv = " + lwcrv);
            ReadView readView = LWCCommands.getReadViewObject(lwcrv);
            // com.ptc.core.lwc.server.LWCIBAAttDefinition:126007
            System.out.println("readView.getOid() = " + readView.getOid());
            if (readView != null && readView instanceof AttributeDefinitionReadView) {
                // 获取抽象读取视图
                AttributeDefinitionReadView attributeDefinitionReadView = (AttributeDefinitionReadView) readView;
                ReadViewIdentifier readViewIdentifier = ReadViewIdentifier.fromEncodedString(lwcrv);
                System.out.println("readViewIdentifier.getOid() = " + readViewIdentifier.getOid());
                if (readViewIdentifier.getRootContextIdentifier() == null) {
                    throw new WTException("cannot get root context identifier from read view " + lwcrv);
                } else {
                    ObjectIdentifier objectIdentifier = readViewIdentifier.getRootContextIdentifier().getOid();
                    System.out.println("objectIdentifier.getStringValue() = " + objectIdentifier.getStringValue());
                    HashMap map = new HashMap(1);
                    map.put("inflatorMode", "newConstraint");
                    // 创建默认读取规则
                    ConstraintRuleDefinitionReadView constraintRuleReadView = BASE_DEF_SERVICE.getConstraintRuleDefView(typeId);
                    if (constraintRuleReadView == null) {
                        throw new WTException("unable to get constraint rule for id " + typeId);
                    }
                    // 获取分隔符
                    ReadViewIdentifier newConstraintReadViewId = NewConstraintInflator.createUniqueFakeRvId(SeparatorReadView.class, readViewIdentifier.getRootContextIdentifier());
                    System.out.println("newConstraintReadViewId = " + newConstraintReadViewId);
                    map.put("constraintRvId", newConstraintReadViewId.toEncodedString());
                    constraintRuleReadView = BASE_DEF_SERVICE.getConstraintRuleDefView(typeId);
                    System.out.println("constraintRuleReadView.getDatatype() = " + constraintRuleReadView.getDatatype());
                    System.out.println("constraintRuleReadView.getRuleClassname() = " + constraintRuleReadView.getRuleClassname());
                    ConstraintDefinitionWriteView writeView = new ConstraintDefinitionWriteView(constraintRuleReadView, (ConstraintDefinitionReadView.RuleDataObject) null,
                            attributeDefinitionReadView.getName(),
                            (Collection) null,
                            (Set) null,
                            (ConstraintDefinitionReadView) null,
                            false, objectIdentifier,
                            newConstraintReadViewId,
                            (String) null,
                            false);
                    // 判断是否是枚举类型
                    System.out.println("EnumerationConstraintsHelper.isClassificationRule(constraintRuleReadView) = " + EnumerationConstraintsHelper.isClassificationRule(constraintRuleReadView));
                    if (EnumerationConstraintsHelper.isClassificationRule(constraintRuleReadView)) {
                        DiscreteSet discreteSet = new DiscreteSet(new Object[]{"com.ptc.core.lwc.common.dynamicEnum.provider.ClassificationEnumerationInfoProvider"});
                        writeView.setRuleDataObj(new ConstraintDefinitionReadView.RuleDataObject(discreteSet));
                    }
                    AttributeDefinitionWriteView writableView = attributeDefinitionReadView.getWritableView();
                    writableView.setConstraintDefinition(writeView);

                    return writeView;
                }
            } else {
                throw new WTException("cannot get attribute read view from " + lwcrv);
            }
        }
    }

    /**
     * 创建约束写入视图
     *
     * @param name               名字
     * @param typeDefWriteViewId 类型 def write view id
     * @param typeId             类型 ID
     * @return {@link ConstraintDefinitionWriteView }
     * @throws WTException WTException
     */
    public static ConstraintDefinitionWriteView createConstraintsWriteView(String name, ReadViewIdentifier typeDefWriteViewId, Long typeId) throws WTException {
        ObjectIdentifier objectIdentifier = typeDefWriteViewId.getOid();
        System.out.println("objectIdentifier.getStringValue() = " + objectIdentifier.getStringValue());
        ConstraintRuleDefinitionReadView constraintRuleReadView = BASE_DEF_SERVICE.getConstraintRuleDefView(typeId);
        System.out.println("constraintRuleReadView.getDatatype() = " + constraintRuleReadView.getDatatype());
        System.out.println("constraintRuleReadView.getOid() = " + constraintRuleReadView.getOid());
        if (constraintRuleReadView == null) {
            throw new WTException("unable to get constraint rule for id " + typeId);
        }

        // 获取分隔符
        ReadViewIdentifier newConstraintReadViewId = NewConstraintInflator.createUniqueFakeRvId(SeparatorReadView.class, typeDefWriteViewId);
        System.out.println("newConstraintReadViewId = " + newConstraintReadViewId);
        constraintRuleReadView = BASE_DEF_SERVICE.getConstraintRuleDefView(typeId);
        System.out.println("constraintRuleReadView.getDatatype() = " + constraintRuleReadView.getDatatype());
        System.out.println("constraintRuleReadView.getRuleClassname() = " + constraintRuleReadView.getRuleClassname());
        return new ConstraintDefinitionWriteView(constraintRuleReadView,
                (ConstraintDefinitionReadView.RuleDataObject) null,
                name, (Collection) null, (Set) null,
                (ConstraintDefinitionReadView) null, false,
                objectIdentifier,
                newConstraintReadViewId, (String) null,
                false);
    }

    /**
     * 更改编号
     * 可持续化doc的编码修改
     *
     * @param doc    文档对象
     * @param number 数
     * @return {@link WTDocument}
     * @throws WTException WT异常
     */
    private synchronized WTDocument changeNumber(WTDocument doc, String number) throws WTException {
        boolean accessEnforced = SessionServerHelper.manager.isAccessEnforced();
        System.out.println("accessEnforced = " + accessEnforced);
        // 设置权限可访问
        boolean access = SessionServerHelper.manager.setAccessEnforced(false);
        try {
            WTDocumentMaster master = (WTDocumentMaster) doc.getMaster();
            WTDocumentMasterIdentity idobj = (WTDocumentMasterIdentity) master.getIdentificationObject();
            idobj.setNumber(number);
            PDMLinkProduct pdmLinkProduct = PDMLinkProduct.newPDMLinkProduct();
            WTContainerIdentity identificationObject = (WTContainerIdentity) pdmLinkProduct.getIdentificationObject();
            identificationObject.setName("111");
            IdentityHelper.service.changeIdentity(pdmLinkProduct, identificationObject);
            doc = (WTDocument) PersistenceHelper.manager.refresh(doc);
        } catch (WTPropertyVetoException e) {
            e.printStackTrace();
        } finally {
            // 恢复权限
            SessionServerHelper.manager.setAccessEnforced(access);
        }
        return doc;
    }

    /**
     * @param classificationInnerName 分类内部值
     * @Decription:根据分类内部名称获取分类属性
     * @return: java.util.Map<java.lang.String, java.lang.String>
     * @Date: 2023/7/19 9:01
     * @Author:anzhen
     * @Version:1.0
     */
    public static Map<String, String> findClassificationAttrs(String classificationInnerName) {
        try {
            TypeDefinitionReadView classificationTypeDefView = CSMTypeDefHelper.getClassificationTypeDefView(classificationInnerName);
            if (classificationTypeDefView == null) {
                return Collections.emptyMap();
            }
            Collection<AttributeDefinitionReadView> allAttributes = classificationTypeDefView.getAllAttributes();
            if (allAttributes.isEmpty()) {
                return Collections.emptyMap();
            }
            Map<String, String> attributeDescriptionMap = Maps.newHashMap();
            // 获取分类属性和相关描述
            for (AttributeDefinitionReadView attribute : allAttributes) {
                System.out.println("attribute = " + attribute.getName());
                PropertyValueReadView propertyValueByName = attribute.getPropertyValueByName(PropertyDefinitionConstants.DESCRIPTION_PROPERTY);
                String description = "";
                if (java.util.Objects.nonNull(propertyValueByName)) {
                    description = propertyValueByName.getValueAsString();
                }
                attributeDescriptionMap.put(attribute.getName(), description);
                // 获取所有的规则
                Collection<ConstraintDefinitionReadView> allConstraints = attribute.getAllConstraints();
                // 获取所有的条件规则
                for (ConstraintDefinitionReadView constraintDefinitionReadView : allConstraints) {
                    // 获取规则对象
                    ConstraintDefinitionReadView.RuleDataObject ruleDataObj = constraintDefinitionReadView.getRuleDataObj();
                    // 如果为空则表示是基本规则
                    if (ruleDataObj != null) {
                        System.out.println("ruleDataObj = " + ruleDataObj);
                        System.out.println("ruleDataObj.getRuleData() = " + ruleDataObj.getRuleData());
                        // 获取枚举值查看是否存在
                        if (ruleDataObj.getEnumDef() != null) {
                            System.out.println("ruleDataObj.getEnumDef() = " + ruleDataObj.getEnumDef());
                            EnumerationDefinitionReadView enumDef = ruleDataObj.getEnumDef();
                            Map<String, EnumerationEntryReadView> allEnumerationEntries = enumDef.getAllEnumerationEntries();
                            if (!allEnumerationEntries.isEmpty()) {
                                for (Map.Entry<String, EnumerationEntryReadView> entry : allEnumerationEntries.entrySet()) {
                                    String key = entry.getKey();
                                    System.out.println("key = " + key);
                                    System.out.println("entry.getValue() = " + entry.getValue());
                                    EnumerationEntryReadView value = entry.getValue();
                                    PropertyValueReadView entryValueView = value.getPropertyValueByName(PropertyDefinitionConstants.DISPLAY_NAME_PROPERTY);
                                    if (Objects.nonNull(entryValueView)) {
                                        System.out.println("entryValueView.getValueAsString() = " + entryValueView.getValueAsString());
                                    }
                                }
                            }
                            System.out.println("enumDef.getName() = " + enumDef.getName());
                        }
                    }
                }
            }
            return attributeDescriptionMap;
        } catch (Exception e) {
            logger.error("根据物料分类，查询分类特征属性异常，物料分类:{}", classificationInnerName, e);
        }
        return Collections.emptyMap();
    }

}
