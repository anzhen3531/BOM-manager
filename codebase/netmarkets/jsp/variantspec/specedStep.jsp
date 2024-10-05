<%@ include file="/netmarkets/jsp/util/beginPopup.jspf" %>
<div id="speceditor.main.div" style="width:100%;height:100%"></div>
<div id="speceditor.main.temp"></div>
<input type="hidden" name="pagetype" id="pagetype" value=""/>
<%@ include file="/netmarkets/jsp/util/end.jspf" %>
<script>
    //create a json object and use the formdata for params
    ConfigureWizard.render('speceditor.main.div');

    //window resize event listner.
    Ext.EventManager.onWindowResize(ConfigureWizard.syncSize);


    PTC.onReady(function () {
        setTimeout(function () {
            let elements = document.getElementsByClassName("x-tool x-tool-toggle x-tool-collapse-west");
            console.log(elements)
            elements[0].click();
            console.log("element.click() speced_Step.jsp");
        }, 200);
    });
</script>

