package ext.ziang.common.util;

import java.lang.reflect.Field;

import wt.method.RemoteAccess;

/**
 * 通用日志打印实用程序
 *
 * @author anzhen
 * @date 2024/03/13
 */
public class CommonLogPrintUtil implements RemoteAccess {

	public static boolean DEBUG = true;
	// 打印日志 采用远程开关开启

	/**
	 * 打开调试
	 */
	public static void openDebug() throws NoSuchFieldException, IllegalAccessException {
		Field field = CommonLogPrintUtil.class.getDeclaredField("DEBUG");
		field.setBoolean("DEBUG", true);
	}

	/**
	 * 关闭调试
	 */
	public static void closeDebug() throws NoSuchFieldException, IllegalAccessException {
		Field field = CommonLogPrintUtil.class.getDeclaredField("DEBUG");
		field.setBoolean("DEBUG", false);
	}

	/**
	 * 打印日志
	 *
	 * @param msg
	 *            消息
	 */
	public static void printLog(String msg) {
		if (DEBUG) {
			System.out.println(msg);
		}
	}
}
