package ext.ziang.csm.resource;

import wt.util.resource.*;

/**
 * 通用操作资源
 *
 * @author anzhen
 * @date 2024/03/30
 */
@RBUUID("ext.ziang.csm.resource.CMSResource")
public class CMSResource extends WTListResourceBundle {

    @RBEntry("相关配置")
    public static final String OBJECT_EXT_CMS_TITLE = "object.extCMS.title";
    @RBEntry("相关配置")
    public static final String OBJECT_EXT_CMS_DESCRIPTION = "object.extCMS.description";
    @RBEntry("相关配置")
    public static final String OBJECT_EXT_CMS_TOOLTIP = "object.extCMS.tooltip";


    @RBEntry("属性合并")
    public static final String EXT_CONFIGATTRMERGE_CMS_TITLE = "extCMS.configAttrMerge.title";
    @RBEntry("属性合并")
    public static final String EXT_CMS_CONFIGATTRMERGE_DESCRIPTION = "extCMS.configAttrMerge.description";
    @RBEntry("属性合并")
    public static final String EXT_CMS_CONFIGATTRMERGE_TOOLTIP = "extCMS.configAttrMerge.tooltip";

}
