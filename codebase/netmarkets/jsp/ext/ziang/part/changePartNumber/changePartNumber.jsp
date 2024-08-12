<%@ page import="com.ptc.windchill.enterprise.part.PartConstants" %>
<%@ include file="/netmarkets/jsp/components/beginWizard.jspf" %>
<%@ include file="/netmarkets/jsp/components/includeWizBean.jspf" %>
<%@ taglib prefix="jca" uri="http://www.ptc.com/windchill/taglib/components" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="wctags" tagdir="/WEB-INF/tags" %>

<%@ page import="com.ptc.windchill.enterprise.part.PartConstants" %>

<%--D:\private\product\codebase\netmarkets\jsp\ext\ziang\part\changePartNumber\changePartNumber.jsp--%>

<jca:initializeItem operation="${createBean.create}"
                    objectHandle="<%=PartConstants.ObjectHandles.PART%>"
                    attributePopulatorClass="com.ptc.windchill.enterprise.part.forms.PartAttributePopulator"
                    baseTypeName="WCTYPE|wt.part.WTPart"/>

<%-- Set the default part management help --%>
<c:set var="helpKey" value="PartCreate_help" scope="page"/>
<c:set var="buttonList" value="DefaultWizardButtonsNoApply" scope="page"/>


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

<%@ include file="/netmarkets/jsp/util/end.jspf" %>
