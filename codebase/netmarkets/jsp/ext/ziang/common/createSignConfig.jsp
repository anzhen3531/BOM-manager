<%@ taglib uri="http://www.ptc.com/windchill/taglib/components" prefix="jca" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/wrappers" prefix="w" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/picker" prefix="picker"%>
<%@ include file="/netmarkets/jsp/util/begin.jspf" %>

<jca:renderPropertyPanel>
    <picker:typePicker id="docType" label="文档类型" mode="CREATE">
        <picker:pickerParam name="format"        value="dropdown" />
        <picker:pickerParam name="displayHierarchy"  value="false" />
        <picker:pickerParam name="showRoot"      value="false" />
        <picker:pickerParam name="seedType"      value="wt.doc.WTDocument" />
    </picker:typePicker>
    <w:textBox propertyLabel="contentType" id="contentType" name="内容类型" onblur="foo" required="true"/>
    <w:textBox propertyLabel="workItemName" id="workItemName" name="活动名称" onblur="foo" required="true"/>
    <w:textBox propertyLabel="signXIndex" id="signXIndex" name="签字X轴索引" onblur="foo" required="true"/>
    <w:textBox propertyLabel="signYIndex" id="signYIndex" name="签字Y轴索引" onblur="foo" required="true"/>
    <w:textBox propertyLabel="status" id="status" name="状态" onblur="foo" required="true"/>
    <w:textBox propertyLabel="extendedField" id="extendedField" name="扩展字段" onblur="foo" required="true"/>
    <w:textBox propertyLabel="extendedField1" id="extendedField1" name="扩展字段1" onblur="foo" required="true"/>
    <w:textBox propertyLabel="extendedField2" id="extendedField2" name="扩展字段2" onblur="foo" required="true"/>
</jca:renderPropertyPanel>

<%@ include file="/netmarkets/jsp/util/end.jspf" %>