package com.ptc.core.components.factory.dataUtilities;

import com.ptc.core.components.descriptor.ModelContext;
import com.ptc.core.components.rendering.guicomponents.AttributeGuiComponent;
import com.ptc.core.components.rendering.guicomponents.TextDisplayComponent;
import wt.util.WTException;

public class DisplayIdentifierDataUtility extends StringDataUtility {
    public DisplayIdentifierDataUtility() {
    }

    public AttributeGuiComponent createSingleValueDisplayComponent(String var1, Object var2, Object var3, ModelContext var4) throws WTException {
        AttributeGuiComponent var5 = super.createSingleValueDisplayComponent(var1, var2, var3, var4);
        if (var5 instanceof TextDisplayComponent) {
            ((TextDisplayComponent)var5).setCreateHyperlinks(false);
        }

        return var5;
    }
}
