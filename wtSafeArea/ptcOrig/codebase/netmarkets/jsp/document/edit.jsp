<%@ page import="wt.content.ContentHolder" %>
<%@ page import="wt.access.AccessPermission" %>
<%@ page import="com.ptc.windchill.enterprise.attachments.server.AttachmentsHelper" %>

<%@ taglib prefix="jca" uri="http://www.ptc.com/windchill/taglib/components"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="wctags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="wip" uri="http://www.ptc.com/windchill/taglib/workinprogress"%>

<%@ include file="/netmarkets/jsp/components/beginWizard.jspf"%>
<%@ include file="/netmarkets/jsp/components/includeWizBean.jspf"%>


<%-- This code is checking if we have modify content access. This code should be removed and put into a
     validator on the attachments step --%>
<%
    boolean bPermit = false;
    Object context = commandBean.getPageOid().getRef();
    bPermit = (context instanceof ContentHolder) && AttachmentsHelper.hasPermission((ContentHolder) context, AccessPermission.MODIFY_CONTENT);

    String userAgent = commandBean.getTextParameter("ua");
    String actionName = commandBean.getTextParameter("actionName");

    boolean useCheckinEdit = false;

    NmContext nmContextObject = nmcontext.getContext();
    NmContextItem ci = (NmContextItem) nmContextObject.getContextItems().lastElement();

    String titleDefault = NmActionServiceHelper.service.getAction(ci.getType(), ci.getAction()).getTitle();

    String titleCheckIn = NmActionServiceHelper.service.getAction("wip", "checkin").getTitle();

    if(userAgent != null && userAgent.equals("DTI") && 
        actionName != null && actionName.equals("checkin"))
        useCheckinEdit = true;
    
    if(useCheckinEdit){
%>
        <c:set var="buttonList" value="CheckInEditWizardButtons" scope="page"/>
        <c:set var="title" value="<%=titleCheckIn%>" scope="page"/>
<%
    } else {
%>
        <c:set var="buttonList" value="EditWizardButtons" scope="page"/>
        <c:set var="title" value="<%=titleDefault%>" scope="page"/>
<%
}
%>

<%-- This tag checks out the document and sets magical form inputs and data on the command bean. This
     makes sure that the command bean's get oid methods return the oid of the working copy. --%>
<wip:autoCheckOutItem />

<%-- sets up initial data for common components --%>
<jca:initializeItem operation="${createBean.edit}"/>

<%-- todo PROBLEM!!! we are reinitializing the item again --%>
<c:choose>
     <%-- sets up dti specific data --%>
     <c:when test='${param.externalFormData != null}'>
         <jca:initializeItem operation="${createBean.edit}" attributePopulatorClass="com.ptc.windchill.enterprise.nativeapp.msoi.forms.ExternalFormDataPopulator" />
     </c:when>

     <%-- default set up initial data for common components --%>
     <c:otherwise>
          <jca:initializeItem operation="${createBean.edit}"/>
     </c:otherwise>
</c:choose>

<%-- set up wizard steps. If we don't have modify access don't show attachments step. TODO fix this --%>
<% if (bPermit) { %>
<jca:wizard buttonList="${buttonList}"
            helpSelectorKey="DocMgmtDocEdit" title="${title}">
    <jca:wizardStep action="editAttributesWizStep" type="object"/>
    <jca:wizardStep action="attachments_step" type="attachments" />
</jca:wizard>
<% } else { %>
<jca:wizard buttonList="${buttonList}" title="${title}">
    <jca:wizardStep action="editAttributesWizStep" type="object"/>
</jca:wizard>
<% } // end if bPermit %>

<%--- If we are not DTI then add the applet for doing file browsing and file uploads --%>
<wctags:fileSelectionAndUploadAppletUnlessMSOI forceApplet='${param.addAttachments != null }'/>
<SCRIPT>
PTC.wizard.getContentAreaPaddingHeight = PTC.wizard.getContentAreaPaddingHeight.wrap(function(orig) {
return orig.call(this) + 12;
});
</SCRIPT>

<%@include file="/netmarkets/jsp/util/end.jspf"%>
