<%@ taglib uri="http://www.ptc.com/windchill/taglib/components" prefix="jca"%>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/wrappers" prefix="w"%>
<%@ include file="/netmarkets/jsp/util/begin.jspf"%>

<%-- The next three tags create and display a simple attribute panel for a datum object that is a HashMap --%>

<%--标签解析 指定变量  由于只做展示属性--%>
<jca:describePropertyPanel var="panelDescriptor">
    <jca:describeProperty id="name"/>
    <jca:describeProperty id="address1" label="Address"/>
    <jca:describeProperty id="address2" label=""/>
    <jca:describeProperty id="country"/>
    <jca:describeProperty id="phone"    label="Phone"/>
    <jca:describeProperty id="fax"      label="Fax"/>
</jca:describePropertyPanel>

<jca:getModel var="panelModel" descriptor="${panelDescriptor}"
              serviceName="com.ptc.carambola.property.PropertyUtils"
              methodName="getExamplePropertyPanelData">
</jca:getModel>

<jca:renderPropertyPanel model="${panelModel}"/>

<br/>

<%--填写属性 Tag --%>


<h1> Example of a simple attribute panel that uses GUI component wrapper tags to create and display GUIComponents </h1>
<br/>

<%-- this cmb tag is getting the "dvals," "ivals," and "date" values that the gui component wrapper tags are using --%>
<cmb:data/>

<%-- Note that no describePropertyPanel or getModel tags are used for this panel as the data is being acquired from the tag
     above and the gui components are being created and configured directly from this page --%>
<jca:renderPropertyPanel>
    <w:textBox propertyLabel="Textbox" id="textboxA" name="textboxA" onblur="foo" required="true"/>
    <w:checkBox propertyLabel="Checkbox" id="checkboxA" name="checkboxA" onblur="foo" required="true" submitAsNew="true"/>
    <w:checkBox label="text on the right of a checkbox" id="checkboxB" name="checkboxB" onblur="foo" required="false" submitAsNew="true" renderLabel="true" renderLabelOnRight="true" />
    <w:comboBox propertyLabel="Combobox" id="comboboxA" name="comboboxA" onselect="lauch()" internalValues="${ivals}"  displayValues="${dvals}" />
    <w:textArea propertyLabel="TextArea" id="messageA" name="messageA" value="" cols="39" rows="5" required="false"/>
    <w:dateInputComponent propertyLabel="DateInputComponent" name="startdateA" required="true" dateValueType="DATE_TIME"/>
    <w:button propertyLabel="Button" id="button1" name="button1" value="Click Me" typeSubmit="false" toolTipText="Click Me" onclick="doFoo()"/>
    <w:radioButton propertyLabel="Radio Button" id="ONE" label="One" value="123" name="radio" checked="true" onclick="doFoo()"/>
    <w:radioButton label="Two" id="TWO" value="456" name="radio" onclick="doFoo()"/>
    <w:dateDisplayComponent propertyLabel="Date" name="datetime"  dateDisplayFormat="${standardDateTime}" dateValue="${date}"/>
    <w:textBox propertyLabel="Textbox A" id="textboxA2" name="textboxA" onblur="foo" required="true"/>
    <w:textBox propertyLabel="Textbox B" id="textboxB2" name="textboxB" onblur="foo" required="true"/>
    <w:textBox propertyLabel="Textbox C" id="textboxC2" name="textboxC" onblur="foo" required="true"/>
    <w:textBox propertyLabel="Textbox E" id="textboxE2" name="textboxE" onblur="foo" required="true"/>
    <w:label propertyLabel="Label" id="aLabel" name="aLabel" value="Some Value"/>
</jca:renderPropertyPanel>


<%--> The following code inserts the About This Example links at the bottom of the page.
      It would not be used in production. <--%>
<%--关于此示例 案例--%>
<cmb:aboutThisExampleLinks whcName="WCCG_UICust_CustHTMLClients_AttrPanel_EX_JSPTag"
                           grokName="Client_JCA_AttributePanelBestPractice"
                           anchorName="SimplePanelUsingJSPTags"/>




<%@ include file="/netmarkets/jsp/util/end.jspf"%>