package ext.ziang.resource;

import wt.util.resource.RBComment;
import wt.util.resource.RBEntry;
import wt.util.resource.RBPseudo;
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
	@RBEntry("客户配置")
	public static final String EXTNAVIGATION_CUSTOMCONFIG_TITLE = "navigation.customConfig.activetooltip";
	@RBEntry("客户配置")
	public static final String EXTNAVIGATION_CUSTOMCONFIG_DESCRIPTION = "navigation.customConfig.description";
	@RBEntry("客户配置")
	public static final String EXTNAVIGATION_CUSTOMCONFIG_TOOLTIP = "navigation.customConfig.tooltip";
	@RBEntry("./customConfig.jpg")
	@RBPseudo(false)
	@RBComment("客户配置")
	public static final String EXTNAVIGATION_CUSTOMDP_ICON = "navigation.customConfig.icon";
	
	@RBEntry("一键复制BOM")
	public static final String EXTCOMMONACTION_ONCECOPYBOM_TITLE = "extCommonAction.onceCopyBom.title";
	@RBEntry("一键复制BOM")
	public static final String EXTCOMMONACTION_ONCECOPYBOM_DESCRIPTION = "extCommonAction.onceCopyBom.description";
	@RBEntry("一键复制BOM")
	public static final String EXTCOMMONACTION_ONCECOPYBOM_TOOLTIP = "extCommonAction.onceCopyBom.tooltip";

	@RBEntry("netmarkets/images/part_insert.gif")
	@RBPseudo(false)
	@RBComment("DO NOT TRANSLATE")
	public static final String EXTCOMMONACTION_ONCECOPYBOM_ICON = "extCommonAction.onceCopyBom.icon";

	@RBEntry("netmarkets/images/add16x16.gif")
	@RBPseudo(false)
	@RBComment("add part")
	public static final String EXTCOMMONACTION_SEARCHAFFECTED_ICON = "extCommonAction.searchAffected.icon";

	@RBEntry("开启日志")
	public static final String EXTLOG_STARTSYSLOG_TITLE = "extLog.startSysLog.title";
	@RBEntry("开启日志")
	public static final String EXTLOG_STARTSYSLOG_DESCRIPTION = "extLog.startSysLog.description";
	@RBEntry("开启日志")
	public static final String EXTLOG_STARTSYSLOG_TOOLTIP = "extLog.startSysLog.tooltip";

	@RBEntry("关闭日志")
	public static final String EXTLOG_STOPSYSLOG_TITLE = "extLog.stopSysLog.title";
	@RBEntry("关闭日志")
	public static final String EXTLOG_STOPSYSLOG_DESCRIPTION = "extLog.stopSysLog.description";
	@RBEntry("关闭日志")
	public static final String EXTLOG_STOPSYSLOG_TOOLTIP = "extLog.stopSysLog.tooltip";
}
