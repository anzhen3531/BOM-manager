package ext.ziang.part.resource;

import wt.util.resource.*;

/**
 * 通用操作资源
 *
 * @author anzhen
 * @date 2024/03/30
 */
@RBUUID("ext.ziang.part.resource.PartResource")
public class PartResource extends WTListResourceBundle {

    @RBEntry("create derived material")
    public static final String EXTNAVIGATION_CUSTOMCONFIG_TITLE = "extPart.derivedPart.title";
    @RBEntry("create derived material")
    public static final String EXTNAVIGATION_CUSTOMCONFIG_DESCRIPTION = "extPart.derivedPart.description";
    @RBEntry("create derived material")
    public static final String EXTNAVIGATION_CUSTOMCONFIG_TOOLTIP = "extPart.derivedPart.tooltip";
    @RBEntry("./view_layout_32x.png")
    @RBPseudo(false)
    @RBComment("客户配置")
    public static final String EXTNAVIGATION_CUSTOMDP_ICON = "extPart.derivedPart.icon";
}
