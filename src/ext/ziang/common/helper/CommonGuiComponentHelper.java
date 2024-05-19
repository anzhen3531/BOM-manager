package ext.ziang.common.helper;

import com.ptc.core.components.rendering.guicomponents.SuggestTextBox;
import com.ptc.core.components.rendering.guicomponents.TextDisplayComponent;

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
			Map<String, String> paramsMap, Integer maxLengthNumber, String label) {
		SuggestTextBox suggestTextBox = new SuggestTextBox(id, suggestServiceKey);
		suggestTextBox.setName(name);
		suggestTextBox.setColumnName(columnName);
		suggestTextBox.setRequired(isRequired);
		suggestTextBox.setReadOnly(isReadOnly);
		suggestTextBox.setEditable(isEditable);
		suggestTextBox.setEnabled(isEnabled);
		suggestTextBox.setMaxResults(maxLengthNumber);
		suggestTextBox.setLabel(label);
		suggestTextBox.setMinChars(minSearchCharNum);
		if (paramsMap != null) {
			paramsMap.forEach(suggestTextBox::addParm);
		}
		return suggestTextBox;
	}

	/**
	 * 新文本显示组件
	 *
	 * @param id
	 *            id
	 * @param name
	 *            名字
	 * @param value
	 *            价值
	 * @param label
	 *            标签
	 * @param columnName
	 *            列名称
	 * @return {@link TextDisplayComponent }
	 */
	public static TextDisplayComponent newTextDisplayComponent(String id, String name, String value, String label,
			String columnName) {
		TextDisplayComponent textDisplayComponent = new TextDisplayComponent(label);
		// 编写表格
		textDisplayComponent.setValue(value);
		textDisplayComponent.setId(id);
		textDisplayComponent.setName(name);
		textDisplayComponent.setColumnName(columnName);
		textDisplayComponent.setCheckXSS(false);
		return textDisplayComponent;
	}

	// 编写一个chatbox
}
