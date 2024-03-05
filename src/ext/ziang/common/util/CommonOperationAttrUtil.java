package ext.ziang.common.util;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.ptc.core.lwc.client.commands.LWCCommands;
import com.ptc.core.lwc.client.util.AttributeTemplateFlavorHelper;
import com.ptc.core.lwc.client.util.PropertyDefinitionHelper;
import com.ptc.core.lwc.common.AttributeTemplateFlavor;
import com.ptc.core.lwc.common.BaseDefinitionService;
import com.ptc.core.lwc.common.OidHelper;
import com.ptc.core.lwc.common.TypeDefinitionService;
import com.ptc.core.lwc.common.view.AttributeDefinitionReadView;
import com.ptc.core.lwc.common.view.AttributeDefinitionWriteView;
import com.ptc.core.lwc.common.view.ConstraintDefinitionReadView;
import com.ptc.core.lwc.common.view.ConstraintDefinitionWriteView;
import com.ptc.core.lwc.common.view.ConstraintRuleDefinitionReadView;
import com.ptc.core.lwc.common.view.DatatypeReadView;
import com.ptc.core.lwc.common.view.DisplayStyleReadView;
import com.ptc.core.lwc.common.view.PropertyDefinitionReadView;
import com.ptc.core.lwc.common.view.PropertyValueReadView;
import com.ptc.core.lwc.common.view.PropertyValueWriteView;
import com.ptc.core.lwc.common.view.ReadViewIdentifier;
import com.ptc.core.lwc.common.view.ReusableAttributeReadView;
import com.ptc.core.lwc.common.view.ReusableAttributeWriteView;
import com.ptc.core.lwc.common.view.TypeDefinitionReadView;
import com.ptc.core.lwc.common.view.TypeDefinitionWriteView;
import com.ptc.core.lwc.server.LWCBasicConstraint;
import com.ptc.core.lwc.server.LWCIBAAttDefinition;
import com.ptc.core.lwc.server.TypeDefinitionServiceHelper;
import com.ptc.core.meta.common.CorrectableException;
import com.ptc.core.meta.common.DataTypesUtility;
import com.ptc.core.meta.container.common.impl.SingleValuedConstraint;
import com.ptc.netmarkets.model.NmOid;

import cn.hutool.core.util.StrUtil;
import wt.fc.ObjectIdentifier;
import wt.iba.definition.AbstractAttributeDefinition;
import wt.iba.definition.AttributeDefinition;
import wt.iba.definition.IBADefinitionException;
import wt.iba.definition.ReferenceDefinition;
import wt.iba.definition.UnitDefinition;
import wt.iba.definition.litedefinition.AttributeDefDefaultView;
import wt.iba.definition.service.IBADefinitionCache;
import wt.iba.definition.service.IBADefinitionService;
import wt.inf.container.WTContainerHelper;
import wt.inf.container.WTContainerRef;
import wt.org.WTOrganization;
import wt.services.ServiceFactory;
import wt.session.SessionHelper;
import wt.units.service.QuantityOfMeasureDefaultView;
import wt.units.service.UnitsService;
import wt.util.WTException;
import wt.util.WTProperties;

/**
 * 常用操作 attr util
 *
 * @author anzhen
 * @date 2024/02/29
 */
public class CommonOperationAttrUtil {
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
			falg = WTProperties.getServerProperties().getProperty("com.ptc.core.lwc.autoAddSingleValuedConstraint",
					falg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 是否全局约束
		AUTO_ADD_SINGLE_VALUE_CONSTRAINT_TO_NEW_GLOBAL_ATT = falg;
	}

	/**
	 * 主要
	 *
	 * @param args
	 *            参数
	 * @throws WTException
	 *             WT异常
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
		// TODO
		// CreateAttributeFormProcessor 创建属性关联
		// NewConstraintFormProcessor 创建枚举关联
		// CreateEntryFormProcessor 创建枚举关联根枚举
	}

	/**
	 * 创建 可重用属性
	 *
	 * @param internalName
	 *            内部名称 -> 字符串
	 * @param logicalIdentifier
	 *            逻辑标识符 -> 字符串
	 * @param dataType
	 *            数据类型 -> 内部名称
	 * @param description
	 *            描述 -> 字符串
	 * @param displayName
	 *            显示名称 -> 字符串
	 * @param oid
	 *            oid -> 父对象oid
	 * @param qtyOfMeasure
	 *            测量数量 -> 当数据类型为：测量单位时
	 * @return {@link ReusableAttributeReadView}
	 * @throws WTException
	 *             WT异常
	 */
	public static ReusableAttributeReadView createReusableAttribute(String internalName, String logicalIdentifier,
			String dataType, String description, String displayName, String oid, String qtyOfMeasure)
			throws WTException {
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
			ReusableAttributeWriteView reusableAttribute = createReusableAttribute(internalName, dataType, qtyOfMeasure,
					orgName, description, displayName, internalName, logicalIdentifier, parentIdentifier);
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
	 * @param internalName
	 *            内部名称
	 * @param dataType
	 *            数据类型
	 * @param description
	 *            描述
	 * @param displayName
	 *            显示名称
	 * @param internalName1
	 *            内部名称1
	 * @param logicalIdentifier
	 *            逻辑标识符
	 * @param parentIdentifier
	 *            父标识符
	 * @param qtyOfMeasure
	 *            测量数量
	 * @param orgName
	 *            组织名称
	 * @return {@link ReusableAttributeWriteView}
	 * @throws WTException
	 *             WT异常
	 */
	private static ReusableAttributeWriteView createReusableAttribute(String internalName, String dataType,
			String qtyOfMeasure, String orgName, String description, String displayName, String internalName1,
			String logicalIdentifier, ObjectIdentifier parentIdentifier) throws WTException {
		WTContainerRef containerRef = WTContainerHelper.service.getExchangeRef();
		ReusableAttributeWriteView attributeWriteView = new ReusableAttributeWriteView(internalName, containerRef,
				parentIdentifier, (Collection) null,
				dataType, orgName, (Map) null,
				false, (ReadViewIdentifier) null);
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
	 * 创建属性值读取视图 TODO 等待处理
	 *
	 *
	 * @param objectIdentifier
	 *            对象标识符
	 * @param referenceClass
	 *            引用类
	 * @param qtyOfMeasure
	 *            测量数量
	 * @return {@link PropertyValueReadView}
	 */
	private static PropertyValueReadView createPropertyValueReadView(ObjectIdentifier objectIdentifier,
			String referenceClass, String qtyOfMeasure) {
		PropertyDefinitionReadView readView = new PropertyDefinitionReadView(
				AbstractAttributeDefinition.class.getName(),
				referenceClass, String.class.getName());
		PropertyValueReadView propertyValueReadView = new PropertyValueReadView(objectIdentifier, readView,
				qtyOfMeasure, (Map) null, (ObjectIdentifier) null,
				false, (ReadViewIdentifier) null, false);
		return propertyValueReadView;
	}

	/**
	 * 创建属性定义
	 * 创建属性映射
	 *
	 * @param innerName
	 *            内部名称
	 * @param lwcDisplayName
	 *            LWC 显示名称
	 * @param lwcDescription
	 *            LWC 描述
	 * @param ibaSelectAttrOid
	 *            iba 选择 attr oid
	 * @param classifyAttrOid
	 *            分类 Attr OID
	 * @return {@link AttributeDefinitionReadView}
	 * @throws WTException
	 *             WT异常
	 */
	public static AttributeDefinitionReadView createAttributeDefinition(String innerName,
			String lwcDisplayName,
			String lwcDescription,
			String ibaSelectAttrOid, String classifyAttrOid)
			throws WTException {
		System.out.println("CommonOperationAttrUtil.createAttributeDefinition");
		System.out.println("innerName = " + innerName + ", lwcDisplayName = " + lwcDisplayName + ", lwcDescription = "
				+ lwcDescription + ", ibaSelectAttrOid = " + ibaSelectAttrOid + ", classifyAttrOid = "
				+ classifyAttrOid);
		String selectIbaClassTypeName = null;
		// 通过oid获取内部属性
		// OR:wt.iba.definition.UnitDefinition:112124
		String ibaInternalName = LWCCommands.getIbaInternalName(ibaSelectAttrOid);
		// value="OR:com.ptc.core.lwc.server.LWCStructEnumAttTemplate:112108"
		// 分类属性oid
		NmOid nmOid = NmOid.newNmOid(classifyAttrOid);
		ObjectIdentifier identifier = nmOid.getOidObject();
		AttributeTemplateFlavor attributeType = AttributeTemplateFlavorHelper.getFlavor(nmOid);
		if (attributeType.equals(AttributeTemplateFlavor.LWCSTRUCT)) {
			if (innerName == null || innerName.isEmpty()) {
				innerName = "csmAttr_" + System.currentTimeMillis();
			}
			// 类名
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
			// readView = BASE_DEF_SERVICE.getDatatypeView(ibaDataTypeName);
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
			} catch (Exception var34) {
				throw new WTException(var34, "Error retrieving QoM for \"" + unitDefinitionClassName + "\"");
			}
			if (quantityOfMeasureDefaultView == null) {
				throw new WTException("Error retrieving QoM for \"" + unitDefinitionClassName + "\"");
			}
		}

		TypeDefinitionReadView typeDefReadView = TYPE_DEF_SERVICE.getTypeDefView(
				AttributeTemplateFlavorHelper.getFlavor(nmOid),
				identifier.getId());
		TypeDefinitionWriteView typeDefWriteView = typeDefReadView.getWritableView();
		AttributeDefinitionWriteView attrDefWriteView = new AttributeDefinitionWriteView(selectIbaClassTypeName,
				innerName,
				readView, defDefaultView, quantityOfMeasureDefaultView,
				(DisplayStyleReadView) null, (DisplayStyleReadView) null,
				(Collection) null, false, (Collection) null);
		System.out.println("attrDefWriteView = " + attrDefWriteView);
		attrDefWriteView.setTypeDefId(identifier);
		if (AUTO_ADD_SINGLE_VALUE_CONSTRAINT_TO_NEW_GLOBAL_ATT && defDefaultView != null) {
			addSingleValuedConstraint(attrDefWriteView);
		}
		Set allPropertyDefViews = TYPE_DEF_SERVICE.getAllPropertyDefViews(selectIbaClassTypeName,
				typeDefReadView.getReadViewIdentifier(), readView);
		if (allPropertyDefViews != null && !allPropertyDefViews.isEmpty()) {
			for (Object allPropertyDefView : allPropertyDefViews) {
				PropertyDefinitionReadView propertyDefReadView = (PropertyDefinitionReadView) allPropertyDefView;
				String propertyDefReadViewName = propertyDefReadView.getName();
				String classifyName = "lwc_" + propertyDefReadViewName;
				// 更新属性值数据
				System.out.println("classifyName = " + classifyName);
				if (propertyDefReadViewName.equals("displayName") || propertyDefReadViewName.equals("description")) {
//					ArrayList newPropertyValueData = PropertyDefinitionHelper.getNewPropertyValueData(null,
//							propertyDefReadView, classifyName);
					String valueData;
					if (propertyDefReadViewName.equals("displayName")) {
						valueData = lwcDisplayName;
					} else {
						valueData = lwcDescription;
					}
					//
					boolean isUpdateSuccess = PropertyDefinitionHelper.updatePropertyValue(propertyDefReadView,
							(ReadViewIdentifier) null,
							(PropertyValueWriteView) null, valueData,
							(Map) null,
							false);
					if (isUpdateSuccess) {
						PropertyValueWriteView propertyValueWriteView = new PropertyValueWriteView(
								(ObjectIdentifier) null,
								propertyDefReadView, valueData,
								(Map) null, identifier, false, (ReadViewIdentifier) null, false);
						attrDefWriteView.setProperty(propertyValueWriteView);
					}
				}
			}
		}
		// 类型设置属性
		typeDefWriteView.setAttribute(attrDefWriteView);
		// 更新类型
		typeDefReadView = TYPE_DEF_SERVICE.updateTypeDef(typeDefWriteView);
		AttributeDefinitionReadView readViewAttributeByName = typeDefReadView
				.getAttributeByName(attrDefWriteView.getName());
		System.out.println("readViewAttributeByName = " + readViewAttributeByName);
		return typeDefReadView.getAttributeByName(innerName);
	}

	/**
	 * 添加单值约束
	 *
	 * @param attributeDefView
	 *            属性定义视图
	 * @throws WTException
	 *             WT异常
	 */
	private static void addSingleValuedConstraint(AttributeDefinitionWriteView attributeDefView) throws WTException {
		ConstraintRuleDefinitionReadView readView = BASE_DEF_SERVICE.getConstraintRuleDefView(
				SingleValuedConstraint.class.getName(), LWCBasicConstraint.class.getName(),
				attributeDefView.getDatatype().getId(),
				(String) null);
		ConstraintDefinitionWriteView writeView = new ConstraintDefinitionWriteView(readView,
				(ConstraintDefinitionReadView.RuleDataObject) null, attributeDefView.getName(), (Collection) null,
				attributeDefView.getTypeDefId(), (String) null);
		attributeDefView.setConstraintDefinition(writeView);
	}

	/**
	 * 获取原始文件名
	 *
	 * @param name
	 *            变量0
	 * @return {@link String}
	 */
	public static String getOriginalFilename(String name) {
		if (name == null) {
			return null;
		} else {
			int var1 = name.lastIndexOf("/");
			if (var1 == -1) {
				var1 = name.lastIndexOf("\\");
			}

			return var1 != -1 ? name.substring(var1 + 1) : name;
		}
	}

	/**
	 * 解析属性值
	 *
	 * @param value
	 *            值
	 * @param propertyDefinitionReadView
	 *            属性定义读取视图
	 * @return {@link String}
	 * @throws WTException
	 *             WT异常
	 */
	public static String parsePropertyValue(PropertyDefinitionReadView propertyDefinitionReadView, String value)
			throws WTException {
		Locale var2 = SessionHelper.getLocale();
		String dataType = propertyDefinitionReadView.getDatatype();
		if (value != null && !"".equals(value)) {
			if (Integer.class.getName().equals(dataType)) {
				try {
					return DataTypesUtility.toLong(value, var2).toString();
				} catch (ParseException e) {
					e.printStackTrace();
					throw new WTException(e.getMessage());
				}
			} else {
				return value.trim();
			}
		} else {
			return null;
		}
	}
}
