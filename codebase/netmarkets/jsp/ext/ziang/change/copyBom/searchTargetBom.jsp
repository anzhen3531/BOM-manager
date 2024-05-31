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
        tableBuilderId = "SelectOriginBomBuilder";
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

<script>

    function searchAffectBom(object) {
        try {
            let theJSONObject = object.pickedObject;
            let table = window.opener.PTC.jca.table.Utils.getTable('<%=tableBuilderId%>')
            if (theJSONObject.length > 0) {
                let tableArr = [];
                let rowData = window.opener.PTC.jca.table.Utils.getRowData(table);
                for (let index = 0; index < rowData.getCount(); index++) {
                    let oid = rowData.get(index).data.oid;
                    if (!tableArr.includes(oid)) {
                        tableArr.push(oid);
                    }
                }
                // 获取数据进行判断
                for (let i = 0; i < theJSONObject.length; i++) {
                    if (!tableArr.includes(theJSONObject[i].oid)) {
                        tableArr.push(theJSONObject[i].oid);
                    }
                }
                // 参数
                alert(tableArr.length);
                let params = {
                    oidList: tableArr
                };
                console.log(params);
                alert(params);
                window.opener.PTC.jca.table.Utils.reload('<%=tableBuilderId%>', params, false);
            }
        } catch (e) {
            alert(e);
        }
    }
</script>

<%@ include file="/netmarkets/jsp/util/end.jspf" %>