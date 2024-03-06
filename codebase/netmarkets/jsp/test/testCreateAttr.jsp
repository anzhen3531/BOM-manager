<%@ page import="ext.ziang.common.util.CommonOperationAttrUtil" %>
<%@ page import="wt.util.WTException" %>
<%@ page import="wt.session.SessionServerHelper" %>
<%@ page language="java" pageEncoding="UTF-8" %>

<%
    try {
//        CommonOperationAttrUtil.createReusableAttribute("testCreateAttr",
//                null,
//                "wt.iba.definition.StringDefinition",
//                "test",
//                "测试创建属性",
//                "OR:wt.iba.definition.AttributeOrganizer:156004", null);

        boolean accessEnforced = SessionServerHelper.manager.isAccessEnforced();
        System.out.println("accessEnforced = " + accessEnforced);
        CommonOperationAttrUtil.createAttributeDefinition("materialMark",
                "材料备注",
                "materialMark",
                "OR:wt.iba.definition.StringDefinition:116710",
                "OR:com.ptc.core.lwc.server.LWCStructEnumAttTemplate:112108");
    } catch (WTException e) {
        throw new RuntimeException(e);
    }
%>