package com.ptc.core.components.factory.dataUtilities;

import com.ptc.core.components.descriptor.ModelContext;
import com.ptc.core.components.rendering.guicomponents.AttributeGuiComponent;
import com.ptc.core.components.rendering.guicomponents.TextDisplayComponent;
import ext.ziang.model.PanzerPart;
import wt.part.WTPart;
import wt.util.WTException;

/**
 * 显示标识符数据实用程序
 *
 * @author anzhen
 * @date 2024/07/04
 */
public class DisplayIdentifierDataUtility extends StringDataUtility {
    public DisplayIdentifierDataUtility() {
    }

    /**
     * 创建单值显示组件
     *
     * @param var1 变量1
     * @param var2 变量2
     * @param var3 变量3
     * @param var4 变量4
     * @return {@link AttributeGuiComponent }
     * @throws WTException WTException
     */
    public AttributeGuiComponent createSingleValueDisplayComponent(String var1, Object var2, Object var3, ModelContext var4) throws WTException {

        if (var2 instanceof PanzerPart) {
            // 单独处理
            System.out.println("进入部件处理方式");
        }
        System.out.println("DisplayIdentifierDataUtility.createSingleValueDisplayComponent");
        System.out.println("var1 = " + var1 + ", var2 = " + var2 + ", var3 = " + var3 + ", var4 = " + var4);
        AttributeGuiComponent var5 = super.createSingleValueDisplayComponent(var1, var2, var3, var4);
        if (var5 instanceof TextDisplayComponent) {
            ((TextDisplayComponent) var5).setCreateHyperlinks(false);
        }

        return var5;
    }
}