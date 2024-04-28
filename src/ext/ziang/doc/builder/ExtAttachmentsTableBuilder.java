//package ext.ziang.doc.builder;
//
//import java.util.List;
//
//import com.ptc.mvc.components.ColumnConfig;
//import com.ptc.mvc.components.ComponentConfig;
//import com.ptc.mvc.components.ComponentParams;
//import com.ptc.mvc.components.OverrideComponentBuilder;
//import com.ptc.mvc.components.TableConfig;
//import com.ptc.windchill.enterprise.attachments.mvc.builders.AttachmentsTableBuilder;
//
//import wt.util.WTException;
//
///**
// * 重写内容Builder
// *
// * @author anzhen
// * @date 2024/04/28
// */
//@OverrideComponentBuilder
//public class ExtAttachmentsTableBuilder extends AttachmentsTableBuilder {
//
//	@Override
//	public ComponentConfig buildComponentConfig(ComponentParams componentParams) throws WTException {
//		TableConfig tableconfig = (TableConfig) super.buildComponentConfig(componentParams);
//		List<ComponentConfig> list = tableconfig.getComponents();
//		for (ComponentConfig componentConfig : list) {
//			// 判断是否为类名
//			if (componentConfig.getId().equals("attachmentsName") &&
//					componentConfig instanceof ColumnConfig) {
//				ColumnConfig columnconfig = (ColumnConfig) componentConfig;
//				columnconfig.setDataUtilityId("ValidateDocPrimaryContentDataUtility");
//			}
//		}
//		return tableconfig;
//	}
//}
