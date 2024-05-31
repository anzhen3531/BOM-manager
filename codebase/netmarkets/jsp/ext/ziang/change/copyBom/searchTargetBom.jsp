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

    /**
     * 搜索相关的物料
     * @param object
     */
    function searchAffectBom(object) {
        try {
            let theJSONObject = object.pickedObject;
            let table = window.opener.PTC.jca.table.Utils.getTable('<%=tableBuilderId%>')
            let flag = true;
            if (theJSONObject.length > 0) {
                let oidList = "";
                let rowData = window.opener.PTC.jca.table.Utils.getRowData(table);
                for (let index = 0; index < rowData.getCount(); index++) {
                    let oid = rowData.get(index).data.oid;
                    if (!oidList.includes(oid)) {
                        if (flag) {
                            oidList += oid;
                            flag = false;
                        } else {
                            oidList += "," + oid;
                        }
                    }
                }
                // 获取数据进行判断
                for (let i = 0; i < theJSONObject.length; i++) {
                    if (!oidList.includes(theJSONObject[i].oid)) {
                        if (flag) {
                            oidList += theJSONObject[i].oid;
                            flag = false;
                        } else {
                            oidList += "," + theJSONObject[i].oid;
                        }
                    }
                }
                // 判断此次获取的数据是否是由重复的
                let params = {
                    oidList: oidList
                };
                //
                console.log(oidList);
                window.opener.PTC.jca.table.Utils.reload('<%=tableBuilderId%>', params, true);
            }
        } catch (e) {
            alert(e);
        }
    }
</script>

<%@ include file="/netmarkets/jsp/util/end.jspf" %>