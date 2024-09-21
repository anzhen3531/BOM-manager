<%@page import="com.ptc.windchill.enterprise.part.PartConstants" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/components"
           prefix="jca" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://www.ptc.com/windchill/taglib/mvc" prefix="mvc" %>
<%@ include file="/netmarkets/jsp/util/begin.jspf" %>

<fmt:setLocale value="${localeBean.locale}"/>
<fmt:setBundle basename="com.ptc.windchill.partslink.partslinkClientResource"/>

<%
    pageContext.setAttribute("isMultipart", PartConstants.RequestParam.Values.MULTIPART_WIZARD_TYPE
            .equals(commandBean.getTextParameter(PartConstants.RequestParam.Names.WIZARD_TYPE)));
    request.setAttribute("enforceClassificationNamingRule", commandBean.getTextParameter("enforceClassificationNamingRule"));
%>
<c:if test="${isMultipart == 'false' && enforceClassificationNamingRule == 'true'}">
    <input type="hidden" name="autoGenName" id="autoGenName"></input>
    <input type="hidden" name="autonameOverrideChecked" id="autonameOverrideChecked" value="false"></input>
    <div align="right" style="padding-right: 8px;">
        <jca:action actionName="overrideName" actionType="partslink" button="false"
                    validateAction="true"/>
    </div>
</c:if>
<jsp:include page="${mvc:getComponentURL('classification.attribute.panel')}" flush="true"/>
<%@ include file="/netmarkets/jsp/util/end.jspf" %>
