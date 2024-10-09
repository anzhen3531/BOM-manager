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


    let flag = true;
    function loopTimer() {
        console.log("loopTimer exec ")
        let buttonList = document.getElementsByTagName("button");
        for (let i = 0; i < buttonList.length; i++) {
            if (buttonList[i].innerHTML.concat("变型解决方案")) {
                var b=document.getElementsByClassName('x-tool x-tool-toggle x-tool-collapse-west');
                b[0].click();
                flag = false;
            }
        }
        if (flag){
            setTimeout(loopTimer, 500);
        }
    }
    loopTimer();

</script>

