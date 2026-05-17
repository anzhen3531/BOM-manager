package com.ptc.core.components.factory.dataUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger logger = LoggerFactory.getLogger(DisplayIdentifierDataUtility.class);

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
        logger.debug("{}", "DisplayIdentifierDataUtility.createSingleValueDisplayComponent");
        logger.debug("{}", "var1 = " + var1 + ", var2 = " + var2 + ", var3 = " + var3);
        AttributeGuiComponent var5 = super.createSingleValueDisplayComponent(var1, var2, var3, var4);
        if (var5 instanceof TextDisplayComponent) {
            ((TextDisplayComponent) var5).setCreateHyperlinks(false);
        }

        return var5;
    }
}