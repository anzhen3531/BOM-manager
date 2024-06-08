<%@page import="wt.util.WTProperties" %>
<%@page import="wt.part.WTPart" %>
<%@page import="wt.fc.ReferenceFactory" %>
<%@page import="java.util.Date" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="ext.wanb.part.service.DownloadEPMUtils" %>
<%@ page import="cn.hutool.core.util.StrUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String tempPath = WTProperties.getLocalProperties().getProperty("wt.temp") + "/import/";
    String fileName = request.getParameter("fileName");
    if (StrUtil.isNotBlank(fileName)){
        return;
    }
    String filePath = tempPath + fileName;
    ext.common.util.tool.DownloadReportUtil.downloadFile(filePath, response);
    response.flushBuffer();
    out.clear();
    out = pageContext.pushBody();
%>
