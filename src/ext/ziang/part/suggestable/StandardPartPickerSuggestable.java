package ext.ziang.part.suggestable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.ptc.core.components.suggest.SuggestParms;
import com.ptc.core.components.suggest.SuggestResult;
import com.ptc.core.components.suggest.Suggestable;

import ext.ziang.common.helper.query.CommonMethodHelper;
import ext.ziang.common.util.LoggerHelper;
import wt.part.WTPart;

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
		LoggerHelper.log("StandardPartPicker =>  Suggestable");
		ArrayList<SuggestResult> results = Lists.newArrayList();
		// 获取搜索参数
		String keyword = suggestParms.getSearchTerm();
		// 当前key 为输入值
		String typeName = suggestParms.getParm("typeName");
		LoggerHelper.log("mapKey = " + typeName);
		LoggerHelper.log("keyword = " + keyword);
		if (StrUtil.isNotBlank(keyword) && typeName.contains("WTPart")) {
			try {
				List<WTPart> parts = CommonMethodHelper.findPartByPrefix(keyword);
				LoggerHelper.log("parts = " + parts);
				// Suggest 第一个函数为内部的Value 第二个值填写的为括号的值
				parts.forEach(part -> results
						.add(SuggestResult.valueOf(part.getNumber(), part.getName() + "," + part.getViewName())));
			} catch (Exception e) {
				e.printStackTrace();
				results.add(SuggestResult.valueOf("根据编号查询物料报错"));
			}
		} else {
			results.add(SuggestResult.valueOf(keyword));
		}
		return results;
	}
}
