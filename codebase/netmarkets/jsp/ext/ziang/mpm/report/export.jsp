<%@page import="java.io.File" %>
<%@ page import="ext.ziang.mpm.ExportWorkFlowDoc" %>
<%@ page import="ext.ziang.common.util.DownloadFileUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    try {
        String oid = request.getParameter("oid");
        String type = request.getParameter("type");
        String filePath = "";
        if ("WorkflowPic".equals(type)) {
            filePath = ExportWorkFlowDoc.createExcel(oid);
        }
        System.out.println(oid + "======" + type);
        DownloadFileUtil.downloadFile(filePath, response);
        response.flushBuffer();
        out.clear();
        out = pageContext.pushBody();
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    } catch (Exception e) {
        e.printStackTrace();
%>
<script type="text/javascript">
    alert("<%=e.getMessage()%>");
    window.close();
</script>
<%
    }
%>
<script type="text/javascript">
    window.close();
</script>