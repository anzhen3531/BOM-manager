package ext.ziang.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ext.ziang.common.config.PropertiesHelper;

/**
 * JDBC 模板 Oracle 帮助程序
 * <p>
 * Oracle-jdbc连接工具
 *
 * @author anzhen
 * @date 2024/03/26
 */
public class JdbcTemplateOracleHelper {
	private static final Logger logger = LoggerFactory.getLogger(JdbcTemplateOracleHelper.class);

	private static PropertiesHelper helper = PropertiesHelper.getInstance("commonConfig.properties");
	/**
	 * 网址
	 */
	private static String url = helper.getValueByKey("db.url");
	/**
	 * 用户名
	 */
	private static String username = helper.getValueByKey("db.username");
	/**
	 * 密码
	 */
	private static String password = helper.getValueByKey("db.password");
	/**
	 * class name
	 */
	private static String driver = helper.getValueByKey("db.driver");

	static {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			logger.error("Unexpected error", e);
		}
	}

	/**
	 * 获得 一个 数据库 链接
	 *
	 * 默认的jdbc 的操作， 默认的链接对象 ，事物的处理 都是自动提交的
	 *
	 * @return
	 */
	public static Connection getConnection(boolean isAutoCommit) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
			// 把jdbc 链接 设置成 非自动提交
			conn.setAutoCommit(isAutoCommit);
		} catch (SQLException e) {
			logger.error("Unexpected error", e);
			logger.debug("{}", "获得链接失败!");
		}
		return conn;
	}

	/**
	 * 创建一个 状态 通道
	 */
	private static Statement getStatement(Connection conn) {
		Statement st = null;
		try {
			st = conn.createStatement();
		} catch (SQLException e) {
			logger.error("Unexpected error", e);
			logger.debug("{}", "创建状态通道失败!");
		}
		return st;
	}

	public static void closeAll(Statement stmt, Connection conn) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				logger.error("Unexpected error", e);
			}
		}
		if (conn != null) {
			try {
				conn.close();// 关闭
			} catch (SQLException e) {
				logger.error("Unexpected error", e);
			}
		}
	}

	public static void closeResAll(ResultSet rs, Statement stmt, Connection conn) {
		if (rs != null) {
			try {
				rs.close();// 关闭
			} catch (SQLException e) {
				logger.error("Unexpected error", e);
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				logger.error("Unexpected error", e);
			}
		}
		if (conn != null) {
			try {
				conn.close();// 关闭
			} catch (SQLException e) {
				logger.error("Unexpected error", e);
			}
		}
	}
}
