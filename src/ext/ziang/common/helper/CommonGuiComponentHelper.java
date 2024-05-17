package ext.ziang.common.helper;

import com.ptc.core.components.rendering.guicomponents.SuggestTextBox;

import java.util.Map;

/**
 * 通用 GUI 组件帮助程序
 *
 * @author ander
 * @date 2024/05/17
 */
public class CommonGuiComponentHelper {

	/**
	 * 新建建议文本框
	 *
	 * @param id
	 *            id
	 * @param name
	 *            名字
	 * @param columnName
	 *            列名称
	 * @param isRequired
	 *            是必需
	 * @param isReadOnly
	 *            是只读
	 * @param isEditable
	 *            是可编辑
	 * @param isEnabled
	 *            已启用
	 * @param minSearchCharNum
	 *            最大长度
	 * @param suggestServiceKey
	 *            建议服务密钥
	 * @param paramsMap
	 *            Params 地图
	 * @param maxLengthNumber
	 *            结果限制
	 * @param label
	 *            标签
	 * @return {@link SuggestTextBox }
	 */
	public static SuggestTextBox newSuggestTextBox(String id, String name, String columnName, Boolean isRequired,
			Boolean isReadOnly, Boolean isEditable, Boolean isEnabled, Integer minSearchCharNum,
			String suggestServiceKey,
			Map<String, Object> paramsMap, Integer maxLengthNumber, String label) {
		SuggestTextBox suggestTextBox = new SuggestTextBox(id, suggestServiceKey);
		suggestTextBox.setName(name);
		suggestTextBox.setColumnName(columnName);
		suggestTextBox.setRequired(isRequired);
		suggestTextBox.setReadOnly(isReadOnly);
		suggestTextBox.setEditable(isEditable);
		suggestTextBox.setEnabled(isEnabled);
		suggestTextBox.setSuggestBoxConfig(paramsMap);
		suggestTextBox.setMaxResults(maxLengthNumber);
		suggestTextBox.setLabel(label);
		suggestTextBox.setMinChars(minSearchCharNum);
		return suggestTextBox;
	}
}
