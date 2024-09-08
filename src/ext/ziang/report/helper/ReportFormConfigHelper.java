package ext.ziang.report.helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.*;

import ext.ziang.common.constants.StateEnum;
import ext.ziang.report.model.ReportFormConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.method.MethodContext;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.pom.WTConnection;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;

/**
 * 报表快速查询助手
 */
public class ReportFormConfigHelper {

    public static final Logger logger = LoggerFactory.getLogger(ReportFormConfigHelper.class);

    /**
     * 执行 SQL
     * 
     * @param sql sql 脚本
     * @return 数据集合
     */
    public static List<Map<String, Object>> execSQL(String sql) throws Exception {
        // 执行SQL 返回MAP
        logger.info("exec sql {}", sql);
        MethodContext context = MethodContext.getContext();
        WTConnection connection = (WTConnection)context.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            statement = connection.prepareStatement(sql);
            ResultSetMetaData resultSetMetaData = statement.getMetaData();
            rs = statement.executeQuery();
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
                    logger.info("key {}", resultSetMetaData.getColumnName(i + 1));
                    logger.info("value {}", rs.getString(i + 1));
                    map.put(resultSetMetaData.getColumnName(i + 1), rs.getString(i + 1));
                }
                result.add(map);
            }
        } catch (Exception e) {
            logger.error(" execSQL error", e);
        } finally {
            if (Objects.nonNull(statement)) {
                statement.close();
            }
            if (Objects.nonNull(rs)) {
                rs.close();
            }
        }
        logger.info("result {}", result);
        return result;
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
        querySpec.appendAnd();
        querySpec.appendWhere(new SearchCondition(ReportFormConfig.class, ReportFormConfig.STATE, SearchCondition.EQUAL,
            StateEnum.START.getValue()), new int[] {0});
        QueryResult queryResult = PersistenceHelper.manager.find(querySpec);
        if (queryResult.hasMoreElements()) {
            return (ReportFormConfig)queryResult.nextElement();
        }
        return null;
    }
}
