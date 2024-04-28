//package ext.ziang.doc.datautility;
//
//import com.ptc.core.components.descriptor.ModelContext;
//import com.ptc.core.components.factory.AbstractDataUtility;
//import com.ptc.windchill.enterprise.attachments.dataUtilities.AttachmentsNameDataUtility;
//
//import ext.ziang.common.config.PropertiesHelper;
//import wt.doc.WTDocument;
//import wt.type.TypedUtility;
//import wt.util.WTException;
//
///**
// * 验证 DOC 主要内容数据实用程序
// *
// * @author anzhen
// * @date 2024/04/28
// */
//public class ValidateDocPrimaryContentDataUtility extends AbstractDataUtility {
//	/**
//	 * 读取配置文件
//	 */
//	private static PropertiesHelper helper = PropertiesHelper.getInstance("commonConfig.properties");
//	/**
//	 * 配置文件类型名称
//	 */
//	public static String TYPE_NAME = helper.getValueByKey("doc.type");
//
//	/**
//	 * 获取数据值
//	 *
//	 * @param contentId
//	 *            内容 ID
//	 * @param object
//	 *            对象
//	 * @param modelContext
//	 *            模型上下文
//	 * @return {@link Object}
//	 * @throws WTException
//	 *             WT异常
//	 */
//	@Override
//	public Object getDataValue(String contentId, Object object, ModelContext modelContext) throws WTException {
//		if (object instanceof WTDocument) {
//			// 判断类型是否为可下载主内容类型
//			WTDocument document = (WTDocument) object;
//			String typename = TypedUtility.getTypeIdentifier(document).getTypename();
//			if (typename.contains(TYPE_NAME)) {
//				return null;
//			}
//			AttachmentsNameDataUtility attachmentsNameDataUtility = new AttachmentsNameDataUtility();
//			return attachmentsNameDataUtility.getDataValue(contentId, object, modelContext);
//		} else {
//			AttachmentsNameDataUtility attachmentsNameDataUtility = new AttachmentsNameDataUtility();
//			return attachmentsNameDataUtility.getDataValue(contentId, object, modelContext);
//		}
//	}
//}
