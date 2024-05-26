package ext.ziang.common.helper;

import java.io.File;

import wt.util.WTProperties;

/**
 * WTPath 帮助程序
 *
 * @author ander
 * @date 2024/05/25
 */
public class WTPathHelper {

	/** 临时 */
	public static String TEMP;

	/** 家 */
	public static String HOME;

	static {
		try {
			WTProperties properties = WTProperties.getLocalProperties();
			HOME = properties.getProperty("wt.home", "");
			TEMP = properties.getProperty("wt.temp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 模版文件夹路径 */
	public static String TEMPLATE_PATH = File.separator + "codebase"
			+ File.separator + "netmarkets"
			+ File.separator + "jsp"
			+ File.separator + "ext"
			+ File.separator + "ziang"
			+ File.separator + "mpm"
			+ File.separator + "template";

	/** 模版文件夹路径 */
	public static String VBS_SCRIPT_PATH = File.separator + "src"
			+ File.separator + "ext"
			+ File.separator + "ziang"
			+ File.separator + "mpm"
			+ File.separator + "vbs" + File.separator;
}
