package ext.ziang.part.suggestable;

import java.util.Collection;

import com.ptc.core.components.suggest.SuggestParms;
import com.ptc.core.components.suggest.SuggestResult;
import com.ptc.netmarkets.search.suggest.BeanPickerSuggestable;

import ext.ziang.common.util.CommonLog;
import ext.ziang.part.pciker.StandardPickerConfig;

/**
 * 建议使用标准零件拾取器
 *
 * @author anzhen
 * @date 2024/04/10
 */
public class StandardPartPickerSuggestable extends BeanPickerSuggestable {
	public StandardPartPickerSuggestable() {
		super();
	}

	@Override
	public Collection<SuggestResult> getSuggestions(SuggestParms suggestParms) {
		CommonLog.log("StandardPartPicker =>  Suggestable");
		suggestParms.addParm("configClassName", StandardPickerConfig.class.getName());
		Collection<SuggestResult> suggestions = super.getSuggestions(suggestParms);
		CommonLog.log("suggestions = ", suggestions);
		return suggestions;
	}
}
