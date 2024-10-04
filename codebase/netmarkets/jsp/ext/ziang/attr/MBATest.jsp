<%@ page import="com.ptc.core.lwc.client.commands.LWCCommands" %>
<%@ page import="com.ptc.core.lwc.common.view.AttributeDefinitionReadView" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="ext.ziang.common.util.MbaUtil" %>
<%@ page import="ext.ziang.common.util.ToolUtils" %>
<%@ page import="wt.fc.WTReference" %>
<%@ page import="wt.fc.Persistable" %>


<%
    // \netmarkets\jsp\ext\ziang\attr\MBATest.jsp
    ArrayList<AttributeDefinitionReadView> typeAttributes = LWCCommands.getTypeAttributes("OR:com.ptc.core.lwc.server.LWCTypeDefinition:14763");
    System.out.println("typeAttributes = " + typeAttributes);
    //
    WTReference referenceByOid = ToolUtils.getReferenceByOid("OR%3Awt.part.WTPart%3A110425");
    Persistable object = referenceByOid.getObject();
    MbaUtil.setMBAValue(object, "ipd_status", "量产");
%>