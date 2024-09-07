package ext.ziang.report.helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import ext.ziang.report.model.ReportFormConfig;
import org.apache.log4j.Logger;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.log4j.LogR;
import wt.method.MethodContext;
import wt.pom.WTConnection;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;

/**
 * 报表快速查询助手
 */
public class ReportFormConfigHelper {

    public static final Logger logger = LogR.getLogger(ReportFormConfigHelper.class.getName());

    /**
     * 执行 SQL
     * 
     * @param sql sql 脚本
     * @return 数据集合
     */
    public static Map<String, Object> execSQL(String sql) throws Exception {
        Map<String, Object> map = new HashMap<>();
        // 执行SQL 返回MAP
        MethodContext context = MethodContext.getContext();
        WTConnection connection = (WTConnection)context.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement(sql);
            ResultSetMetaData resultSetMetaData = statement.getMetaData();
            rs = statement.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
                    map.put(resultSetMetaData.getColumnName(i + 1), rs.getString(i + 1));
                    System.out.println(resultSetMetaData.getColumnName(i + 1) + ": " + rs.getString(i + 1));
                }
            }
        } catch (Exception e) {
            logger.error(" execSQL error {}", e);
        } finally {
            if (Objects.nonNull(statement)) {
                statement.close();
            }
            if (Objects.nonNull(rs)) {
                rs.close();
            }
        }
        logger.info("result map {}" + map);
        return map;
    }

    /**
     * 根据id查询相关的配置对象
     * 
     * @param id id
     * 
     * @return 配置对象
     */
    public static ReportFormConfig findConfigById(Long id) throws WTException {
        QuerySpec querySpec = new QuerySpec(ReportFormConfig.class);
        querySpec.appendWhere(new SearchCondition(ReportFormConfig.class, "thePersistInfo.theObjectIdentifier.id",
            SearchCondition.EQUAL, id), new int[] {0});
        QueryResult queryResult = PersistenceHelper.manager.find(querySpec);
        if (queryResult.hasMoreElements()) {
            return (ReportFormConfig)queryResult.nextElement();
        }
        return null;
    }
}
