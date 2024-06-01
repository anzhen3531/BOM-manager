<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/mvc" prefix="mvc" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/wrappers" prefix="w" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/components" prefix="jca" %>
<%@ taglib prefix="wctags" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/netmarkets/jsp/components/beginWizard.jspf" %>
<%@ include file="/netmarkets/jsp/components/includeWizBean.jspf" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/fmt" prefix="fmt" %>


<%--前面的选项--%>
<fieldset id="General" class="x-fieldset x-form-label-left" style="width: auto; display: block;">
    <legend class="x-fieldset-header x-unselectable" id="ext-gen464">
        <div class="x-tool x-tool-toggle" id="Details_hidebutton" onclick="tohide(this)">&nbsp;</div>
        <span class="x-fieldset-header-text" id="ext-gen468">
            <span class="attributePanel-fieldset-title">搜索条件</span>
        </span>
    </legend>
    <div class="x-fieldset-bwrap" id="ext-gen465">
        <input type="hidden" value="selectState">
        <div class="x-fieldset-body" id="ext-gen466" style="width: auto; height: auto;">
            <div id="dataStoreGeneral" style="width: auto; height: auto;">
                <table class="attributePanel-group-panel">
                    <tbody>

                    <tr>
                        <td class="attributePanel-asterisk"></td>
                        <td class="attributePanel-label" id="operationType">
                            操作类型:
                        </td>
                        <td class="attributePanel-value" attrid="replaceType">
                            <w:radioButton label="指定料号"
                                           name="radio"
                                           id="appointPartNumber"
                                           value="appointPartNumber"
                                           onclick="toggleRows(true)"/>

                            <w:radioButton label="模糊匹配"
                                           name="radio"
                                           id="fuzzySearch"
                                           value="fuzzySearch"
                                           onclick="toggleRows(false)"/>
                        </td>
                        <td class="attributePanel-asterisk"></td>
                    </tr>

                    <tr>
                        <td class="attributePanel-asterisk"></td>
                        <td class="attributePanel-label" id="replaceType51406359424000">
                            替换类型:
                        </td>
                        <td class="attributePanel-value" attrid="replaceType">
                            <w:radioButton propertyLabel="替换类型"
                                           id="replace"
                                           label="替换"
                                           value="replace"
                                           name="radio"
                                           checked="true"
                                           onclick="handlerSelectRadio()"/>

                            <w:radioButton label="替代"
                                           name="radio"
                                           id="substitution"
                                           value="substitution"
                                           onclick="handlerSelectRadio()"/>

                            <w:radioButton label="主替交换"
                                           name="radio"
                                           id="mainChangeSubstitute"
                                           value="mainChangeSubstitute"
                                           onclick="handlerSelectRadio()"/>

                            <w:radioButton label="删除替代"
                                           name="radio"
                                           id="deleteSubstitution"
                                           value="deleteSubstitution"
                                           onclick="handlerSelectRadio()"/>
                        </td>
                        <td class="attributePanel-asterisk"></td>
                    </tr>
                    <tr>
                        <td class="attributePanel-asterisk"></td>
                        <td class="attributePanel-label">
                            受影响的部件编号:
                        </td>
                        <td class="attributePanel-value">
                            <wctags:itemPicker id="originPartPicker"
                                               componentId="pdmlPartMasterPicker"
                                               showVersion="true"
                                               defaultVersionValue="LATEST"
                                               objectType="WCTYPE|wt.part.WTPart|com.ziang.formalPanzer"
                                               showTypePicker="true"
                                               multiSelect="false"
                                               pickerCallback="alignmentOriginCallback"
                                               pickedAttributes="number,view"
                                               pickerTitle="搜索受影响的部件编号"
                                               readOnlyPickerTextBox="false"
                                               baseWhereClause="(oneOffVersionInfo.identifier.oneOffVersionId='~~COM_PTC_SEARCH_QB_NULL~~')"/>
                        </td>

                        <td class="attributePanel-asterisk"></td>
                    </tr>
                    <tr>
                        <td class="attributePanel-asterisk"></td>
                        <td class="attributePanel-label" id="originDesc51406359424000">
                            源物料描述:
                        </td>
                        <td class="attributePanel-value" id="originDesc">
                        </td>
                    </tr>
                    <tr>
                        <td class="attributePanel-asterisk"></td>
                        <td class="attributePanel-label">
                            替换的物料编号:
                        </td>
                        <td class="attributePanel-value">
                            <wctags:itemPicker id="replacePartPicker"
                                               componentId="pdmlPartMasterPicker"
                                               showVersion="true"
                                               defaultVersionValue="LATEST"
                                               objectType="WCTYPE|wt.part.WTPart|com.ziang.formalPanzer"
                                               showTypePicker="true"
                                               multiSelect="false"
                                               pickerCallback="alignmentReplaceCallback"
                                               pickedAttributes="number,view"
                                               pickerTitle="搜索需要替换的物料"
                                               readOnlyPickerTextBox="false"
                                               baseWhereClause="(oneOffVersionInfo.identifier.oneOffVersionId='~~COM_PTC_SEARCH_QB_NULL~~')"/>
                        </td>
                        <td class="attributePanel-asterisk"></td>
                    </tr>
                    <tr>
                        <td class="attributePanel-asterisk"></td>
                        <td class="attributePanel-label" id="replacewDesc51406359424000">
                            替换的物料描述:
                        </td>
                        <td class="attributePanel-value" id="replaceDesc">
                        </td>
                    </tr>
                    <tr>
                        <td class="attributePanel-asterisk"></td>
                        <td class="attributePanel-label" id="amount">
                            替换的物料用量:
                        </td>
                        <td class="attributePanel-value" id="amountNum">
                            <w:textBox propertyLabel="替换的物料用量"
                                       id="substitutionAmount"
                                       name="substitutionAmountName"
                                       size="40"
                                       maxlength="60"
                                       required="false"/>
                        </td>
                        <td class="attributePanel-asterisk"></td>
                    </tr>


                    <tr>
                        <td class="attributePanel-asterisk"></td>
                        <td class="attributePanel-label">
                            点击查找:
                        </td>
                        <td class="attributePanel-value">
                            <input type="button" onclick="handlerSearchPartByDesc('Test')" value="点击查找"/>
                        </td>

                        <td class="attributePanel-asterisk"></td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</fieldset>


<div>
    <jsp:include page="${mvc:getComponentURL('ext.ziang.part.builder.SelectTargetBomBuilder')}"
                 flush="true"/>
</div>

<div>
    <jsp:include page="${mvc:getComponentURL('ext.ziang.change.builder.AsyncCorrectBomBuilder')}"
                 flush="true"/>
</div>

<%@ include file="/netmarkets/jsp/util/end.jspf" %>


<script>
    function alignmentOriginCallback(object) {
        try {
            let theJSONObject = object.pickedObject;
            if (theJSONObject.length > 0) {
                writeAttribute(theJSONObject, "originPartPicker$label$", "originDesc");
            }
        } catch (e) {
            alert(e);
        }
    }

    function alignmentReplaceCallback(object) {
        try {
            var theJSONObject = object.pickedObject;
            if (theJSONObject.length > 0) {
                writeAttribute(theJSONObject, "replacePartPicker$label$", "replaceDesc");
            }
        } catch (e) {
            alert(e);
        }
    }

    //用于控制点击按钮的
    function tohide(e) {
        if (e.parentNode.parentElement.className.includes("x-panel-collapsed")) {
            e.parentNode.parentElement.setAttribute("class", "x-fieldset x-form-label-left");
        } else {
            e.parentNode.parentElement.setAttribute("class", "x-fieldset x-form-label-left x-panel-collapsed");
        }
    }

    function writeAttribute(theJSONObject, number, desc) {
        console.log(theJSONObject);
        let oid = theJSONObject[0].oid;
        let baseUrl = getBaseHref();
        console.log(document.getElementById("originPartView"));
        console.log(document.getElementById("originPartPicker$label$"));
        var params = "oid=" + oid;
        fetch(baseUrl + "servlet/rest/part/findPartInfo?oid=" + oid, {
            method: 'POST',
            param: params
        }).then(response => response.json())
            .then(data => {
                console.log(data);
                let body = data.data;
                document.getElementById(number).value = body.number;
                document.getElementById(desc).textContent = body.description;
            }).catch(error => {
            console.error(error)
            alert("导入失败！！请查看日志")
        })
    }

    function handlerSelectRadio() {

    }

    function handlerSearchPartByDesc(str) {
        try {
            let table = PTC.jca.table.Utils.getTable('AsyncCorrectBomBuilder')
            let flag = true;
            let oidList = "";
            let rowData = PTC.jca.table.Utils.getRowData(table);
            for (let index = 0; index < rowData.getCount(); index++) {
                let oid = rowData.get(index).data.oid;
                let description = rowData.get(index).data.description;
                console.log(description);
                let name = rowData.get(index).data.name;
                console.log(name);
                if (name.includes(str)) {
                    if (!oidList.includes(oid)) {
                        if (flag) {
                            oidList += oid;
                            flag = false;
                        } else {
                            oidList += "," + oid;
                        }
                    }
                }
                let params = {
                    oidList: oidList
                };
                // 判断是否满足搜索条件
                console.log(oidList);
                PTC.jca.table.Utils.reload('SelectTargetBomBuilder', params, true);
            }
        } catch (e) {
            alert(e);
        }
    }

    let rowsVisible = false;

    function toggleRows(isVisible) {
        rowsVisible = isVisible;
    }
</script>


