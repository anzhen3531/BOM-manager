package ext.ziang.promotion.helper;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ext.ziang.common.util.ToolUtils;
import wt.fc.PersistenceHelper;
import wt.fc.collections.WTHashSet;
import wt.fc.collections.WTSet;
import wt.inf.container.WTContainer;
import wt.inf.container.WTContainerRef;
import wt.lifecycle.State;
import wt.maturity.MaturityBaseline;
import wt.maturity.MaturityHelper;
import wt.maturity.PromotionNotice;
import wt.part.WTPart;
import wt.pom.Transaction;
import wt.type.TypeDefinitionReference;
import wt.type.TypedUtility;
import wt.vc.baseline.BaselineHelper;

/**
 * 升级请求操作工具
 *
 * @author anzhen
 * @date 2024/04/19
 */
public class PromotionOperationHelper {

	/**
	 * 创建升级请求
	 *
	 * @param partList
	 *            零件清单
	 * @param name
	 *            名字
	 * @param description
	 *            描述
	 * @param typeName
	 *            键入名称
	 * @param folderPath
	 *            文件夹路径
	 * @param containerRef
	 *            容器编号
	 * @param lifeCycle
	 *            生命周期
	 */
	public static PromotionNotice createPromotionRequest(List<WTPart> partList, String name, String description,
			String lifeCycle, String typeName, String folderPath,
			WTContainerRef containerRef) {
		// 手动筛选下
		partList = partList.stream().filter(Objects::nonNull).filter(part -> !part.getNumber().contains("("))
				.collect(Collectors.toList());
		if (partList.isEmpty()) {
			return null;
		}
		// 创建PN
		Transaction trx = new Transaction();
		PromotionNotice notice = null;
		try {
			trx.start();
			MaturityBaseline partBaseline = MaturityBaseline.newMaturityBaseline();
			partBaseline.setContainerReference(containerRef);
			partBaseline = (MaturityBaseline) PersistenceHelper.manager.save(partBaseline);
			notice = PromotionNotice.newPromotionNotice(name);
			Timestamp pDate = new Timestamp(System.currentTimeMillis());
			notice.setPromotionDate(pDate);
			WTContainer container = containerRef.getReferencedContainer();
			notice.setContainer(container);
			notice.setMaturityState(State.toState(lifeCycle));
			notice.setDescription(description);
			TypeDefinitionReference tdRef = TypedUtility.getTypeDefinitionReference(typeName);
			notice.setTypeDefinitionReference(tdRef);
			// 关联文档
			ToolUtils.assignFolder(container, folderPath, notice);
			WTSet promotableSet = new WTHashSet(partList);
			// 部件基线
			partBaseline = (MaturityBaseline) BaselineHelper.service.addToBaseline(promotableSet, partBaseline);
			notice.setConfiguration(partBaseline);
			notice = MaturityHelper.service.savePromotionNotice(notice);
			notice = (PromotionNotice) PersistenceHelper.manager.refresh(notice);
			MaturityHelper.service.savePromotionTargets(notice, promotableSet);
			trx.commit();
			trx = null;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (trx != null) {
				trx.rollback();
			}
		}
		return notice;
	}
}
