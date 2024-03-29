package ext.ziang.resource;

import wt.util.resource.RBEntry;
import wt.util.resource.RBUUID;
import wt.util.resource.WTListResourceBundle;

/**
 * 通用操作资源
 *
 * @author anzhen
 * @date 2024/03/30
 */
@RBUUID("ext.ziang.resource.CommonActionResource")
public class CommonActionResource_zh_CN extends WTListResourceBundle {
	@RBEntry("一键复制BOM")
	public static final String EXTCOMMONACTION_ONCECOPYBOM_TITLE = "extCommonAction.onceCopyBom.title";
	@RBEntry("一键复制BOM")
	public static final String EXTCOMMONACTION_ONCECOPYBOM_DESCRIPTION = "extCommonAction.onceCopyBom.description";
	@RBEntry("一键复制BOM")
	public static final String EXTCOMMONACTION_ONCECOPYBOM_TOOLTIP = "extCommonAction.onceCopyBom.tooltip";
}
