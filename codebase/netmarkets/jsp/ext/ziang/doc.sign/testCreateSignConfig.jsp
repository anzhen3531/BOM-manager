<%@ page import="ext.ziang.model.ElectronicSignatureConfig" %>
<%@ page import="wt.type.TypeDefinitionReference" %>
<%@ page import="wt.type.TypedUtilityServiceHelper" %>
<%@ page import="wt.type.TypedUtility" %>
<%@ page import="com.ptc.core.lwc.server.LWCTypeDefinition" %>
<%@ page import="ext.ziang.doc.sign.helper.ElectronicSignatureConfigHelper" %>
<%
    // codebase/netmarkets/jsp/ext/ziang/doc.sign/testCreateSignConfig.jsp
    ElectronicSignatureConfig signatureConfig = ElectronicSignatureConfig.newElectronicSignatureConfig();
    TypeDefinitionReference typeDefinitionReference = TypedUtility.getTypeDefinitionReference("wt.doc.WTDocument");
    if (typeDefinitionReference != null) {
        signatureConfig.setDocType("wt.doc.WTDocument");
        signatureConfig.setDocTypeName("文档");
        signatureConfig.setContentType("application/pdf");
        signatureConfig.setWorkItemName("Signature");
        signatureConfig.setSignXIndex("100");
        signatureConfig.setSignYIndex("100");
        signatureConfig.setStatus(1);
        ElectronicSignatureConfigHelper.createOrUpdate(signatureConfig);
    }
%>