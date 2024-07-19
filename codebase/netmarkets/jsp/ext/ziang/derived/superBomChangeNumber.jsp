<%@ include file="/netmarkets/jsp/components/beginWizard.jspf" %>

<jsp:include page="/netmarkets/jsp/ext/mtwit/option/superBomChangeNumberView.jsp"/>
<input type="hidden" id="sourceOldValue" name="sourceOldValue" value="">
<%@ include file="/netmarkets/jsp/util/end.jspf" %>
<script type="text/javascript" src="netmarkets/javascript/ext/mt/part/refreshLifeCycleStatus.js"></script>

<script>
    /**
     * 增加Tips指示内容停留时间
     */
    PTC.onReady(function () {
        // Ext.apply(Ext.QuickTips.getQuickTip(), {
        //     dismissDelay: 300000
        // });
    });
    /**
     * 在标准回调函数中添加属性联动的函数
     */
    PTC.search.picker.structuredEnumerationCallBack = function (pickerObject, fieldId, attr, displayFieldId) {
        var pickedObjects = pickerObject.pickedObject;
        var internalFieldToUpdate = document.getElementById(fieldId);
        var displayFieldToUpdate = document.getElementById(displayFieldId);
        if (displayFieldToUpdate === null) {
            var displayFieldByName = document.getElementsByName(displayFieldId);
            if (displayFieldByName !== null && displayFieldByName.length > 0) {
                displayFieldToUpdate = displayFieldByName[0];
            }
        }
        var newInternalValue = '';
        var newDisplayValue = '';

        for (var i = 0; i < pickedObjects.length; i++) {
            var internalName = pickedObjects[i]["internalName"];
            var displayAttribute = pickedObjects[i]["name"];
            newDisplayValue += displayAttribute + ', ';
            newInternalValue += internalName + ',';
            //Setting the title field so tah we can get fullPath on ToolTip.
            displayFieldToUpdate.title = pickedObjects[i]["fullPath"];
        }

        newInternalValue = newInternalValue.substring(0, newInternalValue.length - 1);
        newDisplayValue = newDisplayValue.substring(0, newDisplayValue.length - 2);

        console.log('newInternalValue = ' + newInternalValue + " newDisplayValue = " + newDisplayValue);

        internalFieldToUpdate.value = newInternalValue;
        displayFieldToUpdate.value = HTMLDecode(newDisplayValue);

        // 根据选择的分类设置关联的属性
        setAttr(newDisplayValue);
    };

    function setTips() {
        var attDev = Ext.query("div[id$=driverAttributesPane]")[0];
        var Info_specificationLexicon = document.createElement("div");
        Info_specificationLexicon.id = 'Info_specificationLexicon';
        Info_specificationLexicon.innerHTML = '<br><a href="/Windchill/app/#ptc1/ext/zeku/part/SpecificationLexiconInfo" target="_blank" style="color:red;text-decoration: underline;">'
            + bundleHandler.get('ext.zeku.part.resources.zekuPartResourceBundle.L007')
            + '</a>';
        attDev.parentNode.insertBefore(Info_specificationLexicon, attDev.nextSibling);
    }

    function setAttr(classification) {
        // 设置属性
        let name_input = Ext.query('input[name$=col_name___textbox]')[0];
        if (name_input) {
            name_input.value = classification;
        }
    }

    function HTMLDecode(text) {
        let temp = document.createElement('p');
        temp.innerHTML = text;
        let output = temp.innetText || temp.textContent;
        temp = null;
        return output;
    }
    function searchContainer(url){
        alert(url)
        window.open(url, 'searchContainer', 'toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=yes,top=50,left=300,width=600,height=400');
    }
    // setTips();
</script>