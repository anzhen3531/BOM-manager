package ext.ziang.common.util;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ptc.core.lwc.client.commands.LWCCommands;
import com.ptc.core.lwc.client.util.AttributeTemplateFlavorHelper;
import com.ptc.core.lwc.client.util.NewConstraintInflator;
import com.ptc.core.lwc.client.util.PropertyDefinitionHelper;
import com.ptc.core.lwc.common.AttributeTemplateFlavor;
import com.ptc.core.lwc.common.BaseDefinitionService;
import com.ptc.core.lwc.common.OidHelper;
import com.ptc.core.lwc.common.TypeDefinitionService;
import com.ptc.core.lwc.common.dynamicEnum.EnumerationConstraintsHelper;
import com.ptc.core.lwc.common.view.AttributeDefinitionReadView;
import com.ptc.core.lwc.common.view.AttributeDefinitionWriteView;
import com.ptc.core.lwc.common.view.ConstraintDefinitionReadView;
import com.ptc.core.lwc.common.view.ConstraintDefinitionWriteView;
import com.ptc.core.lwc.common.view.ConstraintRuleDefinitionReadView;
import com.ptc.core.lwc.common.view.DatatypeReadView;
import com.ptc.core.lwc.common.view.DisplayStyleReadView;
import com.ptc.core.lwc.common.view.PropertyDefinitionReadView;
import com.ptc.core.lwc.common.view.PropertyDefinitionWriteView;
import com.ptc.core.lwc.common.view.PropertyValueReadView;
import com.ptc.core.lwc.common.view.PropertyValueWriteView;
import com.ptc.core.lwc.common.view.ReadView;
import com.ptc.core.lwc.common.view.ReadViewIdentifier;
import com.ptc.core.lwc.common.view.ReusableAttributeReadView;
import com.ptc.core.lwc.common.view.ReusableAttributeWriteView;
import com.ptc.core.lwc.common.view.SeparatorReadView;
import com.ptc.core.lwc.common.view.TypeDefinitionReadView;
import com.ptc.core.lwc.common.view.TypeDefinitionWriteView;
import com.ptc.core.lwc.server.LWCBasicConstraint;
import com.ptc.core.lwc.server.LWCIBAAttDefinition;
import com.ptc.core.lwc.server.TypeDefinitionServiceHelper;
import com.ptc.core.meta.common.CorrectableException;
import com.ptc.core.meta.common.DiscreteSet;
import com.ptc.core.meta.container.common.impl.SingleValuedConstraint;
import com.ptc.netmarkets.model.NmOid;

import cn.hutool.core.util.StrUtil;
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
		// CreateAttributeFormProcessor 创建属性关联 完成
		// NewConstraintFormProcessor 创建枚举关联 完成
		// CreateEntryFormProcessor 创建枚举关联根枚举 完成
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
	 * 创建分类属性和IBA属性映射
	 *
	 * @param innerName
	 *            内部名称
	 * @param lwcDisplayName
	 *            LWC 显示名称
	 * @param lwcDescription
	 *            LWC 描述
	 * @param ibaSelectAttrOid
	 *            选择的可重用属性
	 * @param classifyAttrOid
	 *            需要映射的分类属性
	 * @return {@link AttributeDefinitionReadView}
	 * @throws WTException
	 *             WT异常
	 */
	public static AttributeDefinitionReadView createAttributeDefinition(String innerName,
			String lwcDisplayName, String lwcDescription,
			String ibaSelectAttrOid, String classifyAttrOid)
			throws WTException {
		System.out.println("CommonOperationAttrUtil.createAttributeDefinition");
		System.out.println("innerName = " + innerName + ", lwcDisplayName = " + lwcDisplayName + ", lwcDescription = "
				+ lwcDescription + ", ibaSelectAttrOid = " + ibaSelectAttrOid + ", classifyAttrOid = "
				+ classifyAttrOid);
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

		TypeDefinitionReadView typeDefReadView = TYPE_DEF_SERVICE.getTypeDefView(
				AttributeTemplateFlavorHelper.getFlavor(nmOid),
				identifier.getId());
		TypeDefinitionWriteView typeDefWriteView = typeDefReadView.getWritableView();
		// 判断是否存在当前视图
		AttributeDefinitionWriteView attrDefWriteView = new AttributeDefinitionWriteView(
				selectIbaClassTypeName,
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
				if (propertyDefReadViewName.equals("displayName") ||
						propertyDefReadViewName.equals("description")) {
					String valueData;
					if (propertyDefReadViewName.equals("displayName")) {
						valueData = lwcDisplayName;
					} else {
						valueData = lwcDescription;
					}
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

		// 26537L 固定为枚举值
		AttributeDefinitionWriteView writableView = readViewAttributeByName.getWritableView();
		typeDefWriteView = typeDefReadView.getWritableView();
		createConstraintsWriteView(writableView, typeDefWriteView, 26537L);
		typeDefWriteView.setAttribute(writableView);
		typeDefReadView = TYPE_DEF_SERVICE.updateTypeDef(typeDefWriteView);
		System.out.println("更新完成");
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
				(ConstraintDefinitionReadView.RuleDataObject) null, attributeDefView.getName(),
				(Collection) null,
				attributeDefView.getTypeDefId(),
				(String) null);
		attributeDefView.setConstraintDefinition(writeView);
	}

	/**
	 * 创建约束
	 * 创建枚举值
	 * //
	 * http://win-fv1tfp5mpk5.ziang.com/Windchill/ptc1/comp/csm.lwc.type.attribute.info?
	 * // lwcMode=edit&u8=1&
	 * //
	 *
	 * lwcrv=-com.ptc.core.lwc.server.LWCStructEnumAttTemplate%253A112108-com.ptc.core.lwc.server.LWCIBAAttDefinition%253A116903
	 * // &containerRef=OR%3Awt.inf.container.ExchangeContainer%3A5
	 * // &setActiveLayout=contentPanel_twoPanes_typeadmin&portlet=component
	 * // &wt.reqGrp=nzupiwl%3Bltfgos8r%3B4308%3Buv2bdi%3B2399
	 * // &oid=OR%3Acom.ptc.core.lwc.server.LWCIBAAttDefinition%3A116903
	 *
	 * @param lwcrv
	 *            LWCRV型
	 * @param typeId
	 *            类型 ID
	 * @throws WTException
	 *             WT异常
	 */
	public static ConstraintDefinitionWriteView createConstraint(String lwcrv, Long typeId) throws WTException {
		if (StrUtil.isBlank(lwcrv)) {
			throw new WTException("cannot get attributeRv parameter from command bean");
		} else {
			System.out.println("lwcrv = " + lwcrv);
			// 获取读取视图
			// LWCStructEnumAttTemplate View
			//
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
					ConstraintRuleDefinitionReadView constraintRuleReadView = BASE_DEF_SERVICE
							.getConstraintRuleDefView(typeId);
					if (constraintRuleReadView == null) {
						throw new WTException("unable to get constraint rule for id " + typeId);
					}

					// 获取分隔符
					ReadViewIdentifier newConstraintReadViewId = NewConstraintInflator
							.createUniqueFakeRvId(SeparatorReadView.class,
									readViewIdentifier.getRootContextIdentifier());
					System.out.println("newConstraintReadViewId = " + newConstraintReadViewId);
					map.put("constraintRvId", newConstraintReadViewId.toEncodedString());
					constraintRuleReadView = BASE_DEF_SERVICE.getConstraintRuleDefView(typeId);
					System.out
							.println("constraintRuleReadView.getDatatype() = " + constraintRuleReadView.getDatatype());
					System.out.println(
							"constraintRuleReadView.getRuleClassname() = " + constraintRuleReadView.getRuleClassname());

					ConstraintDefinitionWriteView writeView = new ConstraintDefinitionWriteView(
							constraintRuleReadView,
							(ConstraintDefinitionReadView.RuleDataObject) null,
							attributeDefinitionReadView.getName(),
							(Collection) null, (Set) null,
							(ConstraintDefinitionReadView) null,
							false,
							objectIdentifier,
							newConstraintReadViewId, (String) null, false);
					// 判断是否是枚举类型
					System.out.println("EnumerationConstraintsHelper.isClassificationRule(constraintRuleReadView) = "
							+ EnumerationConstraintsHelper.isClassificationRule(constraintRuleReadView));
					if (EnumerationConstraintsHelper.isClassificationRule(constraintRuleReadView)) {
						DiscreteSet discreteSet = new DiscreteSet(new Object[] {
								"com.ptc.core.lwc.common.dynamicEnum.provider.ClassificationEnumerationInfoProvider" });
						writeView.setRuleDataObj(new ConstraintDefinitionReadView.RuleDataObject(discreteSet));
					}
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
	 * @param writeView
	 *            写入视图
	 * @param typeDefWriteView
	 * @param typeId
	 *            类型 ID
	 * @throws WTException
	 *             WT异常
	 */
	public static void createConstraintsWriteView(AttributeDefinitionWriteView writeView,
			TypeDefinitionWriteView typeDefWriteView, Long typeId)
			throws WTException {
		ObjectIdentifier objectIdentifier = typeDefWriteView.getReadViewIdentifier().getOid();
		System.out.println("objectIdentifier.getStringValue() = " + objectIdentifier.getStringValue());
		// 创建默认读取规则
		ConstraintRuleDefinitionReadView constraintRuleReadView = BASE_DEF_SERVICE
				.getConstraintRuleDefView(typeId);
		System.out.println("constraintRuleReadView.getDatatype() = " + constraintRuleReadView.getDatatype());
		System.out.println("constraintRuleReadView.getOid() = " + constraintRuleReadView.getOid());
		if (constraintRuleReadView == null) {
			throw new WTException("unable to get constraint rule for id " + typeId);
		}

		// 获取分隔符
		ReadViewIdentifier newConstraintReadViewId = NewConstraintInflator
				.createUniqueFakeRvId(SeparatorReadView.class,
						typeDefWriteView.getReadViewIdentifier());
		System.out.println("newConstraintReadViewId = " + newConstraintReadViewId);
		constraintRuleReadView = BASE_DEF_SERVICE.getConstraintRuleDefView(typeId);
		System.out.println("constraintRuleReadView.getDatatype() = " + constraintRuleReadView.getDatatype());
		System.out.println("constraintRuleReadView.getRuleClassname() = " + constraintRuleReadView.getRuleClassname());
		ConstraintDefinitionWriteView constWriteView = new ConstraintDefinitionWriteView(
				constraintRuleReadView,
				(ConstraintDefinitionReadView.RuleDataObject) null,
				writeView.getName(),
				(Collection) null, (Set) null,
				(ConstraintDefinitionReadView) null,
				false,
				objectIdentifier,
				newConstraintReadViewId, (String) null, false);
		writeView.setConstraintDefinition(constWriteView);
	}

	/**
	 * 更改编号
	 * 可持续化doc的编码修改
	 *
	 * @param doc
	 *            文档对象
	 * @param number
	 *            数
	 * @return {@link WTDocument}
	 * @throws WTException
	 *             WT异常
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

	// TODO 创建枚举相关代码
}
