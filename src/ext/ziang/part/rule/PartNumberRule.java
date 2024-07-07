package ext.ziang.part.rule;

import wt.inf.container.WTContainerRef;
import wt.rule.algorithm.RuleAlgorithm;
import wt.util.WTException;

/**
 * 零件编号规则
 *
 * @author anzhen
 * @date 2024/04/09
 * FIXME: 初始化规则中使用
 */
public class PartNumberRule implements RuleAlgorithm {
	/**
	 * 算
	 *
	 * @param objects        对象
	 * @param wtContainerRef WT 容器参考
	 * @return {@link Object}
	 * @throws WTException WT异常
	 */
	@Override
	public Object calculate(Object[] objects, WTContainerRef wtContainerRef) throws WTException {
		// 直接读取数据库进行
		return null;
	}
}
