<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/mvc" prefix="mvc" %>
<%@ include file="/netmarkets/jsp/components/beginWizard.jspf" %>
<%@ include file="/netmarkets/jsp/components/includeWizBean.jspf" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/wrappers" prefix="w" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/components" prefix="jca" %>

<jca:wizard title="一键复制BOM" buttonList="DefaultWizardButtonsNoApply">
    <jca:wizardStep action="selectAffectionBomStep" type="extECA" label="选择源BOM"/>
    <jca:wizardStep action="copyBomStructStep" type="extECA" label="选择需要复制的BOM"/>
</jca:wizard>

<%@ include file="/netmarkets/jsp/util/end.jspf" %>



