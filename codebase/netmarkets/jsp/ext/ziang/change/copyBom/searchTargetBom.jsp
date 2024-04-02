<%@ taglib uri="http://www.ptc.com/windchill/taglib/components" prefix="jca" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/mvc" prefix="mvc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="wctags" tagdir="/WEB-INF/tags" %>
<%@ include file="/netmarkets/jsp/components/beginWizard.jspf" %>

<%
    // 获取oid 获取类型
    // 动态查询
    String tableID = request.getParameter("tableID");
    String tableBuilderId = "";
    if (tableID.contains("SelectOriginBomBuilder")) {
        tableBuilderId = "ext.ziang.change.SelectOriginBomBuilder";
    } else if (tableID.contains("SelectTargetBomBuilder")) {
        tableBuilderId = "SelectTargetBomBuilder";
    }
    System.out.println("tableBuilderId = " + tableBuilderId);
%>

<wctags:itemPicker id="searchAffectBom"
                   componentId="pdmlPartMasterPicker"
                   label="输入编号查询BOM"
                   showVersion="true"
                   pickerTitle="选择需要复制的BOM"
                   objectType="WCTYPE|wt.part.WTPart"
                   defaultVersionValue="LATEST"
                   showTypePicker="true"
                   multiSelect="true"
                   pickerCallback="searchAffectBom"
                   inline="true"
/>

<%-- 回调接口得到OID设置到对应中即可 --%>
<script>
    function searchAffectBom(object) {
        let data = [];
        let oid;
        try {
            // 获取选择的对象
            var theJSONObject = object.pickedObject;
            console.log(theJSONObject)
            if (theJSONObject.length > 0) {
                for (let i = 0; i < theJSONObject.length; i++) {
                    console.log(theJSONObject[i].oid);
                    console.log(theJSONObject[i].number);
                    console.log(theJSONObject[i].view);
                    data.push(theJSONObject[i].oid);
                    oid = theJSONObject[i].oid;
                }
            }
            let params = {
                data: data
            };
            console.log(params);
            // 刷新父页面接口
            window.opener.PTC.jca.table.Utils.reload('<%=tableBuilderId%>', params, true);
            alert("添加完成");
        } catch (e) {
            alert(e);
        }
    }
</script>

<%@ include file="/netmarkets/jsp/util/end.jspf" %>