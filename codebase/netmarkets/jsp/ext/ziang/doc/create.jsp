<%@ taglib prefix="jca" uri="http://www.ptc.com/windchill/taglib/components"%>
<%@ include file="/netmarkets/jsp/components/beginWizard.jspf"%>
<%@ include file="/netmarkets/jsp/components/includeWizBean.jspf"%>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/fmt" prefix="fmt"%>

<!-- <script type="text/javascript" src="netmarkets/javascript/ext/gwc/extend/application/applicationForm.js"></script> -->
<script language="JavaScript" src='netmarkets/javascript/util/revisionLabelPicker.js'></script>

<fmt:setLocale value="${localeBean.locale}"/>
<fmt:setBundle basename="com.ptc.windchill.enterprise.doc.documentResource" />

<fmt:message var="describedHeader" key="CREATE_TITLE" />

<jca:initializeItem operation="${createBean.create}" baseTypeName="wt.doc.WTDocument"/>

<jca:wizard title="${describedHeader }" buttonList="DefaultWizardButtonsNoApply" helpSelectorKey="">
    <jca:wizardStep action="technologyAttributes"  type="extDoc"/>
    <jca:wizardStep action="attachments_step" type="attachments" />
</jca:wizard>

<%@include file="/netmarkets/jsp/util/end.jspf"%>