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

<%-- 回调接口得到OID设置到对应中即可 --%>
<script>
    <%--function searchAffectBom(object) {--%>
    <%--    let oidList = [];--%>
    <%--    let oid;--%>
    <%--    try {--%>
    <%--        // 获取选择的对象--%>
    <%--        var theJSONObject = object.pickedObject;--%>
    <%--        console.log(theJSONObject)--%>
    <%--        if (theJSONObject.length > 0) {--%>
    <%--            for (let i = 0; i < theJSONObject.length; i++) {--%>
    <%--                console.log(theJSONObject[i].oid);--%>
    <%--                oidList.push(theJSONObject[i].oid);--%>
    <%--                if (oid === undefined || oid === "") {--%>
    <%--                    oid = theJSONObject[i].oid;--%>
    <%--                } else {--%>
    <%--                    oid += "," + theJSONObject[i].oid;--%>
    <%--                }--%>
    <%--            }--%>
    <%--        }--%>
    <%--        let params = {--%>
    <%--            oidList: oid--%>
    <%--        };--%>
    <%--        console.log(params);--%>
    <%--        alert(oid);--%>
    <%--        // 刷新父页面接口--%>
    <%--        window.opener.PTC.jca.table.Utils.addRow('<%=tableBuilderId%>', params);--%>
    <%--        alert("添加完成");--%>
    <%--    } catch (e) {--%>
    <%--        alert(e);--%>
    <%--    }--%>
    <%--}--%>

    function searchAffectBom(object) {
        try {
            // 获取选择的对象
            var theJSONObject = object.pickedObject;
            let table = window.opener.PTC.jca.table.Utils.getTable('<%=tableBuilderId%>')
            if (theJSONObject.length > 0) {
                for (var i = 0; i < theJSONObject.length; i++) {
                    let rowData = window.opener.PTC.jca.table.Utils.getRowData(table);
                    for (var i = 0; i < rowData.getCount(); i++) {
                        console.log(rowData.get(i));
                        console.log(rowData.get(i).data);
                        alert( "表格中存在" + rowData.get(i).data.oid);
                    }
                    window.opener.PTC.jca.table.Utils.addRow(table, theJSONObject[i]);
                }
            }
            alert("添加完成");
        } catch (e) {
            alert(e);
        }
    }
</script>

<%@ include file="/netmarkets/jsp/util/end.jspf" %>