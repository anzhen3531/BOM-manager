<%@ taglib uri="http://www.ptc.com/windchill/taglib/components" prefix="jca" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/wrappers" prefix="w" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/picker" prefix="picker" %>
<%@ include file="/netmarkets/jsp/util/begin.jspf" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jca:renderPropertyPanel>
    <picker:typePicker id="docType" label="文档类型" mode="CREATE">
        <picker:pickerParam name="format" value="dropdown"/>
        <picker:pickerParam name="displayHierarchy" value="true"/>
        <picker:pickerParam name="showRoot" value="true"/>
        <picker:pickerParam name="seedType" value="wt.doc.WTDocument"/>
    </picker:typePicker>
    <w:textBox propertyLabel="内容类型" id="contentType" name="contentType" onblur="foo" required="true"/>
    <w:textBox propertyLabel="活动名称" id="workItemName" name="workItemName" onblur="foo" required="true"/>
    <w:textBox propertyLabel="签字X轴索引" id="signXIndex" name="signXIndex" onblur="foo" required="true"/>
    <w:textBox propertyLabel="签字Y轴索引" id="signYIndex" name="signYIndex" onblur="foo" required="true"/>
    <w:textBox propertyLabel="状态" id="status" name="status" onblur="foo" required="true"/>
    <w:textBox propertyLabel="扩展字段" id="extendedField" name="extendedField" onblur="foo" required="true"/>
    <w:textBox propertyLabel="扩展字段1" id="extendedField1" name="extendedField1" onblur="foo" required="true"/>
    <w:textBox propertyLabel="扩展字段2" id="extendedField2" name="extendedField2" onblur="foo" required="true"/>
</jca:renderPropertyPanel>

<%@ include file="/netmarkets/jsp/util/end.jspf" %>