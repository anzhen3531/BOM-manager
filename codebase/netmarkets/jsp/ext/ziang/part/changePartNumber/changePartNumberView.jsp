<%@ taglib prefix="jca" uri="http://www.ptc.com/windchill/taglib/components" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/partclient" prefix="partclient" %>
<%@ taglib prefix="wctags" tagdir="/WEB-INF/tags" %>

<%@ page import="com.ptc.core.components.descriptor.DescriptorConstants" %>
<%@ page import="com.ptc.windchill.enterprise.part.PartConstants" %>
<%@ page import="com.ptc.windchill.enterprise.part.partResource" %>
<%@ page import="com.ptc.windchill.uwgm.cadx.createecaddesign.documentECADResource" %>
<%@ page import="com.ptc.core.meta.type.mgmt.server.impl.TypeDomainHelper" %>
<%@ page import="ext.ziang.common.util.IBAUtils" %>
<%@ page import="ext.ziang.common.util.ToolUtils" %>
<%@ page import="wt.fc.Persistable" %>
<%@ page import="wt.part.WTPart" %>
<%@ page import="wt.type.TypedUtility" %>
<%@ page import="ext.ziang.common.helper.attr.IBAOperationHelper" %>
<%@ page import="java.util.Map" %>

<%@ include file="/netmarkets/jsp/components/includeWizBean.jspf" %>

<%-- 引入JS --%>
<script type="text/javascript" SRC="templates/cadx/common/refreshCC.js"></script>
<script language="JavaScript" src="netmarkets/javascript/part/PartHelper.js"></script>
<script language="JavaScript" src='netmarkets/javascript/util/revisionLabelPicker.js'></script>
<script type="text/javascript" src="netmarkets/javascript/uwgmcadx/CADDocument.js"></script>

<script type="text/javascript">
    Ext.ComponentMgr.onAvailable('multiPartWizAttributesTableDescriptor', PTC.jca.table.sortTableOnAfterRowRefresh);
</script>

<fmt:setBundle basename="com.ptc.windchill.enterprise.part.partResource"/>

<fmt:message var="definePartWizStepLabel" key="part.createPartWizard.DEFINE_ITEM_WIZ_STEP_LABEL"/>
<fmt:message var="setAttributesWizStepForCreateMultiPartLabel"
             key="part.createPartWizard.SET_IDENTITY_ATTRIBUTES_WIZ_STEP_LABEL"/>
<fmt:message var="setAttributesWizStepLabel" key="part.createPartWizard.SET_ATTRIBUTES_WIZ_STEP_LABEL"/>
<fmt:message var="setAttributesWizStepMultiCreateLabel"
             key="part.createPartWizard.SET_ATTRIBUTES_WIZ_STEP_MULTI_CREATE_LABEL"/>
<fmt:message var="createMultiplePartWizardTitle" key="part.createMultiPart.WIZARD_LABEL"/>
<fmt:message var="newCADDocWizStepLabel" key="part.createPartWizard.NEW_CAD_DOC_WIZ_STEP_LABEL"/>

<partclient:multiPartWizardLaunch multiPart="isMultiPart"/>
<fmt:message var="wizardTitle" key="part.createPartWizard.title"/>

<fmt:setBundle basename="com.ptc.windchill.uwgm.cadx.EcadActions.ecadActionsResource"/>
<fmt:message var="wizardTitleFromURL" key="${param.wizardWindowTitle}"/>

<c:if test='${param.wizardWindowTitle != ""}'>
    <c:set var="wizardTitle" value="${wizardTitleFromURL}"/>
</c:if>

<%-- 设置类型属性 --%>

<%
    // 获取当前物料类型
    String oid = request.getParameter("oid");
    Persistable persistable = ToolUtils.getObjectByOid(oid);
    WTPart part = null;
    if (persistable instanceof WTPart) {
        part = ((WTPart) persistable);
    }
    // 获取部件类型 wt.part.WTPart|com.ziang.Panzer|com.ziang.PanzerMaterial
    String typename = "WCTYPE|" + TypedUtility.getTypeIdentifier(part).getTypename();
    // 获取所有的IBA属性
    Map<String, Object> allIBAValue = IBAOperationHelper.findAllIBAValue(part);
    System.out.println("allIBAValue = " + allIBAValue);
%>
<jca:initializeItem operation="${createBean.create}"
                    objectHandle="<%=PartConstants.ObjectHandles.PART%>"
                    baseTypeName="<%=typename%>"
                    attributePopulatorClass="com.ptc.windchill.enterprise.part.forms.PartAttributePopulator"/>

<%--   --%>
<c:if test='${param.invokedfrom == "workspace" || param.showContextStep == "true"}'>
    <jsp:setProperty name="createBean"
                     property="contextPickerTypeComponentId" value="PDMLink.containerSearch"/>
    <jsp:setProperty name="createBean"
                     property="contextPickerExcludeTypes" value="WCTYPE|wt.inf.library.WTLibrary|com.ptc.QMS"/>
</c:if>

<%-- Set the default part management help --%>
<c:set var="helpKey" value="PartCreate_help" scope="page"/>
<c:set var="buttonList" value="DefaultWizardButtonsNoApply" scope="page"/>


<c:if test="${isMultiPart == 'false'}">
    <input id="enforceClassificationNamingRule" type="hidden" name="enforceClassificationNamingRule">
    <input id="classificationNameOverride" type="hidden" name="classificationNameOverride">
    <script>
        bundleHandler.set('com.ptc.core.ui.componentRB.NUMBER_GENERATED_DISPLAY_STRING', 'com.ptc.core.ui.componentRB.NUMBER_GENERATED_DISPLAY_STRING');
    </script>
</c:if>


<%-- 设置分类属性 没有这个则会报错 --%>
<script language="Javascript">
    setPartObjectHandle('!~objectHandle~partHandle~!');
</script>


<%@include file="/netmarkets/jsp/attachments/initAttachments.jspf" %>

<jca:wizard helpSelectorKey="${helpKey}" buttonList="${buttonList}" title="${wizardTitle}"
            formProcessorController="com.ptc.windchill.enterprise.part.forms.CreatePartAndCADDocFormProcessorController">
    <jca:wizardStep action="setContextWizStep" objectHandle="partHandle" type="object"/>
    <jca:wizardStep action="defineItemAttributesWizStep" objectHandle="<%=PartConstants.ObjectHandles.PART%>"
                    type="object"/>
    <jca:wizardStep action="setClassificationAttributesWizStep"
                    objectHandle="<%=PartConstants.ObjectHandles.PART%>" type="part"/>
    <jca:wizardStep action="securityLabelStep" objectHandle="<%=PartConstants.ObjectHandles.PART%>"
                    type="securityLabels"/>
    <jca:wizardStep action="defineItemAttributesWizStepForCADDoc"
                    objectHandle="<%=PartConstants.ObjectHandles.CADDOC%>" type="part"
                    label="${newCADDocWizStepLabel}"/>
    <jca:wizardStep action="attachments_step" type="attachments"/>
</jca:wizard>

<%--- If we are not DTI then add the applet for doing file browsing and file uploads --%>
<wctags:fileSelectionAndUploadAppletUnlessMSOI forceApplet='${param.addAttachments != null }'/>

<script Language="JavaScript">
    /* Sets values of the hidden fields used for classification for multi part create wizard.
       Called by the form processor of the Set Classifications wizard.
    */
    function setClassificationAttributes(classificationAttributes) {
        document.getElementById('classificationAttributes').value = classificationAttributes;
    }
</script>


<script Language="JavaScript">
    /* Clicking the Apply button on a New Multi Parts wizard should reset the wizard to the initial step
       Overridden the Apply button in order to redirect the user to the first step */

    function onMultipartsApply() {

        onApply();
        var result = PTC.wizard.checkRequired(true, true, getCurrentStep());
        //Fixed SPR 2156604. Checking whether required attribute validation returns true or false.
        if (result) {
            setActiveStep(steps[0]);
            setNextStepDirty();
            disableOkButton();
            setStepClean(steps[1]);
            getMainForm().reset();
            clearActionFormData();
            resetOkButton();
        } else {
            setActiveStep(steps[1]);
        }
    }

    /* Clicking the Back button on a New Multi Parts wizard should skip the "nameNumberValidation"
	   Overridden the Back button */

    function onMultipartsBack() {
        var stepIndex = 0;
        var currentStepName = "";
        wizardSteps[currentStepStrName].afterServerVK = null;
        goBack();
        disableOkButton();
        stepIndex = findStepIndex(currentStepStrName);
        stepIndex = stepIndex + 1;
        currentStepName = steps[stepIndex];
        wizardSteps[currentStepName].afterServerVK = "nameNumberValidation";
        PTC.jca.state.getLocalStateStore().clear("multiPartWizAttributesTableDescriptor-grid-state");
    }

    /**
     * 页面加载完成之后进行处理
     */
    PTC.onReady(function () {
        alert("加载完成！")
    });

</script>
