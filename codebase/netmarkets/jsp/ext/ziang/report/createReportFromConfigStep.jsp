<%@ taglib uri="http://www.ptc.com/windchill/taglib/components" prefix="jca" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/wrappers" prefix="w" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/picker" prefix="picker" %>
<%@ include file="/netmarkets/jsp/util/begin.jspf" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jca:renderPropertyPanel>
    <w:textBox propertyLabel="描述" id="description" name="description" required="true" maxlength="200"/>
    <w:textArea propertyLabel="内容" id="content" name="content" cols="39" rows="5" required="true" maxLength="2000"/>
</jca:renderPropertyPanel>

<%@ include file="/netmarkets/jsp/util/end.jspf" %>