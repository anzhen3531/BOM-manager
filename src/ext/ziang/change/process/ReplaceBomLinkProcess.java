package ext.ziang.change.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.DefaultObjectFormProcessor;
import com.ptc.core.components.forms.FormProcessingStatus;
import com.ptc.core.components.forms.FormResult;
import com.ptc.core.components.util.FeedbackMessage;
import com.ptc.core.ui.resources.FeedbackType;
import com.ptc.netmarkets.model.NmOid;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.netmarkets.util.misc.NmContext;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import ext.ziang.common.helper.part.CommonPartHelper;
import ext.ziang.common.util.ToolUtils;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.method.RemoteAccess;
import wt.part.Quantity;
import wt.part.QuantityUnit;
import wt.part.SubstituteQuantity;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.part.WTPartSubstituteLink;
import wt.part.WTPartUsageLink;
import wt.session.SessionHelper;
import wt.session.SessionServerHelper;
import wt.util.WTException;
import wt.vc.wip.WorkInProgressHelper;
import wt.vc.wip.Workable;

/**
 * 替换 BOM 链接流程
 *
 * @author anzhen
 * @date 2024/02/21
 */
public class ReplaceBomLinkProcess extends DefaultObjectFormProcessor implements RemoteAccess {

	@Override
	public FormResult doOperation(NmCommandBean nmCommandBean, List<ObjectBean> list) throws WTException {
		FormResult formResult = super.doOperation(nmCommandBean, list);
		List<String> errors = new ArrayList<>();
		List<Workable> workables = new ArrayList<>();
		// 释放权限
		boolean flag = SessionServerHelper.manager.setAccessEnforced(false);
		try {
			HashMap text = nmCommandBean.getText();
			System.out.println("text = " + text);
			HashMap radio = nmCommandBean.getRadio();
			System.out.println("radio = " + radio);
			String replacePartNumber = (String) text.get("replacePartPicker$label$");
			String originPartNumber = (String) text.get("originPartPicker$label$");
			String substitutionAmountName = (String) text.get("substitutionAmountName");
			// 判断单位是否合法
			Double amount = null;
			if (StrUtil.isNotBlank(substitutionAmountName)) {
				amount = Double.valueOf(substitutionAmountName);
			}
			String replaceType = (String) radio.get("radio");
			ArrayList selected = nmCommandBean.getSelected();
			System.out.println("selected = " + selected);
			for (Object object : selected) {
				if (object instanceof NmContext) {
					NmContext context = (NmContext) object;
					NmOid targetOid = context.getTargetOid();
					System.out.println("targetOid = " + targetOid);
					// 得到相关的物料
					String partNumber = targetOid.toString().split("\\|")[0];
					String partView = targetOid.toString().split("\\|")[1];
					WTPartMaster partMaster = CommonPartHelper.getWTPartMasterByNumber(partNumber);
					WTPart componentPart = CommonPartHelper.findLatestWTPartByMasterAndView(partMaster, partView);
					System.out.println("componentPart = " + componentPart);
					try {
						Workable workable = handlerReplaceAndSubstitution(componentPart, originPartNumber,
								replacePartNumber, replaceType, amount);
						if (workable != null) {
							workables.add(workable);
						}
					} catch (Exception e) {
						e.printStackTrace();
						errors.add(e.getMessage());
						// 回退之前的所有版本
						for (Workable workable : workables) {
							WorkInProgressHelper.service.undoCheckout(workable);
						}
					}
				}
			}
			for (Workable workable : workables) {
				// 涉及的原料和之前的物料
				ToolUtils.checkin(workable, "批量更改检入BOM");
			}
			handlerProcessMessage(formResult, "操作成功", true);
		} catch (Exception e) {
			e.printStackTrace();
			errors.add(e.getMessage());
		} finally {
			SessionServerHelper.manager.setAccessEnforced(flag);
		}
		if (CollUtil.isNotEmpty(errors)) {
			handlerProcessMessage(formResult, errors.toString(), false);
		}
		return formResult;
	}

	/**
	 * 处理程序替换和替换
	 *
	 * @param componentPart
	 *            组件
	 * @param originNumber
	 *            原产地编号
	 * @param replaceNumber
	 *            替换编号
	 * @param replaceType
	 *            替换类型
	 * @param personInputAmount
	 *            用户输入数量
	 */
	private static Workable handlerReplaceAndSubstitution(WTPart componentPart, String originNumber,
			String replaceNumber,
			String replaceType, Double personInputAmount) throws WTException {
		Workable workable = null;
		try {
			System.out.println("ReplaceBomLinkProcess.handlerReplaceAndSubstitution");
			System.out.println("componentPart = " + componentPart + ", originNumber = " + originNumber
					+ ", replaceNumber = " + replaceNumber + ", replaceType = " + replaceType);
			WTPartMaster originMaster = CommonPartHelper.getWTPartMasterByNumber(originNumber);
			WTPartMaster replaceMaster = CommonPartHelper.getWTPartMasterByNumber(replaceNumber);
			System.out.println("replaceMaster = " + replaceMaster);
			System.out.println("originMaster = " + originMaster);
			if (originMaster == null || replaceMaster == null) {
				return null;
			}
			String originNumberPrefix = originNumber.substring(0, 2);
			System.out.println("originNumberPrefix = " + originNumberPrefix);
			String replaceNumberPrefix = replaceNumber.substring(0, 2);
			System.out.println("replaceNumberPrefix = " + replaceNumberPrefix);
			if (!originNumberPrefix.equals(replaceNumberPrefix)) {
				throw new WTException("操作异常：当前选择的相关物料不是相同类型");
			}
			WTPart originLatestPart;
			WTPart replaceLatestPart = null;
			if (originNumberPrefix.equals("5A") && replaceNumberPrefix.equals("5A")) {
				originLatestPart = CommonPartHelper.findLatestWTPartByMasterAndView(originMaster,
						componentPart.getViewName());
				replaceLatestPart = CommonPartHelper.findLatestWTPartByMasterAndView(replaceMaster,
						componentPart.getViewName());
			} else {
				originLatestPart = CommonPartHelper.findLatestWTPartByMasterAndView(originMaster, "Design");
				replaceLatestPart = CommonPartHelper.findLatestWTPartByMasterAndView(replaceMaster, "Design");
			}
			// 查询Link
			WTPartUsageLink link = CommonPartHelper.findWTPartUsageLink(componentPart, originLatestPart);
			if (link == null) {
				throw new WTException(
						String.format("操作异常：当前受影响源物料{%s}没有和选择的6A{%s}存在关联关系", originNumber, componentPart.getNumber()));
			}
			// 判断当前物料状态
			workable = ToolUtils.checkout(componentPart, "批量更改检出BOM");
			WTPart componentWorkCopy = (WTPart) workable;
			switch (replaceType) {
				case "replace":
					System.out.println("进入替换结构相关");
					WTPartUsageLink checkoutVersionLink = CommonPartHelper.findWTPartUsageLink(componentWorkCopy,
							originLatestPart);
					QueryResult result = PersistenceServerHelper.manager.query(WTPartUsageLink.class, componentWorkCopy,
							WTPartUsageLink.USED_BY_ROLE,
							replaceMaster);
					System.out.println("result = " + result.size());
					if (result.size() > 0) {
						throw new WTException(
								String.format("存在重复绑定替换 父部件{%s}  -> 子件{%s}", componentWorkCopy.getNumber(),
										replaceMaster.getNumber()));
					} else {
						WTPartUsageLink replaceLink = WTPartUsageLink.newWTPartUsageLink(componentWorkCopy,
								replaceMaster);
						replaceLink.setQuantity(checkoutVersionLink.getQuantity());
						PersistenceHelper.manager.save(replaceLink);
						PersistenceHelper.manager.delete(checkoutVersionLink);
						return workable;
					}
				case "substitution":
					WTPartUsageLink originLink = CommonPartHelper.findWTPartUsageLink(componentWorkCopy,
							originLatestPart);
					System.out.println("originLink = " + originLink);
					if (originLink == null) {
						return workable;
					}
					QueryResult queryResult = PersistenceServerHelper.manager.query(WTPartSubstituteLink.class, link,
							WTPartSubstituteLink.SUBSTITUTE_FOR_ROLE, replaceMaster);
					if (queryResult.size() > 0) {
						throw new WTException(
								String.format("存在重复绑定替代 父部建{%s}  -> 子件{%s}", componentWorkCopy.getNumber(),
										replaceMaster.getNumber()));
					} else {
						WTPartSubstituteLink wtPartSubstituteLink = WTPartSubstituteLink.newWTPartSubstituteLink(
								originLink,
								replaceMaster);
						Quantity quantity = originLink.getQuantity();
						// 更新数量
						if (personInputAmount != null) {
							quantity.setAmount(personInputAmount);
						}
						SubstituteQuantity substituteQuantity = SubstituteQuantity.newSubstituteQuantity(
								quantity.getAmount(),
								quantity.getUnit());
						wtPartSubstituteLink.setQuantity(substituteQuantity);
						PersistenceHelper.manager.save(wtPartSubstituteLink);
						System.out.println("保存成功替代");
						return workable;
					}
				case "deleteSubstitution":
					WTPartUsageLink usageLink = CommonPartHelper.findWTPartUsageLink(componentWorkCopy,
							originLatestPart);
					QueryResult substituteFor = PersistenceHelper.manager.find(WTPartSubstituteLink.class,
							usageLink,
							"substituteFor", replaceMaster);
					if (substituteFor.size() < 1) {
						throw new WTException("当前替换件不存在，无法进行删除 主键编号： " + originLatestPart.getNumber() +
								" => 替代件编号：" + replaceMaster.getNumber());
					} else {
						if (substituteFor.hasMoreElements()) {
							WTPartSubstituteLink targetLink = (WTPartSubstituteLink) substituteFor.nextElement();
							if (targetLink != null) {
								PersistenceHelper.manager.delete(targetLink);
								return workable;
							}
						}
					}
					break;
				case "mainChangeSubstitute":
					WTPartUsageLink originPartLink = CommonPartHelper.findWTPartUsageLink(componentWorkCopy,
							originLatestPart);
					System.out.println("originLink = " + originPartLink);
					if (originPartLink == null) {
						return workable;
					}
					QueryResult substituteLinkQR = PersistenceHelper.manager.find(WTPartSubstituteLink.class,
							originPartLink,
							"substituteFor", replaceMaster);
					if (substituteLinkQR.size() < 1) {
						throw new WTException("当前替换件不存在，无法进行替换 主键编号： " + originLatestPart.getNumber() +
								" => 替代件编号：" + replaceMaster.getNumber());
					} else {
						if (substituteLinkQR.hasMoreElements()) {
							WTPartSubstituteLink targetLink = (WTPartSubstituteLink) substituteLinkQR.nextElement();
							if (targetLink != null) {
								try {
									WTPartUsageLink wtPartUsageLink = WTPartUsageLink
											.newWTPartUsageLink(componentWorkCopy, replaceMaster);
									SubstituteQuantity quantity = targetLink.getQuantity();
									if (quantity != null) {
										QuantityUnit unit = quantity.getUnit();
										System.out.println("unit = " + unit);
										Double amount = quantity.getAmount();
										System.out.println("amount = " + amount);
										if (amount != null) {
											wtPartUsageLink.setQuantity(
													Quantity.newQuantity(amount, replaceMaster.getDefaultUnit()));
										}
									}
									PersistenceHelper.manager.delete(targetLink);
									PersistenceHelper.manager.save(wtPartUsageLink);
									// 处理之前的主件关系
									WTPartSubstituteLink newSubstituteLink = WTPartSubstituteLink
											.newWTPartSubstituteLink(wtPartUsageLink, originMaster);
									Quantity linkQuantity = originPartLink.getQuantity();
									QuantityUnit unit = linkQuantity.getUnit();
									System.out.println("linkQuantity = " + linkQuantity);
									Double amount = linkQuantity.getAmount();
									wtPartUsageLink.setQuantity(Quantity.newQuantity(amount, unit));
									PersistenceHelper.manager.delete(originPartLink);
									PersistenceHelper.manager.save(newSubstituteLink);
									return workable;
									// ts.commit();
								} catch (Exception e) {
									e.printStackTrace();
									// ts.rollback();
								}
							}
						}
					}
					break;
			}
		} catch (Exception e) {
			throw new WTException(e.getMessage());
		}
		return null;
	}

	/**
	 * 处理程序进程消息
	 *
	 * @param result
	 *            结果
	 * @param msg
	 *            信息
	 * @param isSuccess
	 * @throws WTException
	 *             WTException
	 */
	private void handlerProcessMessage(FormResult result, String msg, boolean isSuccess) throws WTException {
		if (!isSuccess) {
			result.addFeedbackMessage(
					new FeedbackMessage(FeedbackType.FAILURE, SessionHelper.getLocale(), msg, null, null, null));
			result.setStatus(FormProcessingStatus.FAILURE);
		} else {
			result.addFeedbackMessage(
					new FeedbackMessage(FeedbackType.SUCCESS, SessionHelper.getLocale(), msg, null, null, null));
			result.setStatus(FormProcessingStatus.SUCCESS);
		}
	}
}
