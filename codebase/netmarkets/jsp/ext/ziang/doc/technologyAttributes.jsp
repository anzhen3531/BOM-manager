<%@page pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/components" prefix="jca" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/attachments" prefix="attachments" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/wrappers" prefix="w" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/picker" prefix="picker" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/mvc" prefix="mvc" %>
<%@ taglib prefix="wctags" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/fmt" prefix="fmt" %>

<%@ include file="/netmarkets/jsp/util/begin.jspf" %>
<%@ include file="/netmarkets/jsp/components/createEditUIText.jspf" %>
<%@ include file="/netmarkets/jsp/components/includeWizBean.jspf" %>

<fmt:setLocale value="${localeBean.locale}"/>
<%--<fmt:setBundle basename="ext.gwc.extend.application.resource.applicationResource"/>--%>
<%--<fmt:message var="SELECT_A_TYPE" key="SELECT_OBJECT_TYPE"/>--%>

<div id='driverAttributesPane'>
    <%@ include file="/netmarkets/jsp/components/defineItemReadOnlyPropertyPanel.jspf" %>

    <jca:configureTypePicker>
        <picker:pickerParam name="seedType" value="wt.doc.WTDocument"/>
        <picker:pickerParam name="defaultType" value='wt.doc.WTDocument|com.ptc.ReferenceDocument'/>
    </jca:configureTypePicker>


    <%@ include file="/netmarkets/jsp/components/defineItem.jspf" %>
</div>

<input type="hidden" id="formType" name="formType"/>

<div id='setAttributesTablePane'>
    <script language='Javascript'>
        PTC.wizard.loadAttributeTableURL = "${mvc:getTypeBasedComponentURL('attributesTable')}";
        PTC.driverAttributes.on("afterRefresh", function () {
            PTC.wizard.attributePanelLoader.reloadAttributesTable();
            loadCompenetPanel();
        });
        PTC.onReady(function () {
            document.getElementById("createType").onchange = function () {
                var createType = document.getElementById("createType").value;
                pickerGo(createType, '');
                PTC.wizard.attributePanelLoader.goAttributeTableProgress();
                PTC.driverAttributes.refreshDriverAttributes('/servlet/TypeBasedIncludeServlet?contextAction=defineItemAttributesPanel');
                //选择事件.
                setSimpleBd();
            }
        });

    </script>
</div>


<input type="hidden" id="formType" name="formType"/>
<script Language="JavaScript">

    var loadCompenetPanel = function () {
        var mform = getMainForm();
        var createType = document.getElementById("createType").value;
        if (createType == "") {
            document.getElementById("applicationObjectTable").style.display = "none";
        } else {
            document.getElementById("formType").value = createType;
        }
    };

    function ajaxRequest(url, params) { // Ajax方法体
        var options = {
            asynchronous: false,
            parameters: params,
            method: 'POST'
        };
        return requestHandler.doRequest(url, options).responseText;
    }

    function setSimpleBd() {
        var createType = document.getElementById("createType").value;
        document.getElementById("formType").value = createType;
    }


</script>
<%--<%--%>
<%--    if("createTechnologyDoc".equals(actionName)){--%>
<%--%>--%>
<%--<jsp:include page="${mvc:getComponentURL('ext.olight.pmgt.taskbook.builder.TaskBookLayoutBuilder')}" flush="true" />--%>
<%--<%--%>

<%--}else if("editTaskBook".equals(actionName)){--%>

<%--%>--%>
<%--<jsp:include page="${mvc:getComponentURL('ext.olight.pmgt.taskbook.builder.TaskBookLayoutBuilder')}" flush="true" />--%>
<%--<%--%>
<%--}--%>
<%--%>--%>
<%@ include file="/netmarkets/jsp/util/end.jspf" %>