package ext.ziang.part.helper;

import ext.ziang.common.util.CommonLog;
import ext.ziang.common.util.ToolUtils;
import wt.fc.ObjectReference;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.collections.WTCollection;
import wt.part.WTPart;
import wt.part.WTPartHelper;
import wt.part.WTPartMaster;
import wt.part.WTPartSubstituteLink;
import wt.part.WTPartUsageLink;
import wt.query.ConstantExpression;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.query.TableColumn;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.wip.WorkInProgressHelper;
import wt.vc.wip.Workable;

/**
 * 公共处理程序 BOM 结构帮助程序
 *
 * @author anzhen
 * @date 2024/01/12
 */
public class CommonHandlerBomStructHelper {
	/**
	 * 复制 BOM 结构
	 *
	 * @param selectObject
	 *            被复制的对象
	 * @param originObject
	 *            需要复制的对象
	 */
	public static void copyBomStruct(WTPart selectObject, WTPart originObject) {
		// 复制bom结构
		if (originObject == null || selectObject == null) {
			return;
		}

		WTPartUsageLink originLink = null;
		Workable workable;
		try {
			// 判断对象是否为工作副本
			if (WorkInProgressHelper.isWorkingCopy(originObject)) {
				workable = originObject;
			} else if (WorkInProgressHelper.isCheckedOut(originObject)) {
				// 获取工作副本
				workable = WorkInProgressHelper.service.workingCopyOf(originObject);
			} else {
				// 检出当前BOM
				workable = ToolUtils.checkout(originObject, "一键复制BOM结构检出");
			}
			WTPart originPartBak = (WTPart) workable;
			// 2、进行BOM复制 复制数量和结构
			QueryResult qr = WTPartHelper.service.getUsesWTPartMasters(selectObject);
			WTPartUsageLink link;
			CommonLog.printLog("部件使用数量 = " + qr.size());
			while (qr.hasMoreElements()) {
				link = (WTPartUsageLink) qr.nextElement();
				// 创建link
				WTPartMaster partMaster = (WTPartMaster) link.getRoleBObject();
				// 验证是否存在相同的link
				if (!validateExistLink(originPartBak, WTPartUsageLink.class, partMaster)) {
					originLink = WTPartUsageLink.newWTPartUsageLink(originPartBak, partMaster);
					originLink.setQuantity(link.getQuantity());
					PersistenceHelper.manager.save(originLink);
				}

				// 3、复制当前替代件
				WTCollection substituteLinks = WTPartHelper.service.getSubstituteLinks(link);
				CommonLog.printLog("替代件数量 links.size() = " + substituteLinks.size());
				if (!substituteLinks.isEmpty()) {
					// 遍历所有的替代件
					for (Object object : substituteLinks) {
						ObjectReference reference = (ObjectReference) object;
						CommonLog.printLog("object = ", object);
						WTPartSubstituteLink substituteLink = (WTPartSubstituteLink) reference.getObject();
						WTPartMaster substituteMaster = (WTPartMaster) substituteLink.getRoleBObject();
						if (originLink == null) {
							return;
						}
						// 验证是否存在相同的link
						if (!validateExistLink(originLink, WTPartSubstituteLink.class, substituteMaster)) {
							WTPartSubstituteLink originSubstituteLink = WTPartSubstituteLink
									.newWTPartSubstituteLink(originLink, substituteMaster);
							originSubstituteLink.setQuantity(substituteLink.getQuantity());
							// 保存即可
							PersistenceHelper.manager.save(originSubstituteLink);
						}
					}
				}
			}
			// 4、检入当前BOM
			ToolUtils.checkin(originPartBak, "一键复制BOM结构检入");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 验证存在链接
	 *
	 * @param clazz
	 *            克拉兹
	 * @param roleAObj
	 *            角色 AOBJ
	 * @param roleBObj
	 *            角色 Bobj
	 * @return boolean
	 * @throws WTPropertyVetoException
	 *             WTFalty财产否决权例外
	 * @throws WTException
	 *             WTException
	 */
	public static boolean validateExistLink(Persistable roleAObj, Class clazz, Persistable roleBObj)
			throws WTPropertyVetoException, WTException {
		QuerySpec querySpec = new QuerySpec(clazz);
		querySpec.setAdvancedQueryEnabled(true);
		querySpec.setDescendantQuery(false);
		querySpec.setDistinct(true);
		String aliasAt = querySpec.getFromClause().getAliasAt(0);
		CommonLog.printLog("aliasAt = " + aliasAt);
		querySpec.appendWhere(new SearchCondition(new TableColumn(aliasAt, "ida3a5"),
				SearchCondition.EQUAL, new ConstantExpression(
						roleAObj.getPersistInfo().getObjectIdentifier().getId())),
				new int[] { 0 });
		querySpec.appendAnd();
		querySpec.appendWhere(new SearchCondition(new TableColumn(aliasAt, "ida3b5"),
				SearchCondition.EQUAL, new ConstantExpression(
						roleBObj.getPersistInfo().getObjectIdentifier().getId())),
				new int[] { 0 });
		CommonLog.printLog("验证是否存在关联关系SQL = " + querySpec);
		QueryResult qr = PersistenceHelper.manager.find(querySpec);
		return qr.hasMoreElements();
	}
}
