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

/**
 * 通用查询帮助程序
 *
 * @author anzhen
 * @date 2024/05/09
 */
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
		// true 和 false 控制是否返回值
		return querySpec.appendClassList(persistable.getClass(), true);
	}

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
