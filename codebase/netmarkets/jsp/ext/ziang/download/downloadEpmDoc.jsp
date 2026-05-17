<%@page import="com.ptc.netmarkets.util.beans.NmCommandBean" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.ptc.netmarkets.util.misc.NmContext" %>
<%@ page import="java.io.File" %>
<%@ page import="ext.ziang.common.helper.applicationData.ApplicationDataOperationHelper" %>
<%@ page import="cn.hutool.core.util.StrUtil" %>
<%@ include file="/netmarkets/jsp/util/beginPopup.jspf" %>
<%@ page language="java" pageEncoding="UTF-8" %>

<%
    // NmCommandBean commandBean = new NmCommandBean();
    ArrayList selectedInOpener = commandBean.getSelectedContextsForPopup();
    org.slf4j.LoggerFactory.getLogger("jsp").debug("{}", "commandBean.getNmOidSelected() = " + commandBean.getNmOidSelected());
    org.slf4j.LoggerFactory.getLogger("jsp").debug("{}", "commandBean.getNmOidSelectedInOpener() = " + commandBean.getNmOidSelectedInOpener());
    org.slf4j.LoggerFactory.getLogger("jsp").debug("{}", "commandBean.getSelectedOidForPopup() = " + commandBean.getSelectedOidForPopup());
    org.slf4j.LoggerFactory.getLogger("jsp").debug("{}", "commandBean.getSelectedOidForPopup() = " + commandBean.getSelectedOidForPopup());
    org.slf4j.LoggerFactory.getLogger("jsp").debug("{}", "commandBean.getSelectedContextsForPopup() = " + commandBean.getSelectedContextsForPopup());
    ArrayList<String> strings = new ArrayList<>();
    String filePath;
    String parameter = request.getParameter("type");
    if ("mult".equals(parameter)) {
        for (Object selected : selectedInOpener) {
            if (selected instanceof NmContext) {
                NmContext context = (NmContext) selected;
                org.slf4j.LoggerFactory.getLogger("jsp").debug("{}", "context.getTargetOid() = " + context.getTargetOid());
                strings.add(context.getTargetOid().toString());
            }
        }
        filePath = ApplicationDataOperationHelper.downloadEpmDocList(strings);
    } else {
        filePath = ApplicationDataOperationHelper.downloadEpmDoc(request.getParameter("oid"));
    }
    if (StrUtil.isNotBlank(filePath)) {
        filePath = ApplicationDataOperationHelper.escapePathForJavaScript(filePath);
        org.slf4j.LoggerFactory.getLogger("jsp").debug("{}", "filePath = " + filePath);
%>
<script>
    let filePath = '<%=filePath%>';
    window.location.href = '/Windchill/netmarkets/jsp/ext/ziang/download/downloadFile.jsp?fileName=' + filePath;
</script>
<%}%>
<%@ include file="/netmarkets/jsp/util/end.jspf" %>

