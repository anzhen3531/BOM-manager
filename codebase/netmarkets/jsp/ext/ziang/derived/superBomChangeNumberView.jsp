<%@ taglib prefix="jca" uri="http://www.ptc.com/windchill/taglib/components" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/partclient" prefix="partclient" %>
<%@ taglib prefix="wctags" tagdir="/WEB-INF/tags" %>
<%@page pageEncoding="UTF-8" %>
<%@ page import="com.ptc.core.components.descriptor.DescriptorConstants" %>
<%@ page import="com.ptc.windchill.enterprise.part.PartConstants" %>
<%@ page import="com.ptc.windchill.enterprise.part.partResource" %>
<%@ page import="com.ptc.windchill.uwgm.cadx.createecaddesign.documentECADResource" %>
<%@ page import="com.ptc.core.meta.type.mgmt.server.impl.TypeDomainHelper" %>
<%@ page import="ext.mt.part.constants.ComponentConstant" %>
<%@ page import="ext.mt.util.WCUtil" %>
<%@ page import="wt.util.WTException" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="wt.part.WTPart" %>
<%@ page import="ext.mtwit.core.MtWitConstants" %>`
<%@ page import="java.util.Map" %>
<%@ page import="com.ptc.core.lwc.server.LWCStructEnumAttTemplate" %>
<%@ page import="cn.witsoft.partlink.service.StandarClassificationServiceImpl" %>
<%@ page import="ext.mt.enums.PartAttributeEnum" %>
<%@ page import="ext.mt.common.util.IbaUtil" %>
<%@ page import="ext.exception.BizException" %>
<%@ page import="ext.exception.ErrorCodeEnum" %>
<%@ page import="wt.session.SessionServerHelper" %>

<%@ include file="/netmarkets/jsp/components/includeWizBean.jspf" %>

<script type="text/javascript" SRC="templates/cadx/common/refreshCC.js"></script>
<script language="JavaScript" src="netmarkets/javascript/part/PartHelper.js"></script>
<script language="JavaScript" src='netmarkets/javascript/util/revisionLabelPicker.js'></script>
<script type="text/javascript" src="netmarkets/javascript/uwgmcadx/CADDocument.js"></script>

<script type="text/javascript">
    Ext.ComponentMgr.onAvailable('multiPartWizAttributesTableDescriptor', PTC.jca.table.sortTableOnAfterRowRefresh);
</script>

<fmt:setBundle basename="com.ptc.windchill.enterprise.part.partResource"/>

<fmt:message var="definePartWizStepLabel" key="part.createPartWizard.DEFINE_ITEM_WIZ_STEP_LABEL"/>
<fmt:message var="setAttributesWizStepForCreateMultiPartLabel"
             key="part.createPartWizard.SET_IDENTITY_ATTRIBUTES_WIZ_STEP_LABEL"/>
<fmt:message var="setAttributesWizStepLabel" key="part.createPartWizard.SET_ATTRIBUTES_WIZ_STEP_LABEL"/>
<fmt:message var="setAttributesWizStepMultiCreateLabel"
             key="part.createPartWizard.SET_ATTRIBUTES_WIZ_STEP_MULTI_CREATE_LABEL"/>
<fmt:message var="createMultiplePartWizardTitle" key="part.createMultiPart.WIZARD_LABEL"/>
<fmt:message var="newCADDocWizStepLabel" key="part.createPartWizard.NEW_CAD_DOC_WIZ_STEP_LABEL"/>

<partclient:multiPartWizardLaunch multiPart="isMultiPart"/>
<fmt:message var="wizardTitle" key="part.createPartWizard.title"/>

<fmt:setBundle basename="com.ptc.windchill.uwgm.cadx.EcadActions.ecadActionsResource"/>
<fmt:message var="wizardTitleFromURL" key="${param.wizardWindowTitle}"/>

<c:if test='${param.wizardWindowTitle != ""}'>
    <c:set var="wizardTitle" value="${wizardTitleFromURL}"/>
</c:if>

<c:if test='${param.invokedfrom == "workspace" || param.showContextStep == "true"}'>
    <jsp:setProperty name="createBean"
                     property="contextPickerTypeComponentId" value="PDMLink.containerSearch"/>
    <jsp:setProperty name="createBean"
                     property="contextPickerExcludeTypes" value="WCTYPE|wt.inf.library.WTLibrary|com.ptc.QMS"/>
</c:if>

<c:set var="helpKey" value="PartCreate_help" scope="page"/>
<%--设置提交按钮为自定义的按钮集合--%>
<c:set var="buttonList" value="DefaultWizardButtonsNoApplyCustom" scope="page"/>

<jca:initializeItem
        operation="${createBean.create}"
        baseTypeName="WCTYPE|wt.part.WTPart|wt.part.Placeholder"
        attributePopulatorClass="com.ptc.windchill.enterprise.part.forms.PartAttributePopulator"/>

<jca:initializeItem operation="${createBean.create}" baseTypeName="wt.part.WTPart|com.sankuai.part"
                    objectHandle="<%=PartConstants.ObjectHandles.PART%>"
                    attributePopulatorClass="com.ptc.windchill.enterprise.part.forms.PartAttributePopulator"/>

<script language="Javascript">
    setPartObjectHandle('!~objectHandle~partHandle~!');
</script>
<%@include file="/netmarkets/jsp/attachments/initAttachments.jspf" %>
<jca:wizard helpSelectorKey="${helpKey}" buttonList="${buttonList}" title="${wizardTitle}">
    <jca:wizardStep action="setContextWizStep" objectHandle="<%=PartConstants.ObjectHandles.PART%>" type="object"/>
    <jca:wizardStep action="defineItemAttributesWizStep" objectHandle="<%=PartConstants.ObjectHandles.PART%>"
                    type="object"/>
    <jca:wizardStep action="setClassificationAttributesWizStep" objectHandle="<%=PartConstants.ObjectHandles.PART%>"
                    type="classification"/>
    <jca:wizardStep action="securityLabelStep" objectHandle="<%=PartConstants.ObjectHandles.PART%>"
                    type="securityLabels"/>
    <jca:wizardStep action="attachments_step" type="attachments"/>
</jca:wizard>

<%--- If we are not DTI then add the applet for doing file browsing and file uploads --%>
<wctags:fileSelectionAndUploadAppletUnlessMSOI forceApplet='${param.addAttachments != null }'/>

<%-- It will load classificationHelper.js file which used for classification functionality --%>
<wctags:loadCreateClassificationScript isMultiObject="${isMultiPart}"/>

<input type="hidden" id="isInheritClassification" name="isInheritClassification" value="true">

<script lang="JavaScript">
    function setClassificationAttributes(classificationAttributes) {
        document.getElementById('classificationAttributes').value = classificationAttributes;
    }
</script>

<%
    String partName = null;
    String partVersion = null;
    String oid = request.getParameter("oid");
    // 存货类别
    String materialType = "";
    // 物料类型
    String materialTemplate = "";
    // 分类内部名称
    String classification = "";
    // 分类显示名称
    String classificationDisplayName = "";
    // 来源
    String source = "";
    // 单位
    String defaultUnit = "";
    // 是否维修件
    String isServicePart = "";
    // 物料英文描述
    String materialDescriptionInEnglish = "";
    if (StringUtils.isNotBlank(oid)) {
        // 忽略权限
        boolean accessFlag = SessionServerHelper.manager.setAccessEnforced(false);
        try {
            Object objectByOid = WCUtil.getObjectByOid(oid);
            if (objectByOid instanceof WTPart) {
                WTPart part = (WTPart) objectByOid;
                partName = part.getName();
                partVersion = part.getIterationDisplayIdentifier().toString();
                Map<String, Object> allIBAValues = IbaUtil.getAllIbaValues(part);
                // 存货类别
                materialType = null != allIBAValues.get(PartAttributeEnum.MATERIAL_TYPE.getValue()) ? String.valueOf(allIBAValues.get(PartAttributeEnum.MATERIAL_TYPE.getValue())) : "";
                // 物料类型
                materialTemplate = null != allIBAValues.get(PartAttributeEnum.MATERIAL_TEMPLATE.getValue()) ? (String) allIBAValues.get(PartAttributeEnum.MATERIAL_TEMPLATE.getValue()) : "";
                // 分类内部名称
                classification = null != allIBAValues.get(MtWitConstants.IBA_STRING_CLASSIFICATION) ? (String) allIBAValues.get(MtWitConstants.IBA_STRING_CLASSIFICATION) : "";
                // 来源
                source = part.getSource().toString();
                // 单位
                defaultUnit = part.getDefaultUnit().toString();
                // 分类显示名称
                LWCStructEnumAttTemplate lwcStructEnumAttTemplate = StandarClassificationServiceImpl.newStandarClassificationServiceImpl().getLwcsByInnerName(classification, true);
                classificationDisplayName = StandarClassificationServiceImpl.newStandarClassificationServiceImpl().getClassificationNodeDisplayName(lwcStructEnumAttTemplate);
                // 是否维修件
                isServicePart = null != allIBAValues.get(PartAttributeEnum.IS_SERVICE_PART.getValue()) ? (String) allIBAValues.get(PartAttributeEnum.IS_SERVICE_PART.getValue()) : "";
                // 物料英文描述
                materialDescriptionInEnglish = null != allIBAValues.get(PartAttributeEnum.MATERIAL_DESCRIPTION_IN_ENGLISH.getValue()) ? (String) allIBAValues.get(PartAttributeEnum.MATERIAL_DESCRIPTION_IN_ENGLISH.getValue()) : "";
            }
        } catch (WTException e) {
            throw new BizException(ErrorCodeEnum.ADMIN_COMBINATION.getCode(), e.getMessage());
        } finally {
            SessionServerHelper.manager.setAccessEnforced(accessFlag);
        }
    }

%>

<script Language="JavaScript">
    function onMultipartsApply() {
        onApply();
        var result = PTC.wizard.checkRequired(true, true, getCurrentStep());
        //Fixed SPR 2156604. Checking whether required attribute validation returns true or false.
        if (result) {
            setActiveStep(steps[0]);
            setNextStepDirty();
            disableOkButton();
            setStepClean(steps[1]);
            getMainForm().reset();
            clearActionFormData();
            resetOkButton();
        } else {
            setActiveStep(steps[1]);
        }
    }

    function onMultipartsBack() {
        var stepIndex = 0;
        var currentStepName = "";
        wizardSteps[currentStepStrName].afterServerVK = null;
        goBack();
        disableOkButton();
        stepIndex = findStepIndex(currentStepStrName);
        stepIndex = stepIndex + 1;
        currentStepName = steps[stepIndex];
        wizardSteps[currentStepName].afterServerVK = "nameNumberValidation";
        PTC.jca.state.getLocalStateStore().clear("multiPartWizAttributesTableDescriptor-grid-state");
    }

    // 提交自定义函数
    function onSubmitCustom() {
        // 参数校验
        if (null == document.getElementById("manufacturer_model") || null == document.getElementsByTagName("input")) {
            // 该函数为系统默认的函数
            onSubmit();
            return;
        }
        // 发送http请求
        var http = new XMLHttpRequest();
        var url = "/Windchill/ptc1/ext/mt/mpnModelUniqueCheck";
        var model = document.getElementById("manufacturer_model").value;
        var tds = document.getElementsByTagName("input");
        for (var i = 0; i < tds.length; i++) {
            // 获取制造商组织信息
            var td = tds[i];
            if (td.id.indexOf('organizationReference') > 0 && td.type != 'hidden') {
                var mpnOrg = td.value
            }
        }
        var params = 'mpnOrg=' + encodeURIComponent(mpnOrg) + '&mpnModel=' + encodeURIComponent(model);
        console.log(params);
        http.open('POST', url, true);
        http.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        http.send(params);
        // 监听返回结果
        http.addEventListener('load', function () {
            var res = http.response;
            // 制造商型号唯一性确认，校验规则：制造商+型号唯一
            if (res == "") {
                // 唯一直接保存
                onSubmit();
            } else {
                // 不唯一则拦截
                var confirmResult = alert(res);
            }
        })
    }

    // 页面一启动加载相关的函数
    PTC.onReady(function () {
        // 加载超级BOM的属性和相关的数据
        createChildElementByTable('superBomName', 'pp', "超级BOM名称", '<%=partName %>');
        createChildElementByTable('superBomVersion', 'pp', "版本信息", '<%=partVersion %>');
        // 在属性表格加载之后通过js函数进行二次加载并将当前对象的属性赋值到相关对象中
        PTC.attributePanel.on("afterRender", function () {
            // 新增相关属性
            let genericTypeElement = document.getElementById("genericType");
            if (genericTypeElement) {
                // 将这属性默认设置为是
                for (let i = 0; i < genericTypeElement.options.length; i++) {
                    if (genericTypeElement.options[i].value === "dynamic") {
                        genericTypeElement.options[i].selected = true;
                        break;
                    }
                }
                // 设置父节点为隐藏
                genericTypeElement.parentNode.parentNode.style.display = 'none';
            }
            // 将值回填
            const isInheritClassEle = document.getElementById("isInheritClassification");
            if (isInheritClassEle && "true" === isInheritClassEle.value) {
                // 物料类型
                const materialTemplateEle = document.getElementById("material_template");
                if (materialTemplateEle) {
                    materialTemplateEle.value = '<%=materialTemplate%>';
                }
                // 存货类别
                const materialTypeEle = document.getElementById("material_type");
                if (materialTypeEle) {
                    materialTypeEle.value = '<%=materialType%>';
                }
                // 来源
                const sourceEle = document.getElementById("source");
                if (sourceEle) {
                    sourceEle.value = '<%=source%>';
                }
                // 单位
                const defaultUnitEle = document.getElementById("defaultUnit");
                if (defaultUnitEle) {
                    defaultUnitEle.value = '<%=defaultUnit%>';
                }
                // 更改类别
                const change_categoryEle = document.getElementById("change_category");
                if (change_categoryEle) {
                    change_categoryEle.value = 'NC';
                }
                // 是否维修件
                const isServicePartEle = document.getElementById("isServicePart");
                if (isServicePartEle) {
                    isServicePartEle.value = '<%=isServicePart%>';
                }
                // 物料英文描述
                const materialDescriptionInEnglishEle = document.getElementById("material_description_in_English");
                if (materialDescriptionInEnglishEle) {
                    materialDescriptionInEnglishEle.value = '<%=materialDescriptionInEnglish%>';
                }

                setTimeout(function () {
                    const inputs = document.querySelectorAll('input');
                    // 遍历并打印每个input元素
                    inputs.forEach(function (input) {
                        if (input.name.indexOf('Classification') > -1 && input.name.indexOf('Classification~~NEW') > -1
                            && input.name.indexOf('+null___textboxAltField') === -1) {
                            if (input.type === "hidden") {
                                //alert('1:input.value=:' + input.value + " input.name=" + input.name);
                                // 分类内部名称
                                input.value = '<%=classification%>';
                            }
                        }
                        if (input.name.indexOf('Classification') > -1
                            && input.name.indexOf('~objectHandle~partHandle~!_col_Classification___textbox') > -1) {
                            if (input.type === "text") {
                                //alert('2:input.value=:'+input.value + " input.name=" + input.name);
                                // 分类显示名称
                                input.value = '<%=classificationDisplayName%>';
                            }
                        }
                    });
                }, 200);
                // 初始化设置完成之后，设置false，防止后面修改
                isInheritClassEle.value = "false";
            }
            createChildElementByAttributePanel('optionSet', 'attributePanel-group-panel')
        });
    });

    /**
     * 创建属性列表中的子元素
     * @param tableClassName 属性列表的类名
     * @param optionSetId 创建的选项集ID
     */
    function createChildElementByAttributePanel(optionSetId, tableClassName) {
        let optionSet = document.getElementById(optionSetId);
        if (optionSet == null) {
            let table = document.getElementsByClassName(tableClassName);
            let tbody = table[0].querySelector("tbody");
            const tr = document.createElement('tr');
            // 增加必填*号
            const tdRisk = document.createElement('td');
            tdRisk.classList.add("attributePanel-asterisk");
            tdRisk.innerText = '*';
            // 增加属性名
            const tdKey = document.createElement('td');
            tdKey.classList.add("attributePanel-label");
            tdKey.innerText = '选择集名称:';
            // 增加属性值和增加必填class
            const tdValue = document.createElement('td');
            tdValue.classList.add("attributePanel-value");
            let input = document.createElement('input');
            input.id = optionSetId;
            input.name = optionSetId;
            input.type = 'text';
            input.classList.add("required");
            input.onchange = () => {
                let optionSetNameInput = document.getElementById(optionSetId);
                const currentUrl = new URL(window.location);
                console.log(currentUrl)
                currentUrl.searchParams.set('optionSetName', optionSetNameInput.value);
                window.history.replaceState(null, '', currentUrl);
            };
            tdValue.appendChild(input)
            tr.appendChild(tdRisk);
            tr.appendChild(tdKey);
            tr.appendChild(tdValue);
            // 将 tr 添加到 tbody
            tbody.appendChild(tr);
        }
    }


    /**
     * 创建首页的超级BOM版本和名称信息
     * @param tableClassName 属性列表的类名
     * @param elementId 新增的行id
     * @param keyDisplayName 展示key
     * @param keyDisplayValue 展示Value
     */
    function createChildElementByTable(elementId, tableClassName, keyDisplayName, keyDisplayValue) {
        let htmlElement = document.getElementById(elementId);
        if (htmlElement == null) {
            let table = document.getElementsByClassName(tableClassName);
            let tbody = table[0].querySelector("tbody");
            const tr = document.createElement('tr');
            const ppKey = document.createElement('td');
            ppKey.classList.add("pplabel");
            let label = document.createElement('label')
            label.innerText = keyDisplayName;
            ppKey.appendChild(label);
            const ppData = document.createElement('td');
            ppData.classList.add("ppdata");
            let dataLabel = document.createElement('label')
            dataLabel.id = elementId;
            dataLabel.innerText = keyDisplayValue;
            ppData.appendChild(dataLabel);
            tr.appendChild(ppKey);
            tr.appendChild(ppData);
            // 将 tr 添加到 tbody
            tbody.appendChild(tr);
        }
    }
</script>
