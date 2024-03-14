<%@ taglib prefix="wctags" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/components" prefix="jca"%>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page import="com.ptc.windchill.enterprise.part.PartConstants" %>
<%@ page import="com.ptc.windchill.enterprise.part.partResource"%>

<fmt:setBundle basename="com.ptc.windchill.enterprise.part.partResource"/>

<fmt:message var="endItemLabel" key="part.createPartDefineItemWizStep.END_ITEM" />
<fmt:message var="genericTypeLabel" key="<%= partResource.GENERICTYPE %>"/>

<%@ include file="/netmarkets/jsp/util/begin.jspf"%>
<%@ include file="/netmarkets/jsp/components/createEditUIText.jspf"%>
<%@ include file="/netmarkets/jsp/components/includeWizBean.jspf"%>

<jca:describePropertyPanel var="defineItemStepAttributesPanelDescriptor" scope="request"
        id = "create.driverAttributes"
        type = "wt.part.WTPart"
        componentType="WIZARD_ATTRIBUTES_TABLE" 
        mode="CREATE">

   <jca:describeProperty id="orgid" need="organization.id"/>
   <jca:describeProperty id="genericType" label="${genericTypeLabel}"/>

   <%-->
   Set the label for enditem to null string because we need to 
   display the label to the right. The data utility inserts the label.
   <--%>
   <jca:describeProperty id="<%=PartConstants.ColumnIdentifiers.ENDITEM%>" label="${endItemLabel}" selectionListStyle="dropdown"/>

</jca:describePropertyPanel>

<jca:getModel var="propertyModel" descriptor="${defineItemStepAttributesPanelDescriptor}"
               serviceName="com.ptc.core.components.forms.CreateAndEditModelGetter"
               methodName="getItemAttributes">
   <jca:addServiceArgument value="${defineItemStepAttributesPanelDescriptor}" />
   <jca:addServiceArgument value="${commandBean}" />
   <jca:addServiceArgument value="${nmcontext.context}" />
</jca:getModel>

<div>
   <button>1223123</button>
</div>


<jca:renderPropertyPanel model="${propertyModel}" />

<%@ include file="/netmarkets/jsp/util/end.jspf"%>
