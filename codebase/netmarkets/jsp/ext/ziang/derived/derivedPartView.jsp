<%@ taglib prefix="jca" uri="http://www.ptc.com/windchill/taglib/components"%>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/partclient" prefix="partclient"%>
<%@ taglib prefix="wctags" tagdir="/WEB-INF/tags" %>

<%@ page import="com.ptc.core.components.descriptor.DescriptorConstants" %>
<%@ page import="com.ptc.windchill.enterprise.part.PartConstants" %>
<%@ page import="com.ptc.windchill.enterprise.part.partResource" %>
<%@ page import="com.ptc.windchill.uwgm.cadx.createecaddesign.documentECADResource" %>
<%@ page import="com.ptc.core.meta.type.mgmt.server.impl.TypeDomainHelper" %>

<%--  --%>
<%@ include file="/netmarkets/jsp/components/includeWizBean.jspf"%>

<script type="text/javascript" SRC="templates/cadx/common/refreshCC.js"></script>
<script language="JavaScript" src="netmarkets/javascript/part/PartHelper.js"></script>
<script language="JavaScript" src='netmarkets/javascript/util/revisionLabelPicker.js'></script>
<script type="text/javascript" src="netmarkets/javascript/uwgmcadx/CADDocument.js" ></script>

<%--  --%>
<script type="text/javascript">
    Ext.ComponentMgr.onAvailable('multiPartWizAttributesTableDescriptor', PTC.jca.table.sortTableOnAfterRowRefresh);
</script>

<fmt:setBundle basename="com.ptc.windchill.enterprise.part.partResource"/>
<fmt:message var="definePartWizStepLabel" key="part.createPartWizard.DEFINE_ITEM_WIZ_STEP_LABEL" />
<fmt:message var="setAttributesWizStepForCreateMultiPartLabel" key="part.createPartWizard.SET_IDENTITY_ATTRIBUTES_WIZ_STEP_LABEL" />
<fmt:message var="setAttributesWizStepLabel" key="part.createPartWizard.SET_ATTRIBUTES_WIZ_STEP_LABEL" />
<fmt:message var="setAttributesWizStepMultiCreateLabel" key="part.createPartWizard.SET_ATTRIBUTES_WIZ_STEP_MULTI_CREATE_LABEL" />
<fmt:message var="createMultiplePartWizardTitle" key="part.createMultiPart.WIZARD_LABEL" />
<fmt:message var="newCADDocWizStepLabel" key="part.createPartWizard.NEW_CAD_DOC_WIZ_STEP_LABEL" />
<partclient:multiPartWizardLaunch multiPart="isMultiPart"/>
<fmt:message var="wizardTitle" key="part.createPartWizard.title" />
<fmt:setBundle basename="com.ptc.windchill.uwgm.cadx.EcadActions.ecadActionsResource"/>
<fmt:message var="wizardTitleFromURL" key="${param.wizardWindowTitle}" />
<c:if test='${param.wizardWindowTitle != ""}'>
    <c:set var="wizardTitle" value="${wizardTitleFromURL}"/>
</c:if>

<jca:initializeItem operation="${createBean.create}" baseTypeName="wt.part.WTPart"
                    objectHandle="<%=PartConstants.ObjectHandles.PART%>"
                    attributePopulatorClass="com.ptc.windchill.enterprise.part.forms.PartAttributePopulator"/>


<c:set var="helpKey" value="PartCreate_help" scope="page"/>
<c:set var="buttonList" value="DefaultWizardButtonsNoApply" scope="page"/>

<%--    --%>
<c:if test="${isMultiPart == 'false'}">
    <input id="enforceClassificationNamingRule" type="hidden" name="enforceClassificationNamingRule" >
    <input id="classificationNameOverride" type="hidden" name="classificationNameOverride" >
    <script>
        bundleHandler.set('com.ptc.core.ui.componentRB.NUMBER_GENERATED_DISPLAY_STRING', 'com.ptc.core.ui.componentRB.NUMBER_GENERATED_DISPLAY_STRING');
    </script>
</c:if>

<%@include file="/netmarkets/jsp/attachments/initAttachments.jspf"%>
<jca:wizard helpSelectorKey="${helpKey}" buttonList="${buttonList}" title="${wizardTitle}">
    <jca:wizardStep action="setContextWizStep"  type="object"/>
    <jca:wizardStep action="defineItemAttributesWizStep"  type="object"/>
    <jca:wizardStep action="setClassificationAttributesWizStep" type="part"/>
    <jca:wizardStep action="securityLabelStep" type="securityLabels"/>
    <jca:wizardStep action="attachments_step"  type="attachments" />
</jca:wizard>

<wctags:fileSelectionAndUploadAppletUnlessMSOI forceApplet='${param.addAttachments != null }'/>

<%--  --%>
<script Language="JavaScript">
    function setClassificationAttributes(classificationAttributes) {
        document.getElementById('classificationAttributes').value=classificationAttributes;
    }
</script>

<script Language="JavaScript">

    function onMultipartsApply() {
        onApply();
        var result = PTC.wizard.checkRequired(true, true, getCurrentStep());
        // Fixed SPR 2156604. Checking whether required attribute validation returns true or false.
        if(result) {
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

    //
    function onMultipartsBack() {
        var stepIndex=0;
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
</script>
