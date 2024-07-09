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

<%@ include file="/netmarkets/jsp/components/includeWizBean.jspf" %>


<%
    // 保持物料类型同步
    String derivedPartType = "";
%>

<%--
refreshCC.js below is required for refreshing local cache when part is created
in active workspace within uwgm browser.
--%>
<script type="text/javascript" SRC="templates/cadx/common/refreshCC.js"></script>

<%--
PartHelper.js below is required dynamically insert/remove the classification step
--%>
<script language="JavaScript" src="netmarkets/javascript/part/PartHelper.js"></script>

<%--
revisionLabelPicker.js is required if insert revision action is being performed.
--%>
<script language="JavaScript" src='netmarkets/javascript/util/revisionLabelPicker.js'></script>

<%--
CADDocument.js is required if a CAD document is created in the wizard.
--%>
<script type="text/javascript" src="netmarkets/javascript/uwgmcadx/CADDocument.js"></script>

<%--
Below javascript is required to add listener on multiPartWizAttributesTableDescriptor table on afterRowRefresh event.
--%>
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

<%-- New CAD Doc panel populated from the New Part attributes --%>
<c:choose>
    <c:when test='${param.showNewCADDocStep == "true"}'>
        <%-- Creating a part and possibly a CAD document and attachments in this wizard --%>
        <jca:initializeItem operation="${createBean.create}" objectHandle="<%=PartConstants.ObjectHandles.PART%>"
                            attributePopulatorClass="com.ptc.windchill.enterprise.part.forms.PartAttributePopulator"/>

        <%
            String baseTypeName = "wt.epm.EPMDocument";
            String domain = TypeDomainHelper.getExchangeDomain();
            String typeName = "DefaultEPMDocument";
            String softType = baseTypeName + "|" + domain + "." + typeName;
        %>

        <jca:initializeItem operation="${createBean.create}" objectHandle="<%=PartConstants.ObjectHandles.CADDOC%>"
                            baseTypeName="<%=softType%>"/>
        <%-- To set operation for attachment step --%>
        <jca:initializeItem operation="${createBean.create}"/>
    </c:when>

    <c:when test='${param.showNewCADDocStep == "false"}'>
        <jca:initializeItem operation="${createBean.create}"
                            objectHandle="<%=PartConstants.ObjectHandles.PART%>"
                            baseTypeName="<%=derivedPartType%>"
                            attributePopulatorClass="com.ptc.windchill.enterprise.part.forms.PartAttributePopulator"/>
    </c:when>

    <c:when test='${param.isPlaceholderAction == "true"}'>
        <fmt:setBundle basename="com.ptc.windchill.enterprise.revisionControlled.insertWizardResource"/>
        <fmt:message var="wizardTitle" key="part.createNewPlaceholder.title"/>
        <jca:initializeItem operation="${createBean.create}"
                            baseTypeName="WCTYPE|wt.part.WTPart|wt.part.Placeholder"
                            attributePopulatorClass="com.ptc.windchill.enterprise.part.forms.PartAttributePopulator"/>
    </c:when>

    <c:otherwise>
        <%-- Creating a part and attachments in this wizard --%>
        <%-- populate number attribute for insert revision action only --%>
        <jca:initializeItem operation="${createBean.create}"
                            attributePopulatorClass="com.ptc.windchill.enterprise.part.forms.PartAttributePopulator"/>
    </c:otherwise>
</c:choose>

<%--
    Set the typeComponentId to restrict the containers to PDMLink
    containers i.e. Product and Library. This affects the setContext
    wizard step that only gets displayed while creating a part from
    a workspace that is associated to a product or a library or when launched from the
    Advanced Assembly Editor. For a workspace
    that is associated to a project, the step validator hides the setContext
    wizard step.
--%>
<c:if test='${param.invokedfrom == "workspace" || param.showContextStep == "true"}'>
    <jsp:setProperty name="createBean"
                     property="contextPickerTypeComponentId" value="PDMLink.containerSearch"/>
    <jsp:setProperty name="createBean"
                     property="contextPickerExcludeTypes" value="WCTYPE|wt.inf.library.WTLibrary|com.ptc.QMS"/>
</c:if>

<%-- Set the default part management help --%>
<c:set var="helpKey" value="PartCreate_help" scope="page"/>
<c:set var="buttonList" value="DefaultWizardButtonsNoApply" scope="page"/>

<%-->
Depending on if the wizard is multiple part create, different steps are included in main wizard. Label for setAttributesWizStep
is also different for multiple part create wizard.
<--%>

<!-- autoNaming fields for single part create wizard . restricted for multiPart wizard-->
<c:if test="${isMultiPart == 'false'}">
    <input id="enforceClassificationNamingRule" type="hidden" name="enforceClassificationNamingRule">
    <input id="classificationNameOverride" type="hidden" name="classificationNameOverride">
    <script>
        bundleHandler.set('com.ptc.core.ui.componentRB.NUMBER_GENERATED_DISPLAY_STRING', 'com.ptc.core.ui.componentRB.NUMBER_GENERATED_DISPLAY_STRING');
    </script>
</c:if>

<c:choose>
    <c:when test="${isMultiPart == 'true'}">
        <jca:wizard helpSelectorKey="PMPartMultipleCreate" buttonList="NewMultiPartsWizardButtons"
                    title="${createMultiplePartWizardTitle}">
            <jca:wizardStep action="setContextWizStep" type="object"/>
            <jca:wizardStep action="defineItemWizStep" label="${definePartWizStepLabel}" type="object"/>
            <jca:wizardStep action="setAttributesWizStepForCreateMultiPart"
                            label="${setAttributesWizStepForCreateMultiPartLabel}" type="part"/>
            <jca:wizardStep action="securityLabelStep" type="securityLabels"/>
        </jca:wizard>

        <!-- Hidden fields to store classification information -->
        <input id="selectedClfNodes" type="hidden" name="selectedClfNodes">
        <input id="selectedClfNodesDisplayName" type="hidden" name="selectedClfNodesDisplayName">
        <input id="classificationAttributes" type="hidden" name="classificationAttributes">

    </c:when>

    <c:when test='${param.showNewCADDocStep == "true"}'>
        <%-->
        Two object types might be created, so the object handle is included in the names
        of the steps. This sets the value of a variable in PartHelper.js so the
        classification step will have the correct name when it is attached or detached.
        <--%>
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
    </c:when>

    <c:when test='${param.showNewCADDocStep == "false"}'>
        <%@include file="/netmarkets/jsp/attachments/initAttachments.jspf" %>
        <jca:wizard helpSelectorKey="${helpKey}" buttonList="${buttonList}" title="${wizardTitle}">
            <jca:wizardStep action="setContextWizStep" objectHandle="<%=PartConstants.ObjectHandles.PART%>"
                            type="object"/>
            <jca:wizardStep action="defineItemAttributesWizStep" objectHandle="<%=PartConstants.ObjectHandles.PART%>"
                            type="object"/>
            <jca:wizardStep action="setClassificationAttributesWizStep"
                            objectHandle="<%=PartConstants.ObjectHandles.PART%>" type="part"/>
            <jca:wizardStep action="securityLabelStep" objectHandle="<%=PartConstants.ObjectHandles.PART%>"
                            type="securityLabels"/>
            <jca:wizardStep action="attachments_step" type="attachments"/>
        </jca:wizard>
    </c:when>

    <c:otherwise>
        <%@include file="/netmarkets/jsp/attachments/initAttachments.jspf" %>
        <jca:wizard helpSelectorKey="${helpKey}" buttonList="${buttonList}" title="${wizardTitle}">
            <jca:wizardStep action="setContextWizStep" type="object"/>
            <jca:wizardStep action="defineItemAttributesWizStep" type="object"/>
            <jca:wizardStep action="setClassificationAttributesWizStep" type="part"/>
            <jca:wizardStep action="securityLabelStep" type="securityLabels"/>
            <jca:wizardStep action="attachments_step" type="attachments"/>
        </jca:wizard>
    </c:otherwise>
</c:choose>

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

    // 编写默认复制属性的代码
    PTC.onReady()

</script>
