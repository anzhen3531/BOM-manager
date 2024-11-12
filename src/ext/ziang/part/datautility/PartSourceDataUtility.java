package ext.ziang.part.datautility;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import com.ptc.core.components.rendering.AbstractGuiComponent;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.ptc.core.components.descriptor.ModelContext;
import com.ptc.core.components.rendering.guicomponents.*;
import com.ptc.core.meta.common.IdentifierFactory;
import com.ptc.core.meta.common.TypeIdentifier;
import com.ptc.core.meta.type.common.TypeInstance;
import com.ptc.core.ui.resources.ComponentMode;
import com.ptc.netmarkets.model.NmOid;
import com.ptc.windchill.enterprise.part.dataUtilities.SourceDataUtility;

import ext.ziang.common.util.ToolUtils;
import wt.facade.suma.SumaFacade;
import wt.inf.container.WTContainer;
import wt.inf.library.WTLibrary;
import wt.part.Source;
import wt.part.WTPart;
import wt.services.applicationcontext.implementation.DefaultServiceProvider;
import wt.util.WTException;

/**
 * 物料来源数据单元
 */
public class PartSourceDataUtility extends SourceDataUtility {
    private static final String SOURCE_FORM_PROCESSOR_DELEGATE =
        "com.ptc.windchill.enterprise.part.forms.SourceFormProcessorDelegate";
    private static final IdentifierFactory IDENTIFIER_FACTORY =
        (IdentifierFactory)DefaultServiceProvider.getService(IdentifierFactory.class, "logical");
    private static TypeIdentifier supplierPartType = null;
    private static final Logger log;

    @Override
    public Object getDataValue(String componentId, Object object, ModelContext modelContext) throws WTException {
        Object superObj = super.getDataValue(componentId, object, modelContext);
        if (log.isDebugEnabled()) {
            log.debug("getDataValue() componentId= {}", componentId);
            log.debug("rawValue= {}", modelContext.getRawValue());
            log.debug("mode= {}", modelContext.getDescriptorMode());
        }
        GUIComponentArray guicomponentarray = new GUIComponentArray();
        if (superObj instanceof AttributeInputCompositeComponent) {
            boolean supplierPart = this.isSupplierPart(object);
            // 获取OOTB默认返回的组件
            AttributeInputCompositeComponent inputCompositeComponent = (AttributeInputCompositeComponent)superObj;
            AttributeInputComponent inputComponent = inputCompositeComponent.getValueInputComponent();
            inputComponent.addHiddenField("FormProcessorDelegate", SOURCE_FORM_PROCESSOR_DELEGATE);
            // 如果为供应商物料 默认为外采
            if (supplierPart) {
                log.debug("Part type selected is a supplier part.");
                TextDisplayComponent textDisplayComponent = new TextDisplayComponent(inputComponent.getLabel());
                textDisplayComponent.setValue(Source.BUY.getLocalizedMessage(modelContext.getLocale()));
                textDisplayComponent.addHiddenField("FormProcessorDelegate", SOURCE_FORM_PROCESSOR_DELEGATE);
                return textDisplayComponent;
            }

            String containerId = modelContext.getNmCommandBean().getRequest().getParameter("ContainerOid");
            if (StringUtils.isEmpty(containerId)) {
                containerId = ToolUtils.getOROid(modelContext.getNmCommandBean().getContainer());
            }
            // 获取操作模型
            ComponentMode operationView = modelContext.getDescriptorMode();
            if (StringUtils.isNotEmpty(containerId)
                && (operationView.equals(ComponentMode.CREATE) || operationView.equals(ComponentMode.EDIT))) {
                WTContainer container = (WTContainer)ToolUtils.getObjectByOid(containerId);
                if (Objects.nonNull(container) && container instanceof WTLibrary) {
                    setJSAction(inputComponent, modelContext);
                    return superObj;
                }
            }
            setJSAction(inputComponent, modelContext);
        }
        return superObj;
    }

    /**
     * 根据类型获取默认值
     * 
     * @param modelContext 模型上下文
     * @return 默认值
     * @throws WTException
     */
    private static String getDefaultValue(ModelContext modelContext) throws WTException {
        String value = "";
        if (modelContext.getDescriptorMode().equals(ComponentMode.CREATE)) {
            value = Source.MAKE.getStringValue();
        } else {
            NmOid actionOid = modelContext.getNmCommandBean().getActionOid();
            if (actionOid != null && (actionOid.getRefObject() instanceof WTPart)) {
                WTPart part = (WTPart)actionOid.getRefObject();
                Source source = part.getSource();
                if (Source.MAKE.equals(source)) {
                    value = Source.MAKE.getStringValue();
                }
            }
        }
        return value;
    }

    /**
     * 判断是否为供应商部件
     * 
     * @param part 物料
     * @return true / false
     */
    private boolean isSupplierPart(Object part) {
        boolean supplierPart = false;
        if (supplierPartType != null && part instanceof TypeInstance) {
            TypeInstance typeInstance = (TypeInstance)part;
            TypeIdentifier typeIdentifier = (TypeIdentifier)typeInstance.getIdentifier().getDefinitionIdentifier();
            supplierPart = typeIdentifier.isDescendedFrom(supplierPartType);
        }

        return supplierPart;
    }

    static {
        try {
            log = LoggerFactory.getLogger(PartSourceDataUtility.class);
            if (SumaFacade.getInstance().isInstalled()) {
                supplierPartType = IDENTIFIER_FACTORY.newWCTypeIdentifier("com.ptc.windchill.suma.part.SupplierPart");
            }
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * 设置JS Action
     * 
     * @param inputComponent 组件
     */
    public void setJSAction(AbstractGuiComponent inputComponent, ModelContext modelContext) {
        if (inputComponent instanceof StringInputComponent) {
            StringInputComponent stringInputComponentOld = (StringInputComponent)inputComponent;
            AbstractGuiComponent guiComponent = stringInputComponentOld.getTextUI();
            if (guiComponent instanceof ComboBox) {
                ComboBox comboBox = (ComboBox)guiComponent;
                String jsScript = "refreshLifeCycleStatusBySource('%s','%s','%s')";
                comboBox.addJsAction("onChange",
                    String.format(jsScript, "source", "ipd_status", modelContext.getDescriptorMode().name()));
            }
        }
    }
}
