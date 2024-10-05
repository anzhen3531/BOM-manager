<%@ include file="/netmarkets/jsp/util/beginPopup.jspf"%>
<%@ page import = "wt.util.WTMessage"%>
<%@ page import = "com.ptc.cat.gxt.client.GXTMessages"%>
<%@ page import="com.ptc.wpcfg.doc.DocHelper"%>
<script src="gwt/com.ptc.windchill.wncgwt.WncGWT/com.ptc.windchill.wncgwt.WncGWT.nocache.js"></script>
<div id='optionComponentTableDiv'></div>

<!-- Showing the loading message till the EC gets loaded. Here div id should be 'ec_loading_div' -->
<div id="ec_loading_div" style="position:absolute; top:50%; width:100%; text-align:center;">
    <img src="netmarkets/javascript/gxt/resources/images/gxt/icons/loading.gif" border=0> <%=WTMessage.getLocalizedMessage(GXTMessages.class.getName(), GXTMessages.LOADING) %>
</div>

<div id='vspec.expansioncriteria.div' style="width:100%;height:95%"></div>
<input type="hidden" name="useEnforeRequiredPreference" id="useEnforeRequiredPreference" value="false"/>

<script>
    <%@ include file="/netmarkets/jsp/ecComponent/initializeECComponent.jspf"%>   
    var params = {
        'context'           : $("configure-context").value,
        'ecid'              : $("initEcid").value,
        'containerOid'      : $("CONTAINER_REF").value,
        'appName'           : 'com.ptc.windchill.enterprise.part.psb.PSB',
        'disableApplyToTop' : '',
        'centricity'        : 'false',
        'hashKey'           : 'configSpec',
        'div'               : 'vspec.expansioncriteria.div',
        'remoteAddr'        : null,
        'sessionId'         : null
    };

    function loadEC(){ 
        if (typeof ECComponentPicker_renderECTabs == "undefined"){ 
            setTimeout("loadEC()", 500); 
            return; 
        } 
        //load ECComponent
        if (typeof ECComponentPicker_renderECTabs == 'function') {
            ECComponentPicker_setParams("useExistingECId=true", params.div);
            ECComponentPicker_renderECTabs(params.context, params.ecid, params.appName,
                                                                     params.containerOid, params.disableApplyToTop, 
                                                                     params.centricity, params.div, params.remoteAddr, 
                                                                     params.sessionId, params.hashKey);

            var delayedTask = new Ext.util.DelayedTask( function() {
                ConfigureWizard.disableAllWizBtns(true);
            }); 
            delayedTask.delay(100);            
        }
    }
    PTC.onReady(function() {
        ConfigureWizard.disableAllWizBtns(true);
        loadEC();

        let elements = document.getElementsByClassName("x-tool x-tool-toggle x-tool-collapse-west");
        elements[0].click();

    });

</script>
<%@ include file="/netmarkets/jsp/util/end.jspf"%>
