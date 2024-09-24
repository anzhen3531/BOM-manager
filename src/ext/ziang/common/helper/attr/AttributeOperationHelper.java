package ext.ziang.common.helper.attr;

import com.google.gwt.thirdparty.guava.common.collect.Maps;
import com.ptc.core.lwc.client.commands.LWCCommands;
import com.ptc.core.lwc.client.util.PropertyDefinitionHelper;
import com.ptc.core.lwc.common.*;
import com.ptc.core.lwc.common.view.*;
import com.ptc.core.lwc.server.LWCBasicConstraint;
import com.ptc.core.lwc.server.LWCIBAAttDefinition;
import com.ptc.core.lwc.server.TypeDefinitionServiceHelper;
import com.ptc.core.meta.common.CorrectableException;
import com.ptc.core.meta.container.common.impl.SingleValuedConstraint;
import com.ptc.windchill.csm.client.helpers.CSMTypeDefHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wt.fc.ObjectIdentifier;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.iba.definition.*;
import wt.iba.definition.litedefinition.AttributeDefDefaultView;
import wt.iba.definition.service.IBADefinitionCache;
import wt.iba.definition.service.IBADefinitionService;
import wt.inf.container.WTContainerHelper;
import wt.inf.container.WTContainerRef;
import wt.org.WTOrganization;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.ServiceFactory;
import wt.units.service.QuantityOfMeasureDefaultView;
import wt.units.service.UnitsService;
import wt.util.WTException;
import wt.util.WTProperties;

import java.io.IOException;
import java.util.*;

/**
 * 常用操作 attr util
 * <p/>
 * 创建分类节点源码 CreateTypeFormProcessor
 * <p/>
 * 创建分类属性源码
 * <p/>
 * 创建枚举源码
 *
 * @author anzhen
 * @date 2024/02/29
 */
public class AttributeOperationHelper {
    public static final Logger logger = LoggerFactory.getLogger(AttributeOperationHelper.class);
    /**
     * 基本 def 服务
     */
    public static final BaseDefinitionService BASE_DEF_SERVICE;

    /**
     * 类型 def service
     */
    public static final TypeDefinitionService TYPE_DEF_SERVICE;

    /**
     * IBA DEF 服务
     */
    public static final IBADefinitionService IBA_DEF_SERVICE;

    /**
     * 单位服务
     */
    public static final UnitsService UNITS_SERVICE;

    /**
     * iba def 缓存
     */
    public static final IBADefinitionCache IBA_DEF_CACHE;

    /**
     * 自动将单值约束添加到新全局属性
     */
    private static final boolean AUTO_ADD_SINGLE_VALUE_CONSTRAINT_TO_NEW_GLOBAL_ATT;

    static {
        TYPE_DEF_SERVICE = TypeDefinitionServiceHelper.service;
        BASE_DEF_SERVICE = (BaseDefinitionService)ServiceFactory.getService(BaseDefinitionService.class);
        IBA_DEF_SERVICE = (IBADefinitionService)ServiceFactory.getService(IBADefinitionService.class);
        IBA_DEF_CACHE = IBADefinitionCache.getIBADefinitionCache();
        UNITS_SERVICE = (UnitsService)ServiceFactory.getService(UnitsService.class);
        boolean falg = true;
        try {
            falg =
                WTProperties.getServerProperties().getProperty("com.ptc.core.lwc.autoAddSingleValuedConstraint", falg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 是否全局约束
        AUTO_ADD_SINGLE_VALUE_CONSTRAINT_TO_NEW_GLOBAL_ATT = falg;
    }

    /**
     * 创建 可重用属性
     *
     * @param internalName 内部名称 -> 字符串
     * @param logicalIdentifier 逻辑标识符 -> 字符串
     * @param dataType 数据类型 -> 内部名称
     * @param description 描述 -> 字符串
     * @param displayName 显示名称 -> 字符串
     * @param oid oid -> 父对象oid
     * @param qtyOfMeasure 测量数量 -> 当数据类型为：测量单位时
     * @return {@link ReusableAttributeReadView}
     * @throws WTException WT异常
     */
    public static ReusableAttributeReadView createReusableAttribute(String internalName, String logicalIdentifier,
        String dataType, String description, String displayName, String oid, String qtyOfMeasure) throws WTException {
        ReusableAttributeReadView reusableAttributeReadView;
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
                if (StringUtils.isBlank(qtyOfMeasure)) {
                    qtyOfMeasure = "Acceleration";
                }
            }

            String orgName = null;
            if (ReferenceDefinition.class.getName().equals(dataType)) {
                orgName = WTOrganization.class.getName();
            }

            // 获取父级对象oid 父级对象类型 AttributeOrganizer 可以编写高级查询查询
            ObjectIdentifier parentIdentifier = OidHelper.getOid(oid);
            ReusableAttributeWriteView reusableAttribute = createReusableAttribute(internalName, dataType, qtyOfMeasure,
                orgName, description, displayName, internalName, logicalIdentifier, parentIdentifier);
            try {
                reusableAttributeReadView = BASE_DEF_SERVICE.createReusableAttribute(reusableAttribute);
                return reusableAttributeReadView;
            } catch (IBADefinitionException exception) {
                logger.error("createReusableAttribute ", exception);
                throw new WTException(exception);
            }
        }
    }

    /**
     * 创建可重用属性
     *
     * @param internalName 内部名称
     * @param dataType 数据类型
     * @param description 描述
     * @param displayName 显示名称
     * @param internalName1 内部名称1
     * @param logicalIdentifier 逻辑标识符
     * @param parentIdentifier 父标识符
     * @param qtyOfMeasure 测量数量
     * @param orgName 组织名称
     * @return {@link ReusableAttributeWriteView}
     * @throws WTException WT异常
     */
    private static ReusableAttributeWriteView createReusableAttribute(String internalName, String dataType,
        String qtyOfMeasure, String orgName, String description, String displayName, String internalName1,
        String logicalIdentifier, ObjectIdentifier parentIdentifier) throws WTException {
        WTContainerRef containerRef = WTContainerHelper.service.getExchangeRef();
        ReusableAttributeWriteView attributeWriteView = new ReusableAttributeWriteView(internalName, containerRef,
            parentIdentifier, (Collection)null, dataType, orgName, (Map)null, false, (ReadViewIdentifier)null);
        ObjectIdentifier objectIdentifier = attributeWriteView.getOid();
        attributeWriteView.setProperty(createPropertyValueReadView(objectIdentifier, "internalName", internalName));
        attributeWriteView
            .setProperty(createPropertyValueReadView(objectIdentifier, "hierarchyDisplayName", internalName1));
        attributeWriteView.setProperty(createPropertyValueReadView(objectIdentifier, "displayName", displayName));
        attributeWriteView
            .setProperty(createPropertyValueReadView(objectIdentifier, "logicalIdentifier", logicalIdentifier));
        attributeWriteView.setProperty(createPropertyValueReadView(objectIdentifier, "description", description));
        attributeWriteView.setProperty(createPropertyValueReadView(objectIdentifier, "referenceClass", qtyOfMeasure));
        return attributeWriteView;
    }

    /**
     * 创建属性值读取视图
     *
     * @param objectIdentifier 对象标识符
     * @param referenceClass 引用类
     * @param qtyOfMeasure 测量数量
     * @return {@link PropertyValueReadView}
     */
    private static PropertyValueReadView createPropertyValueReadView(ObjectIdentifier objectIdentifier,
        String referenceClass, String qtyOfMeasure) {
        PropertyDefinitionReadView readView = new PropertyDefinitionReadView(
            AbstractAttributeDefinition.class.getName(), referenceClass, String.class.getName());
        PropertyValueReadView propertyValueReadView = new PropertyValueReadView(objectIdentifier, readView,
            qtyOfMeasure, (Map)null, (ObjectIdentifier)null, false, (ReadViewIdentifier)null, false);
        return propertyValueReadView;
    }

    /**
     * 创建分类属性和IBA属性映射
     *
     * @param attrInnerName attr 内部名称
     * @param lwcDisplayName LWC 显示名称
     * @param lwcDescription LWC 描述
     * @param classifyInnerName 分类内部名称
     * @return {@link AttributeDefinitionReadView}
     * @throws WTException wtexception
     */
    public static AttributeDefinitionWriteView createAttributeDefinitionView(String attrInnerName,
        String lwcDisplayName, String lwcDescription, String classifyInnerName) throws WTException {
        AbstractAttributeDefinition attributeDefinition = findAttributeDefinition(attrInnerName);
        if (Objects.isNull(attributeDefinition)) {
            throw new WTException("当前全局IBA属性不存在，请创建当前属性");
        }
        TypeDefinitionReadView typeDefView = CSMTypeDefHelper.getClassificationTypeDefView(classifyInnerName);
        if (Objects.isNull(typeDefView)) {
            throw new WTException("当前分类节点不存在，请创建当前分类节点");
        }
        ObjectIdentifier identifier = typeDefView.getOid();
        String selectIbaClassTypeName = null;
        String ibaInternalName = attributeDefinition.getName();
        AttributeTemplateFlavor attributeType = typeDefView.getAttTemplateFlavor();
        logger.info("attributeType {}", attributeType);
        if (AttributeTemplateFlavor.LWCSTRUCT.equals(attributeType)) {
            selectIbaClassTypeName = LWCIBAAttDefinition.class.getName();
        }
        String ibaDataTypeName;
        DatatypeReadView readView = null;
        // 判断类型是否包含文本翻译类型
        boolean flag = selectIbaClassTypeName.contains("LWCTranslatedTextAttDefinition");
        if (selectIbaClassTypeName.contains("LWCIBAAttDefinition")) {
            // 获取IBA数据类型
            ibaDataTypeName =
                LWCCommands.getIbaDatatype(attributeDefinition.getPersistInfo().getObjectIdentifier().toString());
            readView = BASE_DEF_SERVICE.getDatatypeView(attributeDefinition.getName());
        } else if (flag) {
            // 判断是否是文本翻译属性
            ibaDataTypeName = String.class.getName();
            readView = BASE_DEF_SERVICE.getDatatypeView(ibaDataTypeName);
        } else {
            // 没有进入
            ibaDataTypeName = "";
        }
        // 是否是计算属性
        if (selectIbaClassTypeName.contains("calculated")) {
            if (ibaDataTypeName.contains("AttributeTypeIdentifierSet")) {
                selectIbaClassTypeName = "com.ptc.core.lwc.server.LWCAttributeSetAttDefinition";
            } else {
                selectIbaClassTypeName = "com.ptc.core.lwc.server.LWCNonPersistedAttDefinition";
            }
        }
        logger.info("selectIbaClassTypeName = {}", selectIbaClassTypeName);
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
            // 判断IBA类型是否为单位类型
            AttributeDefinition definition = IBA_DEF_CACHE.getAttributeDefinition(ibaInternalName);
            if (definition instanceof UnitDefinition) {
                unitDefinitionClassName = ((UnitDefinition)definition).getQuantityOfMeasure().getName();
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
        // 创建属性写视图
        assert readView != null;
        AttributeDefinitionWriteView attrDefWriteView = new AttributeDefinitionWriteView(selectIbaClassTypeName,
            attrInnerName, readView, defDefaultView, quantityOfMeasureDefaultView, (DisplayStyleReadView)null,
            (DisplayStyleReadView)null, (Collection)null, false, (Collection)null);
        attrDefWriteView.setTypeDefId(identifier);
        if (AUTO_ADD_SINGLE_VALUE_CONSTRAINT_TO_NEW_GLOBAL_ATT && defDefaultView != null) {
            ConstraintDefinitionWriteView singleValuedConstraint = addSingleValuedConstraint(attrDefWriteView);
            attrDefWriteView.setConstraintDefinition(singleValuedConstraint);
            ConstraintDefinitionWriteView definitionWriteView =
                createConstraintsWriteView(attrInnerName, attrDefWriteView.getReadViewIdentifier(), 27329L);
            attrDefWriteView.setConstraintDefinition(definitionWriteView);
        }
        // 所有的属性定义视图
        Set allPropertyDefViews = TYPE_DEF_SERVICE.getAllPropertyDefViews(selectIbaClassTypeName,
            typeDefView.getReadViewIdentifier(), readView);
        // 添加默认的属性
        if (allPropertyDefViews != null && !allPropertyDefViews.isEmpty()) {
            for (Object allPropertyDefView : allPropertyDefViews) {
                PropertyDefinitionReadView propertyDefReadView = (PropertyDefinitionReadView)allPropertyDefView;
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
                    boolean isUpdateSuccess = PropertyDefinitionHelper.updatePropertyValue(propertyDefReadView,
                        (ReadViewIdentifier)null, (PropertyValueWriteView)null, valueData, (Map)null, false);
                    if (isUpdateSuccess) {
                        PropertyValueWriteView propertyValueWriteView =
                            new PropertyValueWriteView((ObjectIdentifier)null, propertyDefReadView, valueData,
                                (Map)null, identifier, false, (ReadViewIdentifier)null, false);
                        attrDefWriteView.setProperty(propertyValueWriteView);
                    }
                }
            }
        }
        return attrDefWriteView;
    }

    /**
     * 添加单值约束
     *
     * @param attributeDefView 属性定义视图
     * @throws WTException WT异常
     */
    private static ConstraintDefinitionWriteView
        addSingleValuedConstraint(AttributeDefinitionWriteView attributeDefView) throws WTException {
        // 设置单值 基本规则 约束
        ConstraintRuleDefinitionReadView readView =
            BASE_DEF_SERVICE.getConstraintRuleDefView(SingleValuedConstraint.class.getName(),
                LWCBasicConstraint.class.getName(), attributeDefView.getDatatype().getId(), (String)null);
        return new ConstraintDefinitionWriteView(readView, (ConstraintDefinitionReadView.RuleDataObject)null,
            attributeDefView.getName(), (Collection)null, attributeDefView.getTypeDefId(), (String)null);

    }

    /**
     * 添加枚举约束
     *
     * @param classifyName 分类名称
     * @param attrName 属性名称
     * @throws WTException 更新失败异常
     */
    public static void addEnumConstraints(String classifyName, String attrName) throws WTException {
        logger.info("classifyName:{}, attrName:{}", classifyName, attrName);
        TypeDefinitionReadView typeDefView = CSMTypeDefHelper.getClassificationTypeDefView(classifyName);
        TypeDefinitionWriteView writableView = typeDefView.getWritableView();
        Collection<AttributeDefinitionReadView> allAttributes = typeDefView.getAllAttributes();
        for (AttributeDefinitionReadView attributeDefinitionReadView : allAttributes) {
            if (attributeDefinitionReadView.getName().equals(attrName)) {
                ConstraintDefinitionWriteView constraintsWriteView =
                    createConstraintsWriteView(attrName, writableView.getReadViewIdentifier(), 27846L);
                AttributeDefinitionWriteView attributeDefinitionWriteView =
                    attributeDefinitionReadView.getWritableView();
                attributeDefinitionWriteView.setConstraintDefinition(constraintsWriteView);
                writableView.setAttribute(attributeDefinitionWriteView);
            }
        }
        TYPE_DEF_SERVICE.updateTypeDef(writableView);
    }

    /**
     * 创建枚举约束或者是其他约束 TODO 建议查询当前KEY来处理
     *
     * @param name 名字
     * @param typeDefWriteViewId 类型 def write view id
     * @param typeId 类型 ID
     * @return {@link ConstraintDefinitionWriteView }
     * @throws WTException WTException
     */
    public static ConstraintDefinitionWriteView createConstraintsWriteView(String name,
        ReadViewIdentifier typeDefWriteViewId, Long typeId) throws WTException {
        ObjectIdentifier objectIdentifier = typeDefWriteViewId.getOid();
        ConstraintRuleDefinitionReadView constraintRuleReadView = BASE_DEF_SERVICE.getConstraintRuleDefView(typeId);
        if (constraintRuleReadView == null) {
            throw new WTException("unable to get constraint rule for id " + typeId);
        }
        // 通过类型获取读取视图
        constraintRuleReadView = BASE_DEF_SERVICE.getConstraintRuleDefView(typeId);
        // 如果是全局枚举则使用这个
        return new ConstraintDefinitionWriteView(constraintRuleReadView,
            (ConstraintDefinitionReadView.RuleDataObject)null, name, (Collection)null, objectIdentifier, (String)null);
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
            TypeDefinitionReadView classificationTypeDefView =
                CSMTypeDefHelper.getClassificationTypeDefView(classificationInnerName);
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
                logger.info("attribute = {}", attribute.getName());
                PropertyValueReadView propertyValueByName =
                    attribute.getPropertyValueByName(PropertyDefinitionConstants.DESCRIPTION_PROPERTY);
                String description = "";
                if (Objects.nonNull(propertyValueByName)) {
                    description = propertyValueByName.getValueAsString();
                }
                attributeDescriptionMap.put(attribute.getName(), description);
                // 获取所有的规则
                Collection<ConstraintDefinitionReadView> allConstraints = attribute.getAllConstraints();
                // 获取所有的条件规则
                for (ConstraintDefinitionReadView constraintDefinitionReadView : allConstraints) {
                    // 获取规则对象
                    ConstraintDefinitionReadView.RuleDataObject ruleDataObj =
                        constraintDefinitionReadView.getRuleDataObj();
                    // 如果为空则表示是基本规则
                    if (ruleDataObj != null) {
                        logger.info("ruleDataObj = " + ruleDataObj);
                        logger.info("ruleDataObj.getRuleData() = " + ruleDataObj.getRuleData());
                        // 获取枚举值查看是否存在
                        if (ruleDataObj.getEnumDef() != null) {
                            logger.info("ruleDataObj.getEnumDef() = " + ruleDataObj.getEnumDef());
                            EnumerationDefinitionReadView enumDef = ruleDataObj.getEnumDef();
                            Map<String, EnumerationEntryReadView> allEnumerationEntries =
                                enumDef.getAllEnumerationEntries();
                            if (!allEnumerationEntries.isEmpty()) {
                                for (Map.Entry<String, EnumerationEntryReadView> entry : allEnumerationEntries
                                    .entrySet()) {
                                    String key = entry.getKey();
                                    logger.info("key = " + key);
                                    logger.info("entry.getValue() = " + entry.getValue());
                                    EnumerationEntryReadView value = entry.getValue();
                                    PropertyValueReadView entryValueView =
                                        value.getPropertyValueByName(PropertyDefinitionConstants.DISPLAY_NAME_PROPERTY);
                                    if (Objects.nonNull(entryValueView)) {
                                        logger.info(
                                            "entryValueView.getValueAsString() = " + entryValueView.getValueAsString());
                                    }
                                }
                            }
                            logger.info("enumDef.getName() = " + enumDef.getName());
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

    /**
     * 查询IBA属性
     *
     * @param attrName 属性内部名称
     * @return IBA属性
     * @throws WTException 查询失败
     */
    public static AbstractAttributeDefinition findAttributeDefinition(String attrName) throws WTException {
        QuerySpec querySpec = new QuerySpec(AbstractAttributeDefinition.class);
        querySpec.appendWhere(new SearchCondition(AbstractAttributeDefinition.class, AbstractAttributeDefinition.NAME,
            SearchCondition.EQUAL, attrName), new int[] {0});
        QueryResult queryResult = PersistenceHelper.manager.find(querySpec);
        if (queryResult.size() == 1) {
            return (AbstractAttributeDefinition)queryResult.nextElement();
        }
        return null;
    }
}
