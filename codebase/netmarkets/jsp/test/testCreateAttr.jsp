<%@ page import="ext.ziang.common.util.CommonOperationAttrUtil" %>
<%@ page import="wt.util.WTException" %>
<%@ page language="java" pageEncoding="UTF-8" %>

<%
    try {
        CommonOperationAttrUtil.createReusableAttribute("testCreateAttr",
                null,
                "wt.iba.definition.StringDefinition",
                "test",
                "测试创建属性",
                "OR:wt.iba.definition.AttributeOrganizer:156004", null);
    } catch (WTException e) {
        throw new RuntimeException(e);
    }
%>