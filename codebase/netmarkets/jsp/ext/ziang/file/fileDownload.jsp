
<%@ page import="wt.fc.Persistable" %>
<%@ page import="wt.content.ApplicationData" %>
<%@ page import="wt.content.ContentServerHelper" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="ext.ziang.common.util.ToolUtils" %>
<%@ page import="ext.ziang.common.util.DownloadFileUtil" %>
<%
    String oid = request.getParameter("oid");
    try {
        Persistable persistable = ToolUtils.getObjectByOid(oid);
        if (persistable != null) {
            if (persistable instanceof ApplicationData) {
                ApplicationData applicationData = (ApplicationData) persistable;
                // 下载文件
                InputStream contentStream = ContentServerHelper.service.findContentStream(applicationData);
                DownloadFileUtil.downloadFile(contentStream, response, applicationData.getFileName());
                response.flushBuffer();
                out.clear();
                out = pageContext.pushBody();
            }
        }
    }catch (Exception e){
        e.printStackTrace();
    }
%>