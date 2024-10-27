<%@ include file="/netmarkets/jsp/util/begin.jspf" %>

<script type="text/javascript">
<%--    加载JS函数--%>
    PTC.navigation.loadScript('netmarkets/javascript/carambola/TwoPaneExamples.js');
</script> 

Split Pane Example With Horizontal Orientation

<br /><br />
<jca:renderTwoPanes
   headerPane="/netmarkets/jsp/ext/ziang/workflowConfig/workflowConfigHeaderPane.jsp"
   headerPaneHeight="60"
   leftPane="/netmarkets/jsp/ext/ziang/workflowConfig/workFlowConfigLeftPane.jsp"
   leftPaneSize="30"
   rightPane="/netmarkets/jsp/carambola/customization/examples/twoPanePanels/rightOrBottomPane.jsp"
   rightPaneSize="70"
   orientation="horizontal"
   onClick="PTC.carambola.twoPane.onClickHandler"/>
<%@ include file="/netmarkets/jsp/util/end.jspf"%>
