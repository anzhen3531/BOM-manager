<%@ page import="ext.ziang.common.helper.attr.AttributeOperationHelper" %>
<%@ page import="wt.util.WTException" %>
<%@ page import="wt.session.SessionServerHelper" %>
<%@ page import="com.ptc.core.lwc.common.view.ReusableAttributeReadView" %>
<%@ page import="wt.fc.ObjectIdentifier" %>
<%@ page import="com.ptc.core.lwc.common.view.TypeDefinitionReadView" %>
<%@ page language="java" pageEncoding="UTF-8" %>

<%
    // \netmarkets\jsp\test\testCreateAttr.jsp
    try {
        ReusableAttributeReadView attributeReadView = AttributeOperationHelper.createReusableAttribute(
                "4A00100100101",
                null,
                "wt.iba.definition.StringDefinition",
                "4A00100100101",
                "机型",
                "OR:wt.iba.definition.AttributeOrganizer:105910",
                null);
        ObjectIdentifier objectIdentifier = attributeReadView.getOid();
        String string = objectIdentifier.toString();
        System.out.println("string = " + string);
    } catch (WTException e) {
        e.printStackTrace();
    }


    try {
        boolean accessEnforced = SessionServerHelper.manager.isAccessEnforced();
        System.out.println("accessEnforced = " + accessEnforced);
        //  OR:com.ptc.core.lwc.server.LWCStructEnumAttTemplate:125588
        // 通过名称查询对应的节点 传递节点名称
        TypeDefinitionReadView typeDefinitionReadView = AttributeOperationHelper.createAttributeDefinitionAndConstraint("4A00100100101",
                "机型",
                "4A00100100101",
                "OR:wt.iba.definition.StringDefinition:190124",
                "OR:com.ptc.core.lwc.server.LWCStructEnumAttTemplate:154177");
        System.out.println("typeDefinitionReadView = " + typeDefinitionReadView);
    } catch (Exception e) {
        e.printStackTrace();
    }

    try {
        // 创建枚举
        AttributeOperationHelper.createConstraint(
                "-com.ptc.core.lwc.server.LWCStructEnumAttTemplate:154177-com.ptc.core.lwc.server.LWCIBAAttDefinition:206033", 29611L);
    } catch (Exception e) {
        e.printStackTrace();
    }
//    AttributeOperationHelper.findClassificationAttrs("MainGun");
%>