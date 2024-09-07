<%@ page import="java.util.ArrayList" %>
<%@ page import="com.ptc.netmarkets.model.NmOid" %>
<%@ page import="ext.ziang.report.model.ReportFormConfig" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/components" prefix="jca" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/wrappers" prefix="w" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/picker" prefix="picker" %>
<%@ include file="/netmarkets/jsp/util/begin.jspf" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    ArrayList selectedOidForPopup = nmCommandBean.getSelectedOidForPopup();
    NmOid nmOid = (NmOid)selectedOidForPopup.get(0);
    ReportFormConfig reportFormConfig = (ReportFormConfig)nmOid.getRefObject();
    String description = reportFormConfig.getDescription();
    String content = reportFormConfig.getContent();
    Integer state = reportFormConfig.getState();
%>

<jca:renderPropertyPanel>
    <w:textBox propertyLabel="描述" id="description" name="description" required="true" maxlength="200" value="<%=description%>"/>
    <w:textArea propertyLabel="内容" id="content" name="content" cols="39" rows="5" required="true" maxLength="2000" value="<%=content%>"/>
    <%--    配置选择框 是否开启或者是关闭 --%>
    <%--    默认数据反写--%>
    <w:radioButton propertyLabel="是否开启" id="start" label="开启" value="0" name="state" checked="true" onclick="doFoo()"/>
    <w:radioButton id="stop" label="关闭" value="1" name="state" onclick="doFoo()"/>
</jca:renderPropertyPanel>


<%@ include file="/netmarkets/jsp/util/end.jspf" %>


<script>
    // 默认数据加载
    PTC.onReay(function () {

    });
</script>