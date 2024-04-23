package ext.ziang.doc.sign.datautility;

import com.ptc.core.components.descriptor.ModelContext;
import com.ptc.core.components.factory.AbstractDataUtility;
import com.ptc.core.components.rendering.guicomponents.GUIComponentArray;
import com.ptc.core.components.rendering.guicomponents.TextDisplayComponent;

import ext.ziang.model.ElectronicSignatureConfig;

/**
 * 通用配置数据实用程序
 *
 * @author anzhen
 * @date 2024/02/13
 */
public class ElectronicSignatureConfigUtility extends AbstractDataUtility {
	@Override
	public Object getDataValue(String column, Object currentObject, ModelContext modelContext) {
		// 配置相关的
		GUIComponentArray guiComponentArray = new GUIComponentArray();
		if (currentObject instanceof ElectronicSignatureConfig) {
			ElectronicSignatureConfig config = (ElectronicSignatureConfig) currentObject;
			// 获取总成料号 并填充
			TextDisplayComponent textDisplayComponent = new TextDisplayComponent("");
			textDisplayComponent.setCheckXSS(false);
			if (ElectronicSignatureConfig.DOC_TYPE_NAME.equals(column)) {
				textDisplayComponent.setValue(config.getDocTypeName());
			} else if (ElectronicSignatureConfig.CONTENT_TYPE.equals(column)) {
				textDisplayComponent.setValue(config.getContentType());
			} else if (ElectronicSignatureConfig.WORK_ITEM_NAME.equals(column)) {
				textDisplayComponent.setValue(config.getWorkItemName());

			} else if (ElectronicSignatureConfig.SIGN_XINDEX.equals(column)) {
				textDisplayComponent.setValue(config.getSignXIndex());

			} else if (ElectronicSignatureConfig.SIGN_YINDEX.equals(column)) {
				textDisplayComponent.setValue(config.getSignYIndex());

			} else if (ElectronicSignatureConfig.STATUS.equals(column)) {
				Integer status = config.getStatus();
				if (status == 1) {
					textDisplayComponent.setValue("开启");
				} else {
					textDisplayComponent.setValue("关闭");
				}
			} else if (ElectronicSignatureConfig.EXTENDED_FIELD.equals(column)) {
				textDisplayComponent.setValue(config.getExtendedField());

			} else if (ElectronicSignatureConfig.EXTENDED_FIELD1.equals(column)) {
				textDisplayComponent.setValue(config.getExtendedField1());

			} else if (ElectronicSignatureConfig.EXTENDED_FIELD2.equals(column)) {
				textDisplayComponent.setValue(config.getExtendedField2());

			}
			guiComponentArray.addGUIComponent(textDisplayComponent);
		}
		return guiComponentArray;
	}
}
