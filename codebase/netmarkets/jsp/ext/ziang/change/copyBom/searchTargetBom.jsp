<%@ taglib uri="http://www.ptc.com/windchill/taglib/components" prefix="jca" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/mvc" prefix="mvc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="ext.trinasolar.common.constant.CommonConstants" %>
<%@ page import="wt.fc.Persistable" %>
<%@ page import="ext.trinasolar.common.utils.ToolUtils" %>
<%@ page import="wt.part.WTPart" %>
<%@ page import="wt.type.TypedUtility" %>
<%@ page import="ext.ziang.common.util.ToolUtils" %>
<%@ taglib prefix="wctags" tagdir="/WEB-INF/tags" %>
<%@ include file="/netmarkets/jsp/components/beginWizard.jspf" %>


<%--
动态获取类型并放置在当前页面中
--%>
<%
    // 获取oid 获取类型
    // 动态查询
    String originPartOid = request.getParameter("oid");
    System.out.println("oid = " + originPartOid);
    Persistable persistable = ToolUtils.getObjectByOid(originPartOid);
    String type;
    if (persistable instanceof WTPart) {
        WTPart part = (WTPart) persistable;
        type = TypedUtility.getTypeIdentifier(part).getTypename();
    } else {
        type = "WCTYPE|wt.part.WTPart";
    }
%>

<wctags:itemPicker id="alignmentWTpartPicker"
                   componentId="pdmlPartMasterPicker"
                   label="输入编号查询BOM"
                   showVersion="true"
                   pickerTitle="选择需要复制的BOM"
                   objectType="<%=type%>"
                   defaultVersionValue="LATEST"
                   showTypePicker="true"
                   multiSelect="true"
                   pickerCallback="doNothing"
                   inline="true"
/>

<%-- 回调接口得到OID设置到对应中即可 --%>

<%@ include file="/netmarkets/jsp/util/end.jspf" %>