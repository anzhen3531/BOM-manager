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
	@RBEntry("./view_layout_32x.png")
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

	@RBEntry("签字位置配置")
	public static final String CUSTOM_DOCSIGNCONFIG_TITLE = "custom.docSignConfig.title";
	@RBEntry("签字位置配置")
	public static final String CUSTOM_DOCSIGNCONFIG_DESCRIPTION = "custom.docSignConfig.description";
	@RBEntry("签字位置配置")
	public static final String CUSTOM_DOCSIGNCONFIG_TOOLTIP = "custom.docSignConfig.tooltip";

	@RBEntry("创建签字位置配置")
	public static final String CUSTOM_CREATESIGNCONFIG_TITLE = "custom.createSignConfig.title";
	@RBEntry("创建签字位置配置")
	public static final String CUSTOM_CREATESIGNCONFIG_DESCRIPTION = "custom.createSignConfig.description";
	@RBEntry("创建签字位置配置")
	public static final String CUSTOM_CREATESIGNCONFIG_TOOLTIP = "custom.createSignConfig.tooltip";
	@RBEntry("netmarkets/images/add16x16.gif")
	@RBPseudo(false)
	@RBComment("add part")
	public static final String CUSTOM_CREATESIGNCONFIG_ICON = "custom.createSignConfig.icon";

	@RBEntry("修改签字位置配置")
	public static final String CUSTOM_UPDATESIGNCONFIG_TITLE = "custom.updateSignConfig.title";
	@RBEntry("修改签字位置配置")
	public static final String CUSTOM_UPDATESIGNCONFIG_DESCRIPTION = "custom.updateSignConfig.description";
	@RBEntry("修改签字位置配置")
	public static final String CUSTOM_UPDATESIGNCONFIG_TOOLTIP = "custom.updateSignConfig.tooltip";

	@RBEntry("批量下载文档主内容")
	public static final String EXTCOMMONACTION_DOWNLOADDOCMULT_TITLE = "extCommonAction.downloadDocMult.title";
	@RBEntry("批量下载文档主内容")
	public static final String EXTCOMMONACTION_DOWNLOADDOCMULT_DESCRIPTION = "extCommonAction.downloadDocMult.description";
	@RBEntry("批量下载文档主内容")
	public static final String EXTCOMMONACTION_DOWNLOADDOCMULT_TOOLTIP = "extCommonAction.downloadDocMult.tooltip";

	@RBEntry("批量下载图纸")
	public static final String EXTCOMMONACTION_downloadEPMDocMult_TITLE = "extCommonAction.downloadEPMDocMult.title";
	@RBEntry("批量下载图纸")
	public static final String EXTCOMMONACTION_downloadEPMDocMult_DESCRIPTION = "extCommonAction.downloadEPMDocMult.description";
	@RBEntry("批量下载图纸")
	public static final String EXTCOMMONACTION_downloadEPMDocMult_TOOLTIP = "extCommonAction.downloadEPMDocMult.tooltip";

	@RBEntry("批量下载部件相关文档")
	public static final String EXTCOMMONACTION_downloadPartDocMult_TITLE = "extCommonAction.downloadPartDocMult.title";
	@RBEntry("批量下载部件相关文档")
	public static final String EXTCOMMONACTION_downloadPartDocMult_DESCRIPTION = "extCommonAction.downloadPartDocMult.description";
	@RBEntry("批量下载部件相关文档")
	public static final String EXTCOMMONACTION_downloadPartDocMult_TOOLTIP = "extCommonAction.downloadPartDocMult.tooltip";

	@RBEntry("下载部件相关文档")
	public static final String EXTCOMMONACTION_DOWNLOADPARTDOCSINGLE_TITLE = "extCommonAction.downloadPartDocSingle.title";
	@RBEntry("下载部件相关文档")
	public static final String EXTCOMMONACTION_DOWNLOADPARTDOCSINGLE_DESCRIPTION = "extCommonAction.downloadPartDocSingle.description";
	@RBEntry("下载部件相关文档")
	public static final String EXTCOMMONACTION_DOWNLOADPARTDOCSINGLE_TOOLTIP = "extCommonAction.downloadPartDocSingle.tooltip";

	@RBEntry("下载文档内容")
	public static final String EXTCOMMONACTION_DOWNLOADDOCSINGLE_TITLE = "extCommonAction.downloadDocSingle.title";
	@RBEntry("下载文档内容")
	public static final String EXTCOMMONACTION_DOWNLOADDOCSINGLE_DESCRIPTION = "extCommonAction.downloadDocSingle.description";
	@RBEntry("下载文档内容")
	public static final String EXTCOMMONACTION_DOWNLOADDOCSINGLE_TOOLTIP = "extCommonAction.downloadDocSingle.tooltip";

	@RBEntry("下载图纸内容")
	public static final String EXTCOMMONACTION_DOWNLOADEPMDOCSINGLE_TITLE = "extCommonAction.downloadEPMDocSingle.title";
	@RBEntry("下载图纸内容")
	public static final String EXTCOMMONACTION_DOWNLOADEPMDOCSINGLE_DESCRIPTION = "extCommonAction.downloadEPMDocSingle.description";
	@RBEntry("下载图纸内容")
	public static final String EXTCOMMONACTION_DOWNLOADEPMDOCSINGLE_TOOLTIP = "extCommonAction.downloadEPMDocSingle.tooltip";


	@RBEntry("报表SQL配置")
	public static final String CUSTOM_REPORT_CONFIG_TITLE = "custom.reportConfig.title";
	@RBEntry("报表SQL配置")
	public static final String CUSTOM_REPORT_CONFIG_DESCRIPTION = "custom.reportConfig.description";
	@RBEntry("报表SQL配置")
	public static final String CUSTOM_REPORT_CONFIG_TOOLTIP = "custom.reportConfig.tooltip";


	@RBEntry("创建SQL配置")
	public static final String CUSTOM_CREATEREPORTFROMCONFIG_TITLE = "custom.createReportFromConfig.title";
	@RBEntry("创建SQL配置")
	public static final String CUSTOM_CREATEREPORTFROMCONFIG_DESCRIPTION = "custom.createReportFromConfig.description";
	@RBEntry("创建SQL配置")
	public static final String CUSTOM_CREATEREPORTFROMCONFIG_TOOLTIP = "custom.createReportFromConfig.tooltip";
	@RBEntry("netmarkets/images/add16x16.gif")
	@RBPseudo(false)
	@RBComment("创建SQL配置")
	public static final String CUSTOM_CREATEREPORTFROMCONFIG_ICON = "custom.createReportFromConfig.icon";

}
