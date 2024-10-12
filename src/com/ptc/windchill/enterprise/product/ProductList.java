package com.ptc.windchill.enterprise.product;

import com.ptc.core.htmlcomp.components.JCAConfigurableTable;
import com.ptc.core.htmlcomp.tableview.SortColumnDescriptor;
import com.ptc.core.htmlcomp.tableview.TableColumnDefinition;
import com.ptc.core.htmlcomp.tableview.TableViewCriterion;
import com.ptc.core.htmlcomp.tableview.TableViewDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import wt.pdmlink.PDMLinkProduct;
import wt.util.WTException;
import wt.util.WTMessage;
import wt.util.WTPropertyVetoException;
import wt.util.WTRuntimeException;

public class ProductList extends JCAConfigurableTable {
    public static final String PRODUCT_TABLE_NAME = "name";
    public static final String PRODUCT_TABLE_INFO_ACTION = "infoPageAction";
    public static final String PRODUCT_TABLE_FAVORITE = "isFavorite";
    public static final String PRODUCT_TABLE_ACTIONS = "nmActions";
    public static final String PRODUCT_TABLE_OWNER = "containerInfo.owner";
    public static final String PRODUCT_TABLE_ORG = "orgid";
    public static final String PRODUCT_TABLE_TYPE = "type";
    public static final String PRODUCT_TABLE_MOD_DT = "thePersistInfo.modifyStamp";
    public static final String PRODUCT_TABLE_DESCRIPTION = "containerInfo.description";
    public static final String PRODUCT_TABLE_CREATOR = "containerInfo.creator";
    public static final String PRODUCT_TABLE_CRE_DT = "thePersistInfo.createStamp";
    public static final String PRODUCT_TABLE_PRIVATE_ACCESS = "containerInfo.privateAccess";
    public static final String ALL_VIEW_NAME = "All";
    public static final String ACTIVE_MEMBER_VIEW_NAME = "Active Member";
    public static final String GUEST_MEMBER_VIEW_NAME = "Guest Member";
    private static final String RESOURCE_BUNDLE = "com.ptc.windchill.enterprise.product.productResourceClient";
    private static final String FAVORITES_RESOURCE_BUNDLE = "com.ptc.netmarkets.favorites.favoritesResource";

    public ProductList() {
    }

    public boolean canAttributeBeUsedInFilter(String var1) {
        return false;
    }

    public Class[] getClassTypes() {
        return new Class[]{PDMLinkProduct.class};
    }

    public String getLabel(Locale var1) {
        String var2 = WTMessage.getLocalizedMessage("com.ptc.windchill.enterprise.product.productResourceClient", "3", (Object[])null, var1);
        return var2;
    }

    public String getOOTBActiveViewName() {
        return getViewResourceEntryKey("com.ptc.windchill.enterprise.product.productResourceClient", "4");
    }

    public List getOOTBTableViews(String var1, Locale var2) throws WTException {
        ArrayList var3 = new ArrayList(4);
        Vector var4 = new Vector(13);

        try {
            ArrayList var5 = new ArrayList();
            SortColumnDescriptor var6 = new SortColumnDescriptor();
            var6.setColumnId("name");
            var6.setOrder("ASCENDING");
            var5.add(var6);
            var4.add(TableColumnDefinition.newTableColumnDefinition("name", true));
            var4.add(TableColumnDefinition.newTableColumnDefinition("infoPageAction", true));
            var4.add(TableColumnDefinition.newTableColumnDefinition("isFavorite", true, 0, false, false, true, 0));
            var4.add(TableColumnDefinition.newTableColumnDefinition("nmActions", true));
            var4.add(TableColumnDefinition.newTableColumnDefinition("containerInfo.owner", false));
            var4.add(TableColumnDefinition.newTableColumnDefinition("orgid", false));
            var4.add(TableColumnDefinition.newTableColumnDefinition("type", false));
            var4.add(TableColumnDefinition.newTableColumnDefinition("business_lines", false));
            var4.add(TableColumnDefinition.newTableColumnDefinition("thePersistInfo.modifyStamp", false));
            var4.add(TableColumnDefinition.newTableColumnDefinition("containerInfo.description", false));
            var4.add(TableColumnDefinition.newTableColumnDefinition("containerInfo.creator", false));
            var4.add(TableColumnDefinition.newTableColumnDefinition("thePersistInfo.createStamp", false));
            var4.add(TableColumnDefinition.newTableColumnDefinition("containerInfo.privateAccess", false));
            String var7 = getViewResourceEntryKey("com.ptc.windchill.enterprise.product.productResourceClient", "4");
            String var8 = getViewResourceEntryKey("com.ptc.windchill.enterprise.product.productResourceClient", "7");
            // 添加类型拦截
            Vector var9 = new Vector(1);
            var9.add(TableViewCriterion.newClassTypeFilter(PDMLinkProduct.class));
            // 添加视图组
            TableViewDescriptor var10 = TableViewDescriptor.newTableViewDescriptor(var7, var1, true, true, var4, var9, true, var8);
            var10.setColumnSortOrder(var5);
            var3.add(var10);
            var7 = getViewResourceEntryKey("com.ptc.windchill.enterprise.product.productResourceClient", "5");
            var8 = getViewResourceEntryKey("com.ptc.windchill.enterprise.product.productResourceClient", "8");
            var10 = TableViewDescriptor.newTableViewDescriptor(var7, var1, true, true, var4, var9, true, var8);
            var10.setColumnSortOrder(var5);
            var3.add(var10);
            var7 = getViewResourceEntryKey("com.ptc.windchill.enterprise.product.productResourceClient", "6");
            var8 = getViewResourceEntryKey("com.ptc.windchill.enterprise.product.productResourceClient", "9");
            var10 = TableViewDescriptor.newTableViewDescriptor(var7, var1, true, true, var4, var9, true, var8);
            var10.setColumnSortOrder(var5);
            var3.add(var10);
            var7 = getViewResourceEntryKey("com.ptc.netmarkets.favorites.favoritesResource", "4");
            var8 = getViewResourceEntryKey("com.ptc.netmarkets.favorites.favoritesResource", "5");
            var10 = TableViewDescriptor.newTableViewDescriptor(var7, var1, true, true, var4, var9, true, var8);
            var10.setColumnSortOrder(var5);
            var3.add(var10);
            return var3;
        } catch (WTPropertyVetoException var11) {
            throw new WTRuntimeException(var11);
        }
    }

    public List getSpecialTableColumnsAttrDefinition(Locale var1) {
        ArrayList var2 = new ArrayList(11);
        return var2;
    }

    public boolean isColumnLocked(String var1) {
        return var1.equals("name") || var1.equals("nmActions");
    }

    public String getDefaultSortColumn() {
        return "name";
    }
}
