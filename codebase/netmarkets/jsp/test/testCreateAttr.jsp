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
<%@ page import="wt.iba.definition.AbstractAttributeDefinition" %>
<%@ page import="java.util.Objects" %>
<%@ page language="java" pageEncoding="UTF-8" %>

<%
    // \netmarkets\jsp\test\testCreateAttr.jsp
    boolean accessEnforced = SessionServerHelper.manager.isAccessEnforced();
    try {
        AbstractAttributeDefinition attributeDefinition = AttributeOperationHelper.findAttributeDefinition("4A00100100101");
        String displayName;
        String description;
        if (Objects.isNull(attributeDefinition)) {
            ReusableAttributeReadView attributeReadView = AttributeOperationHelper.createReusableAttribute(
                    "4A00100100101",
                    "4A00100100101",
                    "wt.iba.definition.StringDefinition",
                    "4A00100100101",
                    "机型",
                    "OR:wt.iba.definition.AttributeOrganizer:100715",
                    null);
            description = attributeReadView.getPropertyValueByName(PropertyDefinitionConstants.DESCRIPTION_PROPERTY).getValueAsString();
            displayName = attributeReadView.getPropertyValueByName(PropertyDefinitionConstants.DISPLAY_NAME_PROPERTY).getValueAsString();
        } else {
            description = attributeDefinition.getDescription();
            displayName = attributeDefinition.getDisplayName();
        }

        TypeDefinitionReadView classificationTypeDefView = CSMTypeDefHelper.getClassificationTypeDefView("Panzer");
        AttributeDefinitionWriteView attributeDefinitionView = AttributeOperationHelper.createAttributeDefinitionView(
                "4A00100100101",
                displayName,
                description,
                classificationTypeDefView.getName());
        // 关联局部枚举 默认创建空的 如果是全局枚举则一开始创建
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