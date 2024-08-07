<%@ taglib uri="http://www.ptc.com/windchill/taglib/components" prefix="jca" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/wrappers" prefix="w" %>
<%@ include file="/netmarkets/jsp/util/begin.jspf" %>

<%--标签解析 指定变量  由于只做展示属性--%>
<jca:describePropertyPanel var="panelDescriptor">
    <jca:describeProperty id="className" label="className"/>
    <jca:describeProperty id="cache" label="Address"/>
    <jca:describeProperty id="test" label="test"/>
</jca:describePropertyPanel>

<%--
https://plm.ziang.com/Windchill/app/#netmarkets/jsp/ext/ziang/jcaTag/learnReadJcaTag.jsp
/netmarkets/jsp/ext/ziang/jcaTag/learnWriteJcaTag.jsp
--%>
<%--声明一个模型变量 数据来源调用ServiceName中指定类中MethodName指定方法--%>
<jca:getModel var="panelModel" descriptor="${panelDescriptor}"
              serviceName="ext.ziang.doc.docTag.LearnJcaTagUtil"
              methodName="getExamplePropertyPanelData">
</jca:getModel>

<%--使用这个模型--%>
<jca:renderPropertyPanel model="${panelModel}"/>

<%@ include file="/netmarkets/jsp/util/end.jspf" %>