<%@page import="com.ptc.netmarkets.util.beans.NmCommandBean" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.ptc.netmarkets.util.misc.NmContext" %>
<%@ page import="java.io.File" %>
<%@ page import="ext.ziang.common.helper.applicationData.ApplicationDataOperationHelper" %>
<%@ include file="/netmarkets/jsp/util/beginPopup.jspf" %>
<%@ page language="java" pageEncoding="UTF-8" %>

<%
    ArrayList selected = commandBean.getSelectedContextsForPopup();
    System.out.println("commandBean.getNmOidSelected() = " + commandBean.getNmOidSelected());
    System.out.println("commandBean.getNmOidSelectedInOpener() = " + commandBean.getNmOidSelectedInOpener());
    System.out.println("commandBean.getSelectedOidForPopup() = " + commandBean.getSelectedOidForPopup());
    System.out.println("commandBean.getSelectedOidForPopup() = " + commandBean.getSelectedOidForPopup());
    System.out.println("commandBean.getSelectedContextsForPopup() = " + commandBean.getSelectedContextsForPopup());
    System.out.println("selectedInOpener.size() = " + selected.size());
    ArrayList<String> strings = new ArrayList<>();
    String filePath;
    if (request.getParameter("type").equals("mult")) {
        for (Object select : selected) {
            if (select instanceof NmContext) {
                NmContext context = (NmContext) select;
                System.out.println("context.getTargetOid() = " + context.getTargetOid());
                strings.add(context.getTargetOid().toString());
            }
        }
        filePath = ApplicationDataOperationHelper.downloadListPartRelateDocContent(strings);
    } else {
        filePath = ApplicationDataOperationHelper.downloadPartRelateDocContent(request.getParameter("oid"));
    }
    ApplicationDataOperationHelper.downloadFile(filePath, response);
    response.flushBuffer();
    out.clear();
    out = pageContext.pushBody();
    File file = new File(filePath);
    if (file.exists()) {
        file.delete();
    }
%>

<%@ include file="/netmarkets/jsp/util/end.jspf" %>

