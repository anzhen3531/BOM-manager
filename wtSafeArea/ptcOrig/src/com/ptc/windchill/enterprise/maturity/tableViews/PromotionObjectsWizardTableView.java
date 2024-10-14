package com.ptc.windchill.enterprise.maturity.tableViews;

import com.ptc.core.htmlcomp.createtableview.Attribute;
import com.ptc.core.htmlcomp.tableview.TableViewCriterion;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import wt.util.WTException;
import wt.util.WTMessage;

public class PromotionObjectsWizardTableView extends PromotionObjectsTableView {
    private static final String MESSAGE_TYPE_DATA = "promoteMsgTypeData";
    private static final String PROMOTABLE_STATES_DATA = "promoteStatesData";
    private static final String SELECTED_FOR_PROMOTE_STATUS = "selectedForPromoteStatus";
    private static final String TABLE_RESOURCE = "com.ptc.core.ui.tableRB";
    private static final String MATURITY_RESOURCE = "com.ptc.windchill.enterprise.maturity.maturityClientResource";
    private static final String DATAUTILITY_RESOURCE = "com.ptc.windchill.enterprise.object.dataUtilities.dataUtilitiesResource";
    private static final List<String> LOCKED_COLUMNS = new ArrayList();
    private static final List<String> DEFAULT_COLUMNS;
    public static final String PROMOTION_CANDIDATES_VIEW = getViewResourceEntryKey("com.ptc.windchill.enterprise.maturity.maturityClientResource", "PROMOTION_CANDIDATES_VIEW");
    private static final String[] COLUMN_IDS = new String[]{"promotionStatus", "promotionInitialSelectionStatus", "statusFamily_General", "statusFamily_Share", "latestStatus", "promotionMessageStatus", "type_icon", "number", "orgid", "version", "name", "state", "promotableStates", "selectedForPromoteStatus", "promotionTargetComments"};

    public PromotionObjectsWizardTableView() {
    }

    protected List<String> getLockedColumns() {
        return LOCKED_COLUMNS;
    }

    protected List<String> getDefaultColumns() {
        return DEFAULT_COLUMNS;
    }

    public String getOOTBActiveViewName() {
        return getViewResourceEntryKey("com.ptc.core.ui.tableRB", "ALL_TABLEVIEW_NAME");
    }

    public List<?> getOOTBTableViews(String var1, Locale var2) throws WTException {
        List var3 = this.createCommonTableViews(var1, var2);
        Vector var4 = new Vector();
        var4.add(TableViewCriterion.newTableViewCriterion("selectedForPromoteStatus", "EQUALTO", "true", (String)null));
        var3.addAll(this.createTableView(var1, PROMOTION_CANDIDATES_VIEW, var2, var4));
        return var3;
    }

    public List<?> getSpecialTableColumnsAttrDefinition(Locale var1) {
        ArrayList var2 = new ArrayList();
        var2.add(new Attribute.TextAttribute("promotionStatus", WTMessage.getLocalizedMessage("com.ptc.windchill.enterprise.maturity.maturityClientResource", "PROMOTE_STATUS", (Object[])null, var1), var1));
        var2.add(new Attribute.TextAttribute("selectedForPromoteStatus", WTMessage.getLocalizedMessage("com.ptc.windchill.enterprise.maturity.maturityClientResource", "PROMOTE_STATUS", (Object[])null, var1), var1));
        var2.add(new Attribute.TextAttribute("latestStatus", WTMessage.getLocalizedMessage("com.ptc.windchill.enterprise.object.dataUtilities.dataUtilitiesResource", "LATEST_STATUS", (Object[])null, var1), var1));
        var2.add(new Attribute.TextAttribute("promotionMessageStatus", WTMessage.getLocalizedMessage("com.ptc.core.ui.tableRB", "MESSAGE_STATUS", (Object[])null, var1), var1));
        var2.add(new Attribute.TextAttribute("promotableStates", WTMessage.getLocalizedMessage("com.ptc.windchill.enterprise.maturity.maturityClientResource", "AVAILABLE_STATES", (Object[])null, var1), var1));
        var2.add(new Attribute.TextAttribute("promotionInitialSelectionStatus", WTMessage.getLocalizedMessage("com.ptc.windchill.enterprise.maturity.maturityClientResource", "INITIALLY_SELECTED_COLUMN_LABEL", (Object[])null, var1), var1));
        var2.add(new Attribute.TextAttribute("identityBuilder", WTMessage.getLocalizedMessage("com.ptc.core.ui.tableRB", "IDENTITY_COLUMN_NAME", (Object[])null, var1), var1));
        var2.add(new Attribute.TextAttribute("promotionTargetComments", WTMessage.getLocalizedMessage("com.ptc.windchill.enterprise.maturity.maturityClientResource", "PROMOTION_TARGET_COMMENTS", (Object[])null, var1), var1));
        return var2;
    }

    public boolean canAttributeBeUsedInFilter(String var1) {
        return "promotionStatus".equals(var1) ? false : super.canAttributeBeUsedInFilter(var1);
    }

    public boolean isAttributeValidForColumnStep(String var1) {
        return !"selectedForPromoteStatus".equals(var1) && !"promoteMsgTypeData".equals(var1) && !"promoteStatesData".equals(var1) ? super.isAttributeValidForColumnStep(var1) : false;
    }

    protected String getDefaultViewDescription(String var1) {
        String var2 = super.getDefaultViewDescription(var1);
        if (PROMOTION_CANDIDATES_VIEW.equals(var1)) {
            var2 = getViewResourceEntryKey("com.ptc.windchill.enterprise.maturity.maturityClientResource", "PROMOTION_CANDIDATES_VIEW_DESCRIPTION");
        }

        return var2;
    }

    static {
        LOCKED_COLUMNS.add("selectedForPromoteStatus");
        LOCKED_COLUMNS.add("promotionStatus");
        LOCKED_COLUMNS.add("promotionMessageStatus");
        LOCKED_COLUMNS.add("promotableStates");
        LOCKED_COLUMNS.add("promotionInitialSelectionStatus");
        DEFAULT_COLUMNS = new ArrayList();
        String[] var0 = COLUMN_IDS;
        int var1 = var0.length;

        for(int var2 = 0; var2 < var1; ++var2) {
            String var3 = var0[var2];
            DEFAULT_COLUMNS.add(var3);
        }

    }
}
