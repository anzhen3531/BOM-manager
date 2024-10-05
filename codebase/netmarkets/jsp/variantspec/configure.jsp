<%-- bcwti
 *
 * Copyright (c) 2003 Parametric Technology Corporation (PTC). All Rights
 * Reserved.
 *
 * This software is the confidential and proprietary information of PTC.
 * You shall not disclose such confidential information and shall use it
 * only in accordance with the terms of the license agreement.
 *
 * ecwti
 * --%>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/components" prefix="jca"%>
<%@ include file="/netmarkets/jsp/components/beginWizard.jspf"%>
<%@ include file="/netmarkets/jsp/components/includeWizBean.jspf"%>
<%@ include file="/netmarkets/jsp/variantspec/configure.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="jca" uri="http://www.ptc.com/windchill/taglib/components" %>
<%@ page import="wt.util.HTMLEncoder"%>
<%@ include file="/netmarkets/jsp/components/createEditUIText.jspf"%>

<%
    String mode = HTMLEncoder.encodeForHTMLAttribute(request.getParameter("MODE"));
    mode = (mode == null || mode.isEmpty()) ? "CREATE" : mode.trim();
%><input type="hidden" name="CONFIGURE_MODE" id="CONFIGURE_MODE" value="<%=mode%>"/>

<c:choose>
    <c:when test="<%=mode.equals(\"CREATE\")%>">
        <jca:initializeItem operation="${createBean.create}" baseTypeName="com.ptc.wpcfg.doc.VariantSpec"/>
    </c:when>
    <c:otherwise>
        <jca:initializeItem operation="${createBean.edit}" baseTypeName="com.ptc.wpcfg.doc.VariantSpec"
            formProcessor="com.ptc.windchill.option.variantspec.form.EditVariantSpecFormProcessor"/>
    </c:otherwise>
</c:choose>

<div id="configure_wizard">

<c:choose>
    <c:when test="<%=DocHelper.isGenerateVariantsSupported()%>">
<jca:wizard helpSelectorKey="variantspec_configure_help" buttonList="ConfigureWizardButtons">
        <jca:wizardStep action="ecStep"               type="variantspec"/>
        <jca:wizardStep action="specedStep"           type="variantspec"/>
        <jca:wizardStep action="specPreviewStep"      type="variantspec"/>
        <jca:wizardStep action="defineVariantSpec"    type="variantspec"/>
        <jca:wizardStep action="variantsPreviewStep"  type="variantspec"/>
</jca:wizard>
    </c:when>
    <c:otherwise>
<jca:wizard helpSelectorKey="variantspec_configure_help" buttonList="ConfigureWizardButtons">
        <jca:wizardStep action="ecStep"               type="variantspec"/>
        <jca:wizardStep action="defineVariantSpec"    type="variantspec"/>
</jca:wizard>
    </c:otherwise>
</c:choose>

</div>
<%@ include file="/netmarkets/jsp/util/end.jspf"%>

<script>
    PTC.onReady(function() {
        let elements = document.getElementsByClassName("x-tool x-tool-toggle x-tool-collapse-west");
        elements[0].click();
    });
</script>
