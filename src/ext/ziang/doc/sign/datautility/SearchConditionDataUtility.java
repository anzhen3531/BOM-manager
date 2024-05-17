package ext.ziang.doc.sign.datautility;

import java.util.ArrayList;
import java.util.List;

import com.ptc.core.components.descriptor.ModelContext;
import com.ptc.core.components.factory.dataUtilities.DefaultDataUtility;
import com.ptc.core.components.rendering.guicomponents.ComboBox;
import com.ptc.core.components.rendering.guicomponents.PickerIconButton;
import com.ptc.core.components.rendering.guicomponents.TextBox;

import wt.util.WTException;

/**
 * 搜索条件数据实用程序
 *
 * @author anzhen
 * @date 2024/04/16
 */
public class SearchConditionDataUtility extends DefaultDataUtility {
	@Override
	public Object getDataValue(String column, Object o, ModelContext modelContext) throws WTException {
		System.out.println("column = " + column);
		List list = new ArrayList<String>();
		list.add("productType");
		list.add("fragment");
		list.add("productClassify");
		list.add("type");
		list.add("productType");
		if (list.contains(column)) {
			return createCheckBox(column);
		} else if (column.equals("workspace")) {
			return createTextSpace(column);
		} else if (column.equals("SearchCommand")) {
			return createButton(column);
		}
		return null;
	}

	private PickerIconButton createButton(String column) {
		PickerIconButton button = new PickerIconButton(column);
		button.setId(column);
		button.setName("name" + column);
		button.addJsAction("onclick", "searchCommand()");
		return button;
	}

	/**
	 * “创建”复选框
	 *
	 * @param columnConfigName
	 *            列配置名称
	 * @return {@link ComboBox}
	 */
	public ComboBox createCheckBox(String columnConfigName) {
		ArrayList<String> innerList = new ArrayList<>();
		ArrayList<String> displayList = new ArrayList<>();
		ArrayList<String> selectValueList = new ArrayList<>();
		ComboBox createCheckBox = new ComboBox(innerList, displayList, selectValueList);
		createCheckBox.setEditable(true);
		System.out.println("createCheckBox = " + createCheckBox);
		createCheckBox.setId(columnConfigName);
		createCheckBox.setName("name" + columnConfigName);
		switch (columnConfigName) {
			case "productType":
				// 创建对应的产品库
				innerList.add("");
				innerList.add("158.75");
				innerList.add("156.75");
				innerList.add("166");
				innerList.add("182");
				innerList.add("182L");

				displayList.add("");
				displayList.add("158.75");
				displayList.add("156.75");
				displayList.add("166");
				displayList.add("182");
				displayList.add("182L");
				createCheckBox.addJsAction("onchange", "changeCheckBox()");
				break;
			case "fragment":
				// 设置分片类型
				innerList.add("");
				innerList.add("3-cut");
				innerList.add("Half-cut");
				innerList.add("Full cell");

				displayList.add("");
				displayList.add("3-cut");
				displayList.add("Half-cut");
				displayList.add("Full cell");
				createCheckBox.addJsAction("onchange", "changeCheckBox()");
				break;
			case "productClassify":
				// 设置车间
				innerList.add("");
				innerList.add("Backsheet");
				innerList.add("Dual-glass");

				displayList.add("");
				displayList.add("Backsheet");
				displayList.add("Dual-glass");
				createCheckBox.addJsAction("onchange", "changeCheckBox()");
				break;
			case "type":
				// 电池片类型
				innerList.add("");
				innerList.add("P TYPE");
				innerList.add("N TYPE");

				displayList.add("");
				displayList.add("P TYPE");
				displayList.add("N TYPE");
				createCheckBox.addJsAction("onchange", "changeCheckBox()");
				break;
		}
		return createCheckBox;
	}

	/**
	 * 创建文本空间
	 *
	 * @param columnConfigName
	 *            列配置名称
	 * @return {@link TextBox}
	 */
	public TextBox createTextSpace(String columnConfigName) {
		TextBox textBox = new TextBox();
		// 直接填写即可
		textBox.setId(columnConfigName);
		textBox.setName("name" + columnConfigName);
		return textBox;
	}
}
