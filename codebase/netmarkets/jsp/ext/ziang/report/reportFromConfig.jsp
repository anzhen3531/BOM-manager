<%@ taglib uri="http://www.ptc.com/windchill/taglib/components" prefix="jca" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/mvc" prefix="mvc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/netmarkets/jsp/components/beginWizard.jspf" %>
<%@ include file="/netmarkets/jsp/components/includeWizBean.jspf" %>


<div class="inlineHelpBox">
    当前为配置报表的快速查询接口而用
</div>


<div>
    <jsp:include page="${mvc:getComponentURL('ext.ziang.report.builder.PartDerivedBuilder')}" flush="true"/>
</div>

<%@ include file="/netmarkets/jsp/util/end.jspf" %>