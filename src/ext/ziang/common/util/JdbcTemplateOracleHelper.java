package ext.ziang.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * JDBC 模板 Oracle 帮助程序
 * <p>
 * Oracle-jdbc连接工具
 *
 * @author anzhen
 * @date 2024/03/26
 */
public class JdbcTemplateOracleHelper {

	/**
	 * 网址
	 */
	private static String url;
	/**
	 * 用户名
	 */
	private static String username;
	/**
	 * 密码
	 */
	private static String password;
	/**
	 * class name
	 */
	private static String driver;

	static {
		InputStream is = JdbcTemplateOracleHelper.class.getClassLoader().getResourceAsStream("oracleConfig.properties");
		Properties pro = new Properties();
		/*
		 * url = "jdbc:oracle:thin:@172.0.0.1:1521:rdt1";
		 * username = "errtrans";
		 * password = "root@123";
		 * driver = "oracle.jdbc.driver.OracleDriver";
		 */
		try {
			pro.load(is);
			url = pro.getProperty("oracle.url");
			username = pro.getProperty("oracle.username");
			password = pro.getProperty("oracle.password");
			driver = pro.getProperty("oracle.driver");
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 注册驱动
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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
			e.printStackTrace();
			System.out.println("获得链接失败!");
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
			e.printStackTrace();
			System.out.println("创建状态通道失败!");
		}
		return st;
	}

	public static void closeAll(Statement stmt, Connection conn) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();// 关闭
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void closeResAll(ResultSet rs, Statement stmt, Connection conn) {

		if (rs != null) {
			try {
				rs.close();// 关闭
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();// 关闭
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
