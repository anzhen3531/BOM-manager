//package ext.ziang.part.pciker;
//
//import java.util.Map;
//
//import com.ptc.core.components.rendering.PickerRenderConfigs;
//import com.ptc.jca.mvc.components.AbstractGenericPickerConfig;
//import com.ptc.netmarkets.util.beans.NmCommandBean;
//
//import wt.util.WTException;
//
//public class StandardPickerConfig extends AbstractGenericPickerConfig {
//
//	public static final String PICKER_ID = "StandardPickerConfig";
//	public static final String PICKER_TITLE = "Test Picker";
//	public static final String PICKER_CLASS = "wt.part.WTPart";
//	public static final String PICKER_MULTI_SELECT = "true";
//	public static final String PICKER_INLINE = "false";
//	public static final String PICKER_MULTI_SELECT_LABEL = "Multi-Select";
//	public static final String PICKER_INLINE_LABEL = "false";
//	public static final String PICKER_CALLBACK = "donothing";
//
//
//	public StandardPickerConfig() {
//	}
//
//	public String getMultiSelect(NmCommandBean nmCommandBean) {
//		return PICKER_MULTI_SELECT;
//	}
//
//	public String getInline(NmCommandBean nmCommandBean) {
//		return PICKER_INLINE;
//	}
//
//	public String getPickerTitle(NmCommandBean nmCommandBean) {
//		return PICKER_TITLE;
//	}
//
//	public String getObjectType(NmCommandBean nmCommandBean) {
//		// try {
//		// return nmCommandBean.getPrimaryOid().getClass().getName();
//		// } catch (WTException e) {
//		// return Persistable.class.getName();
//		// }
//		return PICKER_CLASS;
//	}
//
//	public static void setPickerProperties(String params, NmCommandBean nmCommandBean, Map<Object, Object> paramsMap) {
//		PickerRenderConfigs.setDefaultPickerProperty(paramsMap, PickerRenderConfigs.PICKER_ID, PICKER_ID);
//		PickerRenderConfigs.setDefaultPickerProperty(paramsMap, PickerRenderConfigs.PICKER_TITLE, PICKER_TITLE);
//		PickerRenderConfigs.setDefaultPickerProperty(paramsMap, PickerRenderConfigs.OBJECT_TYPE, PICKER_CLASS);
//		PickerRenderConfigs.setDefaultPickerProperty(paramsMap, PickerRenderConfigs.READ_ONLY_TEXTBOX, "false");
//		PickerRenderConfigs.setDefaultPickerProperty(paramsMap, PickerRenderConfigs.INCLUDE_TII, "true");
//		PickerRenderConfigs.setDefaultPickerProperty(paramsMap, PickerRenderConfigs.PICKER_CALLBACK, PICKER_CALLBACK);
//	}
//
//	/**
//	 * 获取显示建议
//	 *
//	 * @param nmCommandBean
//	 *            nm 命令 bean
//	 * @return {@link String}
//	 */
//	public String getShowSuggestion(NmCommandBean nmCommandBean) {
//		return "true";
//	}
//
//	/**
//	 * 获取建议服务密钥
//	 *
//	 * @param nmCommandBean
//	 *            nm 命令 bean
//	 * @return {@link String}
//	 */
//	public String getSuggestServiceKey(NmCommandBean nmCommandBean) {
//		return "StandardPartPickerSuggestable";
//	}
//
//	/**
//	 * 获取标签
//	 *
//	 * @param nmCommandBean
//	 *            nm 命令 bean
//	 * @return {@link String}
//	 * @throws WTException
//	 *             WT异常
//	 */
//	public String getLabel(NmCommandBean nmCommandBean) throws WTException {
//		return "Bean Picker Suggestable Test (WTPart)";
//	}
//}
