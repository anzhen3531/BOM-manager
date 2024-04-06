<%@ page import="ext.ziang.common.util.CommonOperationAttrUtil" %>
<%@ page import="wt.util.WTException" %>
<%@ page import="wt.session.SessionServerHelper" %>
<%@ page import="wt.iba.definition.StringDefinition" %>
<%@ page import="com.ptc.core.lwc.common.view.ReusableAttributeReadView" %>
<%@ page import="wt.fc.ObjectIdentifier" %>
<%@ page language="java" pageEncoding="UTF-8" %>

<%
    try {
        ReusableAttributeReadView attributeReadView = CommonOperationAttrUtil.createReusableAttribute(
                "4A00100100101",
                null,
                "wt.iba.definition.StringDefinition",
                "4A00100100101",
                "机型",
                "OR:wt.iba.definition.AttributeOrganizer:112123",
                null);

        ObjectIdentifier objectIdentifier = attributeReadView.getOid();
        String string = objectIdentifier.toString();
        System.out.println("string = " + string);

        boolean accessEnforced = SessionServerHelper.manager.isAccessEnforced();
        System.out.println("accessEnforced = " + accessEnforced);
        //  OR:com.ptc.core.lwc.server.LWCStructEnumAttTemplate:125588
        // 通过名称查询对应的节点
        CommonOperationAttrUtil.createAttributeDefinition("4A00100100101",
                "机型",
                "4A00100100101",
                "OR:wt.iba.definition.StringDefinition:126002",
                "OR:com.ptc.core.lwc.server.LWCStructEnumAttTemplate:125588");

        //  创建枚举
        //  CommonOperationAttrUtil.createConstraint(
        //          "-com.ptc.core.lwc.server.LWCStructEnumAttTemplate:125588-com.ptc.core.lwc.server.LWCIBAAttDefinition:126007", 26537L);

    } catch (WTException e) {
        throw new RuntimeException(e);
    }
%>