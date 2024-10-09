<%@ include file="/netmarkets/jsp/util/beginPopup.jspf" %>
<div id="speceditor.main.div" style="width:100%;height:100%"></div>
<div id="speceditor.main.temp"></div>
<%@page language="java" session="true" pageEncoding="UTF-8" %>
<input type="hidden" name="pagetype" id="pagetype" value=""/>
<%@ include file="/netmarkets/jsp/util/end.jspf" %>
<script>
    //create a json object and use the formdata for params
    ConfigureWizard.render('speceditor.main.div');
    //window resize event listner.
    Ext.EventManager.onWindowResize(ConfigureWizard.syncSize);

    let flag = true;
    function loopTimer() {
        let buttonList = document.getElementsByTagName("button");
        for (let i = 0; i < buttonList.length; i++) {
            let buttonText =  buttonList[i].textContent;
            buttonText = buttonText.replace(/\s+/g, '').trim();
            if (buttonText.includes("变型解决方案")) {
                let leftNode=document.getElementsByClassName('x-tool x-tool-toggle x-tool-collapse-west');
                console.log(leftNode)
                leftNode[0].click();
                flag = false;
            }
        }
        if (flag){
            setTimeout(loopTimer, 500);
        }
    }
    loopTimer();

</script>

