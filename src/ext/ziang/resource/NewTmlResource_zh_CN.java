//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ext.ziang.resource;

import wt.util.resource.RBEntry;
import wt.util.resource.RBUUID;
import wt.util.resource.WTListResourceBundle;

@RBUUID("com.ptc.tml.NewTmlResource")
public class NewTmlResource_zh_CN extends WTListResourceBundle {
    @RBEntry("文件: 无法删除 {0}，因此无法继续导入进程。")
    public static final String TRANS_PACKAGE_EXCEPTION_CANNOT_DELETE = "TRANS_PACKAGE_EXCEPTION_CANNOT_DELETE";
    @RBEntry("翻译的包: {0} 不包含以下条目: {1}")
    public static final String TRANS_PACKAGE_EXCEPTION_NOT_CONTAIN_FILE = "TRANS_PACKAGE_EXCEPTION_NOT_CONTAIN_FILE";
    @RBEntry("文档不匹配")
    public static final String TRANS_PACKAGE_EXCEPTION_DOCUMENT_MISMATCH = "TRANS_PACKAGE_EXCEPTION_DOCUMENT_MISMATCH";
    @RBEntry("不支持 {0} 语言。请检查“支持的语言”首选项。")
    public static final String TRANS_PREP_EXCEPTION_LANG_LIB_NOT_FOUND = "TRANS_PREP_EXCEPTION_LANG_LIB_NOT_FOUND";
    @RBEntry("找不到翻译库 {0}。请检查“支持的语言”首选项。")
    public static final String TRANS_PREP_EXCEPTION_LANG_LIB_NOT_SUPPORTED = "TRANS_PREP_EXCEPTION_LANG_LIB_NOT_SUPPORTED";
    @RBEntry("现有翻译 {0} ({1}) 处于 {2} 状态。这不是翻译准备中修订版本应具有的状态。")
    public static final String TRANS_PREP_EXCEPTION_TRANSLATION_IMPROPER_STATE = "TRANS_PREP_EXCEPTION_TRANSLATION_IMPROPER_STATE";
    @RBEntry("文档 {0} 不符合指定的配置规范。")
    public static final String TRANS_PREP_EXCEPTION_DOC_CONFIG_SPEC_MISMATCH = "TRANS_PREP_EXCEPTION_DOC_CONFIG_SPEC_MISMATCH";
    @RBEntry("无法转换文档 {0} 的链接，因为该文档未翻译。")
    public static final String TRANS_PREP_EXCEPTION_MASTER_NOT_FOUND = "TRANS_PREP_EXCEPTION_MASTER_NOT_FOUND";
    @RBEntry("翻译包成功")
    public static final String BANNER_SUCCESS_CREATE_TRANSLATION = "BANNER_SUCCESS_CREATE_TRANSLATION";
    @RBEntry("取消翻译包")
    public static final String BANNER_CANCEL_TRANSLATION = "BANNER_CANCEL_TRANSLATION";
    @RBEntry("报价包成功")
    public static final String BANNER_SUCCESS_CREATE_QUOTATION = "BANNER_SUCCESS_CREATE_QUOTATION";
    @RBEntry("翻译包失败")
    public static final String BANNER_FAILURE_CREATE_TRANSLATION = "BANNER_FAILURE_CREATE_TRANSLATION";
    @RBEntry("报价包失败")
    public static final String BANNER_FAILURE_CREATE_QUOTATION = "BANNER_FAILURE_CREATE_QUOTATION";
    @RBEntry("无法创建翻译包。本地语言首选项所含语言与受支持的语言全局枚举值不匹配: {0}")
    public static final String BANNER_FAILURE_INVALID_SUPPORTED_LANG_PREFERENCE_SINGLE = "BANNER_FAILURE_INVALID_SUPPORTED_LANG_PREFERENCE_SINGLE";
    @RBEntry("无法创建翻译包。本地语言首选项所含语言与受支持的语言全局枚举值不匹配: {0}")
    public static final String BANNER_FAILURE_INVALID_SUPPORTED_LANG_PREFERENCE_MULTI = "BANNER_FAILURE_INVALID_SUPPORTED_LANG_PREFERENCE_MULTI";
    @RBEntry("以下语言的翻译包处理已开始: <b>{0}</b>")
    public static final String BANNER_TRANSLATION_SUCCESS_LANGUAGE_LIST = "BANNER_TRANSLATION_SUCCESS_LANGUAGE_LIST";
    @RBEntry("以下语言的报价包处理已开始: <b>{0}</b>")
    public static final String BANNER_QUOTE_SUCCESS_LANGUAGE_LIST = "BANNER_QUOTE_SUCCESS_LANGUAGE_LIST";
    @RBEntry("无法创建以下中枢语言的报价包: <b>{0}</b>")
    public static final String BANNER_QUOTE_PIVOT_FAILURE_LANGUAGE_LIST = "BANNER_QUOTE_PIVOT_FAILURE_LANGUAGE_LIST";
    @RBEntry("翻译时出错 - 请查阅: {0}")
    public static final String BANNER_MESSAGE_LOGFILE_LOC = "BANNER_MESSAGE_LOGFILE_LOC";
    @RBEntry("已开始取消翻译包。")
    public static final String BANNER_MESSAGE_CANCEL_SUCCESSFULL = "BANNER_MESSAGE_CANCEL_SUCCESSFULL";
    @RBEntry("您无法取消翻译包。您没有权限执行此操作; 请向管理员寻求协助。\n\n 无法取消以下包 ID:\n{0}")
    public static final String MESSAGE_CANCEL_NO_PERMISSIONS = "MESSAGE_CANCEL_NO_PERMISSIONS";
    @RBEntry("{0} 的创建者: {1}")
    public static final String NON_CANCELLABLE_BASELINE_DETAIL = "NON_CANCELLABLE_BASELINE_DETAIL";
    @RBEntry("取消翻译包失败。")
    public static final String MESSAGE_CANCEL_FAILED = "MESSAGE_CANCEL_FAILED";
    @RBEntry("无法遍历 SIM 结构。")
    public static final String SIM_DYNAMIC_TRAVERSAL_FAIL = "SIM_DYNAMIC_TRAVERSAL_FAIL";
    @RBEntry("无法从 SIM 结构检索内容。")
    public static final String SIM_DYNAMIC_RETRIEVE_FAIL = "SIM_DYNAMIC_RETRIEVE_FAIL";
    @RBEntry("IBA {0} 不是布尔类型")
    public static final String EXCEPTION_IBA_NOT_BOOL = "EXCEPTION_IBA_NOT_BOOL";
    @RBEntry("IBA {0} 不是浮点类型")
    public static final String EXCEPTION_IBA_NOT_FLOAT = "EXCEPTION_IBA_NOT_FLOAT";
    @RBEntry("不支持的类型: {0}")
    public static final String EXCEPTION_UNSUPPORTED_TYPE = "EXCEPTION_UNSUPPORTED_TYPE";
    @RBEntry("无法更新 IBAHolder")
    public static final String EXCEPTION_UNABLE_TO_UPDATE_IBAHOLDER = "EXCEPTION_UNABLE_TO_UPDATE_IBAHOLDER";
    @RBEntry("IBA {0} 不是整数类型")
    public static final String EXCEPTION_IBA_NOT_INTEGER = "EXCEPTION_IBA_NOT_INTEGER";
    @RBEntry("IBA {0} 不是时间戳类型")
    public static final String EXCEPTION_IBA_NOT_TIMESTAMP = "EXCEPTION_IBA_NOT_TIMESTAMP";
    @RBEntry("IBA {0} 不是字符串类型")
    public static final String EXCEPTION_IBA_NOT_STRING = "EXCEPTION_IBA_NOT_STRING";
    @RBEntry("IBA {0} 不是单位类型")
    public static final String EXCEPTION_IBA_NOT_UNIT = "EXCEPTION_IBA_NOT_UNIT";
    @RBEntry("IBA {0} 不是 URL 类型")
    public static final String EXCEPTION_IBA_NOT_URL = "EXCEPTION_IBA_NOT_URL";
    @RBEntry("具有键 {0} 的首选项不包含 {1} 语言的条目")
    public static final String EXCEPTION_PREFERENCE_NOT_CONTAIN_LANGUAGE = "EXCEPTION_PREFERENCE_NOT_CONTAIN_LANGUAGE";
    @RBEntry("文档 {0} 不符合指定的配置规范。")
    public static final String EXCEPTION_DOCUMENT_UNMATCH_CONFIGSPEC = "EXCEPTION_DOCUMENT_UNMATCH_CONFIGSPEC";
    @RBEntry("无法遍历 SIM 结构。")
    public static final String EXCEPTION_NO_TRAVERSE_SIM_STRUCTURES = "EXCEPTION_NO_TRAVERSE_SIM_STRUCTURES";
    @RBEntry("无法获取 XLIFF 文档。")
    public static final String EXCEPTION_NO_XLIFF_DOCUMENT = "EXCEPTION_NO_XLIFF_DOCUMENT";
    @RBEntry("无法翻译文档 {0} 版本 {1}，不存在占位符。")
    public static final String EXCEPTION_UNABLE_TO_TRANSLATE_NO_PLACEHOLDER = "EXCEPTION_UNABLE_TO_TRANSLATE_NO_PLACEHOLDER";
    @RBEntry("{0} 缺少 XLIFF。")
    public static final String ERROR_MISSING_XLIFF = "ERROR_MISSING_XLIFF";
    @RBEntry("翻译错误报告")
    public static final String ERROR_HEADER_LOG = "ERROR_HEADER_LOG";
    @RBEntry("错误日志位于 {0}")
    public static final String BANNER_LOG_LOCATION = "BANNER_LOG_LOCATION";
    @RBEntry("可翻译内容")
    public static final String TranslatableContentTableLabel = "TranslatableContentTableLabel";
    @RBEntry("翻译表格")
    public static final String TranslationPackageTableLabel = "TranslationPackageTableLabel";
    @RBEntry("目标语言")
    public static final String TARGET_LANGUAGE = "TARGET_LANGUAGE";
    @RBEntry("基线编号")
    public static final String BASELINE_NUMBER = "BASELINE_NUMBER";
    @RBEntry("包 ID")
    public static final String PACKAGE_ID = "PACKAGE_ID";
    @RBEntry("中枢语言")
    public static final String PIVOT = "PIVOT";
    @RBEntry("创建者")
    public static final String CREATOR = "CREATOR";
    @RBEntry("源对象")
    public static final String SOURCE_OBJECT = "SOURCE_OBJECT";
    @RBEntry("源类型")
    public static final String SOURCE_OBJECT_TYPE = "SOURCE_OBJECT_TYPE";
    @RBEntry("可翻译内容")
    public static final String TranslatableContent_ACTION_DESCRIPTION = "translation.translatableContent.description";
    @RBEntry("源语言内容")
    public static final String TRANSLATABLE_CONTENT_BASE_LANGUAGE_CONTENT_COLUMN_LABEL = "TRANSLATABLE_CONTENT_BASE_LANGUAGE_CONTENT_COLUMN_LABEL";
    @RBEntry("翻译")
    public static final String TRANSLATABLE_CONTENT_TRANSLATED_CONTENT_COLUMN_LABEL = "TRANSLATABLE_CONTENT_TRANSLATED_CONTENT_COLUMN_LABEL";
    @RBEntry("语言")
    public static final String LANGUAGE_COLUMN_NAME = "LANGUAGE_COLUMN_NAME";
    @RBEntry("版本")
    public static final String VERSION_COLUMN_NAME = "VERSION_COLUMN_NAME";
    @RBEntry("无法识别文档 '{0}' 的语言")
    public static final String EXCEPTION_UNABLE_TO_IDENTIFY_SOURCE_LANGUAGE = "EXCEPTION_UNABLE_TO_IDENTIFY_SOURCE_LANGUAGE";
    @RBEntry("翻译流程未启动; 找到已检出文档。")
    public static final String TRANSLATION_PROCESS_NOT_STARTED_CHECKED_OUT_DOCUMENTS = "TRANSLATION_PROCESS_NOT_STARTED_CHECKED_OUT_DOCUMENTS";
    @RBEntry("无法将 {0} 类型的对象转换为类型 {1}")
    public static final String EXCEPTION_CAST_ERROR = "EXCEPTION_CAST_ERROR";
    @RBEntry("无法在现有用户中解析用户名 '{0}'")
    public static final String EXCEPTION_UNABLE_TO_RESOLVE_USER = "EXCEPTION_UNABLE_TO_RESOLVE_USER";
    @RBEntry("无法解析会话用户")
    public static final String EXCEPTION_UNABLE_TO_RESOLVE_SESSION_USER = "EXCEPTION_UNABLE_TO_RESOLVE_SESSION_USER";
    @RBEntry("选择翻译包的属性。")
    public static final String SET_UP_TRANSLATION_PACKAGE_HEADER = "SET_UP_TRANSLATION_PACKAGE_HEADER";
    @RBEntry("<b>由于首选项错误，创建翻译包失败。请更正以下首选项:</b>")
    public static final String SET_UP_TRANSLATION_CORRECT_LISTED_PREFERENCE = "SET_UP_TRANSLATION_CORRECT_LISTED_PREFERENCE";
    @RBEntry("请选择一个或多个要取消的包 ID。")
    public static final String CANCEL_TRANSLATION_PACKAGE_HEADER = "CANCEL_TRANSLATION_PACKAGE_HEADER";
    @RBEntry("一个或多个选定的中枢语言翻译包具有依存的目标语言翻译。取消中枢语言也会取消依存翻译包。是否继续?")
    public static final String CANCEL_TRANSLATION_PIVOT_COLLECTION_WARNING = "CANCEL_TRANSLATION_PIVOT_COLLECTION_WARNING";
    @RBEntry("包类型:")
    public static final String SET_UP_TRANSLATION_PACKAGE_TYPE_LABEL = "SET_UP_TRANSLATION_PACKAGE_TYPE_LABEL";
    @RBEntry("中枢语言:")
    public static final String SET_UP_TRANSLATION_PACKAGE_PIVOT_LABEL = "SET_UP_TRANSLATION_PACKAGE_PIVOT_LABEL";
    @RBEntry("翻译")
    public static final String SET_UP_TRANSLATION_PACKAGE_TYPE_TRANSLATION = "SET_UP_TRANSLATION_PACKAGE_TYPE_TRANSLATION";
    @RBEntry("报价")
    public static final String SET_UP_TRANSLATION_PACKAGE_TYPE_QUOTE = "SET_UP_TRANSLATION_PACKAGE_TYPE_QUOTE";
    @RBEntry("目标供应商:")
    public static final String SET_UP_TRANSLATION_VENDOR_LABEL = "SET_UP_TRANSLATION_VENDOR_LABEL";
    @RBEntry("目标语言:")
    public static final String SET_UP_CANCEL_TRANSLATION_LANGUAGES_LABEL = "SET_UP_CANCEL_TRANSLATION_LANGUAGES_LABEL";
    @RBEntry("请选择一个或多个要取消的目标语言。")
    public static final String SET_UP_CANCEL_TRANSLATION_PACKAGE_HEADER = "SET_UP_CANCEL_TRANSLATION_PACKAGE_HEADER";
    @RBEntry("要取消翻译包，必须选择至少一个目标语言。")
    public static final String SET_UP_CANCEL_TRANSLATION_NO_LANG_SELECTED = "SET_UP_CANCEL_TRANSLATION_NO_LANG_SELECTED";
    @RBEntry("目标语言:")
    public static final String SET_UP_TRANSLATION_LANGUAGES_LABEL = "SET_UP_TRANSLATION_LANGUAGES_LABEL";
    @RBEntry("选定文档没有支持的翻译语言。")
    public static final String SET_UP_TRANSLATION_LANGUAGES_NONE = "SET_UP_TRANSLATION_LANGUAGES_NONE";
    @RBEntry("包内容:")
    public static final String SET_UP_TRANSLATION_CONTENT_CHOICE = "SET_UP_TRANSLATION_CONTENT_CHOICE";
    @RBEntry("等待所有内容获得批准")
    public static final String SET_UP_TRANSLATION_CONTENT_WAIT = "SET_UP_TRANSLATION_CONTENT_WAIT";
    @RBEntry("内容已批准。")
    public static final String SET_UP_TRANSLATION_CONTENT_IMMEDIATE = "SET_UP_TRANSLATION_CONTENT_IMMEDIATE";
    @RBEntry("源语言:")
    public static final String SET_UP_TRANSLATION_SOURCE_LANGUAGE_LABEL = "SET_UP_TRANSLATION_SOURCE_LANGUAGE_LABEL";
    @RBEntry("源语言:")
    public static final String SET_UP_TRANSLATION_SOURCE_LANGUAGES_LABEL = "SET_UP_TRANSLATION_SOURCE_LANGUAGES_LABEL";
    @RBEntry("要创建翻译包，必须选择至少一种语言。")
    public static final String SET_UP_TRANSLATION_NO_LANG_SELECTED = "SET_UP_TRANSLATION_NO_LANG_SELECTED";
    @RBEntry("要取消翻译包，必须选择至少一个包 ID。")
    public static final String SET_UP_CANCEL_TRANSLATION_NO_ROW_SELECTED = "SET_UP_CANCEL_TRANSLATION_NO_ROW_SELECTED";
    @RBEntry("用户 '{0}' 对于下列上下文不具备翻译协调员角色: {1}")
    public static final String INACCESSIBLE_LANGUAGES_MESSAGE = "INACCESSIBLE_LANGUAGES_MESSAGE";
    @RBEntry("用户 '{0}' 不属于上下文 '{1}' 的翻译协调员角色，该上下文用于下列语言: {2}")
    public static final String INACCESSIBLE_TRANSLATION_DATA_CONTEXT = "INACCESSIBLE_TRANSLATION_DATA_CONTEXT";
    @RBEntry("无法在上下文“{1}”中获取首选项 \"{0}\" 的值。")
    public static final String UNABLE_TO_GET_CONTAINER_STRING_PREFERENCE_ERROR_MESSAGE = "UNABLE_TO_GET_CONTAINER_STRING_PREFERENCE_ERROR_MESSAGE";
    @RBEntry("无法在站点容器中获取首选项 '{0}' 的值。")
    public static final String UNABLE_TO_GET_SITE_STRING_PREFERENCE_ERROR_MESSAGE = "UNABLE_TO_GET_SITE_STRING_PREFERENCE_ERROR_MESSAGE";
    @RBEntry("无法在上下文“{1}”中获取首选项 \"{0}\" 的值。")
    public static final String UNABLE_TO_GET_CONTEXT_BOOLEAN_PREFERENCE_ERROR_MESSAGE = "UNABLE_TO_GET_CONTEXT_BOOLEAN_PREFERENCE_ERROR_MESSAGE";
    @RBEntry("无法在上下文“{1}”中获取整数首选项 \"{0}\" 的值。")
    public static final String INT_PREFERENCE_CONTAINER_UNAVAILABLE_ERROR_MESSAGE = "INT_PREFERENCE_CONTAINER_UNAVAILABLE_ERROR_MESSAGE";
    @RBEntry("上下文“{1}”中的首选项 \"{0}\" 为空或空白; 无法将其转换为 int。")
    public static final String INT_PREFERENCE_CONTAINER_NULL_ERROR_MESSAGE = "INT_PREFERENCE_CONTAINER_NULL_ERROR_MESSAGE";
    @RBEntry("无法在站点上下文中获取整数首选项 \"{0}\" 的值。")
    public static final String INT_PREFERENCE_SITE_UNAVAILABLE_ERROR_MESSAGE = "INT_PREFERENCE_SITE_UNAVAILABLE_ERROR_MESSAGE";
    @RBEntry("用于导入间隔的数值格式有误，已使用 '{0}' 代替。")
    public static final String POORLY_FORMATTED_IMPORT_INTERVAL_ERROR_MESSAGE = "POORLY_FORMATTED_IMPORT_INTERVAL_ERROR_MESSAGE";
    @RBEntry("未在翻译压缩包 {0} 中找到内容清单文件")
    public static final String EXCEPTION_UNABLE_TO_FIND_MANIFEST_FILE = "EXCEPTION_UNABLE_TO_FIND_MANIFEST_FILE";
    @RBEntry("无法在翻译内容清单文件中找到以下翻译 zip 包的 <contextContainerOid> 标记: {0}")
    public static final String EXCEPTION_UNABLE_TO_CONTEXT_CONTAINER_OID_TAG = "EXCEPTION_UNABLE_TO_CONTEXT_CONTAINER_OID_TAG";
    @RBEntry("此翻译包中需要以下文档，但未找到这些文档: {0}")
    public static final String EXCEPTION_MISSING_FILES_IN_PACKAGE = "EXCEPTION_MISSING_FILES_IN_PACKAGE";
    @RBEntry("链接至 xliff 文件的类型不符合要求: {0}")
    public static final String EXCEPTION_UNKOWN_XLIFF_SOURCE_TYPE = "EXCEPTION_UNKOWN_XLIFF_SOURCE_TYPE";
    @RBEntry("无法检出并更新文档 {0}; 该文档不是最新的。")
    public static final String EXCEPTION_UNABLE_TO_UPDATE_NON_LATEST_ITERATION_OR_REVISION = "EXCEPTION_UNABLE_TO_UPDATE_NON_LATEST_ITERATION_OR_REVISION";
    @RBEntry("未找到翻译导入包 '{0}'")
    public static final String EXCEPTION_UNABLE_TO_FIND_PACKAGE_FILE = "EXCEPTION_UNABLE_TO_FIND_PACKAGE_FILE";
    @RBEntry("无法重命名翻译导入包，目标已存在: '{0}'")
    public static final String EXCEPTION_NEW_PACKAGE_FILE_NAME_ALREADY_EXISTS = "EXCEPTION_NEW_PACKAGE_FILE_NAME_ALREADY_EXISTS";
    @RBEntry("无法重命名翻译导入包: '{0}'")
    public static final String EXCEPTION_UNABLE_TO_RENAME_TRANSLATION_PACKAGE = "EXCEPTION_UNABLE_TO_RENAME_TRANSLATION_PACKAGE";
    @RBEntry("无法重命名导出文件夹中的已完成翻译包: 原名称='{0}' 新名称='{1}'")
    public static final String ERROR_UNABLE_TO_MOVE_PACKAGE = "ERROR_UNABLE_TO_MOVE_PACKAGE";
    @RBEntry("翻译集合中不允许插入操作")
    public static final String EXCEPTION_INSERT_NOTALLOWED_ON_TC = "EXCEPTION_INSERT_NOTALLOWED_ON_TC";
    @RBEntry("翻译中枢集合中不允许删除操作")
    public static final String EXCEPTION_REMOVE_NOTALLOWED_ON_TPC = "EXCEPTION_REMOVE_NOTALLOWED_ON_TPC";
    @RBEntry("此安装不支持 DOM Level 3 LS")
    public static final String EXCEPTION_DOM_OP_NOT_SUPPORTED = "EXCEPTION_DOM_OP_NOT_SUPPORTED";
    @RBEntry("无法解决不可翻译的子文档")
    public static final String TRANS_PREP_EXCEPTION_RESOLVE_DOC_CHILD = "TRANS_PREP_EXCEPTION_RESOLVE_DOC_CHILD";
    @RBEntry("不受支持的 OID 类型: {0}")
    public static final String UNSUPPORTED_OID_TYPE = "UNSUPPORTED_OID_TYPE";
    @RBEntry("不受支持的子类型: {0}")
    public static final String UNSUPPORTED_CHILD_TYPE = "UNSUPPORTED_CHILD_TYPE";
    @RBEntry("未找到可翻译的有效文档。")
    public static final String NO_DOCUMENTS_TO_TRANSLATE = "NO_DOCUMENTS_TO_TRANSLATE";
    @RBEntry("未找到可导入的有效文档。")
    public static final String NO_DOCUMENTS_TO_IMPORT = "NO_DOCUMENTS_TO_IMPORT";
    @RBEntry("无法解析以下 XML 文档:")
    public static final String UNABLE_TO_PARSE_XML_DOCS = "UNABLE_TO_PARSE_XML_DOCS";
    @RBEntry("翻译包中的文件")
    public static final String ERROR_HEADER_FILE_IN_PACKAGE = "ERROR_HEADER_FILE_IN_PACKAGE";
    @RBEntry("动态文档名称和编号")
    public static final String ERROR_HEADER_DD_NAME_NUMBER = "ERROR_HEADER_DD_NAME_NUMBER";
    @RBEntry("翻译集合不能有负的“剩余接受计数”: {0}")
    public static final String EXCEPTION_SET_NEGATIVE_REMAINING_ACCEPTANCE_COUNT = "EXCEPTION_SET_NEGATIVE_REMAINING_ACCEPTANCE_COUNT";
    @RBEntry("必须从子类型为 TranslationCollection 的 ManagedCollection 构造翻译集合，类型 {0} 不正确")
    public static final String EXCEPTION_MC_NOT_TC = "EXCEPTION_MC_NOT_TC";
    @RBEntry("集合 OID 无效，指向 NULL 对象")
    public static final String EXCEPTION_NULL_OID_COLLECTION_REFERENCE = "EXCEPTION_NULL_OID_COLLECTION_REFERENCE";
    @RBEntry("集合 OID 无效; 期望 TranslationCollection，实际对象为 {0}")
    public static final String EXCEPTION_UNEXPECTED_OID_COLLECTION_REFERENCE = "EXCEPTION_UNEXPECTED_OID_COLLECTION_REFERENCE";
    @RBEntry("属性值格式不正确; 属性类型: {0}")
    public static final String EXCEPTION_POORLY_FORMATTED_ATT_VALUE = "EXCEPTION_POORLY_FORMATTED_ATT_VALUE";
    @RBEntry("必须从子类型正确的 ManagedCollection 构造翻译根集合，类型 {0} 不正确")
    public static final String EXCEPTION_UNEXPECTED_COLLECTION_BASE_TYPE = "EXCEPTION_UNEXPECTED_COLLECTION_BASE_TYPE";
    @RBEntry("无法找到名为 '{0}' 的上下文")
    public static final String UNABLE_TO_FIND_CONTAINER_BY_NAME = "UNABLE_TO_FIND_CONTAINER_BY_NAME";
    @RBEntry("无法识别上下文类型 '{0}'")
    public static final String UNABLE_TO_IDENTIFY_CONTAINER_TYPE = "UNABLE_TO_IDENTIFY_CONTAINER_TYPE";
    @RBEntry("sourceObj 自变量不能为空")
    public static final String EXCEPTION_WFL_UTIL_SOURCEOBJ_NULL = "EXCEPTION_WFL_UTIL_SOURCEOBJ_NULL";
    @RBEntry("sourceObj 自变量必须是 EPMDocument 或 WTPart")
    public static final String EXCEPTION_WFL_UTIL_SOURCEOBJ_WRONG_TYPE = "EXCEPTION_WFL_UTIL_SOURCEOBJ_WRONG_TYPE";
    @RBEntry("contextContainer 自变量不能为空")
    public static final String EXCEPTION_WFL_UTIL_CONTEXTCONTAINER_NULL = "EXCEPTION_WFL_UTIL_CONTEXTCONTAINER_NULL";
    @RBEntry("outputDir 自变量不能为空")
    public static final String EXCEPTION_WFL_UTIL_OUTPUTDIR_NULL = "EXCEPTION_WFL_UTIL_OUTPUTDIR_NULL";
    @RBEntry("outputDir 自变量不能为空白")
    public static final String EXCEPTION_WFL_UTIL_OUTPUTDIR_EMPTY = "EXCEPTION_WFL_UTIL_OUTPUTDIR_EMPTY";
    @RBEntry("outputDir 自变量必须是有效的路径字符串: '{0}'")
    public static final String EXCEPTION_WFL_UTIL_OUTPUTDIR_INVALID_PATH = "EXCEPTION_WFL_UTIL_OUTPUTDIR_INVALID_PATH";
    @RBEntry("outputDir 自变量必须是有效目录的路径: '{0}'")
    public static final String EXCEPTION_WFL_UTIL_INVALID_DIR = "EXCEPTION_WFL_UTIL_INVALID_DIR";
    @RBEntry("用户自变量不能为空")
    public static final String EXCEPTION_WFL_UTIL_USER_NULL = "EXCEPTION_WFL_UTIL_USER_NULL";
    @RBEntry("nc 自变量只能包含 1 个 ConfigSpec，不是 '{0}'")
    public static final String EXCEPTION_WFL_UTIL_TOO_MANY_CONFIGSPECS = "EXCEPTION_WFL_UTIL_TOO_MANY_CONFIGSPECS";
    @RBEntry("由于配置和安全性设置，内容受到限制。")
    public static final String ERROR_LOG_SECURE_DOCS_NOT_PERMITTED = "ERROR_LOG_SECURE_DOCS_NOT_PERMITTED";
    @RBEntry("翻译包失败; 违反访问权限。")
    public static final String ERROR_LOG_ACCESS_VIOLATION = "ERROR_LOG_ACCESS_VIOLATION";
    @RBEntry("无法创建 EPMDocument 的参考")
    public static final String EXCEPTION_UNABLE_TO_CREATE_DOC_REFERENCE = "EXCEPTION_UNABLE_TO_CREATE_DOC_REFERENCE";
    @RBEntry("非正常类型的安全标签对象: {0}")
    public static final String EXCEPTION_UNEXPECTED_SECURITY_LABEL_TYPE = "EXCEPTION_UNEXPECTED_SECURITY_LABEL_TYPE";
    @RBEntry("翻译操控板")
    public static final String TRANSLATIONS_SUMMARY_DESC = "translation.translationSummary.description";
    @RBEntry("翻译内容")
    public static final String TRANSLATIONS_CONTENT_DESC = "translation.translationContent.description";
    @RBEntry("正在翻译")
    public static final String IN_TRANSLATION = "IN_TRANSLATION";
    @RBEntry("已翻译")
    public static final String TRANSLATED = "TRANSLATED";
    @RBEntry("已请求")
    public static final String REQUESTED = "REQUESTED";
    @RBEntry("未请求")
    public static final String NOT_REQUESTED = "NOT_REQUESTED";
    @RBEntry("未请求 - 需要新翻译")
    public static final String NOT_REQUESTED_NEW_TRANSLATION_NEEDED = "NOT_REQUESTED_NEW_TRANSLATION_NEEDED";
    @RBEntry("已取消")
    public static final String CANCELLED = "CANCELLED";
    @RBEntry("文字内容")
    public static final String CONTENT = "CONTENT";
    @RBEntry("图形内容")
    public static final String GRAPHIC_CONTENT = "GRAPHIC_CONTENT";
    @RBEntry("Xliff")
    public static final String XLIFF = "XLIFF";
    @RBEntry("语言")
    public static final String TRANSLATION_SUMMARY_LANGUAGE = "TRANSLATION_SUMMARY_LANGUAGE";
    @RBEntry("状况")
    public static final String TRANSLATION_SUMMARY_STATUS = "TRANSLATION_SUMMARY_STATUS";
    @RBEntry("路径")
    public static final String TRANSLATION_SUMMARY_PATH = "TRANSLATION_SUMMARY_PATH";
    @RBEntry("语言代码")
    public static final String TRANSLATION_SUMMARY_LANGUAGE_CODE = "TRANSLATION_SUMMARY_LANGUAGE_CODE";
    @RBEntry("上次刷新")
    public static final String TRANSLATION_SUMMARY_RERESHED = "TRANSLATION_SUMMARY_RERESHED";
    @RBEntry("要获取翻译状况，必须选择至少一种语言。")
    public static final String SHOW_TRANSLATION_NO_LANG_SELECTED = "SHOW_TRANSLATION_NO_LANG_SELECTED";
    @RBEntry("refresh_tbar.gif")
    public static final String TranslationSummary_ACTION_ICON = "translationSummaryActions.refresh.icon";
    @RBEntry("刷新选定语言的翻译状况")
    public static final String TranslationSummary_ACTION_TOOLTIP = "translationSummaryActions.refresh.tooltip";
    @RBEntry("刷新")
    public static final String TranslationSummary_ACTION_TITLE = "translationSummaryActions.refresh.title";
    @RBEntry("刷新")
    public static final String TranslationSummary_ACTION_DESC = "translationSummaryActions.refresh.description";
    @RBEntry("源名称")
    public static final String TRANSLATION_CONTENT_SOURCE_NAME = "TRANSLATION_CONTENT_SOURCE_NAME";
    @RBEntry("源编号")
    public static final String TRANSLATION_CONTENT_SOURCE_NUMBER = "TRANSLATION_CONTENT_SOURCE_NUMBER";
    @RBEntry("源版本")
    public static final String TRANSLATION_CONTENT_SOURCE_VERSION = "TRANSLATION_CONTENT_SOURCE_VERSION";
    @RBEntry("源上下文")
    public static final String TRANSLATION_CONTENT_SOURCE_CONTEXT = "TRANSLATION_CONTENT_SOURCE_CONTEXT";
    @RBEntry("源语言")
    public static final String TRANSLATION_CONTENT_SOURCE_LANGUAGE = "TRANSLATION_CONTENT_SOURCE_LANGUAGE";
    @RBEntry("源状态")
    public static final String TRANSLATION_CONTENT_SOURCE_STATE = "TRANSLATION_CONTENT_SOURCE_STATE";
    @RBEntry("翻译名称")
    public static final String TRANSLATION_CONTENT_TRANSLATION_NAME = "TRANSLATION_CONTENT_TRANSLATION_NAME";
    @RBEntry("翻译编号")
    public static final String TRANSLATION_CONTENT_TRANSLATION_NUMBER = "TRANSLATION_CONTENT_TRANSLATION_NUMBER";
    @RBEntry("翻译上下文")
    public static final String TRANSLATION_CONTENT_TRANSLATION_CONTEXT = "TRANSLATION_CONTENT_TRANSLATION_CONTEXT";
    @RBEntry("目标语言")
    public static final String TRANSLATION_CONTENT_TRANSLATION_LANGUAGE = "TRANSLATION_CONTENT_TRANSLATION_LANGUAGE";
    @RBEntry("翻译版本")
    public static final String TRANSLATION_CONTENT_TRANSLATION_VERSION = "TRANSLATION_CONTENT_TRANSLATION_VERSION";
    @RBEntry("翻译状况")
    public static final String TRANSLATION_CONTENT_TRANSLATION_STATUS = "TRANSLATION_CONTENT_TRANSLATION_STATUS";
    @RBEntry("翻译状态")
    public static final String TRANSLATION_CONTENT_TRANSLATION_STATE = "TRANSLATION_CONTENT_TRANSLATION_STATE";
    @RBEntry("包 ID")
    public static final String TRANSLATION_CONTENT_PACKAGEID = "TRANSLATION_CONTENT_PACKAGEID";
    @RBEntry("集合 ID")
    public static final String TRANSLATION_CONTENT_COLLECTIONID = "TRANSLATION_CONTENT_COLLECTIONID";
    @RBEntry("已翻译 - 不在词典中")
    public static final String NOT_IN_DICTIONARY = "NOT_IN_DICTIONARY";
    @RBEntry("已翻译 - 不在可视项中")
    public static final String NOT_INVIEWABLE = "NOT_INVIEWABLE";
    @RBEntry("所有源语言")
    public static final String ALL_SOURCE_LANGUAGES = "ALL_SOURCE_LANGUAGES";
    @RBEntry("所有目标语言")
    public static final String ALL_TRANSLATION_LANGUAGES = "ALL_TRANSLATION_LANGUAGES";
    @RBEntry("所有翻译状况")
    public static final String ALL_TRANSLATION_STATUS = "ALL_TRANSLATION_STATUS";
    @RBEntry("所有内容类型")
    public static final String ALL_CONTENT_TYPE = "ALL_CONTENT_TYPE";
    @RBEntry("翻译内容")
    public static final String TRANSLATION_CONTENT = "TRANSLATION_CONTENT";
    @RBEntry("结果")
    public static final String SEARCH_BUTTON_LABEL = "SEARCH_BUTTON_LABEL";
    @RBEntry("结果")
    public static final String SEARCH_RESULT_LABEL = "SEARCH_RESULT_LABEL";
    @RBEntry("是")
    public static final String YES = "YES";
    @RBEntry("否")
    public static final String NO = "NO";
    @RBEntry("受管理的基线 {0} 不是 {1} 状态 - 不允许导入。")
    public static final String EXCEPTION_BASELINE_NOT_IN_INTRANSLATION_STATE = "EXCEPTION_BASELINE_NOT_IN_INTRANSLATION_STATE";
    @RBEntry("试图设置对象 {0} 的状态失败。在生命周期 {2} 内，{1} 不是有效状态。")
    public static final String EXCEPTION_MULTIPLE_LIFECYCLE_STATE_IMPROPER = "EXCEPTION_MULTIPLE_LIFECYCLE_STATE_IMPROPER";
    @RBEntry("试图设置对象 {0} 的状态失败。在生命周期 {2} 内，{1} 不是有效状态。")
    public static final String EXCEPTION_SINGLE_LIFECYCLE_STATE_IMPROPER = "EXCEPTION_SINGLE_LIFECYCLE_STATE_IMPROPER";
    @RBEntry("无法标识内容清单文件中的唯一子项: <{0}>")
    public static final String EXCEPTION_UNIQUE_SUB_ELEMENT = "EXCEPTION_UNIQUE_SUB_ELEMENT";
    @RBEntry("未知")
    public static final String TRANSLATION_STATE_UNKNOWN = "TRANSLATION_STATE_UNKNOWN";
    @RBEntry("无法将条目添加到取消队列")
    public static final String UNABLE_TO_ADD_TO_CANCELLATION_QUEUE = "UNABLE_TO_ADD_TO_CANCELLATION_QUEUE";
    @RBEntry("{0} 创建失败。")
    public static final String FAILED_TO_START_QUEUE = "FAILED_TO_START_QUEUE";
    @RBEntry("翻译包取消")
    public static final String TRANSLATION_CANCELATION_NOTIFICATION_TITLE_SUCCESS = "TRANSLATION_CANCELATION_NOTIFICATION_TITLE_SUCCESS";
    @RBEntry("翻译包取消失败")
    public static final String TRANSLATION_CANCELATION_NOTIFICATION_TITLE_FAILURE = "TRANSLATION_CANCELATION_NOTIFICATION_TITLE_FAILURE";
    @RBEntry("取消时间:")
    public static final String TRANSLATION_CANCELATION_NOTIFICATION_CANCELATION_TIME = "TRANSLATION_CANCELATION_NOTIFICATION_CANCELATION_TIME";
    @RBEntry("无法解析 WTUser 的发送者: {0}")
    public static final String EXCEPTION_UNABLE_TO_RESOLVE_PRINCIPAL_TO_USER = "EXCEPTION_UNABLE_TO_RESOLVE_PRINCIPAL_TO_USER";
    @RBEntry("已取消的翻译")
    public static final String TRANSLATION_CANCELATION_NOTIFICATION_BODY_TITLE_SUCCESS = "TRANSLATION_CANCELATION_NOTIFICATION_BODY_TITLE_SUCCESS";
    @RBEntry("无法取消的翻译")
    public static final String TRANSLATION_CANCELATION_NOTIFICATION_BODY_TITLE_FAILURE = "TRANSLATION_CANCELATION_NOTIFICATION_BODY_TITLE_FAILURE";
    @RBEntry("取消翻译成功")
    public static final String TRANSLATION_CANCELATION_NOTIFICATION_SUBJECT_SUCCESS = "TRANSLATION_CANCELATION_NOTIFICATION_SUBJECT_SUCCESS";
    @RBEntry("取消翻译失败")
    public static final String TRANSLATION_CANCELATION_NOTIFICATION_SUBJECT_FAILURE = "TRANSLATION_CANCELATION_NOTIFICATION_SUBJECT_FAILURE";
    @RBEntry("成功生成翻译文档。")
    public static final String TRANSLATION_DOCUMENT_SUCCESS = "TRANSLATION_DOCUMENT_SUCCESS";
    @RBEntry("已成功生成翻译文档。")
    public static final String TRANSLATION_DOCUMENTS_SUCCESS = "TRANSLATION_DOCUMENTS_SUCCESS";
    @RBEntry("主翻译文档未更改。结构的翻译文档可能已生成。")
    public static final String TRANSLATION_DOCUMENT_NOT_NECESSARY = "TRANSLATION_DOCUMENT_NOT_NECESSARY";
    @RBEntry("生成翻译文档失败。")
    public static final String TRANSLATION_DOCUMENT_FAILURE = "TRANSLATION_DOCUMENT_FAILURE";
    @RBEntry("未设置文档 {0} 的源语言")
    public static final String EXCEPTION_PTC_DD_LANGUAGE_NOT_SET = "EXCEPTION_PTC_DD_LANGUAGE_NOT_SET";
    @RBEntry("内容清单文件格式不正确或未包含于翻译导入 ZIP 文件: {0}")
    public static final String MANIFEST_FILE_ISSUE = "MANIFEST_FILE_MISSING";
    @RBEntry("文档 {0} 版本 {1} 中 XML 格式不正确。")
    public static final String EXCEPTION_POORLY_FORMED_XML = "EXCEPTION_POORLY_FORMED_XML";
    @RBEntry("无法唯一标识以下内容载体的主要内容: {0}")
    public static final String EXCEPTION_NON_UNIQUE_PRIMARY_CONTENT = "EXCEPTION_NON_UNIQUE_PRIMARY_CONTENT";
    @RBEntry("无法解析内容载体 {0} 的 ApplicationData")
    public static final String EXCEPTION_UNABLE_TO_RESOLVE_PRIMARY_CONTENT = "EXCEPTION_UNABLE_TO_RESOLVE_PRIMARY_CONTENT";
    @RBEntry("可用目标语言:")
    public static final String SET_UP_TRANSLATION_AVAILABLE_TARGET_LANGUAGES_LABEL = "SET_UP_TRANSLATION_AVAILABLE_TARGET_LANGUAGES_LABEL";
    @RBEntry("选定目标语言:")
    public static final String SET_UP_TRANSLATION_SELECTED_TARGET_LANGUAGES_LABEL = "SET_UP_TRANSLATION_SELECTED_TARGET_LANGUAGES_LABEL";
    @RBEntry("翻译包取消失败，因为内容已翻译")
    public static final String TRANSLATION_CANCELATION_NOTIFICATION_DUE_TO_PARTIAL_IMPORT_TITLE_FAILURE = "TRANSLATION_CANCELATION_NOTIFICATION_DUE_TO_PARTIAL_IMPORT_TITLE_FAILURE";
    @RBEntry("无法为 {0}->{1}->{2} 创建翻译基线；此 API 不支持翻译中枢包。")
    public static final String EXCEPTION_UNSUPPORTED_PIVOT_API = "EXCEPTION_UNSUPPORTED_PIVOT_API";
    @RBEntry("不支持语言代码“{0}”。请检查“支持的语言”首选项。")
    public static final String LANGUAGE_NAME_MISSING_IN_SUPPORTED_LANGUAGES = "LANGUAGE_NAME_MISSING_IN_SUPPORTED_LANGUAGES";

    public NewTmlResource_zh_CN() {
    }
}
