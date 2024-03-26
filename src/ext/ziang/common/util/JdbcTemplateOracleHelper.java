package ext.ziang.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
			// 把jdbc 链接 设置成 非自动提交
			conn.setAutoCommit(false);
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

	/**
	 * 创建一个 预状态通道 传递的sql 语句参数 一定模板语句 是不完整带有问号占位符 sql
	 *
	 * @param sql
	 *            insert update delete select insert into user_tb
	 *            values(?,?,?,?,?,?,?);
	 */
	private static PreparedStatement getPreparedStatement(String sql) {
		Connection conn = getConnection();
		try {
			return conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("创建预状态通道失败!");
			return null;
		}

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

	public static List<Map<String, Object>> selectAll(String querysql) {
		List<Map<String, Object>> list = new ArrayList<>();
		long starttime = System.currentTimeMillis();
		Connection conn = getConnection();
		Statement statement = null;
		ResultSet rs = null;
		try {
			statement = conn.createStatement();
			rs = statement.executeQuery(querysql);
			ResultSetMetaData data = rs.getMetaData();
			int columnCount = data.getColumnCount();
			while (rs.next()) {
				int i = 1;
				Map<String, Object> map = new HashMap<>();
				while (i <= columnCount) {
					String name = data.getColumnName(i);
					// System.out.println(name + " : " + rs.getObject(i));
					if (rs.getObject(i) == null || "".equals(rs.getObject(i))) {
						map.put(name, "");
					} else {
						map.put(name, rs.getObject(i));
					}
					i++;
				}
				list.add(map);
			}
			System.out.println(" 执行成功一次,总使用时间：" + (System.currentTimeMillis() - starttime) + "ms");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResAll(rs, statement, conn);
		}
		return list;
	}

	/**
	 * 基于预状态通道执行批处理操作
	 *
	 * @param sqltemplet
	 * @param params
	 * @return
	 */
	public static boolean batchData(String sqltemplet, List<List<Object>> params) {
		Connection conn = getConnection();
		// 记录起始时间
		long startTime = System.currentTimeMillis();
		PreparedStatement statement = null;
		boolean isok = false;
		try {
			try {
				statement = conn.prepareStatement(sqltemplet);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("执行批处理时    创建预状态通道失败!");
			}
			if (params != null && !params.isEmpty()) {
				for (List<Object> plist : params) {
					int size = plist.size();
					for (int i = 0; i < size; i++) {
						statement.setObject(i + 1, plist.get(i));
					}
					statement.addBatch();
				}
				statement.executeBatch();
				conn.commit();
				isok = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("在 预 状 态 通 道 下 执 行 批 出 操 作 失 败!");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			closeAll(statement, conn);
		}
		long endTime = System.currentTimeMillis();
		long time = endTime - startTime;
		System.out.println("用时：" + time + "毫秒");
		return isok;
	}

	/**
	 * 基于预状态通道执行批处理操作
	 *
	 * @param sqltemplet
	 * @param params
	 * @return
	 */
	public static boolean batchAllData(String sqltemplet, List<List<Object>> params) {
		Connection conn = getConnection();
		long starttime = System.currentTimeMillis();
		PreparedStatement statement = null;
		boolean isok = false;
		try {
			try {
				statement = conn.prepareStatement(sqltemplet);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			if (params != null && !params.isEmpty()) {
				int size2 = params.size();
				for (int j = 0; j < size2; j++) {
					List<Object> plist = params.get(j);
					int size = plist.size();
					for (int i = 0; i < size; i++) {
						statement.setObject(i + 1, plist.get(i));
					}
					statement.addBatch();
					if (j % 1000 == 0) {
						statement.executeBatch();
						conn.commit();
						statement.clearParameters();
					}
				}

				statement.executeBatch();
				conn.commit();
				statement.clearParameters();
				isok = true;
				System.out.println(" 执行成功一次,总使用时间："
						+ (System.currentTimeMillis() - starttime) + "ms");

			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("在 预 状 态 通 道 下 执 行 批 出 操 作 失 败!");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			closeAll(statement, conn);
		}

		return isok;
	}

	// 批处理方法
	/**
	 * 基于状态通道 完成数据 批出操作
	 *
	 * @params 多个sql 语句 update insert delete
	 * @return
	 */
	public static boolean batchData(List<String> sqls) {
		Connection conn = getConnection();
		// 记录起始时间
		long startTime = System.currentTimeMillis();
		Statement st = getStatement(conn);
		boolean isok = false;
		try {
			if (sqls != null && !sqls.isEmpty()) {
				for (int i = 0; i < sqls.size(); i++) {
					String sql = sqls.get(i);
					// 把每次循环得到 sql 语句 添加 状态通道 批处理缓存中
					st.addBatch(sql);
				}
				st.executeBatch();
				conn.commit();
				isok = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("在状态通道下执行批出操作失败!");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			closeAll(st, conn);
		}
		long endTime = System.currentTimeMillis();
		long time = endTime - startTime;

		System.out.println("用时：" + time + "毫秒");
		return isok;
	}

	/**
	 * 返回单个结果的值，如count\min\max等等
	 *
	 * @param sql
	 *            sql语句
	 * @return 结果集
	 * @throws SQLException
	 */
	public static Object getSingle(String sql) {
		Object result = null;
		Connection conn = getConnection();
		Statement st = null;
		ResultSet rs = null;
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);

			if (rs.next()) {
				result = rs.getObject(1);
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResAll(rs, st, conn);
		}
		return null;

	}

	public static boolean createTable(String sql) {
		Object result = null;
		Connection conn = getConnection();
		Statement st = null;
		try {
			st = conn.createStatement();
			int executeUpdate = st.executeUpdate(sql);

			if (executeUpdate > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(st, conn);
		}
		return false;

	}
}
