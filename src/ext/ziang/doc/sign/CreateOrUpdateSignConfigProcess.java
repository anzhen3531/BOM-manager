package ext.ziang.doc.sign;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.DefaultObjectFormProcessor;
import com.ptc.core.components.forms.FormResult;
import com.ptc.core.meta.common.TypeIdentifier;
import com.ptc.core.meta.common.impl.DefaultIdentifierFactory;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import ext.ziang.common.util.LoggerHelper;
import ext.ziang.common.util.CommonProcessUtil;
import ext.ziang.doc.sign.helper.ElectronicSignatureConfigHelper;
import ext.ziang.model.ElectronicSignatureConfig;
import wt.type.TypedUtilityService;
import wt.util.WTException;

import javax.servlet.http.HttpServletRequest;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * 创建或更新签名配置过程
 *
 * @author ander
 * @date 2024/05/20
 *       ext.ziang.doc.sign.CreateOrUpdateSignConfigProcess
 */
public class CreateOrUpdateSignConfigProcess extends DefaultObjectFormProcessor {
	/**
	 * 执行操作
	 * 
	 * @param nmCommandBean
	 *            当前命令对象
	 * @param list
	 *            当前受影响对象列表
	 * @return 操作结果
	 * @throws WTException
	 */
	@Override
	public FormResult doOperation(NmCommandBean nmCommandBean, List<ObjectBean> list) throws WTException {
		HttpServletRequest request = nmCommandBean.getRequest();
		String view = request.getParameter("View");
		FormResult formResult = super.doOperation(nmCommandBean, list);
		HashMap text = nmCommandBean.getText();
		HashMap comboBox = nmCommandBean.getComboBox();
		if (view.equals("CREATE")) {
			ElectronicSignatureConfig signatureConfig = ElectronicSignatureConfig.newElectronicSignatureConfig();
			try {
				ArrayList docTypeArr = (ArrayList) comboBox.get("docType");
				String docType = (String) docTypeArr.get(0);
				String typeDisplay = getDisplayNameByType(docType);
				LoggerHelper.log("typeDisplay = " + typeDisplay);
				// 输出结果：TR自检与评审
				signatureConfig.setDocType(docType);
				signatureConfig.setDocTypeName(typeDisplay);
				signatureConfig.setContentType((String) text.get("contentType"));
				signatureConfig.setSignXIndex((String) text.get("signXIndex"));
				signatureConfig.setSignYIndex((String) text.get("signYIndex"));
				signatureConfig.setStatus(Integer.valueOf((String) text.get("status")));
				signatureConfig.setWorkItemName((String) text.get("workItemName"));
				signatureConfig.setExtendedField((String) text.get("extendedField"));
				signatureConfig.setExtendedField1((String) text.get("extendedField1"));
				signatureConfig.setExtendedField2((String) text.get("extendedField2"));
				ElectronicSignatureConfigHelper.createOrUpdate(signatureConfig);
				CommonProcessUtil.handlerProcessMessage(formResult, "创建成功", true);
			} catch (Exception e) {
				e.printStackTrace();
				CommonProcessUtil.handlerProcessMessage(formResult, "创建失败", false);
			}
		} else if (view.equals("UPDATE")) {
			// 获取当前对象
			// 之后通过当前对象查询到相关的
			ObjectBean objectBean = list.get(0);
			Object object = objectBean.getObject();
			if (object instanceof ElectronicSignatureConfig) {
				ElectronicSignatureConfig signatureConfig = (ElectronicSignatureConfig) object;
				try {
					ElectronicSignatureConfigHelper.createOrUpdate(signatureConfig);
					CommonProcessUtil.handlerProcessMessage(formResult, "创建成功", true);
				} catch (Exception e) {
					e.printStackTrace();
					CommonProcessUtil.handlerProcessMessage(formResult, "创建失败", false);
				}
			}
		}
		return formResult;
	}

	/**
	 * 按类型获取显示名称
	 *
	 * @param type
	 *            类型
	 * @return {@link String }
	 */
	public static String getDisplayNameByType(String type) {
		DefaultIdentifierFactory factory = new DefaultIdentifierFactory();
		if (!type.contains("WTTYPE")) {
			type = "WTTYPE|" + type;
		}
		TypeIdentifier tid = factory.newWCTypeIdentifier(type);
		TypedUtilityService service = wt.services.ServiceFactory.getService(TypedUtilityService.class);
		try {
			String displayName = service.getLocalizedTypeName(tid, Locale.CHINA);
			return displayName;
		} catch (RemoteException | WTException e) {
			e.printStackTrace();
		}
		return null;
	}
}
