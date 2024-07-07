package com.ptc.core.components.factory.dataUtilities;

import com.ptc.core.components.descriptor.ModelContext;
import com.ptc.core.components.rendering.guicomponents.AttributeGuiComponent;
import com.ptc.core.components.rendering.guicomponents.TextDisplayComponent;
import wt.util.WTException;

import java.util.Locale;

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
        System.out.println("DisplayIdentifierDataUtility.createSingleValueDisplayComponent");
        System.out.println("var1 = " + var1 + ", var2 = " + var2 + ", var3 = " + var3);
        if (var2 instanceof PanzerPart) {
            PanzerPart panzerPart = (PanzerPart) var2;
            // 单独处理
            System.out.println("进入部件处理方式");
            TextDisplayComponent textDisplayComponent = new TextDisplayComponent(var1);
            textDisplayComponent.setValue(panzerPart.getNumber() + ", " +
                    panzerPart.getName() + ", " +
                    panzerPart.getIterationDisplayIdentifier().getLocalizedMessage(Locale.CHINA));
            return textDisplayComponent;
        }
        AttributeGuiComponent var5 = super.createSingleValueDisplayComponent(var1, var2, var3, var4);
        if (var5 instanceof TextDisplayComponent) {
            ((TextDisplayComponent) var5).setCreateHyperlinks(false);
        }

        return var5;
    }
}