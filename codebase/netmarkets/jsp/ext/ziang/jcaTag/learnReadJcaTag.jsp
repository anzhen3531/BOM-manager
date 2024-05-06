<%@ page import="ext.ziang.docTag.LearnJcaTagUtil" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/components" prefix="jca" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/wrappers" prefix="w" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/carambola" prefix="cmb" %>
<%@ include file="/netmarkets/jsp/util/begin.jspf" %>

<%--标签解析 指定变量  由于只做展示属性--%>
<jca:describePropertyPanel var="panelDescriptor">
    <jca:describeProperty id="className" label="className"/>
    <jca:describeProperty id="cache" label="Address"/>
    <jca:describeProperty id="test" label="test"/>
</jca:describePropertyPanel>

<jca:getModel var="panelModel" descriptor="${panelDescriptor}"
              serviceName="ext.ziang.docTag.LearnJcaTagUtil"
              methodName="getExamplePropertyPanelData">
</jca:getModel>

<jca:renderPropertyPanel model="${panelModel}"/>

<%@ include file="/netmarkets/jsp/util/end.jspf" %>