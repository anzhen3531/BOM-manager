package com.ptc.windchill.enterprise.maturity.mvc;

import com.ptc.core.htmlcomp.components.ConfigurableTableBuilder;
import com.ptc.core.htmlcomp.tableview.ConfigurableTable;
import com.ptc.core.ui.resources.ComponentMode;
import com.ptc.jca.mvc.components.JcaComponentParams;
import com.ptc.jca.mvc.components.JcaTableConfig;
import com.ptc.mvc.components.ColumnConfig;
import com.ptc.mvc.components.ComponentBuilder;
import com.ptc.mvc.components.ComponentConfig;
import com.ptc.mvc.components.ComponentConfigFactory;
import com.ptc.mvc.components.ComponentParams;
import com.ptc.mvc.components.TableConfig;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.windchill.enterprise.maturity.commands.PromotionItemQueryCommands;
import com.ptc.windchill.enterprise.maturity.search.PromotionObjectsPickerConfig;
import com.ptc.windchill.enterprise.maturity.tableViews.PromotionObjectsWizardTableView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import wt.maturity.Promotable;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTMessage;

@ComponentBuilder({"promotionRequest.promotionObjectsWizard"})
public class PromotionObjectsWizardTableBuilder extends AsyncPromotionObjectsWizardTableBuilder implements ConfigurableTableBuilder {
    private static final String SELECTED_FOR_PROMOTE_STATUS = "selectedForPromoteStatus";
    private static final String PROMOTION_ITEMS_TABLE_ID = "promotionRequest.promotionObjects";
    private static final String PROMOTION_STATUS = "promotionStatus";
    private static final String PROMOTION_MESSAGE_STATUS = "promotionMessageStatus";
    private static final String PROMOTABLE_STATES = "promotableStates";
    private static final String TABLE_ID = "TABLE_ID";
    private static final String PROMOTION_REQUEST_WIZARD_PROMOTION_ITEMS_TABLE = "promotionRequest wizard promotionItems table";
    private static final String MATURITY_RESOURCE = "com.ptc.windchill.enterprise.maturity.maturityClientResource";
    private static final String INITIAL_SELECTION_STATUS = "promotionInitialSelectionStatus";

    public PromotionObjectsWizardTableBuilder() {
    }

    public ConfigurableTable buildConfigurableTable(String var1) throws WTException {
        return new PromotionObjectsWizardTableView();
    }

    public ComponentConfig buildComponentConfig(ComponentParams var1) throws WTException {
        ComponentConfigFactory var2 = this.getComponentConfigFactory();
        TableConfig var3 = var2.newTableConfig();
        var3.setTypes(new String[]{Promotable.class.getName()});
        var3.setId("promotionRequest.promotionObjects");
        var3.setSelectable(true);
        var3.setConfigurable(true);
        var3.setTargetObject("origObject");
        var3.setLabel(WTMessage.getLocalizedMessage("com.ptc.windchill.enterprise.maturity.maturityClientResource", "PROMOTION_OBJECTS_TABLEVIEW_LABEL", (Object[])null, SessionHelper.getLocale()));
        var3.setActionModel("promotionRequest wizard promotionItems table");
        if (var3 instanceof JcaTableConfig) {
            ((JcaTableConfig)var3).setDescriptorProperty("referenceType", "OR");
            ((JcaTableConfig)var3).setInitialRows(true);
            ((JcaTableConfig)var3).setDescriptorProperty("enforceTargetObject", true);
        }

        var3.setToolbarAutoSuggestPickerConfig(new PromotionObjectsPickerConfig());
        this.addColumn("statusFamily_General", var3, var2);
        this.addColumn("statusFamily_Share", var3, var2);
        this.addColumn("latestStatus", var3, var2);
        this.addColumn("promotionInitialSelectionStatus", var3, var2);
        this.addColumn("type_icon", var3, var2);
        this.addColumn("number", var3, var2);
        this.addColumn("orgid", var3, var2);
        this.addColumn("version", var3, var2);
        this.addColumn("name", var3, var2);
        this.addColumn("state", var3, var2);
        this.addColumn("validateStatus", var3, var2);
        ColumnConfig var4 = var2.newColumnConfig("promotionTargetComments", true);
        var4.setVariableHeight(true);
        var4.setTargetObject("");
        var4.setComponentMode(ComponentMode.EDIT);
        var3.addComponent(var4);
        this.addColumn("promotionStatus", var3, var2, "");
        this.addColumn("promotionMessageStatus", var3, var2, "");
        this.addColumn("promotableStates", var3, var2, "");
        ColumnConfig var5 = var2.newColumnConfig("selectedForPromoteStatus", false);
        var5.setTargetObject("");
        var5.setHidden(true);
        var5.setDataStoreOnly(true);
        var3.addComponent(var5);
        ArrayList var6 = new ArrayList(1);
        var6.add("promotionTablePlugin");
        ((JcaTableConfig)var3).setPtypes(var6);
        return var3;
    }

    public Object buildComponentData(ComponentConfig var1, ComponentParams var2) throws WTException {
        ArrayList var3 = new ArrayList();
        if (var2 instanceof JcaComponentParams) {
            JcaComponentParams var4 = (JcaComponentParams)var2;
            NmCommandBean var5 = var4.getHelperBean().getNmCommandBean();
            HashMap var6 = var5.getText();
            var5.addToMap(var6, "TABLE_ID", var1.getId(), true);
            List var7 = PromotionItemQueryCommands.getPromotionItems(var5);
            return var7;
        } else {
            return var3;
        }
    }

    private void addColumn(String var1, TableConfig var2, ComponentConfigFactory var3) {
        ColumnConfig var4 = var3.newColumnConfig(var1, true);
        var2.addComponent(var4);
    }

    private void addColumn(String var1, TableConfig var2, ComponentConfigFactory var3, String var4) {
        ColumnConfig var5 = var3.newColumnConfig(var1, true);
        var5.setTargetObject(var4);
        var2.addComponent(var5);
    }
}
