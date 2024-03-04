package ext.ziang.common.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.lwc.client.commands.AttributesCacheManager;
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
import com.ptc.core.meta.container.common.impl.SingleValuedConstraint;
import com.ptc.netmarkets.model.NmOid;
import com.ptc.netmarkets.util.beans.NmCommandBean;

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
	 * 创建属性值读取视图  TODO 等待处理
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
	 * 创建属性映射
	 *
	 * @param var1
	 *            变量1
	 * @param var2
	 *            变量2
	 * @return {@link AttributeDefinitionReadView}
	 * @throws WTException
	 *             WT异常
	 */
	public AttributeDefinitionReadView createAttributeDefinition(NmCommandBean var1, ObjectBean var2)
			throws WTException {
		String var3 = var2.getTextParameter("null___attributeName___textbox");
		String var4 = null;
		Map var5 = var2.getRadio();
		Iterator var6 = var5.keySet().iterator();

		String var7;
		while (var6.hasNext()) {
			var7 = (String) var6.next();
			if (var7.contains("attributeType")) {
				var4 = (String) var5.get(var7);
			}
		}

		String var36 = var2.getTextParameter("selectedIba");
		var7 = LWCCommands.getIbaInternalName(var36);
		String var8 = var1.getTextParameter("attributeContext");
		NmOid var9 = NmOid.newNmOid(var8);
		ObjectIdentifier var10 = var9.getOidObject();
		AttributeTemplateFlavor var11 = AttributeTemplateFlavorHelper.getFlavor(var9);
		if (var11.equals(AttributeTemplateFlavor.LWCSTRUCT)) {
			if (var3 == null || var3.isEmpty()) {
				var3 = "csmAttr_" + System.currentTimeMillis();
			}

			var4 = LWCIBAAttDefinition.class.getName();
		}

		String var12;
		DatatypeReadView var13;
		boolean var14 = var4.contains("LWCTranslatedTextAttDefinition");
		if (var4.contains("LWCIBAAttDefinition")) {
			var12 = LWCCommands.getIbaDatatype(var36);
			var13 = BASE_DEF_SERVICE.getDatatypeView(var12);
		} else if (var14) {
			var12 = String.class.getName();
			var13 = BASE_DEF_SERVICE.getDatatypeView(var12);
		} else {
			var12 = var2.getTextParameter("null___attributeDatatype___combobox");
			var13 = BASE_DEF_SERVICE.getDatatypeView(var12);
		}

		if (var4.contains("calculated")) {
			if (var12.contains("AttributeTypeIdentifierSet")) {
				var4 = "com.ptc.core.lwc.server.LWCAttributeSetAttDefinition";
			} else {
				var4 = "com.ptc.core.lwc.server.LWCNonPersistedAttDefinition";
			}
		}

		AttributeDefDefaultView var15 = null;
		String var16 = null;
		if (var4.contains("LWCIBAAttDefinition")) {
			try {
				var15 = IBA_DEF_SERVICE.getAttributeDefDefaultViewByPath(var7);
				if (var15 == null) {
					throw new WTException("Error retrieving IBA for \"" + var7 + "\"");
				}
			} catch (Exception var35) {
				throw new WTException(var35, "Error retrieving IBA for \"" + var7 + "\"");
			}

			AttributeDefinition var17 = IBA_DEF_CACHE.getAttributeDefinition(var7);
			if (var17 instanceof UnitDefinition) {
				var16 = ((UnitDefinition) var17).getQuantityOfMeasure().getName();
			} else {
				var16 = null;
			}
		}

		QuantityOfMeasureDefaultView var37 = null;
		String var18 = null;
		String var19 = null;
		if (var4.contains("LWCIBAAttDefinition") && var16 != null) {
			try {
				var37 = UNITS_SERVICE.getQuantityOfMeasureDefaultView(var16);
			} catch (Exception var34) {
				throw new WTException(var34, "Error retrieving QoM for \"" + var16 + "\"");
			}

			if (var37 == null) {
				throw new WTException("Error retrieving QoM for \"" + var16 + "\"");
			}
		} else if (var14) {
			var18 = var2.getTextParameter("null___transDictionary___textbox");
			var19 = var2.getTextParameter("selectedSourceText");
		} else if (var12.contains("FloatingPointWithUnits")
				&& (var4.contains("LWCFlexAttDefinition") || var4.contains("LWCNonPersistedAttDefinition"))) {
			var16 = var2.getTextParameter("null___attributeQom___combobox");
			try {
				var37 = UNITS_SERVICE.getQuantityOfMeasureDefaultView(var16);
			} catch (Exception var33) {
				throw new WTException(var33, "Error retrieving QoM for \"" + var16 + "\"");
			}
			if (var37 == null) {
				throw new WTException("Error retrieving QoM for \"" + var16 + "\"");
			}
		}

		TypeDefinitionReadView var20 = TYPE_DEF_SERVICE.getTypeDefView(AttributeTemplateFlavorHelper.getFlavor(var9),
				var10.getId());
		TypeDefinitionWriteView var21 = var20.getWritableView();
		AttributeDefinitionWriteView var22 = new AttributeDefinitionWriteView(var4, var3, var13, var15, var37,
				(DisplayStyleReadView) null, (DisplayStyleReadView) null, (Collection) null, false, (Collection) null);
		var22.setTypeDefId(var10);
		if (var14) {
			var22.setTranslationDictionary(var18);
			long var23 = OidHelper.getOid(var19).getId();
			AttributeDefinitionReadView var25 = var21.getAttributeById(var23);
			var22.setSourceTextAttribute(var25.getWritableView());
		}

		if (AUTO_ADD_SINGLE_VALUE_CONSTRAINT_TO_NEW_GLOBAL_ATT && var15 != null) {
			this.addSingleValuedConstraint(var22);
		}

		Set var38 = TYPE_DEF_SERVICE.getAllPropertyDefViews(var4, var20.getReadViewIdentifier(), var13);
		if (var38 != null && var38.size() > 0) {
			Iterator var24 = var38.iterator();

			while (var24.hasNext()) {
				PropertyDefinitionReadView var40 = (PropertyDefinitionReadView) var24.next();
				String var26 = var40.getName();
				String var27 = "lwc_" + var26;
				ArrayList var28 = PropertyDefinitionHelper.getNewPropertyValueData(var2, var40, var27);
				String var29 = (String) var28.get(0);
				boolean var30 = var28.size() > 1 ? Boolean.valueOf((String) var28.get(1)) : false;
				boolean var31 = PropertyDefinitionHelper.updatePropertyValue(var40, (ReadViewIdentifier) null,
						(PropertyValueWriteView) null, var29, (Map) null, var30);
				if (var31) {
					PropertyValueWriteView var32 = new PropertyValueWriteView((ObjectIdentifier) null, var40, var29,
							(Map) null, var10, false, (ReadViewIdentifier) null, false);
					var22.setProperty(var32);
				}
			}
		}

		var21.setAttribute(var22);
		var20 = TYPE_DEF_SERVICE.updateTypeDef(var21);
		AttributeDefinitionReadView var39 = var20.getAttributeByName(var22.getName());
		AttributesCacheManager.putAttributeIntoCache(var39, var1);
		return var20.getAttributeByName(var3);
	}

	/**
	 * 添加单值约束
	 *
	 * @param attributeDefView
	 *            属性定义视图
	 * @throws WTException
	 *             WT异常
	 */
	private void addSingleValuedConstraint(AttributeDefinitionWriteView attributeDefView) throws WTException {
		ConstraintRuleDefinitionReadView readView = BASE_DEF_SERVICE.getConstraintRuleDefView(
				SingleValuedConstraint.class.getName(), LWCBasicConstraint.class.getName(),
				attributeDefView.getDatatype().getId(),
				(String) null);
		ConstraintDefinitionWriteView writeView = new ConstraintDefinitionWriteView(readView,
				(ConstraintDefinitionReadView.RuleDataObject) null, attributeDefView.getName(), (Collection) null,
				attributeDefView.getTypeDefId(), (String) null);
		attributeDefView.setConstraintDefinition(writeView);
	}

}
