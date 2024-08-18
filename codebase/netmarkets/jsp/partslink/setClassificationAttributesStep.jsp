<%@page import="com.ptc.windchill.enterprise.part.PartConstants" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="ext.ziang.common.util.IbaUtil" %>
<%@ page import="wt.session.SessionServerHelper" %>
<%@ page import="ext.ziang.common.util.ToolUtils" %>
<%@ page import="wt.part.WTPart" %>
<%@ page import="java.util.Map" %>
<%@ page import="wt.util.WTException" %>
<%@ page import="ext.ziang.common.helper.attr.ClassificationHelper" %>
<%@ page import="java.util.Set" %>
<%@ page import="org.apache.commons.lang3.StringEscapeUtils" %>
<%@ page import="com.alibaba.fastjson.JSONObject" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="com.alibaba.fastjson.JSON" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/components"
           prefix="jca" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://www.ptc.com/windchill/taglib/mvc" prefix="mvc" %>
<%@ include file="/netmarkets/jsp/util/begin.jspf" %>

<fmt:setLocale value="${localeBean.locale}"/>
<fmt:setBundle basename="com.ptc.windchill.partslink.partslinkClientResource"/>

<%
    pageContext.setAttribute("isMultipart", PartConstants.RequestParam.Values.MULTIPART_WIZARD_TYPE
            .equals(commandBean.getTextParameter(PartConstants.RequestParam.Names.WIZARD_TYPE)));
    request.setAttribute("enforceClassificationNamingRule", commandBean.getTextParameter("enforceClassificationNamingRule"));

    String oid = request.getParameter("oid");
    // 判断ActionName
    String actionName = request.getParameter("actionName");
    String classificationAttrKeys = "";
    if ("changePart".equals(actionName)) {
        boolean accessFlag = SessionServerHelper.manager.setAccessEnforced(false);
        try {
            // 获取页面上不部件所有的分类属性
            HashMap text = commandBean.getText();
            System.out.println("text = " + text);
            for (Object key : text.keySet()) {
                // 获取当前填写的分类的绑定的所有的属性
                String keyStr = (String) key;
                Object value = text.get(key);
                System.out.println("value = " + value);
                if (keyStr.contains("Classify") && value != null) {
                    // 通过属性列表查询对应的属性
                    Set<String> classificationAttr = ClassificationHelper.findClassificationAttr(((String) value));
                    classificationAttrKeys = String.join(",", classificationAttr);
                    break;
                }
            }
            Object object = ToolUtils.getObjectByOid(oid);
            if (object instanceof WTPart) {
                WTPart part = (WTPart) object;
                Map<String, Object> allIBAValues = IbaUtil.findAllIBAValue(part);
                System.out.println("allIBAValues = " + allIBAValues);
                JSONObject jsonObject = new JSONObject(allIBAValues);
                System.out.println("jsonObject = " + jsonObject);
                request.setAttribute("allIBAValues", StringEscapeUtils.escapeJson(jsonObject.toJSONString()));
            }
        } catch (WTException e) {
            throw new WTException(e.getMessage());
        } finally {
            SessionServerHelper.manager.setAccessEnforced(accessFlag);
        }
    }
%>

<input type="hidden" id="classificationAttrKeys" value="<%=classificationAttrKeys%>>">
<c:if test="${isMultipart == 'false' && enforceClassificationNamingRule == 'true'}">
    <input type="hidden" name="autoGenName" id="autoGenName"></input>
    <input type="hidden" name="autonameOverrideChecked" id="autonameOverrideChecked" value="false"></input>
    <div align="right" style="padding-right: 8px;">
        <jca:action actionName="overrideName" actionType="partslink" button="false"
                    validateAction="true"/>
    </div>
</c:if>
<jsp:include page="${mvc:getComponentURL('classification.attribute.panel')}"
             flush="true"/>
<%@ include file="/netmarkets/jsp/util/end.jspf" %>


<script>

    // 编写设置属性函数
    PTC.onReady(function () {
        let value = document.getElementById("classificationAttrKeys").value;
        let split = value.split( ",");
        for (let i = 0; i < split.length; i++) {
            console.log(split[i]);
        }
        let valueMap = JSON.parse("<%=request.getAttribute("allIBAValues")%>");
        console.log(valueMap)
        // 遍历所有的文本框
        // setTimeout(function () {
        //     for (let i = 0; i < keySet.length; i++) {
        //         let keySetElement = keySet[i];
        //         setClassifyValue(keySetElement, valueMap[keySetElement])
        //     }
        // }, 200);
    });


    /**
     * 设置分类属性值
     * @param key
     * @param value
     */
    function setClassifyValue(key, value) {
        console.log("key" + key);
        console.log("value" + value);
        // 查询所有的输入框
        const inputs = document.querySelectorAll('input');
        // 遍历并打印每个input元素
        inputs.forEach(function (input) {
            if (input.name.indexOf(key) && input.type !== "hidden") {
                input.value = value;
            }
        });

        const texts = document.querySelectorAll('text');
        // 遍历并打印每个input元素
        texts.forEach(function (text) {
            if (text.name.indexOf(key) && text.type !== "hidden") {
                text.value = value;
            }
        });

        // 获取输入框
        const selects = document.querySelectorAll('select');
        // 遍历并打印每个input元素
        selects.forEach(function (select) {
            if (select.name.indexOf(key) && select.type !== "hidden") {
                select.value = value;
            }
        });
    }
</script>
