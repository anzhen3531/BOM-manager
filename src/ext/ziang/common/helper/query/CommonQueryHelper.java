package ext.ziang.common.helper.query;

import java.util.List;

import wt.fc.Persistable;
import wt.query.ArrayExpression;
import wt.query.ConstantExpression;
import wt.query.KeywordExpression;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.query.TableColumn;
import wt.util.WTException;

public class CommonQueryHelper {

	/**
	 * 获取查询规范
	 *
	 * @param persistable
	 *            持久性
	 * @return {@link QuerySpec}
	 * @throws QueryException
	 *             查询异常
	 */
	public static Integer getQueryIndex(Persistable persistable, QuerySpec querySpec) throws QueryException {
		return querySpec.appendClassList(persistable.getClass(), true);
	}

	/**
	 * @param columnName
	 *            列名称
	 * @param value
	 *            价值
	 * @return {@link SearchCondition}
	 */
	public static SearchCondition getAppointColumnSearchConditionByConstantValue(String columnName, Object value,
			String alias, String constraint) throws QueryException {
		return new SearchCondition(new TableColumn(alias, columnName), constraint,
				new ConstantExpression(value));
	}

	/**
	 * @param columnName
	 *            列名称
	 * @param value
	 *            价值
	 * @return {@link SearchCondition}
	 */
	public static SearchCondition getAppointColumnSearchConditionByArrayList(String columnName, List<Object> value,
			String alias, String constraint) throws QueryException {
		return new SearchCondition(new TableColumn(alias, columnName), constraint,
				new ArrayExpression(value.toArray()));
	}

	/**
	 * 添加行号条件
	 *
	 * @param qs
	 *            查询对象
	 * @param rowNumber
	 *            行号
	 * @return {@link QuerySpec} SQL
	 * @throws WTException
	 *             WT异常
	 */
	public static QuerySpec addRowNumberCondition(QuerySpec qs, int rowNumber, int index) throws WTException {
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(KeywordExpression.Keyword.ROWNUM.newKeywordExpression(),
				SearchCondition.LESS_THAN_OR_EQUAL, new ConstantExpression(rowNumber)), new int[] { index });
		return qs;
	}
}
