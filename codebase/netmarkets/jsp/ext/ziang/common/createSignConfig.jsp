<%@ taglib uri="http://www.ptc.com/windchill/taglib/components" prefix="jca" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/wrappers" prefix="w" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/carambola" prefix="cmb" %>
<%@ include file="/netmarkets/jsp/util/begin.jspf" %>

<jca:renderPropertyPanel>
    <%--    文档类型 Picker --%>
    <%--    内容类型 checkBox --%>
    <%--    流程名称  --%>
    <%--    文本 X --%>
    <%--    文本 Y --%>
    <%--    State 文本 --%>
    <%--    State 文本 --%>
    <%--    State 文本 --%>
    <%--    State 文本 --%>
    <w:textBox propertyLabel="Textbox" id="textboxA" name="textboxA" onblur="foo" required="true"/>
    <w:checkBox propertyLabel="Checkbox" id="checkboxA" name="checkboxA" onblur="foo" required="true"
                submitAsNew="true"/>
    <w:textBox propertyLabel="Textbox A" id="textboxA2" name="textboxA" onblur="foo" required="true" value="123213"/>
    <w:textBox propertyLabel="Textbox B" id="textboxB2" name="textboxB" onblur="foo" required="true"/>
    <w:textBox propertyLabel="Textbox C" id="textboxC2" name="textboxC" onblur="foo" required="true"/>
    <w:textBox propertyLabel="Textbox E" id="textboxE2" name="textboxE" onblur="foo" required="true"/>
</jca:renderPropertyPanel>

<%@ include file="/netmarkets/jsp/util/end.jspf" %>