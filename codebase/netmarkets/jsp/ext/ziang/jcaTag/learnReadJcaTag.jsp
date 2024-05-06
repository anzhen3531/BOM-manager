<%@ page import="ext.ziang.docTag.LearnJcaTagUtil" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/components" prefix="jca" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/wrappers" prefix="w" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/carambola" prefix="cmb" %>
<%@ include file="/netmarkets/jsp/util/begin.jspf" %>

<%-- The next three tags create and display a simple attribute panel for a datum object that is a HashMap --%>
<%
    LearnJcaTagUtil.initAttr();
    Object[] keys = LearnJcaTagUtil.getKeys();
%>
<%--标签解析 指定变量  由于只做展示属性--%>
<jca:describePropertyPanel var="panelDescriptor">
    <% for (Object key : keys) { %>
    <jca:describeProperty id="<%=key%>"/>
    <% } %>
</jca:describePropertyPanel>

<jca:getModel var="panelModel" descriptor="${panelDescriptor}"
              serviceName="ext.ziang.docTag.LearnJcaTagUtil"
              methodName="getExamplePropertyPanelData">
</jca:getModel>

<jca:renderPropertyPanel model="${panelModel}"/>

<%@ include file="/netmarkets/jsp/util/end.jspf" %>