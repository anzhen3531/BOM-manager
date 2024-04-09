package ext.ziang.part.pciker;

import com.ptc.jca.mvc.components.AbstractGenericPickerConfig;
import com.ptc.netmarkets.util.beans.NmCommandBean;

import wt.fc.Persistable;
import wt.util.WTException;

public class StandardPickerConfig extends AbstractGenericPickerConfig {
	public StandardPickerConfig() {
	}

	public String getMultiSelect(NmCommandBean nmCommandBean) {
		return "false";
	}

	public String getInline(NmCommandBean nmCommandBean) {
		return "false";
	}

	public String getPickerTitle(NmCommandBean nmCommandBean) {
		return "Test Picker";
	}

	public String getObjectType(NmCommandBean nmCommandBean) {
		try {
			return nmCommandBean.getPrimaryOid().getClass().getName();
		} catch (WTException e) {
			return Persistable.class.getName();
		}
	}


	public static void setPickerProperties(NmCommandBean nmCommandBean) {

	}
	/**
	 * 获取显示建议
	 *
	 * @param nmCommandBean
	 *            nm 命令 bean
	 * @return {@link String}
	 */
	public String getShowSuggestion(NmCommandBean nmCommandBean) {
		return "true";
	}

	/**
	 * 获取建议服务密钥
	 *
	 * @param nmCommandBean
	 *            nm 命令 bean
	 * @return {@link String}
	 */
	public String getSuggestServiceKey(NmCommandBean nmCommandBean) {
		return "testBeanPickerSuggestable";
	}

	/**
	 * 获取标签
	 *
	 * @param nmCommandBean
	 *            nm 命令 bean
	 * @return {@link String}
	 * @throws WTException
	 *             WT异常
	 */
	public String getLabel(NmCommandBean nmCommandBean) throws WTException {
		return "Bean Picker Suggestable Test (WTPart)";
	}
}
