package ext.ziang.common.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import cn.hutool.core.util.StrUtil;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LocationInfo;
import org.slf4j.spi.LocationAwareLogger;
import wt.method.RemoteAccess;

/**
 * 通用日志打印实用程序
 *
 * @author anzhen
 * @date 2024/03/13
 */
public class LoggerHelper implements RemoteAccess {

	private static Map<String, Logger> loggerMap = new HashMap<String, Logger>();

	public static void debug(Object message) {
		String className = getClassName();
		Logger log = getLogger(className);
		log.debug(message);
	}

	public static void info(Object message) {
		String className = getClassName();
		Logger log = getLogger(className);
		log.info(message);
	}

	public static void warn(Object message) {
		String className = getClassName();
		Logger log = getLogger(className);
		log.warn(message);
	}

	public static void error(Object message) {
		String className = getClassName();
		Logger log = getLogger(className);
		log.error(message);
	}

	/**
	 * 获取最开始的调用者所在类
	 * 
	 * @return
	 */
	private static String getClassName() {
		Throwable th = new Throwable();
		StackTraceElement[] stes = th.getStackTrace();
		StackTraceElement ste = stes[2];
		return ste.getClassName();
	}

	/**
	 * 根据类名获得logger对象
	 * 
	 * @param className
	 * @return
	 */
	private static Logger getLogger(String className) {
		Logger log = null;
		if (loggerMap.containsKey(className)) {
			log = loggerMap.get(className);
		} else {
			try {
				log = Logger.getLogger(Class.forName(className));
				loggerMap.put(className, log);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return log;
	}

	/**
	 * 打印日志 采用远程开关开启
	 */
	public static boolean DEBUG = true;

	/**
	 * 打开调试
	 */
	public static void openDebug() throws NoSuchFieldException, IllegalAccessException {
		Field field = LoggerHelper.class.getDeclaredField("DEBUG");
		field.setBoolean("DEBUG", true);
	}

	/**
	 * 关闭调试
	 */
	public static void closeDebug() throws NoSuchFieldException, IllegalAccessException {
		Field field = LoggerHelper.class.getDeclaredField("DEBUG");
		field.setBoolean("DEBUG", false);
	}

	/**
	 * 打印日志
	 *
	 * @param obj
	 *            OBJ系列
	 */
	public static void log(String prefix, Object obj) {
		if (DEBUG) {
			if (obj == null) {
				System.out.println(prefix + "null");
				return;
			}
			System.out.println(prefix + obj.toString());
		}
	}

	/**
	 * 打印日志
	 *
	 * @param msg
	 *            消息
	 */
	public static void log(String msg) {
		if (DEBUG) {
			System.out.println(msg);
		}
	}

	/**
	 * 错误
	 *
	 * @param unableToGetLocalProperties
	 *            无法获取本地属性
	 * @param e
	 *            e
	 */
	public static void error(String unableToGetLocalProperties, Exception e) {
		System.out.println("unableToGetLocalProperties = " + unableToGetLocalProperties);
		e.printStackTrace();
	}

	public static void main(String[] args) {
		String camelCaseStr = "camelCaseStringExample";

		// 使用 StrUtil.toUnderlineCase 方法转换为下划线命名法
		String snakeCaseStr = StrUtil.toUnderlineCase(camelCaseStr);

		// 打印转换后的字符串
		System.out.println("转换后的字符串: " + snakeCaseStr);
	}

}
