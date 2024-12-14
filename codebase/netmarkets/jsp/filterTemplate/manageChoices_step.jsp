<%@ taglib prefix="wctags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/wrappers" prefix="w"%>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/fmt" prefix="fmt"%>
<%@ taglib prefix="ato" uri="http://www.ptc.com/windchill/taglib/ato"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.ptc.windchill.option.model.OptionSet,
                com.ptc.windchill.option.server.OptionsClientHelper,
               wt.vc.wip.WorkInProgressHelper,
               wt.vc.wip.Workable,
               wt.fc.ObjectIdentifier"%>
<%@ page import="java.util.List" %>
<fmt:setLocale value="${localeBean.locale}" />
<fmt:setBundle basename="com.ptc.windchill.option.optionResource" />
<fmt:message var="context_lbl" key="CONTEXT" />

<%@ include file="/netmarkets/jsp/util/begin.jspf"%>

<%
    OptionSet template = null;
    wt.fc.WTReference objRef = new wt.fc.ReferenceFactory().getReference(commandBean.getActionOid().getOidObject().toString());
    Workable orig_workable = (Workable) objRef.getObject();
    if (WorkInProgressHelper.isCheckedOut(orig_workable) && !WorkInProgressHelper.isWorkingCopy(orig_workable)) {
        template = (OptionSet) WorkInProgressHelper.service.workingCopyOf(orig_workable);
    } else {
        template = (OptionSet) orig_workable;
    }

    OptionSet optionSet = WorkInProgressHelper.isCheckedOut(template) ? (OptionSet) WorkInProgressHelper.service.originalCopyOf(template) : template;

    List<String> context = OptionsClientHelper.service.getManageChoicesContext(optionSet, commandBean.getContainerRef());
    String containerRefStr = context.get(0);
    String containerName = context.get(1);
 %>

<input type="hidden" name="isApply" id="isApply" value="false" />

<table width="98%">
    <tr>
        <td><wctags:contextPicker id="contextPicker"
            typeComponentId="PDMLink.atoOpStContextPicker"
            label="${context_lbl}"
            defaultValue="<%=containerName%>"
            objectType="wt.fc.Persistable"
            displayAttribute="name"
            showTypePicker="true"
            multiSelect="false"
            readOnlyPickerTextBox="true"
            pickerCallback="ATO.components.ChoiceComponent.contextPickerCallback" /></td>
    </tr>
</table>


<table id='carModeTable' width="98%">
    <tr>
        <td>
        </td>
        <td class="ppLabel">
            <b>
                <label id='carModeLabel' for="carMode">选择车型:</label>
            </b>
        </td>
        <td align="left" valign="middle" nowrap="true">
            <select id='carMode' name = 'carMode' >
                <option id="90" selected value="90mm">90mm </option>
            </select>
            <input type="button" id='btn_carMode_public' value='自动匹配选择【全局】' onClick="autoSelectApplicableChoice()"/>
        </td>
    </tr>
</table>

<ato:initOptionComponent tableId="choicecomponent.managechoicetable" 
                         choiceInfoBeanFactory="manageChoice"
                         containerId="<%=containerRefStr%>"
                         displayTopPanel="false"
                         hideOverrideBtn="true"
                         hideInfoLinkBtn="true"
                         hideFilterModeSelector="true"
                         height="95%"
                         trackSelections="true"/>
                         
<script>

function onApply() {
    // this is required for callind onSubmitMain() framework JS
    Ext.getDom("isApply").value=true; // this is used for manipulation in formprocessor
    onSubmitMain();    
}

function goProgress(){
    if (!('progressMessage' in window) || progressMessage === '') {
        progressMessage = bundleHandler.get('com.ptc.core.ui.navigationRB.LOADING');
    }
    var config = {
        msg: progressMessage
    };
    
    var isApply = Ext.getDom("isApply").value;
    if(isApply === true){
        PTC.wizard.loadMask = new Ext.LoadMask(Ext.getBody(), config);
    }else{
        PTC.wizard.loadMask = new Ext.LoadMask('wiz_step_contents', config);
    }
    PTC.wizard.loadMask.show();
}
/**
 * 自动勾选车型符合的选择
 */
function autoSelectApplicableChoice(){
    // 开启加载程序
    ATO.components.ChoiceComponent.showProgress();
    // 初始化参数
    let params = new JCAClone(ATO.components.ChoiceComponent.params);
    console.log(params);
    // 设置初始化选择
    ATO.components.ChoiceComponent.clearUserSelections();
    // 定义参数列表
    params.choicecompaction = 'choicecomp.init';
    params.callback = function (rulesEngineResult) {
        ATO.components.ChoiceComponent.params.rulesSessionId = rulesEngineResult.sessionId;
        params.rulesSessionId = rulesEngineResult.sessionId;
        ATO.components.ChoiceComponent.params.initSelectionDiff = rulesEngineResult.initSelectionDiff;
        params.initSelectionDiff = rulesEngineResult.initSelectionDiff;
        Ext.getDom("rulesSessionId").value = rulesEngineResult.sessionId;
        params.actionType = 'customDefaultSelections';
        params.panzerVersion = document.getElementById("carMode").value;
        // 发送请求到接口，进行选择
        // 回调函数中会自动根据参数处理勾选的选项选择
        PTC.atoChoiceSel.invokeChoiceSelectionController(params,
            ATO.components.ChoiceComponent.customSelectChoiceComponentCallBack, true);
    };
    // 调用对应的函数
    ATO.components.ChoiceComponent.callRulesEngineAction(params);
    // 关闭加载程序
    ATO.components.ChoiceComponent.stopProgress();
}

</script>
<%@ include file="/netmarkets/jsp/util/end.jspf"%>
