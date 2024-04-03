package ext.ziang.common.util;

import com.ptc.core.managedcollection.ManagedCollectionIdentity;
import com.ptc.core.meta.common.IdentifierFactory;
import com.ptc.core.meta.common.TypeInstanceIdentifier;
import com.ptc.core.meta.server.TypeIdentifierUtility;
import com.ptc.windchill.classproxy.WorkPackageClassProxy;
import com.ptc.windchill.mpml.MPMLinkHelper;
import com.ptc.windchill.option.model.ChoiceMaster;
import com.ptc.windchill.option.model.ExpressionAliasMaster;
import com.ptc.windchill.option.model.IndependentAssignedExpressionMaster;
import com.ptc.windchill.option.model.OptionMaster;
import com.ptc.windchill.option.model.OptionSetMaster;
import com.ptc.windchill.option.service.OptionHelper;
import com.ptc.wpcfg.doc.DocHelper;
import com.ptc.wpcfg.doc.VariantSpecMaster;

import wt.access.agreement.AgreementHelper;
import wt.access.agreement.AuthorizationAgreementMaster;
import wt.change2.ChangeHelper2;
import wt.change2.ChangeItemIfc;
import wt.doc.WTDocument;
import wt.doc.WTDocumentHelper;
import wt.doc.WTDocumentMaster;
import wt.facade.mpmlink.MPMLinkFacade;
import wt.facade.persistedcollection.ManagedCollection;
import wt.fc.Identified;
import wt.fc.IdentityHelper;
import wt.fc.ObjectReference;
import wt.fc.Persistable;
import wt.org.WTOrganization;
import wt.part.WTPartHelper;
import wt.part.WTPartMaster;
import wt.part.alternaterep.WTPartAlternateRepMaster;
import wt.part.alternaterep.service.WTPartAlternateRepService;
import wt.services.ServiceFactory;
import wt.services.applicationcontext.implementation.DefaultServiceProvider;
import wt.util.InstalledProperties;
import wt.util.WTException;
import wt.vc.Iterated;
import wt.vc.Mastered;
import wt.vc.baseline.ManagedBaseline;
import wt.vc.baseline.ManagedBaselineIdentity;

/**
 * 自定义公共实用程序
 *
 * @author anzhen
 * @date 2024/04/03
 */
public class CustomCommonUtil {
	private static final IdentifierFactory DEFAULT_IDENTIFIER_FACTORY = (IdentifierFactory) DefaultServiceProvider
			.getService(IdentifierFactory.class, "default");

	/**
	 * 按对象更新名称和编号
	 *
	 * @param name
	 *            名称
	 * @param number
	 *            编码
	 * @param orgId
	 *            组织 ID
	 * @param persistable
	 *            实例对象
	 */
	public static void updateNameAndNumberByObject(Persistable persistable, String name, String number, String orgId) {
		Identified master = null;
		if (persistable instanceof Iterated) {
			Iterated iterated = (Iterated) persistable;
			master = (Identified) iterated.getMaster();
		} else if (persistable instanceof Identified) {
			master = (Identified) persistable;
		}
		try {
			if (master != null) {
				WTOrganization organization = getWTOrganization(orgId);
				if (master instanceof WTPartMaster) {
					WTPartHelper.service.changeWTPartMasterIdentity((WTPartMaster) master, name, number, organization);
				} else if (master instanceof WTPartAlternateRepMaster) {
					WTPartAlternateRepService alternateRepService = ServiceFactory
							.getService(WTPartAlternateRepService.class);
					alternateRepService.updateWTPartAlternateRepMasterIdentity((WTPartAlternateRepMaster) master, name,
							number,
							organization);
				} else if (master instanceof AuthorizationAgreementMaster) {
					AgreementHelper.service.changeAuthorizationAgreementMasterIdentity(
							(AuthorizationAgreementMaster) master, name, number);
				} else if (persistable instanceof ChangeItemIfc && master instanceof Mastered) {
					ChangeHelper2.service.changeChangeItemMasterIdentity((Mastered) master, name, number, organization);
				} else if (WorkPackageClassProxy.isWorkPackageMaster(persistable)
						|| WorkPackageClassProxy.isWorkPackage(persistable) && master instanceof Mastered) {
					WorkPackageClassProxy.changeWorkPackageMasterIdentity((Mastered) master, name, number,
							organization);
				} else if (master instanceof WTDocumentMaster && persistable instanceof WTDocument) {
					// 更改文档名称
					boolean flag = true;
					WTDocument document = (WTDocument) persistable;
					CommonLog.printLog("WTDocument object:" + document);
					String var17 = number != null && !number.isEmpty() ? number : document.getNumber();
					if (document.isTemplated() && !document.getName().equals(name)) {
						flag = WTDocumentHelper.service.validDocTemplateIdentity(name, var17,
								document.getContainerName());
					}
					CommonLog.printLog("perform Rename:" + flag);
					if (flag) {
						WTDocumentHelper.service.changeWTDocumentMasterIdentity((WTDocumentMaster) master, name, var17,
								organization);
					}
				} else if (master instanceof ManagedBaseline) {
					// 更改基线名称
					ManagedBaseline baseline = (ManagedBaseline) master;
					ManagedBaselineIdentity baselineIdentity = ManagedBaselineIdentity
							.newManagedBaselineIdentity(baseline);
					baselineIdentity.setName(name != null && !name.isEmpty() ? name : baseline.getName());
					baselineIdentity.setNumber(number != null && !number.isEmpty() ? number : baseline.getNumber());
					if (!baselineIdentity.getName().equals(baseline.getName())
							|| !baselineIdentity.getNumber().equals(baseline.getNumber())) {
						IdentityHelper.service.changeIdentity(master, baselineIdentity);
						CommonLog.printLog("Set baseline identity");
					}
				} else if (master instanceof ManagedCollection) {
					// 更改管理集合
					ManagedCollection managedCollection = (ManagedCollection) master;
					ManagedCollectionIdentity collectionIdentity = ManagedCollectionIdentity
							.newManagedCollectionIdentity(managedCollection);
					collectionIdentity.setName(name != null && !name.isEmpty() ? name : managedCollection.getName());
					collectionIdentity
							.setNumber(number != null && !number.isEmpty() ? number : managedCollection.getNumber());
					if (!collectionIdentity.getName().equals(managedCollection.getName())
							|| !collectionIdentity.getNumber().equals(managedCollection.getNumber())) {
						IdentityHelper.service.changeIdentity(master, collectionIdentity);
						CommonLog.printLog("Set managed collection identity");
					}
				} else if (master instanceof OptionSetMaster) {
					// 更改选项集名称
					OptionSetMaster optionSetMaster = (OptionSetMaster) master;
					OptionHelper.service.changeOptionSetMasterIdentity(optionSetMaster, name);
					OptionHelper.service.dispatchVetoableEvent("RENAME_OPTION_SET", master);
				} else if (master instanceof OptionMaster) {
					// 更改选项名称
					OptionMaster optionMaster = (OptionMaster) master;
					OptionHelper.service.changeOptionMasterIdentity(optionMaster, name);
					OptionHelper.service.dispatchVetoableEvent("RENAME_OPTION", master);
				} else if (master instanceof ExpressionAliasMaster) {
					ExpressionAliasMaster expressionAliasMaster = (ExpressionAliasMaster) master;
					OptionHelper.service.changeExpressionAliasMasterIdentity(expressionAliasMaster, name, number);
				} else if (master instanceof ChoiceMaster) {
					ChoiceMaster choiceMaster = (ChoiceMaster) master;
					OptionHelper.service.changeChoiceMasterIdentity(choiceMaster, name, number);
					OptionHelper.service.dispatchVetoableEvent("RENAME_CHOICE", master);
				} else if (master instanceof IndependentAssignedExpressionMaster) {
					IndependentAssignedExpressionMaster independentAssignedExpressionMaster = (IndependentAssignedExpressionMaster) master;
					OptionHelper.service.changeIAEMasterIdentity(independentAssignedExpressionMaster, name, number);
				} else {
					// 判断是否为工艺资源的名称修改
					if (MPMLinkFacade.isMPMLinkInstance(master)) {
						MPMLinkHelper.service.changeMPMObjectMasterIdentity((Mastered) master, name, number);
					} else {
						if (InstalledProperties.isInstalled("Windchill.PDMLink") && master.getClass()
								.isAssignableFrom(Class.forName("com.ptc.wpcfg.doc.VariantSpecMaster"))) {
							DocHelper.service.changeVariantSpecMasterIdentity((VariantSpecMaster) master, name, number,
									organization);
						}
					}
				}
			}
		} catch (Exception e) {
			CommonLog.printLog("CustomCommonUtil.updateNameAndNumberByObject  error ====> " + e.getMessage());
		}
	}

	/**
	 * 获取 WTOrganization
	 *
	 * @param orgId
	 *            组织 ID
	 * @return {@link WTOrganization}
	 * @throws WTException
	 *             WT异常
	 */
	private static WTOrganization getWTOrganization(String orgId) throws WTException {
		WTOrganization organization = null;
		if (orgId != null && !orgId.isEmpty()) {
			TypeInstanceIdentifier typeInstanceIdentifier = (TypeInstanceIdentifier) DEFAULT_IDENTIFIER_FACTORY
					.get(orgId);
			if (typeInstanceIdentifier != null) {
				ObjectReference objectReference = TypeIdentifierUtility.getObjectReference(typeInstanceIdentifier);
				Persistable persistable = objectReference.getObject();
				if (persistable instanceof WTOrganization) {
					organization = (WTOrganization) persistable;
				}
			}
		}
		return organization;
	}

}
