<%@page import="wt.util.WTProperties" %>
<%@ page import="cn.hutool.core.util.StrUtil" %>
<%@ page import="ext.ziang.common.helper.applicationData.ApplicationDataOperationHelper" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String fileName = request.getParameter("fileName");
    org.slf4j.LoggerFactory.getLogger("jsp").debug("{}", "fileName = " + fileName);
    if (StrUtil.isBlank(fileName)) {
        return;
    }
    ApplicationDataOperationHelper.downloadFile(fileName, response);
    response.flushBuffer();
    out.clear();
    out = pageContext.pushBody();
%>
