//package ext.ziang.part.builder;
//
//import java.util.List;
//
//import com.ptc.core.lwc.common.ScreenDefinitionName;
//import com.ptc.core.ui.resources.ComponentMode;
//import com.ptc.jca.mvc.builders.TypedAttributesPanelBuilder;
//import com.ptc.jca.mvc.components.JcaAttributeConfig;
//import com.ptc.jca.mvc.components.JcaGroupConfig;
//import com.ptc.mvc.components.AttributeConfig;
//import com.ptc.mvc.components.AttributePanelConfig;
//import com.ptc.mvc.components.ComponentConfig;
//import com.ptc.mvc.components.ComponentParams;
//import com.ptc.mvc.components.TypeBased;
//import com.ptc.mvc.components.TypedAttrLayOutFactory;
//import com.ptc.netmarkets.util.beans.NmCommandBean;
//
//import wt.util.WTException;
//
//@TypeBased({ "wt.part.WTPart" })
//public class ACTypedAttributesPanelBuilder extends TypedAttributesPanelBuilder {
//	TypedAttrLayOutFactory tfactory;
//
//	public void setTypedAttrLayOutFactory(
//			TypedAttrLayOutFactory paramTypedAttrLayOutFactory) {
//		this.tfactory = paramTypedAttrLayOutFactory;
//	}
//
//	protected AttributePanelConfig buildAttributePanelConfig(
//			ComponentParams paramComponentParams) throws WTException {
//		NmCommandBean localNmCommandBean = (NmCommandBean) paramComponentParams
//				.getAttribute("commandBean");
//		int i = 0;
//
//		if (localNmCommandBean != null) {
//			String str = localNmCommandBean.getRequest().getParameter(
//					"insertNumber");
//			if ((str != null) && (str.length() > 0)) {
//				i = 1;
//			}
//		}
//
//		if (i != 0) {
//			return this.tfactory.getAttributePanelConfig(
//					getComponentConfigFactory(), paramComponentParams,
//					ScreenDefinitionName.INSERT);
//		}
//
//		super.setTypedAttrLayOutFactory(this.tfactory);
//		AttributePanelConfig attributePanelConfig = super.buildAttributePanelConfig(paramComponentParams);
//		List<ComponentConfig> components = attributePanelConfig.getComponents();
//
//		Object o = attributePanelConfig.getComponentMode();
//		// System.out.println("=====================:"+o.toString());
//		if (attributePanelConfig.getComponentMode() == ComponentMode.CREATE
//				|| attributePanelConfig.getComponentMode() == ComponentMode.EDIT) {
//			for (ComponentConfig componentConfig : components) {
//				if (componentConfig instanceof JcaGroupConfig) {
//					List<ComponentConfig> attributeConfigs = ((JcaGroupConfig) componentConfig)
//							.getComponents();
//					for (ComponentConfig attributeConfig : attributeConfigs) {
//						String attributeName = ((AttributeConfig) attributeConfig)
//								.getId();
//						if (attributeName.equals("FUnitGroupID")) {
//							((JcaAttributeConfig) attributeConfig)
//									.setDataUtilityId("customer.dropdownlist.unitgroup");
//						} else if (attributeName.equals("FUnitID")) {
//							((JcaAttributeConfig) attributeConfig)
//									.setDataUtilityId("customer.dropdownlist.unitgroup");
//						} else if (attributeName.equals("FOrderUnitID")) {
//							((JcaAttributeConfig) attributeConfig)
//									.setDataUtilityId("customer.dropdownlist.unitgroup");
//						} else if (attributeName.equals("FProductUnitID")) {
//							((JcaAttributeConfig) attributeConfig)
//									.setDataUtilityId("customer.dropdownlist.unitgroup");
//						} else if (attributeName.equals("FSaleUnitID")) {
//							((JcaAttributeConfig) attributeConfig)
//									.setDataUtilityId("customer.dropdownlist.unitgroup");
//						} else if (attributeName.equals("FStoreUnitID")) {
//							((JcaAttributeConfig) attributeConfig)
//									.setDataUtilityId("customer.dropdownlist.unitgroup");
//						} else if (attributeName.equals("FIsBackFlush")) {
//							((JcaAttributeConfig) attributeConfig)
//									.setDataUtilityId("customer.dropdownlist.backflush");
//						} else if (attributeName.equals("FBackFlushStockID")) {
//							((JcaAttributeConfig) attributeConfig)
//									.setDataUtilityId("customer.dropdownlist.backflush");
//						} else if (attributeName.equals("FErpClsID")) {
//							((JcaAttributeConfig) attributeConfig)
//									.setDataUtilityId("customer.dropdownlist.erpcls");
//						} else if (attributeName.equals("FDefaultWorkTypeID")) {
//							((JcaAttributeConfig) attributeConfig)
//									.setDataUtilityId("customer.dropdownlist.erpcls");
//						}
//					}
//				}
//			}
//		}
//		return attributePanelConfig;
//	}
//}
