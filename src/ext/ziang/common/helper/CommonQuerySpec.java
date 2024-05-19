package ext.ziang.common.helper;

import wt.fc.Persistable;
import wt.query.*;
import wt.util.WTException;

import java.util.List;

public class CommonQuerySpec {
	/**
	 * @param columnName
	 *            列名称
	 * @param value
	 *            价值
	 * @return {@link SearchCondition}
	 */
	public static SearchCondition getSearchConditionByConstantValue(String columnName, Object value,
			String alias, String constraint) throws QueryException {
		return new SearchCondition(new TableColumn(alias, columnName), constraint, new ConstantExpression(value));
	}

	/**
	 * 按数组列表获取指定列搜索条件
	 *
	 * @param columnName
	 *            列名称
	 * @param value
	 *            价值
	 * @param alias
	 *            别名
	 * @param constraint
	 *            约束
	 * @return {@link SearchCondition}
	 * @throws QueryException
	 *             查询异常
	 */
	public static SearchCondition getSearchConditionByArrayList(String columnName, List<Object> value,
			String alias, String constraint) throws QueryException {
		return new SearchCondition(new TableColumn(alias, columnName), constraint,
				new ArrayExpression(value.toArray()));
	}

	/**
	 * 增加数量限制
	 *
	 * @param qs
	 *            查询对象
	 * @param index
	 *            指数
	 * @param clazz
	 *            克拉兹
	 * @param column
	 *            列
	 * @param value
	 *            价值
	 * @return {@link QuerySpec} SQL
	 * @throws WTException
	 *             WT异常
	 */
	public static QuerySpec addSearchConditionByClass(QuerySpec qs, int index, Class clazz, String column,
			String condition, String value)
			throws WTException {
		SearchCondition searchCondition = new SearchCondition(clazz, column, condition, value);
		qs.appendWhere(searchCondition, new int[] { index });
		return qs;
	}

	/**
	 * 增加数量限制
	 *
	 * @param qs
	 *            查询对象
	 * @param rowNumber
	 *            行号
	 * @param index
	 *            指数
	 * @return {@link QuerySpec} SQL
	 * @throws WTException
	 *             WT异常
	 */
	public static QuerySpec addSearchNumberLimit(QuerySpec qs, int rowNumber, int index) throws WTException {
		qs.appendWhere(new SearchCondition(KeywordExpression.Keyword.ROWNUM.newKeywordExpression(),
				SearchCondition.LESS_THAN_OR_EQUAL, new ConstantExpression(rowNumber)), new int[] { index });
		return qs;
	}

	/**
	 * 添加排序
	 *
	 * @param qs
	 *            查询
	 * @param clazz
	 *            所属类型
	 * @param column
	 *            列
	 * @param isDesc
	 *            是 desc
	 * @param tableIndex
	 *            表索引
	 * @throws QueryException
	 *             查询异常
	 */
	public static void addOrderByClass(QuerySpec qs, Class clazz, String column, boolean isDesc, int tableIndex)
			throws QueryException {
		OrderBy orderBy = new OrderBy(new ClassAttribute(clazz, column), isDesc);
		qs.appendOrderBy(orderBy, new int[] { tableIndex });
	}

	/**
	 * 添加排序
	 *
	 * @param qs
	 *            查询
	 * @param column
	 *            列
	 * @param isDesc
	 *            是 desc
	 * @param tableIndex
	 *            表索引
	 * @param alias
	 *            别名
	 * @throws QueryException
	 *             查询异常
	 */
	public static void addOrderByTableColumn(QuerySpec qs, String column, boolean isDesc, int tableIndex, String alias)
			throws QueryException {
		OrderBy orderBy = new OrderBy(new TableColumn(alias, column), isDesc);
		qs.appendOrderBy(orderBy, new int[] { tableIndex });
	}
}
