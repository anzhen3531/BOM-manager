<%@ page import="ext.ziang.common.helper.attr.AttributeOperationHelper" %>
<%@ page import="wt.util.WTException" %>
<%@ page import="wt.session.SessionServerHelper" %>
<%@ page import="com.ptc.core.lwc.common.view.ReusableAttributeReadView" %>
<%@ page import="wt.fc.ObjectIdentifier" %>
<%@ page import="com.ptc.core.lwc.common.view.TypeDefinitionReadView" %>
<%@ page import="wt.session.SessionHelper" %>
<%@ page import="com.ptc.windchill.csm.client.helpers.CSMTypeDefHelper" %>
<%@ page import="com.ptc.core.lwc.common.PropertyDefinitionConstants" %>
<%@ page import="com.ptc.core.lwc.common.view.TypeDefinitionWriteView" %>
<%@ page import="com.ptc.core.lwc.common.view.AttributeDefinitionWriteView" %>
<%@ page language="java" pageEncoding="UTF-8" %>

<%
    // \netmarkets\jsp\test\testCreateAttr.jsp
    boolean accessEnforced = SessionServerHelper.manager.isAccessEnforced();
    try {
        ReusableAttributeReadView attributeReadView = AttributeOperationHelper.createReusableAttribute(
                "4A00100100101",
                "4A00100100101",
                "wt.iba.definition.StringDefinition",
                "4A00100100101",
                "机型",
                "OR:wt.iba.definition.AttributeOrganizer:100715",
                null);
        TypeDefinitionReadView classificationTypeDefView = CSMTypeDefHelper.getClassificationTypeDefView("Panzer");
        AttributeDefinitionWriteView attributeDefinitionView = AttributeOperationHelper.createAttributeDefinitionView(attributeReadView.getName(),
                attributeReadView.getPropertyValueByName(PropertyDefinitionConstants.DISPLAY_NAME_PROPERTY).getValueAsString(),
                attributeReadView.getPropertyValueByName(PropertyDefinitionConstants.DESCRIPTION_PROPERTY).getValueAsString(),
                classificationTypeDefView.getName());
        TypeDefinitionWriteView writableView = classificationTypeDefView.getWritableView();
        writableView.setAttribute(attributeDefinitionView);
        AttributeOperationHelper.TYPE_DEF_SERVICE.updateTypeDef(writableView);
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        SessionServerHelper.manager.setAccessEnforced(accessEnforced);
    }
//    AttributeOperationHelper.findClassificationAttrs("MainGun");
%>