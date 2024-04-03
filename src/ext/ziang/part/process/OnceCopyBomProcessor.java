package ext.ziang.part.process;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.DefaultObjectFormProcessor;
import com.ptc.core.components.forms.FormResult;
import com.ptc.netmarkets.model.NmOid;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.netmarkets.util.misc.NmContext;

import cn.hutool.core.collection.CollUtil;
import ext.ziang.part.helper.CommonHandlerBomStructHelper;
import wt.fc.Persistable;
import wt.fc.ReferenceFactory;
import wt.fc.WTReference;
import wt.part.WTPart;
import wt.util.WTException;

/**
 * 一次复制 BOM 处理器
 *
 * @author anzhen
 * @date 2024/04/03
 */
public class OnceCopyBomProcessor extends DefaultObjectFormProcessor {
	/**
	 * 预处理
	 *
	 * @param nmCommandBean
	 *            nm 命令 bean
	 * @param list
	 *            列表
	 * @return {@link FormResult}
	 * @throws WTException
	 *             WT异常
	 */
	@Override
	public FormResult preProcess(NmCommandBean nmCommandBean, List<ObjectBean> list) throws WTException {
		System.out.println("OnceCopyBomProcessor.preProcess");
		ArrayList selected = nmCommandBean.getSelected();
		System.out.println("selected = " + selected);
		validateCopyBomObject(selected);
		// 验证第一个表格中是否为空
		return super.preProcess(nmCommandBean, list);
	}

	/**
	 * 执行操作
	 *
	 * @param nmCommandBean
	 *            nm 命令 bean
	 * @param list
	 *            列表
	 * @return {@link FormResult}
	 * @throws WTException
	 *             WT异常
	 */
	@Override
	public FormResult doOperation(NmCommandBean nmCommandBean, List<ObjectBean> list) throws WTException {
		System.out.println("OnceCopyBomProcessor.doOperation");
		ArrayList selected = nmCommandBean.getSelected();
		System.out.println("selected = " + selected);
		HashMap<String, List<WTPart>> stringListHashMap = validateCopyBomObject(selected);
		List<WTPart> targetPartList = stringListHashMap.get("targetPartList");
		List<WTPart> originPartList = stringListHashMap.get("originPartList");
		// 复制BOM
		for (WTPart part : targetPartList) {
			CommonHandlerBomStructHelper.copyBomStruct(originPartList.get(0), part);
		}
		return super.doOperation(nmCommandBean, list);
	}

	/**
	 * 验证复制物料清单对象
	 *
	 * @param selected
	 *            选择
	 * @return {@link HashMap}<{@link String}, {@link List}<{@link WTPart}>>
	 */
	public HashMap<String, List<WTPart>> validateCopyBomObject(ArrayList selected) throws WTException {
		// 验证第二个表格中是否存在相同的builder
		ArrayList<WTPart> targetPartList = new ArrayList<>();
		WTPart originPart = null;
		ReferenceFactory referencefactory = new ReferenceFactory();
		for (Object object : selected) {
			if (object instanceof NmContext) {
				NmContext context = (NmContext) object;
				System.out.println("context = " + context);
				String contextStr = context.toString();
				// 获取源对象
				if (contextStr.contains("selectAffectionBomStep")) {
					NmOid targetOid = context.getTargetOid();
					WTReference wtreference = referencefactory.getReference(targetOid.toString());
					Persistable refObject = wtreference.getObject();
					if (refObject instanceof WTPart) {
						originPart = (WTPart) refObject;
					}
				}
				// 获取需要复制BOM的对象
				if (contextStr.contains("copyBomStructStep")) {
					NmOid targetOid = context.getTargetOid();
					WTReference wtreference = referencefactory.getReference(targetOid.toString());
					Persistable refObject = wtreference.getObject();
					if (refObject instanceof WTPart) {
						targetPartList.add((WTPart) refObject);
					}
				}
			}
		}
		// 抛出异常
		if (originPart == null || CollUtil.isEmpty(targetPartList)) {
			throw new WTException("被复制的BOM,和目标BOM不能为空，请选择之后进行重试");
		}

		HashMap<String, List<WTPart>> map = new HashMap<>();
		map.put("originPart", Collections.singletonList(originPart));
		map.put("targetParts", targetPartList);
		return map;
	}
}