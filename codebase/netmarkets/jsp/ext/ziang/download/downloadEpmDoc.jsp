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
    System.out.println("commandBean.getNmOidSelected() = " + commandBean.getNmOidSelected());
    System.out.println("commandBean.getNmOidSelectedInOpener() = " + commandBean.getNmOidSelectedInOpener());
    System.out.println("commandBean.getSelectedOidForPopup() = " + commandBean.getSelectedOidForPopup());
    System.out.println("commandBean.getSelectedOidForPopup() = " + commandBean.getSelectedOidForPopup());
    System.out.println("commandBean.getSelectedContextsForPopup() = " + commandBean.getSelectedContextsForPopup());
    ArrayList<String> strings = new ArrayList<>();
    String filePath;
    if (request.getParameter("type").equals("mult")) {
        for (Object selected : selectedInOpener) {
            if (selected instanceof NmContext) {
                NmContext context = (NmContext) selected;
                System.out.println("context.getTargetOid() = " + context.getTargetOid());
                strings.add(context.getTargetOid().toString());
            }
        }
        filePath = ApplicationDataOperationHelper.downloadEpmDocList(strings);
    } else {
        filePath = ApplicationDataOperationHelper.downloadEpmDoc(request.getParameter("oid"));
    }
    if (StrUtil.isNotBlank(filePath)) {
        filePath = ApplicationDataOperationHelper.escapePathForJavaScript(filePath);
        System.out.println("filePath = " + filePath);
%>
<script>
    let filePath = '<%=filePath%>';
    window.location.href = '/Windchill/netmarkets/jsp/ext/ziang/download/downloadFile.jsp?fileName=' + filePath;
</script>
<%}%>
<%@ include file="/netmarkets/jsp/util/end.jspf" %>

