<%@ taglib uri="http://www.ptc.com/windchill/taglib/components" prefix="jca" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/mvc" prefix="mvc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/netmarkets/jsp/components/beginWizard.jspf" %>
<%@ include file="/netmarkets/jsp/components/includeWizBean.jspf" %>

<%--
添加一个搜索表格
--%>
<%--配置帮助文字盒子--%>
<div class="inlineHelpBox">
    当前为配置PDF的签字位置而用
</div>

<div>
    <jsp:include page="${mvc:getComponentURL('ext.ziang.doc.sign.builder.ElectronicSignatureConfigBuilder')}"
                 flush="true"/>
</div>
<%@ include file="/netmarkets/jsp/util/end.jspf" %>