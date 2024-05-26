package ext.ziang.common.constants;

import ext.ziang.common.helper.WTPathHelper;

import java.io.File;

/**
 * 路径常量
 *
 * @author anzhen
 * @date 2024/05/26
 */
public interface PathConstants {

	String WORKFLOW_TEMPLATE_PATH = WTPathHelper.HOME + WTPathHelper.TEMPLATE_PATH + File.separator
			+ "WorkflowPicTepm.xlsx";
}
