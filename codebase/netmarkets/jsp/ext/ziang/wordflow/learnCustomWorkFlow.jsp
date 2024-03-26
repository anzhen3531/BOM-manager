<%@ include file="/netmarkets/jsp/util/begin.jspf" %>

<script type="text/javascript">
    PTC.navigation.loadScript('netmarkets/javascript/carambola/TwoPaneExamples.js');
</script>



Split Pane Example With Horizontal Orientation
<br/>
<br/>


<jca:renderTwoPanes
        headerPane="/netmarkets/jsp/carambola/customization/examples/twoPanePanels/headerPane.jsp"
        headerPaneHeight="125"
        leftPane="/netmarkets/jsp/carambola/customization/examples/twoPanePanels/leftOrTopPane.jsp" leftPaneSize="30"
        rightPane="/netmarkets/jsp/carambola/customization/examples/twoPanePanels/rightOrBottomPane.jsp"
        rightPaneSize="70" orientation="horizontal" onClick="PTC.carambola.twoPane.onClickHandler"/>
<%@ include file="/netmarkets/jsp/util/end.jspf" %>