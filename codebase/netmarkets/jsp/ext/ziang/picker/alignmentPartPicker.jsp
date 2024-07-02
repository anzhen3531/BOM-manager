<%@ page import="com.ptc.core.meta.common.TypeIdentifierHelper" %>
<%@ page import="wt.fc.Persistable" %>
<%@ page import="wt.change2.WTChangeActivity2" %>
<%@ page import="ext.ziang.common.util.ToolUtils" %>
<%@page language="java" session="true" pageEncoding="UTF-8" %>
<%@ include file="/netmarkets/jsp/components/beginWizard.jspf" %>
<%@ include file="/netmarkets/jsp/components/includeWizBean.jspf" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/mvc" prefix="mvc" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/fmt" prefix="fmt" %>
<%@ taglib prefix="wrap" uri="http://www.ptc.com/windchill/taglib/wrappers" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="wctags" %>

<%
    String ecaOid = request.getParameter("ecaOid");
    String partType = request.getParameter("partType");
    System.out.println("partType = " + partType);
    System.out.println("ecaOid = " + ecaOid);
    String status;
    Persistable persistable = ToolUtils.getObjectByOid(ecaOid);
    if (persistable instanceof WTChangeActivity2) {
        WTChangeActivity2 activity2 = (WTChangeActivity2) persistable;
        if (activity2.getName().contains("小样")) {
            status = "(IBA|IPD_Status='小样')";
        } else if (activity2.getName().contains("中大样")) {
            status = "(IBA|IPD_Status='量产') | (IBA|IPD_Status='中大样')";
        } else {
            if (activity2.getName().contains("量产")) {
                status = "(IBA|IPD_Status='量产')";
            } else {
                status = "(IBA|IPD_Status='量产') | (IBA|IPD_Status='中大样')";
            }
        }
    } else {
        status = "(IBA|IPD_Status='量产') | (IBA|IPD_Status='中大样')";
    }
    String type;
    if (TypeConstant.OFFICAL_RAW_MATERIAL.contains(partType)) {
        type = TypeIdentifierHelper.getTypeIdentifier(TypeConstant.OFFICAL_RAW_MATERIAL).toString();
        status = "(IBA|IPD_Status='量产') | (IBA|IPD_Status='中大样') | (IBA|IPD_Status='小样')";
    } else if (TypeConstant.BATTERY_OFFICAL_MATERIAL.contains(partType)) {
        type = TypeIdentifierHelper.getTypeIdentifier(TypeConstant.BATTERY_OFFICAL_MATERIAL).toString();
    } else if (TypeConstant.COMPONENT_OFFICAL_MATERIAL.contains(partType)) {
        type = TypeIdentifierHelper.getTypeIdentifier(TypeConstant.COMPONENT_OFFICAL_MATERIAL).toString();
    } else if (TypeConstant.COMPONENTEXPERIMENT_MATERIAL.contains(partType)) {
        type = TypeIdentifierHelper.getTypeIdentifier(TypeConstant.COMPONENTEXPERIMENT_MATERIAL).toString();
    } else {
        type = "WCTYPE|wt.part.WTPart";
    }
    System.out.println("type = " + type);
%>

<%--可以自定义picker
customAccessController="com.ptc.windchill.enterprise.search.server.LatestVersionAccessController"
--%>
<wctags:itemPicker id="alignmentPartPicker"
                   componentId="pdmlPartMasterPicker"
                   label="查询物料"
                   showVersion="true"
                   pickerTitle="查询物料"
                   defaultVersionValue="LATEST"
                   objectType="<%=type%>"
                   showTypePicker="true"
                   multiSelect="true"
                   pickerCallback="alignmentCallback"
                   pickedAttributes="number,view"
                   inline="true"
                   baseWhereClause="<%=status%>"/>

<script>

    function alignmentCallback(object) {
        let data = [];
        try {
            var theJSONObject = object.pickedObject;
            console.log(theJSONObject)
            if (theJSONObject.length > 0) {
                for (let i = 0; i < theJSONObject.length; i++) {
                    console.log(theJSONObject[i]);
                    console.log(theJSONObject[i].number);
                    console.log(theJSONObject[i].view);
                    let obj = {
                        "number": theJSONObject[i].number,
                        "view": theJSONObject[i].view
                    }
                    // data.push(theJSONObject[i].number)
                    data.push(obj)
                }
            }
        } catch (e) {
            alert(e);
        }
        console.log(data);
        let baseUrl = getBaseHref();
        let ajaxUrl = baseUrl + "/netmarkets/jsp/ext/ziang/ecaRelatePart.jsp";
        var params = "?ecaOid=<%=ecaOid%>";
        const decoder = new TextDecoder('utf-8');
        fetch(ajaxUrl + params, {
            method: 'POST',
            body: JSON.stringify(data)
        }).then(response => response.json())
            .then(data => {
                console.log(data);
                alert("成功");
                window.opener.location.reload(true);
            }).catch(error => {
            console.error(error);
            alert("失败！！请查看日志");
            window.opener.location.reload(true);
        })
    }
</script>
<%@include file="/netmarkets/jsp/util/end.jspf" %>
