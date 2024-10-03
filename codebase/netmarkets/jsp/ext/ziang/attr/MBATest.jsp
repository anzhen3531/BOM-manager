<%@ page import="com.ptc.core.lwc.client.commands.LWCCommands" %>
<%@ page import="com.ptc.core.lwc.common.view.AttributeDefinitionReadView" %>
<%@ page import="java.util.ArrayList" %>


<%
    // \netmarkets\jsp\ext\ziang\attr\MBATest.jsp
    ArrayList<AttributeDefinitionReadView> typeAttributes = LWCCommands.getTypeAttributes("OR:com.ptc.core.lwc.server.LWCTypeDefinition:14763");
    System.out.println("typeAttributes = " + typeAttributes);
%>