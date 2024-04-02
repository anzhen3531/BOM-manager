package ext.ziang.resource;

import wt.util.resource.RBComment;
import wt.util.resource.RBEntry;
import wt.util.resource.RBPseudo;
import wt.util.resource.RBUUID;
import wt.util.resource.WTListResourceBundle;

/**
 * 通用操作资源
 *
 * @author anzhen
 * @date 2024/03/30
 */
@RBUUID("ext.ziang.resource.CommonActionResource")
public class CommonActionResource extends WTListResourceBundle {
    @RBEntry("once copy bom")
    public static final String EXTCOMMONACTION_ONCECOPYBOM_TITLE = "extCommonAction.onceCopyBom.title";
    @RBEntry("once copy bom")
    public static final String EXTCOMMONACTION_ONCECOPYBOM_DESCRIPTION = "extCommonAction.onceCopyBom.description";
    @RBEntry("once copy bom")
    public static final String EXTCOMMONACTION_ONCECOPYBOM_TOOLTIP = "extCommonAction.onceCopyBom.tooltip";

    @RBEntry("netmarkets/images/part_insert.gif")
    @RBPseudo(false)
    @RBComment("DO NOT TRANSLATE")
    public static final String EXTCOMMONACTION_ONCECOPYBOM_ICON = "extCommonAction.onceCopyBom.icon";

    @RBEntry("netmarkets/images/add16x16.gif")
    @RBPseudo(false)
    @RBComment("add part")
    public static final String EXTCOMMONACTION_SEARCHAFFECTED_ICON = "extCommonAction.searchAffected.icon";
}
