//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.ptc.windchill.enterprise.product.mvc.builders;

import com.ptc.core.htmlcomp.components.AbstractConfigurableTableBuilder;
import com.ptc.core.htmlcomp.tableview.ConfigurableTable;
import com.ptc.jca.mvc.components.JcaColumnConfig;
import com.ptc.mvc.components.ColumnConfig;
import com.ptc.mvc.components.ComponentBuilder;
import com.ptc.mvc.components.ComponentConfig;
import com.ptc.mvc.components.ComponentConfigFactory;
import com.ptc.mvc.components.ComponentParams;
import com.ptc.mvc.components.TableConfig;
import com.ptc.mvc.util.ClientMessageSource;
import com.ptc.windchill.enterprise.product.ProductList;
import com.ptc.windchill.enterprise.product.ProductListCommand;
import wt.util.WTException;

@ComponentBuilder({"netmarkets.product.list"})
public class ProductListTableBuilder extends AbstractConfigurableTableBuilder {
    private final ClientMessageSource messageSource = this.getMessageSource("com.ptc.windchill.enterprise.product.ProductClientResource");
    private final ClientMessageSource productMessageSource = this.getMessageSource("com.ptc.netmarkets.product.productResource");

    public ProductListTableBuilder() {
    }

    public Object buildComponentData(ComponentConfig var1, ComponentParams var2) throws Exception {
        String var3 = "netmarkets.product.list";
        return ProductListCommand.getProducts(var3);
    }

    public ComponentConfig buildComponentConfig(ComponentParams var1) throws WTException {
        String var2 = "ProductsHelp";
        ComponentConfigFactory var3 = this.getComponentConfigFactory();
        TableConfig var4 = var3.newTableConfig();
        var4.setId("netmarkets.product.list");
        var4.setConfigurable(true);
        var4.setType("wt.pdmlink.PDMLinkProduct");
        var4.setLabel(this.messageSource.getMessage("11"));
        var4.setActionModel("product list");
        var4.setSelectable(true);
        var4.setHelpContext(var2);
        ColumnConfig var5 = var3.newColumnConfig("name", true);
        var5.setInfoPageLink(true);
        var5.setSortable(true);
        var4.addComponent(var5);
        ColumnConfig var6 = var3.newColumnConfig("infoPageAction", false);
        var6.setSortable(false);
        var4.addComponent(var6);
        ColumnConfig var7 = var3.newColumnConfig("nmActions", false);
        var7.setSortable(false);
        var4.addComponent(var7);
        ColumnConfig var8 = var3.newColumnConfig("containerInfo.owner", false);
        var8.setSortable(true);
        var4.addComponent(var8);
        ColumnConfig var9 = var3.newColumnConfig("orgid", true);
        var9.setSortable(true);
        var4.addComponent(var9);
        ColumnConfig var10 = var3.newColumnConfig("type", false);
        var10.setSortable(false);
        var4.addComponent(var10);
        ColumnConfig var11 = var3.newColumnConfig("thePersistInfo.modifyStamp", true);
        var11.setSortable(true);
        var4.addComponent(var11);
        ColumnConfig var12 = var3.newColumnConfig("containerInfo.description", true);
        ((JcaColumnConfig)var12).setVariableHeight(true);
        var12.setSortable(true);
        var4.addComponent(var12);
        ColumnConfig var13 = var3.newColumnConfig("containerInfo.creator", true);
        var13.setSortable(true);
        var13.setLabel(this.productMessageSource.getMessage("94"));
        var4.addComponent(var13);
        ColumnConfig var14 = var3.newColumnConfig("thePersistInfo.createStamp", true);
        var14.setSortable(true);
        var4.addComponent(var14);
        ColumnConfig var15 = var3.newColumnConfig("containerInfo.privateAccess", true);
        var15.setSortable(true);
        var4.addComponent(var15);
        ColumnConfig var16 = var3.newColumnConfig("containerInfo.description", true);
        ((JcaColumnConfig)var16).setVariableHeight(true);
        var4.addComponent(var16);
        var4.setShowCount(true);
        var4.setShowCustomViewLink(false);
        var4.setView("/product/productlist.jsp");
        return var4;
    }

    public ConfigurableTable buildConfigurableTable(String var1) throws WTException {
        return new ProductList();
    }
}
