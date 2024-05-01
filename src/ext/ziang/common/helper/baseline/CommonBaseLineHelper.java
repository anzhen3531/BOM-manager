package ext.ziang.common.helper.baseline;

import java.util.List;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.folder.Folder;
import wt.folder.FolderHelper;
import wt.folder.FolderNotFoundException;
import wt.inf.container.WTContainer;
import wt.inf.container.WTContainerRef;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleManaged;
import wt.lifecycle.State;
import wt.part.WTPart;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.type.TypeDefinitionReference;
import wt.type.TypedUtility;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.baseline.BaselineHelper;
import wt.vc.baseline.ManagedBaseline;

/**
 * 公共基线帮助程序
 *
 * @author anzhen
 * @date 2024/04/30
 * @finish 2024/04/30
 * @Core BaselineService 系统提供的针对基线的API
 */
public class CommonBaseLineHelper {

	/**
	 * 根据基线名称获取基线
	 *
	 * @param baseLineName
	 *            基线名称
	 * @return {@link ManagedBaseline}
	 * @throws WTException
	 *             WT异常
	 */
	public static ManagedBaseline getBaseLineByName(String baseLineName) throws WTException {
		System.out.println("基线名称:" + baseLineName);
		QuerySpec qs = new QuerySpec(ManagedBaseline.class);
		SearchCondition sc = new SearchCondition(ManagedBaseline.class, "name", SearchCondition.EQUAL, baseLineName);
		qs.appendSearchCondition(sc);
		QueryResult queryresult = PersistenceHelper.manager.find(qs);
		ManagedBaseline baseline = null;
		while (queryresult.hasMoreElements()) {
			baseline = (ManagedBaseline) queryresult.nextElement();
		}
		return baseline;
	}

	/**
	 * 创建基线
	 *
	 * @param container
	 *            容器
	 * @param baseLineName
	 *            基线名称
	 * @param partList
	 *            零件清单
	 * @param state
	 *            状态
	 * @param type
	 *            类型
	 * @return {@link ManagedBaseline} 创建完成的基线对象
	 * @throws WTException
	 *             WT异常
	 * @throws WTPropertyVetoException
	 *             WTFeproperty
	 */
	public static ManagedBaseline createBaseLine(WTContainer container, String baseLineName, List<WTPart> partList,
			String state, String type) throws WTException, WTPropertyVetoException {
		ManagedBaseline baseline = ManagedBaseline.newManagedBaseline();
		baseline.setName(baseLineName);
		String location = "/Default/物料同步基线";
		Folder folder;
		try {
			folder = FolderHelper.service.getFolder(location, WTContainerRef.newWTContainerRef(container));
		} catch (FolderNotFoundException e) {
			folder = null;
		}
		if (folder == null) {
			folder = FolderHelper.service.createSubFolder(location, WTContainerRef.newWTContainerRef(container));
		}
		FolderHelper.assignLocation(baseline, folder);
		// 指定基线类型
		TypeDefinitionReference tdRef = TypedUtility.getTypeDefinitionReference(type);
		baseline.setTypeDefinitionReference(tdRef);
		baseline = (ManagedBaseline) PersistenceHelper.manager.save(baseline);
		LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) baseline, State.toState(state));
		for (WTPart part : partList) {
			BaselineHelper.service.addToBaseline(part, baseline);
		}
		return baseline;
	}

	// 修改基线


	// 删除基线
}
