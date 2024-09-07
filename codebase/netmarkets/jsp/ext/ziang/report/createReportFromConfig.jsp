<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/mvc" prefix="mvc" %>
<%@ include file="/netmarkets/jsp/components/beginWizard.jspf" %>
<%@ include file="/netmarkets/jsp/components/includeWizBean.jspf" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/wrappers" prefix="w" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/components" prefix="jca" %>

<%
    String view = request.getParameter("View");
    if (view.equals("CREATE")) {
%>
<jca:wizard title="创建报表数据配置" buttonList="DefaultWizardButtonsNoApply">
    <jca:wizardStep action="createReportFromConfigStep" type="custom"/>
</jca:wizard>
<% } else {%>
<jca:wizard title="修改报表数据配置" buttonList="DefaultWizardButtonsNoApply">
    <jca:wizardStep action="updateReportFromConfigStep" type="custom"/>
</jca:wizard>
<%}%>
<%@ include file="/netmarkets/jsp/util/end.jspf" %>