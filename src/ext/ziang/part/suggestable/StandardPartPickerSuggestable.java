package ext.ziang.part.suggestable;

import java.util.ArrayList;
import java.util.Collection;

import com.google.common.collect.Lists;
import com.ptc.core.components.suggest.SuggestParms;
import com.ptc.core.components.suggest.SuggestResult;
import com.ptc.core.components.suggest.Suggestable;

import ext.ziang.common.util.CommonLog;

/**
 * 建议使用标准零件拾取器
 *
 * @author anzhen
 * @date 2024/04/10
 */
public class StandardPartPickerSuggestable implements Suggestable {
	/**
	 * 建议使用标准零件拾取器
	 */
	public StandardPartPickerSuggestable() {
		super();
	}

	/**
	 * 获取输入框
	 *
	 * @param suggestParms
	 *            建议 PARMS
	 * @return {@link Collection }<{@link SuggestResult }>
	 */
	@Override
	public Collection<SuggestResult> getSuggestions(SuggestParms suggestParms) {
		ArrayList<SuggestResult> results = Lists.newArrayList();
		CommonLog.log("StandardPartPicker =>  Suggestable");
		// 获取搜索参数
		String keyword = suggestParms.getSearchTerm();
		// 当前key 为输入值
		String mapKey = suggestParms.getParm("number");
		System.out.println("mapKey = " + mapKey);
		System.out.println("keyword = " + keyword);
		results.add(SuggestResult.valueOf(keyword));
		return results;
	}
}
