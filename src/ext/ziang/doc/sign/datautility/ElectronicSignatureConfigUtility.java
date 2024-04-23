package ext.ziang.doc.sign.datautility;

import com.ptc.core.components.descriptor.GuiComponentFactory;
import com.ptc.core.components.descriptor.ModelContext;
import com.ptc.core.components.factory.AbstractDataUtility;

import com.ptc.core.components.rendering.guicomponents.GUIComponentArray;
import ext.ziang.model.ElectronicSignatureConfig;
import wt.util.WTException;

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
			if (ElectronicSignatureConfig.DOC_TYPE_NAME.equals(column)) {

			} else if (ElectronicSignatureConfig.CONTENT_TYPE.equals(column)) {

			} else if (ElectronicSignatureConfig.WORK_ITEM_NAME.equals(column)) {

			} else if (ElectronicSignatureConfig.SIGN_XINDEX.equals(column)) {

			} else if (ElectronicSignatureConfig.SIGN_YINDEX.equals(column)) {

			} else if (ElectronicSignatureConfig.STATUS.equals(column)) {

			} else if (ElectronicSignatureConfig.EXTENDED_FIELD.equals(column)) {

			} else if (ElectronicSignatureConfig.EXTENDED_FIELD1.equals(column)) {

			} else if (ElectronicSignatureConfig.EXTENDED_FIELD2.equals(column)) {

			}
		}
		return null;
	}
}
