package ext.ziang.resource;

import wt.util.resource.RBEntry;
import wt.util.resource.RBUUID;
import wt.util.resource.WTListResourceBundle;

@RBUUID("com.ptc.tml.NewTmlResource")
public class NewTmlResource extends WTListResourceBundle {
    @RBEntry("File: {0} can't be deleted therefore import process can't be continued.")
    public static final String TRANS_PACKAGE_EXCEPTION_CANNOT_DELETE = "TRANS_PACKAGE_EXCEPTION_CANNOT_DELETE";
    @RBEntry("Translated package: {0} doesn't contain following entry: {1}")
    public static final String TRANS_PACKAGE_EXCEPTION_NOT_CONTAIN_FILE = "TRANS_PACKAGE_EXCEPTION_NOT_CONTAIN_FILE";
    @RBEntry("Documents mismatch")
    public static final String TRANS_PACKAGE_EXCEPTION_DOCUMENT_MISMATCH = "TRANS_PACKAGE_EXCEPTION_DOCUMENT_MISMATCH";
    @RBEntry("The language {0} is not supported.  Please check the Supported Languages Preference.")
    public static final String TRANS_PREP_EXCEPTION_LANG_LIB_NOT_FOUND = "TRANS_PREP_EXCEPTION_LANG_LIB_NOT_FOUND";
    @RBEntry("Translation library {0} could not be found.  Please check the Supported Languages Preference.")
    public static final String TRANS_PREP_EXCEPTION_LANG_LIB_NOT_SUPPORTED = "TRANS_PREP_EXCEPTION_LANG_LIB_NOT_SUPPORTED";
    @RBEntry("The existing translation {0} ({1})  has a state of {2}.  It is not in a state required for revision as part of translation preparation.")
    public static final String TRANS_PREP_EXCEPTION_TRANSLATION_IMPROPER_STATE = "TRANS_PREP_EXCEPTION_TRANSLATION_IMPROPER_STATE";
    @RBEntry("Document {0} does not meet the specified configuration specification.")
    public static final String TRANS_PREP_EXCEPTION_DOC_CONFIG_SPEC_MISMATCH = "TRANS_PREP_EXCEPTION_DOC_CONFIG_SPEC_MISMATCH";
    @RBEntry("The link to the document {0} could not be converted, because it does not have a translation.")
    public static final String TRANS_PREP_EXCEPTION_MASTER_NOT_FOUND = "TRANS_PREP_EXCEPTION_MASTER_NOT_FOUND";
    @RBEntry("Translation Package Success")
    public static final String BANNER_SUCCESS_CREATE_TRANSLATION = "BANNER_SUCCESS_CREATE_TRANSLATION";
    @RBEntry("Cancel Translation Package")
    public static final String BANNER_CANCEL_TRANSLATION = "BANNER_CANCEL_TRANSLATION";
    @RBEntry("Quote Package Success")
    public static final String BANNER_SUCCESS_CREATE_QUOTATION = "BANNER_SUCCESS_CREATE_QUOTATION";
    @RBEntry("Translation Package Failure")
    public static final String BANNER_FAILURE_CREATE_TRANSLATION = "BANNER_FAILURE_CREATE_TRANSLATION";
    @RBEntry("Quote Package Failure")
    public static final String BANNER_FAILURE_CREATE_QUOTATION = "BANNER_FAILURE_CREATE_QUOTATION";
    @RBEntry("Unable to create translation package.  Local language preference includes language which does not match a value in Supported Language global enumeration: {0}")
    public static final String BANNER_FAILURE_INVALID_SUPPORTED_LANG_PREFERENCE_SINGLE = "BANNER_FAILURE_INVALID_SUPPORTED_LANG_PREFERENCE_SINGLE";
    @RBEntry("Unable to create translation package.  Local language preference includes languages which do not match values in Supported Language global enumeration: {0}")
    public static final String BANNER_FAILURE_INVALID_SUPPORTED_LANG_PREFERENCE_MULTI = "BANNER_FAILURE_INVALID_SUPPORTED_LANG_PREFERENCE_MULTI";
    @RBEntry("Translation package processing started for: <b>{0}</b>")
    public static final String BANNER_TRANSLATION_SUCCESS_LANGUAGE_LIST = "BANNER_TRANSLATION_SUCCESS_LANGUAGE_LIST";
    @RBEntry("Quote package processing started for: <b>{0}</b>")
    public static final String BANNER_QUOTE_SUCCESS_LANGUAGE_LIST = "BANNER_QUOTE_SUCCESS_LANGUAGE_LIST";
    @RBEntry("Unable to create quote package for these pivot languages: <b>{0}</b>")
    public static final String BANNER_QUOTE_PIVOT_FAILURE_LANGUAGE_LIST = "BANNER_QUOTE_PIVOT_FAILURE_LANGUAGE_LIST";
    @RBEntry("Errors during translation - Please see: {0}")
    public static final String BANNER_MESSAGE_LOGFILE_LOC = "BANNER_MESSAGE_LOGFILE_LOC";
    @RBEntry("Cancellation of Translation packages has begun.")
    public static final String BANNER_MESSAGE_CANCEL_SUCCESSFULL = "BANNER_MESSAGE_CANCEL_SUCCESSFULL";
    @RBEntry("You are unable to cancel a translation package. You may not have permission to do so; please see your administrator for assistance.\n\n Unable to cancel the following Package IDs:\n{0}")
    public static final String MESSAGE_CANCEL_NO_PERMISSIONS = "MESSAGE_CANCEL_NO_PERMISSIONS";
    @RBEntry("{0} Created by: {1}")
    public static final String NON_CANCELLABLE_BASELINE_DETAIL = "NON_CANCELLABLE_BASELINE_DETAIL";
    @RBEntry("Cancel Translation Packages failed.")
    public static final String MESSAGE_CANCEL_FAILED = "MESSAGE_CANCEL_FAILED";
    @RBEntry("Unable to traverse SIM structures.")
    public static final String SIM_DYNAMIC_TRAVERSAL_FAIL = "SIM_DYNAMIC_TRAVERSAL_FAIL";
    @RBEntry("Unable to retrieve content from SIM structures.")
    public static final String SIM_DYNAMIC_RETRIEVE_FAIL = "SIM_DYNAMIC_RETRIEVE_FAIL";
    @RBEntry("IBA {0} is not of type Boolean")
    public static final String EXCEPTION_IBA_NOT_BOOL = "EXCEPTION_IBA_NOT_BOOL";
    @RBEntry("IBA {0} is not of type Float")
    public static final String EXCEPTION_IBA_NOT_FLOAT = "EXCEPTION_IBA_NOT_FLOAT";
    @RBEntry("Unsupported type: {0}")
    public static final String EXCEPTION_UNSUPPORTED_TYPE = "EXCEPTION_UNSUPPORTED_TYPE";
    @RBEntry("Unable to update IBAHolder")
    public static final String EXCEPTION_UNABLE_TO_UPDATE_IBAHOLDER = "EXCEPTION_UNABLE_TO_UPDATE_IBAHOLDER";
    @RBEntry("IBA {0} is not of type Integer")
    public static final String EXCEPTION_IBA_NOT_INTEGER = "EXCEPTION_IBA_NOT_INTEGER";
    @RBEntry("IBA {0} is not of type Timestamp")
    public static final String EXCEPTION_IBA_NOT_TIMESTAMP = "EXCEPTION_IBA_NOT_TIMESTAMP";
    @RBEntry("IBA {0} is not of type String")
    public static final String EXCEPTION_IBA_NOT_STRING = "EXCEPTION_IBA_NOT_STRING";
    @RBEntry("IBA {0} is not of type Unit")
    public static final String EXCEPTION_IBA_NOT_UNIT = "EXCEPTION_IBA_NOT_UNIT";
    @RBEntry("IBA {0} is not of type URL")
    public static final String EXCEPTION_IBA_NOT_URL = "EXCEPTION_IBA_NOT_URL";
    @RBEntry("Preference with following key: {0} does not contain entry for language: {1}")
    public static final String EXCEPTION_PREFERENCE_NOT_CONTAIN_LANGUAGE = "EXCEPTION_PREFERENCE_NOT_CONTAIN_LANGUAGE";
    @RBEntry("Document {0} does not meet the specified configuration specification.")
    public static final String EXCEPTION_DOCUMENT_UNMATCH_CONFIGSPEC = "EXCEPTION_DOCUMENT_UNMATCH_CONFIGSPEC";
    @RBEntry("Unable to traverse SIM structures.")
    public static final String EXCEPTION_NO_TRAVERSE_SIM_STRUCTURES = "EXCEPTION_NO_TRAVERSE_SIM_STRUCTURES";
    @RBEntry("Can't get XLIFF document.")
    public static final String EXCEPTION_NO_XLIFF_DOCUMENT = "EXCEPTION_NO_XLIFF_DOCUMENT";
    @RBEntry("Unable to translate document {0} version {1}, no placeholder exists.")
    public static final String EXCEPTION_UNABLE_TO_TRANSLATE_NO_PLACEHOLDER = "EXCEPTION_UNABLE_TO_TRANSLATE_NO_PLACEHOLDER";
    @RBEntry("XLIFF missing for {0}.")
    public static final String ERROR_MISSING_XLIFF = "ERROR_MISSING_XLIFF";
    @RBEntry("Translation Error Report:")
    public static final String ERROR_HEADER_LOG = "ERROR_HEADER_LOG";
    @RBEntry("Error log located at {0}")
    public static final String BANNER_LOG_LOCATION = "BANNER_LOG_LOCATION";
    @RBEntry("Translatable Content")
    public static final String TranslatableContentTableLabel = "TranslatableContentTableLabel";
    @RBEntry("Translation Table")
    public static final String TranslationPackageTableLabel = "TranslationPackageTableLabel";
    @RBEntry("Target Language")
    public static final String TARGET_LANGUAGE = "TARGET_LANGUAGE";
    @RBEntry("Baseline Number")
    public static final String BASELINE_NUMBER = "BASELINE_NUMBER";
    @RBEntry("Package ID")
    public static final String PACKAGE_ID = "PACKAGE_ID";
    @RBEntry("Pivots")
    public static final String PIVOT = "PIVOT";
    @RBEntry("Creator")
    public static final String CREATOR = "CREATOR";
    @RBEntry("Source Object")
    public static final String SOURCE_OBJECT = "SOURCE_OBJECT";
    @RBEntry("Source Type")
    public static final String SOURCE_OBJECT_TYPE = "SOURCE_OBJECT_TYPE";
    @RBEntry("Translatable Content")
    public static final String TranslatableContent_ACTION_DESCRIPTION = "translation.translatableContent.description";
    @RBEntry("Source Language Content")
    public static final String TRANSLATABLE_CONTENT_BASE_LANGUAGE_CONTENT_COLUMN_LABEL = "TRANSLATABLE_CONTENT_BASE_LANGUAGE_CONTENT_COLUMN_LABEL";
    @RBEntry("Translation")
    public static final String TRANSLATABLE_CONTENT_TRANSLATED_CONTENT_COLUMN_LABEL = "TRANSLATABLE_CONTENT_TRANSLATED_CONTENT_COLUMN_LABEL";
    @RBEntry("Language")
    public static final String LANGUAGE_COLUMN_NAME = "LANGUAGE_COLUMN_NAME";
    @RBEntry("Version")
    public static final String VERSION_COLUMN_NAME = "VERSION_COLUMN_NAME";
    @RBEntry("Unable to identify language of document '{0}'")
    public static final String EXCEPTION_UNABLE_TO_IDENTIFY_SOURCE_LANGUAGE = "EXCEPTION_UNABLE_TO_IDENTIFY_SOURCE_LANGUAGE";
    @RBEntry("Translation Process NOT Started; checked out documents found.")
    public static final String TRANSLATION_PROCESS_NOT_STARTED_CHECKED_OUT_DOCUMENTS = "TRANSLATION_PROCESS_NOT_STARTED_CHECKED_OUT_DOCUMENTS";
    @RBEntry("Unable to cast object of type {0} to type {1}")
    public static final String EXCEPTION_CAST_ERROR = "EXCEPTION_CAST_ERROR";
    @RBEntry("Unable to resolve username '{0}' to existing user")
    public static final String EXCEPTION_UNABLE_TO_RESOLVE_USER = "EXCEPTION_UNABLE_TO_RESOLVE_USER";
    @RBEntry("Unable to resolve user for session")
    public static final String EXCEPTION_UNABLE_TO_RESOLVE_SESSION_USER = "EXCEPTION_UNABLE_TO_RESOLVE_SESSION_USER";
    @RBEntry("Select the attributes for your translation package.")
    public static final String SET_UP_TRANSLATION_PACKAGE_HEADER = "SET_UP_TRANSLATION_PACKAGE_HEADER";
    @RBEntry("<b>Create Translation Package failed due to preference errors. Please correct the following preferences:</b>")
    public static final String SET_UP_TRANSLATION_CORRECT_LISTED_PREFERENCE = "SET_UP_TRANSLATION_CORRECT_LISTED_PREFERENCE";
    @RBEntry("Please select one or more Package IDs for cancellation.")
    public static final String CANCEL_TRANSLATION_PACKAGE_HEADER = "CANCEL_TRANSLATION_PACKAGE_HEADER";
    @RBEntry("One or more of the selected pivot packages has dependent target translations. Cancelling the pivot will also cancel the dependent packages.  Do you want to continue?")
    public static final String CANCEL_TRANSLATION_PIVOT_COLLECTION_WARNING = "CANCEL_TRANSLATION_PIVOT_COLLECTION_WARNING";
    @RBEntry("Package Type:")
    public static final String SET_UP_TRANSLATION_PACKAGE_TYPE_LABEL = "SET_UP_TRANSLATION_PACKAGE_TYPE_LABEL";
    @RBEntry("Pivot:")
    public static final String SET_UP_TRANSLATION_PACKAGE_PIVOT_LABEL = "SET_UP_TRANSLATION_PACKAGE_PIVOT_LABEL";
    @RBEntry("Translation")
    public static final String SET_UP_TRANSLATION_PACKAGE_TYPE_TRANSLATION = "SET_UP_TRANSLATION_PACKAGE_TYPE_TRANSLATION";
    @RBEntry("Quote")
    public static final String SET_UP_TRANSLATION_PACKAGE_TYPE_QUOTE = "SET_UP_TRANSLATION_PACKAGE_TYPE_QUOTE";
    @RBEntry("Target Vendor:")
    public static final String SET_UP_TRANSLATION_VENDOR_LABEL = "SET_UP_TRANSLATION_VENDOR_LABEL";
    @RBEntry("Target Languages:")
    public static final String SET_UP_CANCEL_TRANSLATION_LANGUAGES_LABEL = "SET_UP_CANCEL_TRANSLATION_LANGUAGES_LABEL";
    @RBEntry("Please select one or more target languages for cancellation.")
    public static final String SET_UP_CANCEL_TRANSLATION_PACKAGE_HEADER = "SET_UP_CANCEL_TRANSLATION_PACKAGE_HEADER";
    @RBEntry("To cancel a translation package, you must select at least one target language.")
    public static final String SET_UP_CANCEL_TRANSLATION_NO_LANG_SELECTED = "SET_UP_CANCEL_TRANSLATION_NO_LANG_SELECTED";
    @RBEntry("Target Languages:")
    public static final String SET_UP_TRANSLATION_LANGUAGES_LABEL = "SET_UP_TRANSLATION_LANGUAGES_LABEL";
    @RBEntry("No supported translation languages for the selected document(s).")
    public static final String SET_UP_TRANSLATION_LANGUAGES_NONE = "SET_UP_TRANSLATION_LANGUAGES_NONE";
    @RBEntry("Package Content:")
    public static final String SET_UP_TRANSLATION_CONTENT_CHOICE = "SET_UP_TRANSLATION_CONTENT_CHOICE";
    @RBEntry("Wait for all content to be approved.")
    public static final String SET_UP_TRANSLATION_CONTENT_WAIT = "SET_UP_TRANSLATION_CONTENT_WAIT";
    @RBEntry("Content already approved.")
    public static final String SET_UP_TRANSLATION_CONTENT_IMMEDIATE = "SET_UP_TRANSLATION_CONTENT_IMMEDIATE";
    @RBEntry("Source Language:")
    public static final String SET_UP_TRANSLATION_SOURCE_LANGUAGE_LABEL = "SET_UP_TRANSLATION_SOURCE_LANGUAGE_LABEL";
    @RBEntry("Source Languages:")
    public static final String SET_UP_TRANSLATION_SOURCE_LANGUAGES_LABEL = "SET_UP_TRANSLATION_SOURCE_LANGUAGES_LABEL";
    @RBEntry("To create a translation package, you must select at least one language.")
    public static final String SET_UP_TRANSLATION_NO_LANG_SELECTED = "SET_UP_TRANSLATION_NO_LANG_SELECTED";
    @RBEntry("To cancel a translation package, you must select at least one Package ID.")
    public static final String SET_UP_CANCEL_TRANSLATION_NO_ROW_SELECTED = "SET_UP_CANCEL_TRANSLATION_NO_ROW_SELECTED";
    @RBEntry("User '{0}' does not have the Translation Coordinator role for the following context(s): {1}")
    public static final String INACCESSIBLE_LANGUAGES_MESSAGE = "INACCESSIBLE_LANGUAGES_MESSAGE";
    @RBEntry("User '{0}' does not belong to a Translation Coordinator role for context '{1}', which is used for languages: {2}")
    public static final String INACCESSIBLE_TRANSLATION_DATA_CONTEXT = "INACCESSIBLE_TRANSLATION_DATA_CONTEXT";
    @RBEntry("Unable to get value for preference '{0}' in context '{1}'.")
    public static final String UNABLE_TO_GET_CONTAINER_STRING_PREFERENCE_ERROR_MESSAGE = "UNABLE_TO_GET_CONTAINER_STRING_PREFERENCE_ERROR_MESSAGE";
    @RBEntry("Unable to get value for preference '{0}' in site container.")
    public static final String UNABLE_TO_GET_SITE_STRING_PREFERENCE_ERROR_MESSAGE = "UNABLE_TO_GET_SITE_STRING_PREFERENCE_ERROR_MESSAGE";
    @RBEntry("Unable to get value for preference '{0}' in context '{1}'.")
    public static final String UNABLE_TO_GET_CONTEXT_BOOLEAN_PREFERENCE_ERROR_MESSAGE = "UNABLE_TO_GET_CONTEXT_BOOLEAN_PREFERENCE_ERROR_MESSAGE";
    @RBEntry("Unable to get value for integer preference '{0}' in context '{1}'.")
    public static final String INT_PREFERENCE_CONTAINER_UNAVAILABLE_ERROR_MESSAGE = "INT_PREFERENCE_CONTAINER_UNAVAILABLE_ERROR_MESSAGE";
    @RBEntry("Preference {0} in context {1} null or empty; unable to convert to int.")
    public static final String INT_PREFERENCE_CONTAINER_NULL_ERROR_MESSAGE = "INT_PREFERENCE_CONTAINER_NULL_ERROR_MESSAGE";
    @RBEntry("Unable to get value for integer preference '{0}' in site context.")
    public static final String INT_PREFERENCE_SITE_UNAVAILABLE_ERROR_MESSAGE = "INT_PREFERENCE_SITE_UNAVAILABLE_ERROR_MESSAGE";
    @RBEntry("Poorly formatted numerical value for Import Interval, using '{0}' instead.")
    public static final String POORLY_FORMATTED_IMPORT_INTERVAL_ERROR_MESSAGE = "POORLY_FORMATTED_IMPORT_INTERVAL_ERROR_MESSAGE";
    @RBEntry("Unable to find manifest file in translation zip package: {0}")
    public static final String EXCEPTION_UNABLE_TO_FIND_MANIFEST_FILE = "EXCEPTION_UNABLE_TO_FIND_MANIFEST_FILE";
    @RBEntry("Unable to find the <contextContainerOid> tag in the translation manifest file for the translation zip package: {0}")
    public static final String EXCEPTION_UNABLE_TO_CONTEXT_CONTAINER_OID_TAG = "EXCEPTION_UNABLE_TO_CONTEXT_CONTAINER_OID_TAG";
    @RBEntry("The following documents were expected in this translation package but were not found: {0}")
    public static final String EXCEPTION_MISSING_FILES_IN_PACKAGE = "EXCEPTION_MISSING_FILES_IN_PACKAGE";
    @RBEntry("Unexpected type linked to xliff file: {0}")
    public static final String EXCEPTION_UNKOWN_XLIFF_SOURCE_TYPE = "EXCEPTION_UNKOWN_XLIFF_SOURCE_TYPE";
    @RBEntry("Unable to check out and update document {0}; it is not the latest.")
    public static final String EXCEPTION_UNABLE_TO_UPDATE_NON_LATEST_ITERATION_OR_REVISION = "EXCEPTION_UNABLE_TO_UPDATE_NON_LATEST_ITERATION_OR_REVISION";
    @RBEntry("Unable to find Translation Import Package '{0}'")
    public static final String EXCEPTION_UNABLE_TO_FIND_PACKAGE_FILE = "EXCEPTION_UNABLE_TO_FIND_PACKAGE_FILE";
    @RBEntry("Unable to rename Translation Import Package, target already exists: '{0}'")
    public static final String EXCEPTION_NEW_PACKAGE_FILE_NAME_ALREADY_EXISTS = "EXCEPTION_NEW_PACKAGE_FILE_NAME_ALREADY_EXISTS";
    @RBEntry("Unable to rename Translation Import Package: '{0}'")
    public static final String EXCEPTION_UNABLE_TO_RENAME_TRANSLATION_PACKAGE = "EXCEPTION_UNABLE_TO_RENAME_TRANSLATION_PACKAGE";
    @RBEntry("Unable to rename completed translation package in export folder: from='{0}' to='{1}'")
    public static final String ERROR_UNABLE_TO_MOVE_PACKAGE = "ERROR_UNABLE_TO_MOVE_PACKAGE";
    @RBEntry("Insertions are not allowed in Translation Collection")
    public static final String EXCEPTION_INSERT_NOTALLOWED_ON_TC = "EXCEPTION_INSERT_NOTALLOWED_ON_TC";
    @RBEntry("Deletions are not allowed in Translation Pivot Collection")
    public static final String EXCEPTION_REMOVE_NOTALLOWED_ON_TPC = "EXCEPTION_REMOVE_NOTALLOWED_ON_TPC";
    @RBEntry("DOM Level 3 LS is not supported in this installation")
    public static final String EXCEPTION_DOM_OP_NOT_SUPPORTED = "EXCEPTION_DOM_OP_NOT_SUPPORTED";
    @RBEntry("Unable to resolve non-translatable child document")
    public static final String TRANS_PREP_EXCEPTION_RESOLVE_DOC_CHILD = "TRANS_PREP_EXCEPTION_RESOLVE_DOC_CHILD";
    @RBEntry("Unsupported OID type: '{0}'")
    public static final String UNSUPPORTED_OID_TYPE = "UNSUPPORTED_OID_TYPE";
    @RBEntry("Unsupported child type: '{0}'")
    public static final String UNSUPPORTED_CHILD_TYPE = "UNSUPPORTED_CHILD_TYPE";
    @RBEntry("No valid documents found to translate.")
    public static final String NO_DOCUMENTS_TO_TRANSLATE = "NO_DOCUMENTS_TO_TRANSLATE";
    @RBEntry("No valid documents found to import.")
    public static final String NO_DOCUMENTS_TO_IMPORT = "NO_DOCUMENTS_TO_IMPORT";
    @RBEntry("Unable to parse the following xml documents:")
    public static final String UNABLE_TO_PARSE_XML_DOCS = "UNABLE_TO_PARSE_XML_DOCS";
    @RBEntry("File in translation package")
    public static final String ERROR_HEADER_FILE_IN_PACKAGE = "ERROR_HEADER_FILE_IN_PACKAGE";
    @RBEntry("Dynamic Document Name and Number")
    public static final String ERROR_HEADER_DD_NAME_NUMBER = "ERROR_HEADER_DD_NAME_NUMBER";
    @RBEntry("Translation Collection may not have a negative 'Remaining Acceptance Count': {0}")
    public static final String EXCEPTION_SET_NEGATIVE_REMAINING_ACCEPTANCE_COUNT = "EXCEPTION_SET_NEGATIVE_REMAINING_ACCEPTANCE_COUNT";
    @RBEntry("Translation collection must be constructed from a ManagedCollection of the TranslationCollection subtype, not: {0}")
    public static final String EXCEPTION_MC_NOT_TC = "EXCEPTION_MC_NOT_TC";
    @RBEntry("Collection OID invalid, refers to NULL object")
    public static final String EXCEPTION_NULL_OID_COLLECTION_REFERENCE = "EXCEPTION_NULL_OID_COLLECTION_REFERENCE";
    @RBEntry("Collection OID invalid; expected TranslationCollection, actual object {0}")
    public static final String EXCEPTION_UNEXPECTED_OID_COLLECTION_REFERENCE = "EXCEPTION_UNEXPECTED_OID_COLLECTION_REFERENCE";
    @RBEntry("Poorly formated attribute value; attribute of type: {0}")
    public static final String EXCEPTION_POORLY_FORMATTED_ATT_VALUE = "EXCEPTION_POORLY_FORMATTED_ATT_VALUE";
    @RBEntry("Translation root collection must be constructed from a ManagedCollection of the correct subtype, not {0}")
    public static final String EXCEPTION_UNEXPECTED_COLLECTION_BASE_TYPE = "EXCEPTION_UNEXPECTED_COLLECTION_BASE_TYPE";
    @RBEntry("Unable to find context named '{0}'")
    public static final String UNABLE_TO_FIND_CONTAINER_BY_NAME = "UNABLE_TO_FIND_CONTAINER_BY_NAME";
    @RBEntry("Unable to identify context type '{0}'")
    public static final String UNABLE_TO_IDENTIFY_CONTAINER_TYPE = "UNABLE_TO_IDENTIFY_CONTAINER_TYPE";
    @RBEntry("sourceObj argument must not be null")
    public static final String EXCEPTION_WFL_UTIL_SOURCEOBJ_NULL = "EXCEPTION_WFL_UTIL_SOURCEOBJ_NULL";
    @RBEntry("sourceObj argument must be either an EPMDocument or a WTPart")
    public static final String EXCEPTION_WFL_UTIL_SOURCEOBJ_WRONG_TYPE = "EXCEPTION_WFL_UTIL_SOURCEOBJ_WRONG_TYPE";
    @RBEntry("contextContainer argument must not be null.")
    public static final String EXCEPTION_WFL_UTIL_CONTEXTCONTAINER_NULL = "EXCEPTION_WFL_UTIL_CONTEXTCONTAINER_NULL";
    @RBEntry("outputDir argument must not be null")
    public static final String EXCEPTION_WFL_UTIL_OUTPUTDIR_NULL = "EXCEPTION_WFL_UTIL_OUTPUTDIR_NULL";
    @RBEntry("outputDir argument must not be empty")
    public static final String EXCEPTION_WFL_UTIL_OUTPUTDIR_EMPTY = "EXCEPTION_WFL_UTIL_OUTPUTDIR_EMPTY";
    @RBEntry("outputDir argument must be a valid path string: '{0}'")
    public static final String EXCEPTION_WFL_UTIL_OUTPUTDIR_INVALID_PATH = "EXCEPTION_WFL_UTIL_OUTPUTDIR_INVALID_PATH";
    @RBEntry("outputDir argument must be a path to a valid directory: '{0}'")
    public static final String EXCEPTION_WFL_UTIL_INVALID_DIR = "EXCEPTION_WFL_UTIL_INVALID_DIR";
    @RBEntry("user argument must not be null")
    public static final String EXCEPTION_WFL_UTIL_USER_NULL = "EXCEPTION_WFL_UTIL_USER_NULL";
    @RBEntry("nc argument must contain exactly 1 ConfigSpec, not '{0}'")
    public static final String EXCEPTION_WFL_UTIL_TOO_MANY_CONFIGSPECS = "EXCEPTION_WFL_UTIL_TOO_MANY_CONFIGSPECS";
    @RBEntry("Content is restricted due to configuration and security settings.")
    public static final String ERROR_LOG_SECURE_DOCS_NOT_PERMITTED = "ERROR_LOG_SECURE_DOCS_NOT_PERMITTED";
    @RBEntry("Translation package failed; access violation.")
    public static final String ERROR_LOG_ACCESS_VIOLATION = "ERROR_LOG_ACCESS_VIOLATION";
    @RBEntry("Unable to create a reference for EPMDocument.")
    public static final String EXCEPTION_UNABLE_TO_CREATE_DOC_REFERENCE = "EXCEPTION_UNABLE_TO_CREATE_DOC_REFERENCE";
    @RBEntry("Security Label Object of unexpected type: {0}")
    public static final String EXCEPTION_UNEXPECTED_SECURITY_LABEL_TYPE = "EXCEPTION_UNEXPECTED_SECURITY_LABEL_TYPE";
    @RBEntry("Translation Dashboard")
    public static final String TRANSLATIONS_SUMMARY_DESC = "translation.translationSummary.description";
    @RBEntry("Translation Content")
    public static final String TRANSLATIONS_CONTENT_DESC = "translation.translationContent.description";
    @RBEntry("In Translation")
    public static final String IN_TRANSLATION = "IN_TRANSLATION";
    @RBEntry("Translated")
    public static final String TRANSLATED = "TRANSLATED";
    @RBEntry("Requested")
    public static final String REQUESTED = "REQUESTED";
    @RBEntry("Not Requested")
    public static final String NOT_REQUESTED = "NOT_REQUESTED";
    @RBEntry("Not Requested-New translation needed")
    public static final String NOT_REQUESTED_NEW_TRANSLATION_NEEDED = "NOT_REQUESTED_NEW_TRANSLATION_NEEDED";
    @RBEntry("Canceled")
    public static final String CANCELLED = "CANCELLED";
    @RBEntry("Textual Content")
    public static final String CONTENT = "CONTENT";
    @RBEntry("Graphical Content")
    public static final String GRAPHIC_CONTENT = "GRAPHIC_CONTENT";
    @RBEntry("Xliff")
    public static final String XLIFF = "XLIFF";
    @RBEntry("Language")
    public static final String TRANSLATION_SUMMARY_LANGUAGE = "TRANSLATION_SUMMARY_LANGUAGE";
    @RBEntry("Status")
    public static final String TRANSLATION_SUMMARY_STATUS = "TRANSLATION_SUMMARY_STATUS";
    @RBEntry("Path")
    public static final String TRANSLATION_SUMMARY_PATH = "TRANSLATION_SUMMARY_PATH";
    @RBEntry("Language Code")
    public static final String TRANSLATION_SUMMARY_LANGUAGE_CODE = "TRANSLATION_SUMMARY_LANGUAGE_CODE";
    @RBEntry("Last Refreshed")
    public static final String TRANSLATION_SUMMARY_RERESHED = "TRANSLATION_SUMMARY_RERESHED";
    @RBEntry("To get the Translation status, you must select at least one language.")
    public static final String SHOW_TRANSLATION_NO_LANG_SELECTED = "SHOW_TRANSLATION_NO_LANG_SELECTED";
    @RBEntry("refresh_tbar.gif")
    public static final String TranslationSummary_ACTION_ICON = "translationSummaryActions.refresh.icon";
    @RBEntry("Refresh Translation Status of selected language")
    public static final String TranslationSummary_ACTION_TOOLTIP = "translationSummaryActions.refresh.tooltip";
    @RBEntry("Refresh")
    public static final String TranslationSummary_ACTION_TITLE = "translationSummaryActions.refresh.title";
    @RBEntry("Refresh")
    public static final String TranslationSummary_ACTION_DESC = "translationSummaryActions.refresh.description";
    @RBEntry("Source Name")
    public static final String TRANSLATION_CONTENT_SOURCE_NAME = "TRANSLATION_CONTENT_SOURCE_NAME";
    @RBEntry("Source Number")
    public static final String TRANSLATION_CONTENT_SOURCE_NUMBER = "TRANSLATION_CONTENT_SOURCE_NUMBER";
    @RBEntry("Source Version")
    public static final String TRANSLATION_CONTENT_SOURCE_VERSION = "TRANSLATION_CONTENT_SOURCE_VERSION";
    @RBEntry("Source Context")
    public static final String TRANSLATION_CONTENT_SOURCE_CONTEXT = "TRANSLATION_CONTENT_SOURCE_CONTEXT";
    @RBEntry("Source Language")
    public static final String TRANSLATION_CONTENT_SOURCE_LANGUAGE = "TRANSLATION_CONTENT_SOURCE_LANGUAGE";
    @RBEntry("Source State")
    public static final String TRANSLATION_CONTENT_SOURCE_STATE = "TRANSLATION_CONTENT_SOURCE_STATE";
    @RBEntry("Translation Name")
    public static final String TRANSLATION_CONTENT_TRANSLATION_NAME = "TRANSLATION_CONTENT_TRANSLATION_NAME";
    @RBEntry("Translation Number")
    public static final String TRANSLATION_CONTENT_TRANSLATION_NUMBER = "TRANSLATION_CONTENT_TRANSLATION_NUMBER";
    @RBEntry("Translation Context")
    public static final String TRANSLATION_CONTENT_TRANSLATION_CONTEXT = "TRANSLATION_CONTENT_TRANSLATION_CONTEXT";
    @RBEntry("Target Language")
    public static final String TRANSLATION_CONTENT_TRANSLATION_LANGUAGE = "TRANSLATION_CONTENT_TRANSLATION_LANGUAGE";
    @RBEntry("Translation Version")
    public static final String TRANSLATION_CONTENT_TRANSLATION_VERSION = "TRANSLATION_CONTENT_TRANSLATION_VERSION";
    @RBEntry("Translation Status")
    public static final String TRANSLATION_CONTENT_TRANSLATION_STATUS = "TRANSLATION_CONTENT_TRANSLATION_STATUS";
    @RBEntry("Translation State")
    public static final String TRANSLATION_CONTENT_TRANSLATION_STATE = "TRANSLATION_CONTENT_TRANSLATION_STATE";
    @RBEntry("Package ID")
    public static final String TRANSLATION_CONTENT_PACKAGEID = "TRANSLATION_CONTENT_PACKAGEID";
    @RBEntry("Collection ID")
    public static final String TRANSLATION_CONTENT_COLLECTIONID = "TRANSLATION_CONTENT_COLLECTIONID";
    @RBEntry("Translated-Not In Dictionary")
    public static final String NOT_IN_DICTIONARY = "NOT_IN_DICTIONARY";
    @RBEntry("Translated-Not In Viewable")
    public static final String NOT_INVIEWABLE = "NOT_INVIEWABLE";
    @RBEntry("All Source Languages")
    public static final String ALL_SOURCE_LANGUAGES = "ALL_SOURCE_LANGUAGES";
    @RBEntry("All Target Languages")
    public static final String ALL_TRANSLATION_LANGUAGES = "ALL_TRANSLATION_LANGUAGES";
    @RBEntry("All Translation Status")
    public static final String ALL_TRANSLATION_STATUS = "ALL_TRANSLATION_STATUS";
    @RBEntry("All Content Types")
    public static final String ALL_CONTENT_TYPE = "ALL_CONTENT_TYPE";
    @RBEntry("Translation Content")
    public static final String TRANSLATION_CONTENT = "TRANSLATION_CONTENT";
    @RBEntry("Results")
    public static final String SEARCH_BUTTON_LABEL = "SEARCH_BUTTON_LABEL";
    @RBEntry("Results")
    public static final String SEARCH_RESULT_LABEL = "SEARCH_RESULT_LABEL";
    @RBEntry("Yes")
    public static final String YES = "YES";
    @RBEntry("No")
    public static final String NO = "NO";
    @RBEntry("Managed Baseline {0} is not in {1} state - Import not allowed.")
    public static final String EXCEPTION_BASELINE_NOT_IN_INTRANSLATION_STATE = "EXCEPTION_BASELINE_NOT_IN_INTRANSLATION_STATE";
    @RBEntry("The attempt to set the state of the {0} object failed. {1} are not valid states in the {2} Life Cycle.")
    public static final String EXCEPTION_MULTIPLE_LIFECYCLE_STATE_IMPROPER = "EXCEPTION_MULTIPLE_LIFECYCLE_STATE_IMPROPER";
    @RBEntry("The attempt to set the state of the {0} object failed. {1} is not a valid state in the {2} Life Cycle.")
    public static final String EXCEPTION_SINGLE_LIFECYCLE_STATE_IMPROPER = "EXCEPTION_SINGLE_LIFECYCLE_STATE_IMPROPER";
    @RBEntry("Unable to identify unique child in manifest file: <{0}>")
    public static final String EXCEPTION_UNIQUE_SUB_ELEMENT = "EXCEPTION_UNIQUE_SUB_ELEMENT";
    @RBEntry("Unknown")
    public static final String TRANSLATION_STATE_UNKNOWN = "TRANSLATION_STATE_UNKNOWN";
    @RBEntry("Unable to add entry to cancellation queue")
    public static final String UNABLE_TO_ADD_TO_CANCELLATION_QUEUE = "UNABLE_TO_ADD_TO_CANCELLATION_QUEUE";
    @RBEntry("{0} creation failed.")
    public static final String FAILED_TO_START_QUEUE = "FAILED_TO_START_QUEUE";
    @RBEntry("Translation package cancellation")
    public static final String TRANSLATION_CANCELATION_NOTIFICATION_TITLE_SUCCESS = "TRANSLATION_CANCELATION_NOTIFICATION_TITLE_SUCCESS";
    @RBEntry("Translation package cancellation failed")
    public static final String TRANSLATION_CANCELATION_NOTIFICATION_TITLE_FAILURE = "TRANSLATION_CANCELATION_NOTIFICATION_TITLE_FAILURE";
    @RBEntry("Cancellation Time:")
    public static final String TRANSLATION_CANCELATION_NOTIFICATION_CANCELATION_TIME = "TRANSLATION_CANCELATION_NOTIFICATION_CANCELATION_TIME";
    @RBEntry("Unable to resolve sender to WTUser: {0}")
    public static final String EXCEPTION_UNABLE_TO_RESOLVE_PRINCIPAL_TO_USER = "EXCEPTION_UNABLE_TO_RESOLVE_PRINCIPAL_TO_USER";
    @RBEntry("Canceled Translations")
    public static final String TRANSLATION_CANCELATION_NOTIFICATION_BODY_TITLE_SUCCESS = "TRANSLATION_CANCELATION_NOTIFICATION_BODY_TITLE_SUCCESS";
    @RBEntry("Translations which failed to cancel")
    public static final String TRANSLATION_CANCELATION_NOTIFICATION_BODY_TITLE_FAILURE = "TRANSLATION_CANCELATION_NOTIFICATION_BODY_TITLE_FAILURE";
    @RBEntry("Translation cancellation succeeded")
    public static final String TRANSLATION_CANCELATION_NOTIFICATION_SUBJECT_SUCCESS = "TRANSLATION_CANCELATION_NOTIFICATION_SUBJECT_SUCCESS";
    @RBEntry("Translation cancellation failed")
    public static final String TRANSLATION_CANCELATION_NOTIFICATION_SUBJECT_FAILURE = "TRANSLATION_CANCELATION_NOTIFICATION_SUBJECT_FAILURE";
    @RBEntry("Translation document was generated successfully.")
    public static final String TRANSLATION_DOCUMENT_SUCCESS = "TRANSLATION_DOCUMENT_SUCCESS";
    @RBEntry("Translation documents generated successfully.")
    public static final String TRANSLATION_DOCUMENTS_SUCCESS = "TRANSLATION_DOCUMENTS_SUCCESS";
    @RBEntry("Primary translation document unchanged. Translation document of the structure may have been generated.")
    public static final String TRANSLATION_DOCUMENT_NOT_NECESSARY = "TRANSLATION_DOCUMENT_NOT_NECESSARY";
    @RBEntry("Generation of translation document failed.")
    public static final String TRANSLATION_DOCUMENT_FAILURE = "TRANSLATION_DOCUMENT_FAILURE";
    @RBEntry("The source language of the document {0} is not set")
    public static final String EXCEPTION_PTC_DD_LANGUAGE_NOT_SET = "EXCEPTION_PTC_DD_LANGUAGE_NOT_SET";
    @RBEntry("Manifest file is either malformed or missing in translation import zip: {0}")
    public static final String MANIFEST_FILE_ISSUE = "MANIFEST_FILE_MISSING";
    @RBEntry("Poorly formatted xml in document {0} version {1}")
    public static final String EXCEPTION_POORLY_FORMED_XML = "EXCEPTION_POORLY_FORMED_XML";
    @RBEntry("Unable to uniquely identify primary content for content holder: {0}")
    public static final String EXCEPTION_NON_UNIQUE_PRIMARY_CONTENT = "EXCEPTION_NON_UNIQUE_PRIMARY_CONTENT";
    @RBEntry("Unable to resolve ApplicationData for content holder: {0}")
    public static final String EXCEPTION_UNABLE_TO_RESOLVE_PRIMARY_CONTENT = "EXCEPTION_UNABLE_TO_RESOLVE_PRIMARY_CONTENT";
    @RBEntry("Available Target Languages:")
    public static final String SET_UP_TRANSLATION_AVAILABLE_TARGET_LANGUAGES_LABEL = "SET_UP_TRANSLATION_AVAILABLE_TARGET_LANGUAGES_LABEL";
    @RBEntry("Selected Target Languages:")
    public static final String SET_UP_TRANSLATION_SELECTED_TARGET_LANGUAGES_LABEL = "SET_UP_TRANSLATION_SELECTED_TARGET_LANGUAGES_LABEL";
    @RBEntry("Translation package cancellation failed due to content that is already translated")
    public static final String TRANSLATION_CANCELATION_NOTIFICATION_DUE_TO_PARTIAL_IMPORT_TITLE_FAILURE = "TRANSLATION_CANCELATION_NOTIFICATION_DUE_TO_PARTIAL_IMPORT_TITLE_FAILURE";
    @RBEntry("Unable to create translation baseline for {0}->{1}->{2}; this api does not support translation pivot packages.")
    public static final String EXCEPTION_UNSUPPORTED_PIVOT_API = "EXCEPTION_UNSUPPORTED_PIVOT_API";
    @RBEntry("The language code '{0}' is not supported.  Please check the Supported Languages Preference.")
    public static final String LANGUAGE_NAME_MISSING_IN_SUPPORTED_LANGUAGES = "LANGUAGE_NAME_MISSING_IN_SUPPORTED_LANGUAGES";

    public NewTmlResource() {
    }
}
