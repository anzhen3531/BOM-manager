Ext.apply(ATO.components.ChoiceComponent, new Ext.util.Observable());

//variables for resourcebundle.
var optionResource = null;
var choiceComponentResource = null;

//global variables
ATO.components.ChoiceComponent.EFFECTIVITY  =  'EFFECTIVITY';
ATO.components.ChoiceComponent.ENABLERULE   =  'ENABLERULE';
ATO.components.ChoiceComponent.INCLUDERULE  =  'INCLUDERULE';
ATO.components.ChoiceComponent.SELECTED_OPTION_CHOICES = "selectedOptionChoices";
ATO.components.ChoiceComponent.CLEAR_OPTIONS = "clearOptions";
ATO.components.ChoiceComponent.ALL_CHOICES_FOR_OPTION  =  'allchoicesForOption';
ATO.components.ChoiceComponent.CHOICE_CLASS = 'com.ptc.windchill.option.model.Choice';
ATO.components.ChoiceComponent.PANEL_ID = "choiceSelPanel";
ATO.components.ChoiceComponent.ID = '_choiceComponent';
ATO.components.ChoiceComponent.TABLE_PANEL_ID = '_tablePanel';
ATO.components.ChoiceComponent.activtabid = "";
ATO.components.ChoiceComponent.customFormGetterRegistered = false;

ATO.components.ChoiceComponent.validateRequiredOptionsPrefValue = false;
ATO.components.ChoiceComponent.initParams = {};
ATO.components.ChoiceComponent.tables  =  [];
ATO.components.ChoiceComponent.supportExclusion = false;
ATO.components.ChoiceComponent.cacheKey = "ChoiceComponent_"  + Math.random();

ATO.components.ChoiceComponent.firedComponentRenderedEvent = false;
ATO.components.ChoiceComponent.tableRenderedCount = 0;
ATO.components.ChoiceComponent.tabCount = 0;
ATO.components.ChoiceComponent.loadMask = null;
ATO.components.ChoiceComponent.rulesSessionId = -1;
var affectedChoices = [];

ATO.components.ChoiceComponent.resetConfigSpec = function() {
    var optionFilterConfigSpec = Ext.getDom("optionFilterConfigSpec");
    if(optionFilterConfigSpec) {
        optionFilterConfigSpec.value = optionFilterConfigSpec.getAttribute("defaultvalue");
    }
};

ATO.components.ChoiceComponent.setRuleConfigCallback = function(jsonconfig) {
    var params = {
        choicecompaction  : 'choicecomp.reinit',
        optionFilterConfigSpec : jsonconfig,
        callback : function(rulesEngineResult) {
            var resultstatus = rulesEngineResult.RESULT_STATUS;
            if(resultstatus === "SUCCESS" || resultstatus === "0") { //success
                ATO.components.ChoiceComponent.refreshTable(null, true);
                var optionFilterConfigSpec = Ext.getDom("optionFilterConfigSpec");
                if(optionFilterConfigSpec) {
                    optionFilterConfigSpec.value = jsonconfig;
                }
            }
        }
    };
    ATO.components.ChoiceComponent.callRulesEngineAction(params, false);
};

ATO.util.AssignExpressionToggleURL = function(url) {
    url += (url.indexOf("?") > 0) ? "&" : "?";
    url += "expressionModeToggled=true";
    var postForm = getWindow().document.createElement("FORM");
    postForm.name = "assignOptionalityForm";
    postForm.method = "POST";
    postForm.action = url;
    getWindow().document.body.appendChild(postForm);

    //push the call back to the new window,
    PTC.getMainWindow().PTC.cat.WizardHelper.setCallbackAfterWindowReload(window);
    postForm.submit();
    return true;
};

ATO.util.AssignExpressionToggleOnClick = function(link) {
    if(!link.hasAssignment || link.hasAssignment ==  "false") {
        return ATO.util.AssignExpressionToggleURL(link.goTo);
    }
    Ext.MessageBox.show({
        title  : '',
        msg    : link.message,
        buttons: {
            ok     : bundleHandler.get('com.ptc.core.ui.navigationRB.okButton'),
            cancel : bundleHandler.get('com.ptc.core.ui.navigationRB.cancelButton')
        },
        fn     : function(button) {
            if(button == 'ok') {

                return ATO.util.AssignExpressionToggleURL(link.goTo);
            }
        },
        icon   : Ext.MessageBox.WARNING
    });

};

ATO.components.DateComponent = Ext.extend(Ext.form.DateField,  {
    initComponent : function(){
        ATO.components.DateComponent.superclass.initComponent.call(this);
        var componentRB  = new ATO.util.ResourceBundle({
            resource     : 'com.ptc.core.ui.componentRB'
        });
        var optionResource  = new ATO.util.ResourceBundle({
            resource     : 'com.ptc.windchill.option.optionResource'
        });
        var speceditorResource  = new ATO.util.ResourceBundle({
            resource     : 'com.ptc.windchill.option.variantspec.speced.speceditorResource'
        });
        if(!this.menu || this.menu === null) { //create own menu
            var MONTHS = [componentRB.get('MON_JAN'),componentRB.get('MON_FEB'),componentRB.get('MON_MAR'),componentRB.get('MON_APR'),
                                   componentRB.get('MON_MAY'),componentRB.get('MON_JUN'),componentRB.get('MON_JUL'),componentRB.get('MON_AUG'),
                                   componentRB.get('MON_SEP'),componentRB.get('MON_OCT'),componentRB.get('MON_NOV'),componentRB.get('MON_DEC')];

            var DAYS =  [componentRB.get('WEEK_SUN'),componentRB.get('WEEK_MON'),componentRB.get('WEEK_TUE'),componentRB.get('WEEK_WED'),
                                   componentRB.get('WEEK_THU'),componentRB.get('WEEK_FRI'),componentRB.get('WEEK_SAT')];
            this.menu = new Ext.menu.DateMenu({
                hideOnClick   : false,
                focusOnSelect : false,
                pickerId      : this.id + '_datepicker',
                format        : speceditorResource.get('EXT_DATEENTRY_FORMAT'),
                monthNames    : MONTHS,
                dayNames      : DAYS,
                todayText     : optionResource.get('datePicker_todayText'),
                todayTip      : optionResource.get('datePicker_todayTip'),
                prevText      : optionResource.get('datePicker_prevText'),
                nextText      : optionResource.get('datePicker_nextText'),
                okText        : optionResource.get('datePicker_okText'),
                cancelText    : optionResource.get('datePicker_cancelText'),
                monthYearText : optionResource.get('datePicker_monthYearText')
            });
        }
        this.invalidText = "'{0}' " +speceditorResource.get('INVALID_EXT_DATEENTRY', [this.emptyText]);

        //freeup memory
        componentRB = null;
        optionResource = null;
        speceditorResource = null;
    },
    safeParse : function(value, format) {
        this.defaultUseStrict = Date.useStrict;
        Date.useStrict = (this.useStrict) ? this.useStrict : Date.useStrict;
        var fvalue = ATO.components.DateComponent.superclass.safeParse.call(this, value, format);
        Date.useStrict = this.defaultUseStrict; //reset
        return fvalue;
    }

});
Ext.reg('ato-datecomponent', ATO.components.DateComponent);

ATO.components.ChoiceComponent.isEffectivityDateChanged  = function(){
    var dfield = Ext.getCmp("dateinputfield");
    return (dfield) ? dfield.isDirty() : false;
};

ATO.components.ChoiceComponent.getEffectivityDateValue = function(){
    var dfield = Ext.getCmp("dateinputfield");

    // To fix D-08682 When User clears the date field manually by
    // deleting contents effectivity then dfield.value will not be set
    // as it is not a valid value.
    // Also it is safe to use this here as call will come here only after
    // validation Hence it is ensured raw value is correct. Thus when user
    // delets effectivity empty value will be returned by this API and
    // choices will be enabled back
    return (dfield) ? dfield.getRawValue(): "";
};

ATO.components.ChoiceComponent.showLoadMask = function() {
    if (!('progressMessage' in window) || progressMessage === '') {
        progressMessage = bundleHandler.get('com.ptc.core.ui.navigationRB.LOADING');
    }
    var config = {
        msg: progressMessage
    };
    var comp = Ext.get('com.ptc.windchill.option.choicecomp.div');
    if(comp) {
        ATO.components.ChoiceComponent.loadMask = new Ext.LoadMask(comp, config);
        ATO.components.ChoiceComponent.loadMask.show();
    }
};

ATO.components.ChoiceComponent.hideLoadMask = function() {
    if(ATO.components.ChoiceComponent.loadMask) {
        ATO.components.ChoiceComponent.loadMask.hide();
    }
};

ATO.components.ChoiceComponent.showProgress = function() {
    var componentDiv = Ext.getDom("com.ptc.windchill.option.choicecomp.progress");
    if (componentDiv) {
        componentDiv.innerHTML = '<div style="background: url(netmarkets/javascript/ext/resources/images/default/shared/blue-loading.gif) no-repeat center center; height: 100px">&nbsp;</div>';
    }
};

ATO.components.ChoiceComponent.stopProgress = function() {
    var componentDiv = Ext.getDom("com.ptc.windchill.option.choicecomp.progress");
    if (componentDiv) {
        componentDiv.innerHTML = "";
    }
};

ATO.components.ChoiceComponent.initChoiceComponent = function (createNewSession, needInitialSelections) {
    ATO.components.ChoiceComponent.showProgress();
    var params = new JCAClone(ATO.components.ChoiceComponent.params); //clone the config
    if(needInitialSelections === true) {
        params.actionType = 'initialSelections';
        ATO.components.ChoiceComponent.clearUserSelections();
    }

    if(createNewSession) {
        params.choicecompaction = 'choicecomp.init';
        params.callback = function(rulesEngineResult) {
            ATO.components.ChoiceComponent.params.rulesSessionId = rulesEngineResult.sessionId;
            params.rulesSessionId = rulesEngineResult.sessionId;
            Ext.getDom("rulesSessionId").value = rulesEngineResult.sessionId;
            if(needInitialSelections) {
                params.actionType = 'initialSelections';
                PTC.atoChoiceSel.invokeChoiceSelectionController(params, ATO.components.ChoiceComponent.initChoiceComponentCallBack, true);
            } else {
                ATO.components.ChoiceComponent.stopProgress();
                params = new JCAClone(ATO.components.ChoiceComponent.params); //clone the config
                ATO.components.ChoiceComponent.renderChoiceComponent(params);
            }
        };
        ATO.components.ChoiceComponent.callRulesEngineAction(params);
    } else if(needInitialSelections) {
        PTC.atoChoiceSel.invokeChoiceSelectionController(params,ATO.components.ChoiceComponent.initChoiceComponentCallBack, true);
    }
};

ATO.components.ChoiceComponent.initChoiceComponentCallBack = function(rulesEngineResult) {
    var wasSuccessful = ATO.components.ChoiceComponent.isSuccess(rulesEngineResult);
    ATO.components.ChoiceComponent.handleInitialSelections(rulesEngineResult);
    var params = new JCAClone(ATO.components.ChoiceComponent.params); //clone the config
    ATO.components.ChoiceComponent.stopProgress();
    ATO.components.ChoiceComponent.renderChoiceComponent(params);
};

ATO.components.ChoiceComponent.renderTableCallBack = function(tabitem) {
    var config = tabitem.tableConfig;
    var table = tableUtils.getTable(config.tableUniqueID);
    table.initRequestParams = config; //add this for paged table params
    table.ownerTab = config.ownerTab;
    table.typeIdentity = config.typeIdentity;
    ATO.components.ChoiceComponent.tables.push(table);
};

ATO.components.ChoiceComponent.hideEmptyTab = function(grid) {
    var store = grid.store;
    var count = store.getCount();
    if(count === 0) {
        var tabPanel = Ext.getCmp('com.ptc.windchill.option.choicecomp.main.tabs');
        var tabItem = Ext.getCmp(grid.ownerTab);
        if(tabItem)  {
            tabPanel.hideTabStripItem(tabItem);
            if(tabPanel.getActiveTab().id == tabItem.id) {
                var hiddenTabIdx = 1;
                var tabItems = tabPanel.items;
                var i=0;
                tabPanel.items.each(function(item) {
                    if(item.id == tabItem.id) {
                        hiddenTabIdx = i;
                    }
                    i++;
                });
            }
        }
    }
};

ATO.components.ChoiceComponent.isSuccess = function(rulesEngineResult) {
    var resultstatus = rulesEngineResult.RESULT_STATUS;
    var isSuccess = true;
    ATO.components.ChoiceComponent.loadResourceBundles();
    if(resultstatus == "FAILURE" || resultstatus == "1") { //failure
        var dlg = Ext.MessageBox.show({
            title  : choiceComponentResource.get('ERRORWINDOW_TITLE'),
            msg    : rulesEngineResult.STATUS_MESSAGE,
            minWidth : 500,
            buttons: {
                ok     : bundleHandler.get('com.ptc.core.ui.navigationRB.okButton')
            },
            icon   : Ext.MessageBox.ERROR
        });
        isSuccess = false;
    }
    return isSuccess;
};

ATO.components.ChoiceComponent.closeRulesSession = function() {
    if(ATO.components.ChoiceComponent.rulesSessionId == -1) {
        return;
    }
    var params = {
        choicecompaction  : 'choicecomp.dispose',
        callback : function(rulesEngineResult) {
            var resultstatus = rulesEngineResult.RESULT_STATUS;
            if(resultstatus == "SUCCESS" || resultstatus == "0") { //success
                ATO.components.ChoiceComponent.rulesSessionId = -1;
            } else if(rulesEngineResult.STATUS_MESSAGE) {
                wfalert(rulesEngineResult.STATUS_MESSAGE);
            }
        }
    };
    ATO.components.ChoiceComponent.callRulesEngineAction(params, false);
};

ATO.components.ChoiceComponent.clearUserSelections = function() {
    PTC.util.tableDataManager.removeAll(ATO.components.ChoiceComponent.params.tableId);
};

ATO.components.ChoiceComponent.handleDisableRuleCheckBox = function(cbox, checked) {
    //reset min max (used in configure wizard)
    if (!ATO.components.ChoiceComponent.isSecuredAction()) {
    var minmax = Ext.getDom('xmaminmaxconstraint');

    if(minmax) {
        minmax.value = !checked;
    }

    //set rulesEnabled field in DOM
    var rulesEnabled = Ext.getDom("rulesEnabled");
    var enabledBoolean = !checked;
    ATO.components.ChoiceComponent.params.rulesEnabled = enabledBoolean;
    if(!rulesEnabled || rulesEnabled === null) {
        appendFormHiddenInput($("com.ptc.windchill.option.choicecomp.div"), "rulesEnabled", enabledBoolean, true);
    } else {
        rulesEnabled.value=enabledBoolean;
    }

    ATO.components.ChoiceComponent.disableCSOREffectivityPicker(checked);

    if(ATO.components.ChoiceComponent.isRuleCheckingEnabled() === true) {
        if(ATO.components.ChoiceComponent.getSelectedChoices(null,true).length === 0) {
            ATO.components.ChoiceComponent.startRulesSession();
        } else {
            Ext.MessageBox.show({
                title: optionResource.get ('CONFIRM_TITLE'),
                msg: optionResource.get ('REENABLERULECHECKING_CONFIRMMSG'),
                icon: Ext.MessageBox.QUESTION,
                buttons: {
                    ok: bundleHandler.get('com.ptc.core.ui.navigationRB.okButton'),
                    cancel: bundleHandler.get('com.ptc.core.ui.navigationRB.cancelButton')
                },
                fn: function(btn) {
                    if(btn != 'ok') {
                        cbox.setValue(true);
                    } else {
                        ATO.components.ChoiceComponent.startRulesSession();
                    }
                }
            });
        }
    }
    }
};

ATO.components.ChoiceComponent.startRulesSession = function() {
    ATO.components.ChoiceComponent.clearUserSelections();
    // call the rules engine to reset all choices and refresh the table
    var params = {
        choicecompaction  : 'choicecomp.reset',
        callback          : ATO.components.ChoiceComponent.refreshTable
    };
    ATO.components.ChoiceComponent.callRulesEngineAction( params );
};

ATO.components.ChoiceComponent.disableCSOREffectivityPicker = function(checked) {
    var datefield = Ext.getCmp("dateinputfield");
    if(datefield) {
        datefield.setDisabled(checked);
        datefield.reset();
    }
    // Only clear the date field when re-enabling rules.
    // need to ensure that the handleEffectivity("") call is made when
    // checking the disableRulesCheckbox as this ultimately is what
    // stores the user selections
    if(ATO.components.ChoiceComponent.isRuleCheckingEnabled() === false) {
        ATO.components.ChoiceComponent.handleEffectivity("");
    }

    var csAction = Ext.getCmp("launchConfigSpec");
    if(csAction) {
        csAction.setDisabled(checked);
    }
};

ATO.components.ChoiceComponent.callRulesEngineAction = function(params, async) {
    if(async === undefined || async === null){
        async = true;
    }
    var requestParams = {
        rulesSessionId: ATO.components.ChoiceComponent.rulesSessionId,
        updateOptions: "true"
    };

    Ext.apply(requestParams, params);

    //call rules engine
    ATO.components.ChoiceComponent.showLoadMask();
    return requestHandler.doRequest('ptc1/choiceCompSession', {
        parameters: requestParams,
        asynchronous: async,
        onSuccess: function( response ) {
            var responsetxt = response.responseText;
            var rulesEngineResult = Ext.decode(responsetxt.trim());
            retvalue = ATO.components.ChoiceComponent.isSuccess(rulesEngineResult);
            ATO.components.ChoiceComponent.hideLoadMask();
            if(requestParams.callback) {
                requestParams.callback(rulesEngineResult);
            }
        },
        onFailure: function(response) {
            ATO.components.ChoiceComponent.hideLoadMask();
        }
    });
};

/*
 * Method to call the server to get all selected choices froms session to populate 
 *  
 */
ATO.components.ChoiceComponent.getChoiceSelectionsFromSession = function() {
    var params = {
            actionType: 'currentSelections',
            rulesSessionId: ATO.components.ChoiceComponent.params.rulesSessionId,
    };
    var options = {
        asynchronous: false,
        parameters: Ext.urlEncode(params)
    };
    var request = requestHandler.doRequest('ptc1/ato.choiceSelectionData', options);
    var data = Ext.util.JSON.decode(request.responseText);
    var selectedOptionChoices = [];
    if (data && data.choices) {
        for(var j=0; j<data.choices.length; j++) {
            var choice = {
                    choiceId : ATO.util.handleSpecialChars(data.choices[j].id),
                    name : ATO.util.handleSpecialChars(data.choices[j].choice_number),
                    number : ATO.util.handleSpecialChars(data.choices[j].choice_name),
                    selectionStatus : true,
                    excludeStatus : false
            };
            selectedOptionChoices.push(choice);
      }
  }
      return selectedOptionChoices;
};

ATO.components.ChoiceComponent.getSelectedChoices = function(optionOid, skipRuleSessionSelections) {
    var selectedOptionChoices = [];
    if ( skipRuleSessionSelections !== true && ATO.components.ChoiceComponent.isRuleCheckingEnabled() === true ) {
        return ATO.components.ChoiceComponent.getChoiceSelectionsFromSession();
    }
    else {
        if(optionOid) {
            selectedOptionChoices = PTC.util.tableDataManager.get(ATO.components.ChoiceComponent.params.tableId, optionOid, ATO.components.ChoiceComponent.SELECTED_OPTION_CHOICES);
        } else {
            var optionOids = PTC.util.tableDataManager.getOids(ATO.components.ChoiceComponent.params.tableId);
            for(var i=0, len=optionOids.getCount(); i<len; i++) {
                var oid = optionOids.keys[i];

                var selectedChoices = PTC.util.tableDataManager.get(ATO.components.ChoiceComponent.params.tableId, oid, ATO.components.ChoiceComponent.SELECTED_OPTION_CHOICES);

                if(selectedChoices && selectedChoices.choices) {
                    var choiceLen = selectedChoices.choices.length;
                    for(var j=0; j<choiceLen; j++) {
                        var choice = selectedChoices.choices[j];
                        if(selectedChoices[choice].selectionStatus === true) {
                            selectedOptionChoices.push(choice);
                        }
                    }
                }
            }
        }
    }
    return selectedOptionChoices;
};

/**
* Gets the user selection for filter type from choice component
**/

ATO.components.ChoiceComponent.getFilterMode = function(){
    var combobox = Ext.getCmp('_filterModeSelector');
    if (combobox) {
       return combobox.getValue();
    }
    //The default value for filter type is 1 hence default to 1 if filtermode is hidden or no filter type has been selected.
    return 1;
};

/**
 * Stores the user selection into a json store which is then serialized into the form data when the form is serialized or submitted.
 */
ATO.components.ChoiceComponent.storeSelection = function(checkbox, checked, tableIdParam){
    var tableId = tableIdParam;
    if(!tableId || tableId === null) {
        tableId  = ATO.components.ChoiceComponent.params.tableId;
    }

    if(ATO.components.ChoiceComponent.isRuleCheckingEnabled() === false) {
        PTC.util.tableDataManager.registerTableListener(tableId);
        var choiceOid = checkbox.oid;
        var choiceName = checkbox.choice_name;
        var choiceNumber = checkbox.choice_number;
        var optionOid = checkbox.optionOid;
        var selectedOptionChoices = PTC.util.tableDataManager.get(tableId, optionOid, ATO.components.ChoiceComponent.SELECTED_OPTION_CHOICES);
        if(selectedOptionChoices === null) {
            selectedOptionChoices = {
                choices : []
            };
        }

        if(selectedOptionChoices.choices.indexOf(choiceOid) === -1) {
            selectedOptionChoices.choices.push(choiceOid);
            selectedOptionChoices[choiceOid] = {};
        }

        // First check excluded status for initially selected case. Exclude status should otherwise be undefined.
        var choiceExcludeStatus = checkbox.excludeStatus;
        if(choiceExcludeStatus === null || choiceExcludeStatus === undefined) {
            var excludedOptions = ATO.components.ChoiceComponent.getExcludedOptions();
            choiceExcludeStatus = excludedOptions.indexOf(optionOid) > -1;
        }

        selectedOptionChoices[choiceOid] = {
            name : ATO.util.handleSpecialChars(choiceName),
            number : ATO.util.handleSpecialChars(choiceNumber),
            selectionStatus : checked,
            excludeStatus : choiceExcludeStatus
        };
        PTC.util.tableDataManager.put(tableId, optionOid, ATO.components.ChoiceComponent.SELECTED_OPTION_CHOICES, selectedOptionChoices);
    }
};

ATO.components.ChoiceComponent.getExcludedOptions = function() {
    if(!ATO.components.ChoiceComponent.excludedOptions) {
        ATO.components.ChoiceComponent.excludedOptions = [];
    }
    return ATO.components.ChoiceComponent.excludedOptions;
};

ATO.components.ChoiceComponent.excludeOption = function(checkEvent, optionOid) {
    var excludedOptions = ATO.components.ChoiceComponent.getExcludedOptions();
    //fix SPR 2226589: ""exclude checkbox is not retained on reopening the assign expression dialog"" issue
    //checkEvent.srcElement works for Chrome and IE, checkEvent.target works for Firefox
    var target = checkEvent.srcElement||checkEvent.target;
    var isChecked = (checkEvent && target) ?  target.checked : false;
    if(isChecked === false && excludedOptions.indexOf(optionOid) > -1){
        excludedOptions.splice(excludedOptions.indexOf(optionOid));
    } else if(isChecked) {
        excludedOptions.push(optionOid);
    }

    var selectedOptionChoices = PTC.util.tableDataManager.get(ATO.components.ChoiceComponent.params.tableId, optionOid, ATO.components.ChoiceComponent.SELECTED_OPTION_CHOICES);
    if(selectedOptionChoices !== null) {
        var choiceLen = selectedOptionChoices.choices.length;
        for(var i=0; i<choiceLen; i++) {
            var choice = selectedOptionChoices.choices[i];
            selectedOptionChoices[choice].excludeStatus = isChecked;
        }
        PTC.util.tableDataManager.put(ATO.components.ChoiceComponent.params.tableId, optionOid, ATO.components.ChoiceComponent.SELECTED_OPTION_CHOICES, selectedOptionChoices);
    }
};

ATO.components.ChoiceComponent.refreshTable = function(response, forceRefresh) {
    if(response) {
        ATO.components.ChoiceComponent.storeUserSelections(response);
    }
    if(forceRefresh === true || (ATO.components.ChoiceComponent.isSuccess(response) === true && response.hasAffectedChoices === true)) {
        refreshCurrentElementID(tableUtils.getFormattedTableId(ATO.components.ChoiceComponent.params.tableId));
    }

    if(PTC.atoChoiceSel.currentOptionOid !== null){
        PTC.atoChoiceSel.selectOptionChoices(PTC.atoChoiceSel.currentOptionOid);
    }
};

/**
  The refreshOnTVMCloseFunction is implemented in order that the edit filter is not closed when creating or modifying table views.
*/
refreshOnTVMCloseFunction = function(tableId) {
    refreshCurrentElementID(tableUtils.getFormattedTableId(tableId));
};

/**
 * 保存用户选择数据
 * @param response 响应
 * @param isInitialSelections 是否是初始化选择
 * @param isSelected 是否选择
 */
ATO.components.ChoiceComponent.storeUserSelections = function(response, isInitialSelections, isSelected) {
    if(isSelected !== false) {
        isSelected = true;
    }
    if(response.updateUserSelections !== null && response.updateUserSelections !== undefined) {
        var len = response.updateUserSelections.length;
        if(ATO.components.ChoiceComponent.initParams.initTableId === null || ATO.components.ChoiceComponent.initParams.initTableId === undefined) {
            ATO.components.ChoiceComponent.initParams.initTableId = ATO.components.ChoiceComponent.params.tableId + "_initial";
        }
        for(var i=0; i<len; i++) {
            var selection = response.updateUserSelections[i];
            if(isInitialSelections && isInitialSelections === isSelected) {
                ATO.components.ChoiceComponent.storeSelection(selection, isSelected, ATO.components.ChoiceComponent.params.tableId);
                ATO.components.ChoiceComponent.storeSelection(selection, isSelected, ATO.components.ChoiceComponent.initParams.initTableId);
            } else {
                // 保存选择函数
                ATO.components.ChoiceComponent.storeSelection(selection, isSelected);
            }
        }
    }
};

ATO.components.ChoiceComponent.handleInitialSelections = function(response) {
    var decodedResult = response;
    if(response.responseText && response.responseText !== null) {
        var responsetxt = response.responseText;
        decodedResult = Ext.decode(responsetxt.trim());
    }
    ATO.components.ChoiceComponent.storeUserSelections(decodedResult, true);
};

ATO.components.ChoiceComponent.handleEffectivity = function(dateStr) {
    var params = {
       choicecompaction  : 'choicecomp.effectivitychange',
       callback          : ATO.components.ChoiceComponent.refreshTable,
       effectiveDate     : dateStr,
       rulesEnabled      : ATO.components.ChoiceComponent.isRuleCheckingEnabled()
    };
    ATO.components.ChoiceComponent.callRulesEngineAction( params );
};

ATO.components.ChoiceComponent.getElementById = function(id) {
    var elem = $(id);
    if(!elem) { // if not found try to get it from getWindow.
        elem = getWindow().document.getElementById(id);
    }
    return elem;
};

ATO.components.ChoiceComponent.isRuleCheckingEnabled = function () {
    var enabled = false;
    var disableRuleChkBox = ATO.components.ChoiceComponent.getElementById("_disableRuleChkingChkBox");
    if(disableRuleChkBox === null) {
        enabled = ATO.components.ChoiceComponent.initParams.rulesEnabled;
    } else {
        enabled = disableRuleChkBox.checked !== true;
    }
    if(enabled === null || enabled === undefined) {
        enabled = false;
    }
    return enabled;
};

// destroy choice component.
ATO.components.ChoiceComponent.destroy = function() {
    var choicecomp = Ext.getCmp('com.ptc.windchill.option.choicecomp.main');
    if(choicecomp) {
        var dom = Ext.getDom('com.ptc.windchill.option.choicecomp.main');
        //for some reason when we close using 'x' in the EditFilter UI, dom comes as null.
        if(dom) {
            PTC.util.purge(dom);
        }
        choicecomp.destroy();
    }

    //reset global variables
    ATO.components.ChoiceComponent.params = {};
    ATO.components.ChoiceComponent.loadMask = null;
    ATO.components.ChoiceComponent.tables  = [];
    ATO.components.ChoiceComponent.validateRequiredOptionsPrefValue = false;
    ATO.components.ChoiceComponent.initParams = {};
};

ATO.components.ChoiceComponent.renderToolBar = function(config) {

    //load resource bundles
    ATO.components.ChoiceComponent.loadResourceBundles();

    //toolbar & load items
    var toptoolbar = new Ext.Toolbar({
        autoShow : true,
        //id: config.toolbarId,//'com.ptc.windchill.option.choicecomp.main.toolbar',
        hidden : !config.displayTopPanel,
        listeners  : {
            afterRender  : function(toolbar) {
                //load tbar with items
                ATO.components.ChoiceComponent.renderToolBarItems(toolbar, config);
            }
        }
    });
    return toptoolbar;
};
ATO.components.ChoiceComponent.isConfigureWiz = function(){
    return  ($('CONFIGURE_MODE')) ? true : false;
};
ATO.components.ChoiceComponent.renderToolBarItems = function(toolbar, config){
    Ext.Ajax.request({
        url: 'ptc1/choiceCompTBar',
        params: config,
        success: function( response ) {
            var responsetxt = response.responseText;
            var tbarItems = Ext.decode(responsetxt.trim());
            for(var i=0;i<tbarItems.length;i++) {
                var item = tbarItems[i];
                if(item.id == "_disableRuleChkingChkBox") {
                    item.listeners = {
                        check : ATO.components.ChoiceComponent.handleDisableRuleCheckBox,
                        afterrender : function(chkbox) {
                            chkbox.suspendEvents();
                            chkbox.setValue(chkbox.checkedstatus);
                            chkbox.resumeEvents();
                        }
                    };
                } else if(item.id == "dateinputfield") {
                    item.maskRe = new RegExp('/[a-z]/');
                    item.listeners = {
                        select : function(datefield, date)  {
                            //convert the date into this format, use the same format to parse this back
                            var dateStr = (date) ? date.format('Y-m-d') : "";
                            ATO.components.ChoiceComponent.handleEffectivity(dateStr);
                        },
                        blur : function(datefield) {
                            if(!datefield.isValid()) { //if not valid, clear the value.
                                datefield.setValue("");
                            }
                            var date = datefield.getValue();
                            if(!date || date === null) {
                                ATO.components.ChoiceComponent.handleEffectivity("");
                            }
                        }
                    };
                } else if ( item.id == "optionsetoverridelink") {
                    item.listeners = {
                        click : function(icon) {
                            ATO.components.ChoiceComponent.showOverrideWindow({optionsetname : icon.optionsetname, versionInfo : icon.versionInfo});
                        }
                    };
                } else if ( item.id == "undooverridelink") {
                    item.listeners = {
                        click : function(icon) {
                            var request = {
                                parameters: { 'oid' : icon.optionsetid, 'method' : 'undoOverrideOptionSet' },
                                asynchronous: false,
                                onSuccess: function(response){
                                    if(ATO.components.ChoiceComponent.isConfigureWiz ||
                                        ATO.components.ChoiceComponent.isGWT !== true) {
                                        ATO.components.ChoiceComponent.updateContextOid();
                                    } else {
                                        ATO.components.ChoiceComponent.renderOptionFilterTab();
                                    }
                                },
                                onFailure: function(transport, ipe) {
                                    alert('Error communication with the server: ' + transport.responseText.stripTags());
                                }
                            };
                            requestHandler.doRequest('ptc1/choiceCompInfoSvr', request);
                            ATO.components.ChoiceComponent.resizeTabPanel();
                            ATO.components.ChoiceComponent.resetConfigSpec();
                        }
                    };
                } else if(item.id == "optionsetinfolink") {
                    item.listeners = {
                        click : function(icon) {
                            window.open(icon.url);
                        }
                    };
                } else if(item.id == "assignExpressionLink") {
                    item.listeners = {
                        click : function(link) {
                            ATO.util.AssignExpressionToggleOnClick(link);
                        }
                    };
                } else if(item.id == "launchConfigSpec") {
                    item.listeners = {
                        click : function() {
                            if (typeof launchOptionsFilterConfigSpecUI == 'function') {
                                var hidden = Ext.getDom("optionFilterConfigSpec");
                                var csStr = (hidden) ? hidden.value : null;
                                launchOptionsFilterConfigSpecUI(csStr, ATO.components.ChoiceComponent.setRuleConfigCallback);
                            }
                        }
                    };
                } else if(item.id == "_filterModeSelector") {
                    item.listeners = {
                        render : function(combo) {
                            if (!combo.elMetrics) {
                                combo.elMetrics = Ext.util.TextMetrics.createInstance(combo.getEl());
                            }
                            var m = combo.elMetrics;
                            var width = 0;
                            var el = this.el;
                            combo.store.each(function (r) {
                                var text = r.get('displayValue');
                                width = Math.max(width, m.getWidth(text), combo.defaultTriggerWidth);
                            });
                            if (el) {
                                width += el.getBorderWidth('lr');
                                width += el.getPadding('lr');
                            }
                            if (combo.trigger) {
                                width += this.trigger.getWidth();
                            }
                            //set the width
                            combo.listWidth = width;
                            combo.setWidth(width);
                            combo.setEditable(false);
                        }
                    };
                }
            }
            toolbar.removeAll();
            toolbar.add(tbarItems);
            toolbar.doLayout();
            ATO.components.ChoiceComponent.renderTabs();
        }
    });
};

ATO.components.ChoiceComponent.refreshToolBar = function(config) {
    var mainPanel = Ext.getCmp('com.ptc.windchill.option.choicecomp.main');
    var toolbar = mainPanel.getTopToolbar();
    ATO.components.ChoiceComponent.renderToolBarItems(toolbar, config);
};

ATO.components.ChoiceComponent.loadResourceBundles = function() {
    if(optionResource === undefined || optionResource === null) {
        //load the resources
    optionResource  = new ATO.util.ResourceBundle({
        resource     : 'com.ptc.windchill.option.optionResource'
    });
    }

    if(choiceComponentResource === undefined || choiceComponentResource === null) {
       choiceComponentResource  = new ATO.util.ResourceBundle({
          resource     : 'com.ptc.windchill.option.choicecomponent.choiceComponentResource'
       });
    }
};

ATO.components.ChoiceComponent.renderTable = function(tabitem) {
    // Clone table config
    var params = {};
    Ext.apply(params, ATO.components.ChoiceComponent.params);
    params.typeIdentity = tabitem.tableConfig.typeIdentity;
    params.rulesEnabled = ATO.components.ChoiceComponent.isRuleCheckingEnabled();
    var tableDataParams = PTC.util.tableDataManager.getSerializedParams();
    Ext.apply(params, tableDataParams);

    var tableParams = {
        url: 'ptc1/comp/choicecomponent.table',
        params : params,
        scripts : true
    };
    tabitem.load(tableParams);
    var newTypeIdentity = tabitem.tableConfig.typeIdentity;
    var typeIdentity = Ext.getDom("typeIdentity");
    if(!typeIdentity || typeIdentity === null) {
        appendFormHiddenInput($("com.ptc.windchill.option.choicecomp.div"), "typeIdentity", newTypeIdentity, true);
    } else {
        typeIdentity.value=newTypeIdentity;
    }
};

ATO.components.ChoiceComponent.getID = function(panel) {
    return panel.id + ATO.components.ChoiceComponent.ID;
};

ATO.components.ChoiceComponent.getCmp = function(panel) {
    return Ext.getCmp(ATO.components.ChoiceComponent.getID(panel));
};

ATO.components.ChoiceComponent.loadTab = function(panel) {
    ATO.components.ChoiceComponent.activtabid = panel;
    ATO.components.ChoiceComponent.renderTable(ATO.components.ChoiceComponent.getTablePanel(panel));
    ATO.components.ChoiceComponent.getCmp(panel).add(new Ext.Panel({
        id: ATO.components.ChoiceComponent.PANEL_ID,
        border: false,
        padding : 5,
        baseCls: 'x-window-plain',
        parentId: ATO.components.ChoiceComponent.getID(panel)
    }));
};

ATO.components.ChoiceComponent.clearTab = function(panel) {
    ATO.components.ChoiceComponent.clearSearchField();
    ATO.components.ChoiceComponent.getTablePanel(panel).load({url: 'templates/htmlcomp/Empty.html'});
    var choiceCmp = ATO.components.ChoiceComponent.getCmp(panel);
    choiceCmp.removeAll();
    choiceCmp.setTitle('');
    choiceCmp.collapse();
};

ATO.components.ChoiceComponent.getTablePanelId = function(panel) {
    return panel.id + ATO.components.ChoiceComponent.TABLE_PANEL_ID;
};

ATO.components.ChoiceComponent.getTablePanel = function(panel) {
    return Ext.getCmp(ATO.components.ChoiceComponent.getTablePanelId(panel));
};


ATO.components.ChoiceComponent.getCurrentTabTablePanel = function() {
    var tabId = ATO.components.ChoiceComponent.getChoiceTypeIdentity() + ".tab";
    return ATO.components.ChoiceComponent.getTablePanel({id: tabId});
};

ATO.components.ChoiceComponent.renderPanelContext = function ( response ) {
    var responsetxt = response.responseText;
    var json = Ext.decode(responsetxt.trim());
    var tabItems = json.tabs;
    var tabpanel = Ext.getCmp('com.ptc.windchill.option.choicecomp.main.tabs');

    //get the active table from the cache;
    var cookieKey = ATO.components.ChoiceComponent.cacheKey + ".activetab";
    ATO.components.ChoiceComponent.activtabid = ATO.components.ChoiceComponent.getCookieValue(cookieKey);
    var defaultTabId;
    var itemsLength = tabItems.length;
    ATO.components.ChoiceComponent.tabCount = itemsLength;
    for(var i=0;i<itemsLength;i++) {
        var config = tabItems[i];
        var tabConfig = new JCAClone(tabpanel.choiceCompInitConfig);
        tabConfig.typeIdentity = config.typeIdentity;
        tabConfig.tableUniqueID = tabConfig.tableId;
        tabConfig.ownerTab = config.id;
        tabpanel.add({
            title: config.title,
            xtype : 'panel',
            id: config.id,
            layout : 'border',
            tableConfig : tabConfig,
            bodyStyle: 'background: white;',
            listeners  : {
                activate : ATO.components.ChoiceComponent.loadTab,
                beforehide : ATO.components.ChoiceComponent.clearTab
            },
            items: [{
                xtype: 'panel',
                id : ATO.components.ChoiceComponent.getTablePanelId(config),
                region: 'center',
                tableConfig : tabConfig,
                bodyStyle: 'background: white;',
                listeners  : {
                    bodyresize : ATO.components.ChoiceComponent.resizeTable
                }
            },{
                xtype: 'panel',
                region: 'east',
                title: '',
                id: ATO.components.ChoiceComponent.getID(config),
                collapsed : true,
                collapsible: true,
                floatable: false,
                bodyStyle: 'background: white; white-space: nowrap; border: 1px #AAAAAA solid;',
                autoScroll: true,
                split: true,
                splitBarRegistered: false,
                width : '30%',
                minSize: 200,
                listeners : {
                    expand: function(panel){
                        if(panel.splitBarRegistered === false){
                            panel.splitBarRegistered = true;
                            panel.ownerCt.layout.east.split.dd.onMouseUp = function(){
                                ATO.components.ChoiceComponent.resizeTabPanel();
                            };
                        }
                    }
                }
            }]
        });
        if(i===0) {
            defaultTabId = config.id;
        }
    }

    if(!ATO.components.ChoiceComponent.activtabid) {
        ATO.components.ChoiceComponent.activtabid = defaultTabId;
    }

    var atoFilterTab = Ext.getDom("expansioncriteria.tabpanel.main__ato.filterOptionTab");
    var isActive = false;
    if(!isWizardWindow() && atoFilterTab) {
        isActive = atoFilterTab.className.indexOf("-active") > 0;
    }
    if(isWizardWindow() || isActive === true) {
        ATO.components.ChoiceComponent.activateTab(tabpanel);
    }

    ATO.components.ChoiceComponent.supportExclusion = json.supportExclusion;

    if(tabpanel) {
        tabpanel.show();
    }
    ATO.components.ChoiceComponent.hideLoadMask();
};

ATO.components.ChoiceComponent.activateLastActiveTab = function(){
    var tabpanel = Ext.getCmp('com.ptc.windchill.option.choicecomp.main.tabs');
    if(tabpanel) {
        ATO.components.ChoiceComponent.activateTab(tabpanel);
        ATO.components.ChoiceComponent.resizeTabPanel();
    }
};

ATO.components.ChoiceComponent.activateTab = function(tabpanel) {
    if(tabpanel === undefined) {
        return;
    }
    if(tabpanel.items.getCount() === 1) {
        ATO.components.ChoiceComponent.activtabid = tabpanel.items.get(0).id;
    }
    tabpanel.activate(ATO.components.ChoiceComponent.activtabid);
};

ATO.components.ChoiceComponent.renderChoiceComponent = function (config) {

    //load the resources
    ATO.components.ChoiceComponent.loadResourceBundles();

    ATO.components.ChoiceComponent.tabCount = 0;
    ATO.components.ChoiceComponent.firedComponentRenderedEvent = false;
    ATO.components.ChoiceComponent.tableRenderedCount=0;
    ATO.components.ChoiceComponent.rulesSessionId = (config.rulesSessionId) ? config.rulesSessionId : -1;
    ATO.components.ChoiceComponent.cacheKey=config.tableId;
    ATO.components.ChoiceComponent.validateRequiredOptionsPrefValue = config.validateRequiredOptionsPrefValue;

    //set the initial params to a global variable
    ATO.components.ChoiceComponent.initParams = config;
    ATO.components.ChoiceComponent.initParams.initTableId = config.tableId + "_initial";

    // tab panels
    var tabpanel = new Ext.TabPanel({
        id                    : "com.ptc.windchill.option.choicecomp.main.tabs",
        choiceCompInitConfig  : config,
        height : '100%',
        listeners  : {
            tabchange  : function(tabpanel, tabitem) {
                //cache tab
                var cookieKey = ATO.components.ChoiceComponent.cacheKey + ".activetab";
                ATO.components.ChoiceComponent.setCookie(cookieKey, tabitem.id);
            }
        }
    });

    ATO.components.ChoiceComponent.showLoadMask();

    var mainpanel = new Ext.Panel({
        id : 'com.ptc.windchill.option.choicecomp.main',
        renderTo: config.renderToContainer,
        frame: true,
        layout: 'fit',
        tbar : ATO.components.ChoiceComponent.renderToolBar(config),
        items: tabpanel
    });
};

ATO.components.ChoiceComponent.renderTabs = function(){
    //render tabs
    Ext.Ajax.request({
        url: 'ptc1/choiceCompTabs',
        params: ATO.components.ChoiceComponent.initParams,
        success: ATO.components.ChoiceComponent.renderPanelContext
    });
};

ATO.components.ChoiceComponent.resizeTable = function( tablePanel, width, height ) {
    var currentTablePanel = ATO.components.ChoiceComponent.getCurrentTabTablePanel();

    if(!currentTablePanel) {
        return;
    }
    var tableId = currentTablePanel.tableConfig.tableUniqueID;
    var table =  (tableId) ? tableUtils.getTable(tableId) : null;
    if(table && table.getEl()) {
        //set the content area for the table, required by JCA
        table.clearStickyConfig();
        table.alwaysSetHeight=true;
        var tableEl = table.getEl();
        PTC.jca.table.scroll.setContentArea(currentTablePanel, tableEl);
        PTC.jca.table.scroll.updateTableSize(tableId, false, currentTablePanel.getWidth(), true);

        var tabId = ATO.components.ChoiceComponent.getChoiceTypeIdentity() + ".tab";
        var groupcmb = Ext.getCmp(tabId + tableId + ".groupcombo");
        if(groupcmb) {
            groupcmb.setSize(groupcmb.listWidth, groupcmb.getHeight());
        }

        var filterModeSelector = Ext.getCmp("_filterModeSelector");
        if(filterModeSelector) {
            filterModeSelector.setSize(filterModeSelector.listWidth, filterModeSelector.getHeight());
        }
        //searchfield
        var searchField = Ext.getCmp(tableId + ".searchInListTextBox");
        if(searchField) {
            searchField.resetWidth(120);
        }

       //hide the selection count in the bottom toolbar, SPR 2219855
        var tbar = table.getBottomToolbar();
        if(tbar) {
            var item = tbar.getComponent('selectionMessageCount');
            if(item) {
                item.setVisible(false);
            }
        }
    }
};

ATO.components.ChoiceComponent.resizeTabPanel = function() {
    var mainpanel = Ext.getCmp("com.ptc.windchill.option.choicecomp.main");
    if(!mainpanel) { return; }
    var root = Ext.get('com.ptc.windchill.option.choicecomp.div');
    if(root) {
        var newHeight = root.getHeight();
        var newWidth = root.getWidth();
        mainpanel.setSize(newWidth, newHeight);
    }

    var dateinputfield = Ext.getCmp("dateinputfield");
    if(dateinputfield) {
        dateinputfield.wrap.dom.style.width = dateinputfield.width + 'px';
    }
};

ATO.components.ChoiceComponent.isPreviewUI = function() {
    return ATO.components.ChoiceComponent.params.tableId.indexOf("optioncomponent.previewoptionset.table") !== -1;
};

ATO.components.ChoiceComponent.showOverrideWindow = function(param) {

    // create a version store
    var vstore = new Ext.data.JsonStore({
        fields: ['id', 'display'],
        sortInfo: {field: 'display', direction: 'ASC'},
        data : param.versionInfo
    });

    // version combo
    var cmbversion = new Ext.form.ComboBox( {
            id:'com.ptc.windchill.option.choicecomponent.override.revision',
            editable: false,
            displayField:'display',
            mode: 'local',
            triggerAction: 'all',
            lazyRender:true,
            store : vstore,
            emptyText: optionResource.get ('SELECTVERSION')
        }
    );

    // override window
    var overridewin = new Ext.Window({
        id:'com.ptc.windchill.option.choicecomponent.override.dlg',
        width:450,
        height:150,
        layout:'table',
        layoutConfig: {
            columns: 2
        },
        title : optionResource.get ('TITLE_OVERRIDEWINDOW'),
        buttons: [{
            text: optionResource.get ('OK_BUTTON_LABEL'),
            handler: function(){
                var idx = cmbversion.selectedIndex;
                if(idx == -1) {
                    window.alert(optionResource.get('ERRNOVERSIONSELECTED'));
                    return;
                }
                var selectedOid = cmbversion.store.getAt(idx).get("id");
                if(ATO.components.ChoiceComponent.isPreviewUI() === true){
                    ATO.components.ChoiceComponent.updateContextOid(selectedOid);
                } else {
                    var request = {
                        parameters: { 'oid' : selectedOid, 'method' : 'overrideOptionSet' },
                        asynchronous: false,
                        onSuccess: function(response){
                            ATO.components.ChoiceComponent.updateContextOid();
                        },
                        onFailure: function(transport, ipe) {
                            alert('Error communication with the server: ' + transport.responseText.stripTags());
                        }
                    };
                    requestHandler.doRequest('ptc1/choiceCompInfoSvr', request);
                }
                ATO.components.ChoiceComponent.resizeTabPanel();
                ATO.components.ChoiceComponent.resetConfigSpec();
                // call the service to override
                overridewin.close();
            }
        }, {
            text: optionResource.get ('CANCEL_BUTTON_LABEL'),
            handler: function(){
                overridewin.close();
            }

        }]
    });

    // add all items to the window.
    overridewin.add({xtype:'label', text : optionResource.get ('TEMPLATE_DROPDOWN_LABEL'), style:'font-weight:bold; padding-right:5px;' });
    overridewin.add({xtype:'label', text : param.optionsetname });
    overridewin.add({xtype:'label', text : optionResource.get ('VERSION_DROPDOWN_LABEL'),style:'font-weight:bold; padding-right:5px;' });
    overridewin.add(cmbversion);

    // show window.
    overridewin.show();
};

/**
 * Update the contextOid param to the passed in oid.  If it is undefined, then this function
 * will close the rules session and restart a new one with the default oid.
 */
ATO.components.ChoiceComponent.updateContextOid = function(selectedOid) {
    if(selectedOid) {
        if(selectedOid.indexOf("OR:") === -1){
            selectedOid = "OR:" + selectedOid;
        }
        var contextOid = Ext.getDom("contextOid");
        if(contextOid) {
            contextOid.value = selectedOid;
        }
        ATO.components.ChoiceComponent.params.contextOid = selectedOid;
    }
    ATO.components.ChoiceComponent.closeRulesSession();
    ATO.components.ChoiceComponent.initChoiceComponent(true, true);
};

ATO.components.filterByGroup = function(combo, record, index) {
    var table = Ext.getCmp(combo.tableId);
    var tablestore = table.store;
    if(!record) { return; }


    var optionComponentGroupId = record.get('id');

    tablestore.suspendEvents(false);

    var items = ATO.util.getFilteredAndNonFilteredItems(table);
    for(var i=0,len=items.length; i<len; i++) {
        var row = items[i];
        var isHidden = row.get('rowHidden');
        var groupId = row.get('optionComponentGroupId');
        if (optionComponentGroupId !== 'all' && optionComponentGroupId !== groupId) {
            if (!isHidden) {
                row.set('rowHidden', true);
                row.set('filteredByGroup', true);
            }
        } else if (isHidden === true && row.data.filteredByGroup === true) {
            row.set('rowHidden', false);
            row.set('filteredByGroup', false);
        }
    }

    tablestore.resumeEvents();

    tablestore.filterBy(function(record, id) {
        var isHidden = record.get('rowHidden');
        if (isHidden === true) {
            return false;
        }
        return true;
    });

    //clear search and view selected only checkbox
    if(!ATO.components.ChoiceComponent.renderingGroupComponent){
        var tableId = combo.tableId;
        var searchField = Ext.getCmp(tableId + ".searchInListTextBox");
        searchField.onTrigger2Click();
    }

    //cache the selected group
    ATO.components.ChoiceComponent.setCookie(combo.id, optionComponentGroupId);
    ATO.components.ChoiceComponent.renderOptionSelections(table);
    ATO.components.ChoiceComponent.renderingGroupComponent = false;
};


ATO.components.ChoiceComponent.renderGroupCombo = function(table) {
    var toolbar = Ext.getCmp(table.id + ".toolBar");

    if(!toolbar) { return; }

    // get the groups for the dropdown
    var groups = [];
    var tablestore = table.store;
    if(!tablestore) {
        return;
    }
    var len = tablestore.getTotalCount();
    for(i=0;i<len;i++) {
        var record = tablestore.getAt(i);
        if(!record) { continue; }
        var group = record.get('optionComponentGroupHidden');
        var optionComponentGroupId = record.get('optionComponentGroupId');
        var data = { group : group, id : optionComponentGroupId , internalname : '1'+group};
        if(group && groups.indexOf(data) === -1) {
            groups.push(data);
        }

    }
    groups.push( {group : optionResource.get('LABELALL'), id : 'all', internalname : '0'+optionResource.get('LABELALL')} );
    var gstore = new Ext.data.JsonStore({
        fields: ['group', 'id', 'internalname'],
        sortInfo: {field: 'internalname', direction: 'ASC'},
        data : groups
    });

    var tabId = ATO.components.ChoiceComponent.getChoiceTypeIdentity() + ".tab";
    var groupCacheKey;
    var groupcombo = new Ext.form.ComboBox({
        tableId :  table.id,
        editable: false,
        id : tabId + table.id +  ".groupcombo",
        fields: [
            'id',
            'internalname', //used for sorting. do not use this , use 'group' field for all purposes
            'group'
        ],
        displayField:'group',
        typeAhead: true,
        mode: 'local',
        triggerAction: 'all',
        emptyText: optionResource.get ('SELECTGROUP'),
        defaultTriggerWidth : 120,
        store : gstore,
        listeners  : {
            select  : ATO.components.filterByGroup,
            render : function(combo) {
                if (!combo.elMetrics) {
                    combo.elMetrics = Ext.util.TextMetrics.createInstance(combo.getEl());
                }
                var m = combo.elMetrics;
                var width = 0;
                var el = this.el;
                combo.store.each(function (r) {
                    var text = r.get('group');
                    width = Math.max(width, m.getWidth(text), combo.defaultTriggerWidth);
                });
                if (el) {
                    width += el.getBorderWidth('lr');
                    width += el.getPadding('lr');
                }
                if (combo.trigger) {
                    width += this.trigger.getWidth();
                }
                //set the width
                combo.listWidth = width;
                combo.setWidth(width);
            }
        },

        selectOnFocus:true
    });

    toolbar.insert(toolbar.items.length, groupcombo);
    toolbar.doLayout();
};

ATO.components.ChoiceComponent.setCookie = function(name, value) {
    // number of days by which the cookie expires default 1 day
    var ndays = 1*24*60*60*1000;
    var expires = -1;
    var d = new Date();
    d.setTime( d.getTime()+ ndays );
    expires = d.toGMTString();

    var cookie = name + "=" + value + ";";
          cookie += " expires=" + expires +";";
    document.cookie = cookie;
};

ATO.components.ChoiceComponent.getCookieValue = function (cookieName) {
    var value = "";
    if(document.cookie) {
        var cookies = document.cookie.split(";");
        for(var i=0;i<cookies.length;i++) {
            if(cookies[i].indexOf(cookieName) != -1 ) {
                var cookiePair = cookies[i].split("=");
                value = cookiePair[1];
                break;
            }
        }
    }

    return value;
};

//clear search and view selected only checkbox
ATO.components.ChoiceComponent.clearSearchField = function() {
    var tableId = ATO.components.ChoiceComponent.params.tableId;
    var searchField = Ext.getCmp(tableId + ".searchInListTextBox");
    if(searchField) {
        searchField.setValue("");
        searchField.onTrigger1Click();
    }
};

/**
    Renders the group component and updates the selected group value from the cache.
 */
ATO.components.ChoiceComponent.renderGroupComponent = function(table) {
    //get the cache value
    var tabId = ATO.components.ChoiceComponent.getChoiceTypeIdentity() + ".tab";
    ATO.components.ChoiceComponent.renderGroupCombo(table);
    ATO.components.ChoiceComponent.renderingGroupComponent = true;
    var groupcombo = Ext.getCmp(tabId + table.id + ".groupcombo");
    if(groupcombo){
    var optionComponentGroupId = ATO.components.ChoiceComponent.getCookieValue(groupcombo.id);
    var recordIdx = groupcombo.store.findExact('id', optionComponentGroupId);
    if(recordIdx != -1) {
        groupcombo.selectedIndex = recordIdx;
        var rec = groupcombo.store.getAt(recordIdx);
        groupcombo.setValue(rec.get('group'));
        groupcombo.fireEvent('select', groupcombo, rec, recordIdx);
    }
    }
};

/**
    @return True when the displayed table for the selected option type has one or more selected choices for any given option
    in the table.
*/
ATO.components.hasSelectedChoices = function(table) {
    var hasSelectedChoices = false;
    var items = ATO.util.getFilteredAndNonFilteredItems(table);
    for(var i=0, len=items.length; i<len; i++) {
        var record = items[i];
        if(record.get('hasSelectedChoices') === 'true') {
            hasSelectedChoices = true;
            break;
        }
    }
    return hasSelectedChoices;
};

ATO.components.ChoiceComponent.clearAll =  function (event, table) {
    if(ATO.components.hasSelectedChoices(table) === false) {
        return;
    }

    Ext.MessageBox.show({
        title: optionResource.get ('CONFIRM_TITLE'),
        msg: optionResource.get ('CONFIRM_MSG'),
        icon: Ext.MessageBox.QUESTION,
        buttons: {
            ok: bundleHandler.get('com.ptc.core.ui.navigationRB.okButton'),
            cancel: bundleHandler.get('com.ptc.core.ui.navigationRB.cancelButton')
        },
        fn: function(btn) {
            if(btn == 'ok') {
                ATO.components.ChoiceComponent.handleClearAll(event, table);
            }
        }
    });
};

ATO.components.ChoiceComponent.handleClearAll = function (event, table) {
    if(ATO.util.getFilteredAndNonFilteredItems(table).length === 0){
        return;
    }
    if(ATO.components.ChoiceComponent.isRuleCheckingEnabled() === false) {
        //iterate through table store 
        var items = ATO.util.getFilteredAndNonFilteredItems(table);
        var len = items.length;
        var impactedOptions = new Ext.util.MixedCollection();
        var oids = [];
        for(var i=0; i<len; i++) {
            var oid = items[i].get('oid');
            impactedOptions.add(ATO.components.ChoiceComponent.createOption(oid));
            oids.push(oid);
        }
        PTC.util.tableDataManager.remove(ATO.components.ChoiceComponent.params.tableId, oids);
        ATO.components.ChoiceComponent.updateOptions(impactedOptions);

        // update the panel/ui
        if(PTC.atoChoiceSel.currentOptionOid !== null){
            PTC.atoChoiceSel.selectOptionChoices(PTC.atoChoiceSel.currentOptionOid);
        }
    } else { // if not then make a call to the server and clear all the choices
        var params = {
           choicecompaction  : 'choicecomp.clearall',
           callback          : ATO.components.ChoiceComponent.refreshTable,
           typeIdStr         : table.typeIdentity
        };
        ATO.components.ChoiceComponent.callRulesEngineAction( params );
    }
};

ATO.components.ChoiceComponent.createOption = function(optionOid) {
    return {
        oid : optionOid,
        allSelected : 'false',
        hasSelectedChoices : 'false',
        selectedChoices : [],
        selectedChoices_name : [],
        selectedChoices_number : [],
        selectedChoices_desc : [],
        selectedChoices_dataValue : []
    };
};

/**
    Updates the option table with the selected choices in the selection component that were made by the user and any impacted choices returned from the rules engine.
    @optionsToUpdate: options to update
*/
ATO.components.ChoiceComponent.updateOptions = function(optionsToUpdate){
    var table = tableUtils.getTable(ATO.components.ChoiceComponent.params.tableId);
    var store = table.store;
    var items = ATO.util.getFilteredAndNonFilteredItems(table);

    var recordIndex = {};
    for(var rIdx=0, rlen=items.length; rIdx < rlen; rIdx++) {
        var record = items[rIdx];
        var recordOid = record.get('oid');
        recordIndex[recordOid] = record;
    }

    var selectedChoiceColumns = ['allSelected', 'hasSelectedChoices', 'selectedChoices', 'selectedChoices_name', 'selectedChoices_number', 'selectedChoices_desc', 'selectedChoices_dataValue'];
    var numOfColumns = selectedChoiceColumns.length;

    store.suspendEvents(false);

    for(var i=0, len=optionsToUpdate.getCount(); i < len; i++) {
        var option = optionsToUpdate.get(i);
        var optionRecord = recordIndex[option.oid];
        if(optionRecord !== null && optionRecord !== undefined) {
            for(var colIdx=0; colIdx < numOfColumns; colIdx++) {
                var columnName = selectedChoiceColumns[colIdx];
                var selectedChoices = "";
                if(columnName === 'allSelected' || columnName === 'hasSelectedChoices') {
                    selectedChoices = option[columnName];
                } else {
                    selectedChoices = option[columnName].join("<br>");
                }

                optionRecord.set(columnName, selectedChoices);
                optionRecord.commit();
            }
        }
    }
    store.resumeEvents();
    // update table display
    var v = table.view;
    v._cache = {};
    v.doUpdate(true);
    ATO.components.ChoiceComponent.resizeTable();
};


/**
    @returns: the current type for the selected tab
*/
ATO.components.ChoiceComponent.getChoiceTypeIdentity = function(){
    var typeIdentity = "";
    var typeObj = Ext.getDom("typeIdentity");
    if(typeObj !== null && typeObj !== undefined){
        var typeVal = typeObj.value;
        if(typeVal !== null || typeVal !== undefined || typeVal !== ""){
            typeIdentity = typeVal;
        }
    }
    return typeIdentity;
};

/**
    Updates the option table with the selected choices in the selection component that were made by the user and any impacted choices returned from the rules engine.
    @impactedChoices: choices to update
*/
ATO.components.ChoiceComponent.updateOptionsFromChoices = function(impactedChoices){
    var optionsToUpdate = ATO.components.ChoiceComponent.getOptionsToUpdate(impactedChoices);
    ATO.components.ChoiceComponent.updateOptions(optionsToUpdate);
};

ATO.components.ChoiceComponent.getOptionsToClear = function(impactedChoices) {
    var optionsToUpdate = new Ext.util.MixedCollection();
    if(impactedChoices.containsKey(ATO.components.ChoiceComponent.CLEAR_OPTIONS) === true) {
        var optionsToClear = impactedChoices.get(ATO.components.ChoiceComponent.CLEAR_OPTIONS);
        var optionsToClearLen = optionsToClear.length;
        for(var i=0; i < optionsToClearLen; i++) {
            var optionOid = optionsToClear[i];
            optionsToUpdate.add(optionOid, ATO.components.ChoiceComponent.createOption(optionOid));
        }

        impactedChoices.remove(ATO.components.ChoiceComponent.CLEAR_OPTIONS);
    }
    return optionsToUpdate;
};

/**
    Converts the choices to update into options to update with a list choice display information.
    @impactedChoices: choices to update
*/
ATO.components.ChoiceComponent.getOptionsToUpdate = function(impactedChoices) {
    var optionsToUpdate = ATO.components.ChoiceComponent.getOptionsToClear(impactedChoices);
    //get the number of all choices under the option not the impacted choices, in order to solve SPR 2240611
    var choiceNum = Ext.getCmp(ATO.components.ChoiceComponent.PANEL_ID).items.length;

    for(var i=0, len=impactedChoices.length; i < len; i++) {
        var choice = impactedChoices.get(i);
        if(optionsToUpdate.containsKey(choice.optionOid) === false) {
            var emptyOption = ATO.components.ChoiceComponent.createOption(choice.optionOid);
            emptyOption.allSelected = null;
            optionsToUpdate.add(choice.optionOid, emptyOption);
        }
        var option = optionsToUpdate.get(choice.optionOid);
        if(option !== null && option !== undefined) {
            option.hasSelectedChoices = 'true';
        }
        if(choice.name_desc !== null && choice.name_desc !== undefined) {
            option.selectedChoices.push(choice.name_desc);
        }
        if(choice.choice_name !== null && choice.choice_name !== undefined) {
            option.selectedChoices_name.push(choice.choice_name);
        }
        if(choice.choice_number !== null && choice.choice_number !== undefined) {
            option.selectedChoices_number.push(choice.choice_number);
        }
        if(option && choice.description !== null) {
            option.selectedChoices_desc.push(choice.description === undefined ? "" : choice.description);
        }
        if(choice.dataValue !== null && choice.dataValue !== undefined) {
            option.selectedChoices_dataValue.push(choice.dataValue);
        }
        if(option && option.allSelected === null) {
            //if choiceNum-len is greater than 0, we can conclude the choices under the option is not all selected
            option.allSelected = ((choiceNum-len) > 0) ? 'false' : 'true';
        }
    }

    return optionsToUpdate;
};

ATO.components.ChoiceComponent.checkRequired = function(usePreference) {
    var retvalue = true;
    if (!ATO.components.ChoiceComponent.isSecuredAction()) {
    if(ATO.components.ChoiceComponent.isRuleCheckingEnabled() === true) {
        var validate = (usePreference) ? ATO.components.ChoiceComponent.validateRequiredOptionsPrefValue : true;
        var params = {
            checkRequiredOption : validate,
            callback : function(rulesEngineResult) {
                retvalue = ATO.components.ChoiceComponent.isSuccess(rulesEngineResult);
            },
            choicecompaction  : 'choicecomp.validaterequired'
        };
        ATO.components.ChoiceComponent.callRulesEngineAction(params, false);
    }
    }
    return retvalue;
};

/**
 * Load the loadOptionsFilterTab.jsp and put it into the div ato.filterOptionTab.
 */
ATO.components.ChoiceComponent.renderOptionFilterTab = function(oid, ecid, appName) {
    ATO.components.ChoiceComponent.isGWT = true;
    var formData = getFormData();
    //Remove the optionFilterEcid as the previous one may still be present and it won't be overwritten
    formData = removeParamFromString("optionFilterEcid", formData);
    formData += "&optionFilterEcid=" + ecid;
    formData += "&appName=" + appName;
    if(oid) {
        formData += "&optionContextOid=" + oid;
    }
    getElementHtml(formData, 'ato.filterOptionTab', false, 'netmarkets/jsp/variantspec/loadOptionsFilterTab.jsp', false);
};

/**
    Used to bypass option selection event listeners
*/
ATO.components.ChoiceComponent.ignoreMassSelection = false;

/**
    Renders the initial selections for any option where all choices are currently selected.
*/
ATO.components.ChoiceComponent.renderOptionSelections = function(table) {
    var records = table.store.data;
    var selectedRecords = [];
    var hasSelections = false;
    table.selModel.suspendEvents();
    for(var i=0, len=records.length; i<len; i++) {
        var record = records.get(i);
        var isAllSelected = record.get('allSelected');
        if(isAllSelected === 'true') {
            var ridx = table.store.indexOf(record);
            table.selModel.selectRow(ridx, true, null, record, true);
            hasSelections = true;
        }
    }
    table.selModel.resumeEvents();
    if(hasSelections === true) {
        ATO.components.ChoiceComponent.ignoreMassSelection = true;
        table.selModel.fireEvent('rowmassselection', table.selModel, table.selModel.selections.items);
        ATO.components.ChoiceComponent.ignoreMassSelection = false;
    }
};

/**
 * Overriding method used to keep line breaks in the toolip for the ChoiceComponenet.
 * Reasons for overriding the default tooltip building logic would be multi-line
 * table cells and the want for the tooltip to keep the line-breaks that you see
 * in the cell.
 *
 * Columns:
 * "Choices", "Choice Names", "Choice Descriptions"
 *
 * @param {table} instance of the ChoiceComponent table
 * @returns a method to override the default behavior of table._buildQTipForValue()
 */
ATO.components.ChoiceComponent.buildQTipForValue = function(orig, val, metadata, noEncoding, escape) {
    var multiLineColumns = ['selectedChoices', 'selectedChoices_name', 'selectedChoices_number', 'selectedChoices_desc', 'selectedChoices_dataValue'];

    if (multiLineColumns.indexOf(metadata.id) >= 0) {
        orig(val, metadata, true, false);
    } else {
        orig(val, metadata, noEncoding, escape);
    }
};


/**
*This thing is responsible for handling refreshes to the table after changing views or other reloads.
*
* @class ATO.components.ChoiceComponent.OptionTablePlugin
* @extends Object
* Plugin (ptype = 'OptionTablePlugin')

* @constructor
* @param {Object} config Configuration options
* @ptype OptionTablePlugin
*/
ATO.components.ChoiceComponent.OptionTablePlugin = Ext.extend(Object, {

    constructor: function(config){
        config = config || {};
        Ext.apply(this, config);
    },

    init: function(table){
        ATO.components.ChoiceComponent.initTable = true;
        ATO.components.ChoiceComponent.renderTableCallBack(ATO.components.ChoiceComponent.getCurrentTabTablePanel());
        table.on("render", ATO.components.ChoiceComponent.resizeTabPanel);
        table.on("rowclick", PTC.atoChoiceSel.selectOptionRow);
        table.enableSelectedOnly = false;
        table.on("beforeRender", function(table){
            table.findInTableHiddenColumns = [ATO.components.ChoiceComponent.ALL_CHOICES_FOR_OPTION];
        });
        table.on("datarendered",  function(){
            if (ATO.components.ChoiceComponent.isSecuredAction()) {
                ATO.components.ChoiceComponent.disableToolBarItems();
            }
            //clear search text.
            ATO.components.ChoiceComponent.renderGroupComponent(this);
            ATO.components.ChoiceComponent.initTable = false;
        });

        // to render the group component when user clicks on previous/next link to page within the table.
        table.store.on("dataSourceComplete", function(){
            if(!ATO.components.ChoiceComponent.initTable){
                var rowStore = this;
                var grid = PTC.jca.table.Utils.getTable(rowStore.jscaTableID);
                ATO.components.ChoiceComponent.renderGroupComponent(grid);
                if(ATO.components.ChoiceComponent.enableOptionTableSelections === true) {
                    ATO.components.ChoiceComponent.renderOptionSelections(grid);
                }
            }

        });

        table.store.on("datachanged", function(store){
            // check clear the pane on group filter/search in table
            PTC.atoChoiceSel.checkClearChoiceComponent(false);
            // readjust table size
            ATO.components.ChoiceComponent.resizeTable();
        });

        // clear and close the pane on table view refresh
        PTC.atoChoiceSel.clearChoiceComponent(true);

        // override the default tooltip creation logic in order to preserve line breaks
        table._buildQTipForValue = table._buildQTipForValue.wrap(ATO.components.ChoiceComponent.buildQTipForValue);
    }
});
Ext.preg('optionTablePlugin', ATO.components.ChoiceComponent.OptionTablePlugin);

ATO.components.ChoiceComponent.enableOptionTableSelections = false;

/**
* A table plugin handler for registering and initiating a option table which supports options selections.
*
* @class ATO.components.ChoiceComponent.OptionTableSelectionPlugin
* @extends Object
* Plugin (ptype = 'OptionTableSelectionPlugin')

* @constructor
* @param {Object} config Configuration options
* @ptype OptionTableSelectionPlugin
*/
ATO.components.ChoiceComponent.OptionTableSelectionPlugin = Ext.extend(Object, {

    constructor: function(config){
        config = config || {};
        Ext.apply(this, config);
    },

    init: function(table){
        PTC.jca.ConfirmViewChangeIgnoreSelections = true;
        ATO.components.ChoiceComponent.enableOptionTableSelections = true;

        // override prevent context menus from showing at all
        PTC.menu.onContextMenu = function(grid, rowIndex, evt){
            if (!evt.hasModifier()) {
                // Prevent the browsers context menu.
                evt.stopEvent();
            }
        };

        var selModel = table.getSelectionModel();
        if(selModel !== null || selModel !== undefined) {
            table.on("datarendered",  ATO.components.ChoiceComponent.renderOptionSelections);
            selModel.on('rowmassselection', PTC.atoChoiceSel.option.selectAllHandler);
            selModel.on('rowmassdeselection', PTC.atoChoiceSel.option.deselectAllHandler);
            selModel.on('rowselect', PTC.atoChoiceSel.option.selectionHandler);
            selModel.on('rowdeselect', PTC.atoChoiceSel.option.deselectionHandler);
        }
    }
});

/**
    Options should not be unselected during export or when saving views.
*/
PTC.jca.TableGrid.prototype.onAfterAction = PTC.jca.TableGrid.prototype.onAfterAction.wrap(function (orig, formResult) {
    if(ATO.components.ChoiceComponent.enableOptionTableSelections === true) {
        if(formResult) {
            if ((formResult.actionName === 'saveAsView' || formResult.actionName === 'saveView') && formResult.extraData) {
                var viewName = formResult.extraData.viewName;
                if (viewName) {
                    PTC.jca.table.saveAsViewResponseHandler(this.id, viewName);
                }
            }
        }
        return false;
    } else {
        return orig(formResult);
    }
});

Ext.preg('optionTableSelectionPlugin', ATO.components.ChoiceComponent.OptionTableSelectionPlugin);

PTC.onReady(function() {
    if(isWizardWindow()) {
        Ext.EventManager.onWindowResize(ATO.components.ChoiceComponent.resizeTabPanel);
    }
    window.onunload = ATO.components.ChoiceComponent.closeRulesSession;
});

/**
 * This is a handler for the GWT Events.Resize from the OptionTabRenderer.
 * It will adjust the size of the choice component if it is not a wizard
 * window (edit filter).
 */
ATO.components.ChoiceComponent.GWTOptionTabResize = function() {
    ATO.components.ChoiceComponent.resizeTabPanel();
};

ATO.components.ChoiceComponent.unloadEditFilter = function() {
    if(!isWizardWindow()) {
        ATO.components.ChoiceComponent.closeRulesSession();
        // clear any config params
        ATO.components.ChoiceComponent.destroy();
    }
};

ATO.components.ChoiceComponent.getSummaryPanel = function(parentContainer) {
    var summary = null;
    var listSummary = Ext.DomQuery.select("[id='com.ptc.windchill.option.choicecomp.main.summary']", parentContainer );
    if(listSummary && listSummary.length > 0) {
        summary = listSummary[0];
    }
    return summary;
};

// this summary panel is used in Edit EC UI and Mini-info page
ATO.components.ChoiceComponent.renderSummaryPanel = function(elementIdToAttach, title, height, hidden) {
    var id = (!elementIdToAttach) ? 'com.ptc.windchill.option.choicecomp.main.summary' : elementIdToAttach + ".summary";
    var summary = new Ext.Panel({
                id : id,
                title: title,
                hidden : hidden,
                autoScroll : true,
                items :[],
                layout:'anchor'// arrange items in columns
    });
    if( title !== null && title !== "" ) {
        summary.setTitle(title); // title not set for mini-info page
    }

    if( height !== null && height !== "" ) {
        summary.setHeight(height); // height not set for mini-info page
    }

    if(elementIdToAttach !== null ) {
        summary.render(elementIdToAttach);
    }
    summary.doLayout();
    return summary;
};

ATO.components.ChoiceComponent.addChoicesToSummary = function(elementIdToAttach, choiceObjs) {
    var summary = ATO.components.ChoiceComponent.getSummaryPanel(elementIdToAttach);
    if(!summary) {
        summary = ATO.components.ChoiceComponent.renderSummaryPanel(elementIdToAttach, null, null, false);
    }

    var choices = Ext.decode(choiceObjs);
    if(!choices) {
        return;
    }
    for(var i=0;i<choices.length;i++) {
        var data = choices[i];
        data.checkedStatus='1';
        ATO.components.ChoiceComponent.updateSummary(data, summary);
    }
};

ATO.components.ChoiceComponent.removeSummaryPanel = function(parentElement) {
    var summary = ATO.components.ChoiceComponent.getSummaryPanel(parentElement);
    if(summary) {
        PTC.util.purge(summary);
        Ext.removeNode(summary);
    }
};

ATO.components.ChoiceComponent.updateSummary = function (data, summary) {
    // get the summary panel
    if(!summary) {
       summary = Ext.getCmp("com.ptc.windchill.option.choicecomp.main.summary");
    }
    if(!summary) {
        return;
    }

    var summaryId = summary.getId();
    var checked = (data.checkedStatus == '1' ||  data.checkedStatus == '2');
    var optionId = data.optionId;
    var opDivId = summaryId + "." + optionId + ".summary";
    var optionVisible = true;


    //check if there is an option added already to summary panel
    var opdiv = summary.get(opDivId);
    if(!opdiv)  { //create a new option label
        opdiv = new Ext.Container({
            id : opDivId,
            choices : [],// array to store the selected choices
            layout : 'anchor',
            style: {
                padding: '0px 2px 0px 2px'//top right bottom left
            },
            listeners : { //SPR 2172385
                show : function(c) {
                    if(c.rendered) {
                        c.el.dom.style.display = 'inline';
                    }
                }
            },
            autoEl: {
                tag: 'span' //overriding default div with span to solve resize issues
            }
        });

        var superScript = "<sup>&nbsp;</sup>";// superscript added to avoid alignment issue when we have choices with dagger
        //check if the option has exclude
        var equalstr = (data.excludeStatus == '1') ? superScript + "&ne;&nbsp;" : superScript + "=&nbsp;";
        // replacing spaces with nbsp in the option name to avoid wrap
        var optionName = data.optionName.replace(/\s/g, "&nbsp;") + equalstr;
        // creating the option label
        var oplabel = opdiv.add({xtype:'label' , html : optionName , style: 'font-size: 12px; font-weight: bold; cursor:pointer'});
        oplabel.on('afterrender', function(label) {
            var element = label.el;
            if(element) {
                element.dom.setAttribute('optionId',optionId);
                element.on('click', function(e, t) {
                    var optionId = t.getAttribute('optionId');
                    ATO.components.ChoiceComponent.scrollTo(optionId);
                });
            }
        });
        // Adding option div to the container
        summary.add(opdiv);
    }

    // Options label is handled by now
    // Handling the choices label now.
    var choiceArr = opdiv.choices;// get the selected choices
    // update the cached selected choices array

    if(!checked) { // this is uncheck, remove the choice from the array
        var indexToRemove = -1;
        for( var jj=0; jj < choiceArr.length; jj++ ) {
            var c = choiceArr[jj];
            if(c.id == data.choiceId) {
                indexToRemove = jj;
                break;
            }
        }
        if(indexToRemove != -1) {// remove the choice the json arr
            choiceArr.splice(indexToRemove,1/*count of items*/);
        }
    } else { //add choice ( new or partial )
        var label2 = data.choiceName, newChoice;
        // replacing spaces with nbsp in the option name to avoid wrap
        label2 = label2.replace(/\s/g, "&nbsp;");

        // check if the choice was already added
        for(var idx = 0; idx < choiceArr.length; idx++) {
            if(choiceArr[idx].id == data.choiceId) {
                newChoice = choiceArr[idx];
                break;
            }
        }
        if(data.isPartialChoice) {
            // add dagger to the label
            label2 += "<sup style=font-family:'serif'>&dagger;</sup>";
        }
        if(newChoice) { // if choice already present, potential label update
            newChoice.value = label2;
        } else {//add the choice to the array
            newChoice = {"id":data.choiceId, "value": label2}; // create a smaller obj to store
            choiceArr.unshift(newChoice);// same as push but places it at the top
        }
    }

    // cached choices array updated now. Update it on option div.
    opdiv.choices = choiceArr;
    // Start of the choices rendering code
    var displayLabel = ""; // Choice display string
    var MAX_LBL_COUNT = 5; // No of choices after which the more link is displayed
    var displayLabelCount = (choiceArr.length < MAX_LBL_COUNT)? choiceArr.length : MAX_LBL_COUNT;
    // constructing the choices dispay string
    for (var i = 0; i < displayLabelCount; i++) {
        displayLabel += (i === 0)? choiceArr[i].value : ",&nbsp;" +choiceArr[i].value;
    }
    // adding the ending semi colon if necessary
    displayLabel += (choiceArr.length <= MAX_LBL_COUNT)? "; " : "&nbsp;";
    // create choices label
    var choicesDivId = opDivId + '_choicesummary';
    var choicesDiv = opdiv.findById(choicesDivId);
    if(!choicesDiv) {
        choicesDiv = opdiv.insert(1,
            {xtype:'label' , id: choicesDivId, style: 'font-size: 12px;', hidden : false});
    }

    // add text to the choices label
    choicesDiv.setText(displayLabel, false);
    // end of choices render code
    // Start of more link rendering
    var moreid = opDivId +'.more5elem';
    var diff = choiceArr.length - MAX_LBL_COUNT;
    //handle more ...
    if( diff > 0 ) {
        var moreelem = opdiv.get(moreid);
        if(!moreelem) {
            moreelem = opdiv.add({
                xtype:'label' ,
                text : '',
                id: moreid,
                listeners: {
                    render: function(c){
                        c.getEl().on({
                            click: function(e){
                                // construct tooltip text
                                var opdiv = Ext.getCmp(opDivId);
                                var opChoices = opdiv.choices;

                                var tooltip = '';
                                for( var kk=0; kk < opChoices.length; kk++ ) {
                                    tooltip += (kk === 0)? opChoices[kk].value : ", " + opChoices[kk].value;
                                }
                                var tt =  Ext.getCmp(moreid + '.tooltip');
                                // Calculate X Y position such that tool tip does not go out side UI
                                // Roughly assuming size of tool tip 200 x 75 as actual size may very based on contents
                                var width = getWindowWidth() ;
                                var height = getWindowHeight();
                                var displayX = e.browserEvent.clientX;
                                var displayY = e.browserEvent.clientY;
                                if((width - displayX)<200){
                                    displayX = displayX - 200;
                                }
                                if((height - displayY)<75){
                                    displayY = displayY -75;
                                }
                                if (!tt) {
                                      tt = new ATO.components.ChoiceCompToolTip( {
                                         id : moreid + '.tooltip',
                                         dismissDelay : 3000,
                                         //draggable : true, //Allow tool tip to be drag
                                         autoHide : true,
                                         target: moreelem,
                                         anchorToTarget:false,
                                         tooltip : tooltip,
                                         renderTo : document.body,// Render
                                                                   // immediately so
                                                                   // that tip.body can
                                                                   // be
                                                                   // referenced prior
                                                                   // to the first show.
                                        listeners : {
                                           // Change content dynamically before tip is
                                           // displayed.
                                           // triggered the show.
                                           beforeshow : function updateTipBody(tip) {
                                               tip.body.dom.innerHTML = tip.tooltip;
                                           }
                                        }
                                    });
                                } else {
                                    tt.tooltip = tooltip;
                                }
                                tt.showAt([displayX,displayY]);
                            },
                            scope: c
                        });
                    }
                },
                style: 'font-size: 11px; text-decoration: underline; cursor:pointer'
            });
        }

        var moretext = String.format(optionResource.get ('LABELMOREELEM', ["0"]), diff);
        // converting the spaces to non breaking space, so the link do not wrap in between
        var moretext2 = moretext.replace(/\s/g, "&nbsp;");
        moreelem.setText(moretext2 + " "/*breaking space to wrap*/,false);
        moreelem.setVisible(true);
    } else { // if diff==0 remove more link here
        moreelem = opdiv.get(moreid);
        //if(moreelem) opdiv.remove(moreelem);
        if(moreelem) {
            moreelem.setVisible(false);
        }
    }

    //if no choices to show then hide optiondiv
    if(diff + MAX_LBL_COUNT === 0) {
        optionVisible = false;
        opdiv.choices = [];
        var tt =  Ext.getCmp(moreid + '.tooltip');
        if(tt) {
            tt.destroy();
        }
    }
    summary.doLayout();

    // set the visibility of option
    if(opdiv) {
        opdiv.setVisible(optionVisible);
    }
};

ATO.components.ChoiceCompToolTip = Ext.extend(Ext.ToolTip, {
    onRender : function(ct, position){
        Ext.ToolTip.superclass.onRender.call(this, ct, position);
        if(this.anchorToTarget){
            this.anchorCls = 'x-tip-anchor-' + this.getAnchorPosition();
            this.anchorEl = this.el.createChild({
                cls: 'x-tip-anchor ' + this.anchorCls
            });
        }
    },
    afterRender : function(){
        Ext.ToolTip.superclass.afterRender.call(this);
        if(this.anchorToTarget){
            this.anchorEl.setStyle('z-index', this.el.getZIndex() + 1);
        }
    }
});

/**
 * Need override the getFormData when the option tab is active inside the edit filter.
 */
 ATO.components.EditFilterFormDataWrapper = function(orig, form, options) {
    var formData = orig(form, options);
    if(ATO.components.isOverrideGetMainForm()) {
        var params = {
            typeIdentity: ATO.components.ChoiceComponent.getChoiceTypeIdentity()
        };
        var tableDataParams = PTC.util.tableDataManager.getSerializedParams();
        Ext.apply(params, tableDataParams);
        Ext.apply(params, ATO.components.ChoiceComponent.params);
        delete params["tableId"];
        var data = Ext.urlEncode(params);
        formData += '&' + data;
    }
    return formData;
};

ATO.components.isOverrideGetMainForm = function(){
    if(ATO.components.ChoiceComponent.params && ATO.components.ChoiceComponent.params.overrideGetMainForm === true){
        return true;
    }
    return false;
};

ATO.components.ChoiceComponent.registerCustomFormGetter = function(){
    if(ATO.components.ChoiceComponent.customFormGetterRegistered === false){
        if(ATO.components.isOverrideGetMainForm()) {
            ATO.components.ChoiceComponent.customFormGetterRegistered = true;

            //Needed to handle table refreshes when in the edit filter
            Ext.lib.Ajax.serializeForm = Ext.lib.Ajax.serializeForm.wrap(ATO.components.EditFilterFormDataWrapper);

            //Needed to handle table view refreshes when in the edit filter
            PTC.jca.table.Utils._formDataForHandleViewChange = PTC.jca.table.Utils._formDataForHandleViewChange.wrap(ATO.components.EditFilterFormDataWrapper);
        }
    }
};

/**
 * Picker Callback for changing the context.
 */
ATO.components.ChoiceComponent.contextPickerCallback = function (objects) {
    if(objects !== null && objects && objects.pickedObject.length > 0) {
        var selection = objects.pickedObject[0];

        var currentContainer = ATO.components.ChoiceComponent.params.containerId;
        if(currentContainer.indexOf("OR:") == -1) {
            currentContainer = "OR:" + ATO.components.ChoiceComponent.params.containerId;
        }
        var choseSameContext = selection.oid === currentContainer;
        if(choseSameContext) {
            return;
        }

        ATO.components.ChoiceComponent.clearSearchField();

        if(!ATO.components.ChoiceComponent.params.visitedContainers) {
            ATO.components.ChoiceComponent.params.visitedContainers = [currentContainer];
        }

        var hasVisitedContainer = true;
        if(ATO.components.ChoiceComponent.params.visitedContainers.indexOf(selection.oid) == -1) {
            ATO.components.ChoiceComponent.params.visitedContainers.push(selection.oid);
            hasVisitedContainer = false;
        }

        ATO.components.ChoiceComponent.params.containerId = selection.oid;
        Ext.getDom("contextPicker$label$").value = selection.name;
        Ext.getDom( "containerId" ).value = selection.oid;
        var params = {
            actionType: 'initialSelections'
        };
        Ext.apply(params, ATO.components.ChoiceComponent.params);
        if(hasVisitedContainer === false) {
            ATO.components.ChoiceComponent.showLoadMask();
            PTC.atoChoiceSel.invokeChoiceSelectionController(params, ATO.components.ChoiceComponent.handleSelectionCallback, true);
        } else {
            ATO.components.ChoiceComponent.renderChoiceComponent(ATO.components.ChoiceComponent.params);
        }
    }
};

ATO.components.ChoiceComponent.handleSelectionCallback = function(response) {
    ATO.components.ChoiceComponent.handleInitialSelections(response);
    ATO.components.ChoiceComponent.hideLoadMask();
    ATO.components.ChoiceComponent.renderChoiceComponent(ATO.components.ChoiceComponent.params);
};

ATO.components.ChoiceComponent.saveInitialSelections = function() {
    var tableId = ATO.components.ChoiceComponent.params.tableId;
    PTC.util.tableDataManager.copyOidMap(tableId, tableId + "_initial");
};

ATO.components.ChoiceComponent.afterSubmit = function(url){
    Ext.getDom("isApply").value=false;
    ATO.components.ChoiceComponent.saveInitialSelections();
    parent.stopProgress();
    parent.enableButtons();
    // Refresh the Opener
    window.opener.PTC.navigation.loadContent(url, true);
};

ATO.components.ChoiceComponent.isSecuredAction = function() {
    if (ATO.components.ChoiceComponent.params && ATO.components.ChoiceComponent.params.tableId
            && PTC.jca.table.Utils.getTable(ATO.components.ChoiceComponent.params.tableId) != null) {
        var warningBtnId = ATO.components.ChoiceComponent.params.tableId + "_tablelvlmesg";
        var warningBtn = PTC.jca.handleFooterButtons.findButton(ATO.components.ChoiceComponent.params.tableId,
                warningBtnId);
        if (warningBtn) {
            var errorMsg = warningBtn.tooltip;
            if (errorMsg) {
                var accessResource = new ATO.util.ResourceBundle({
                    resource: 'wt.access.accessResource'
                });
                if (errorMsg.indexOf(accessResource.get('19')) > -1) {
                    return true;
                }
            }
        }
    }
    return false;
}

ATO.components.ChoiceComponent.disableToolBarItems = function() {
    var disableRuleChkBox = ATO.components.ChoiceComponent.getElementById("_disableRuleChkingChkBox");
    if (disableRuleChkBox) {
        disableRuleChkBox.checked = false;
        disableRuleChkBox.disable();
    }
    var toggleSelectedChoiceList = Ext.getCmp('toggleSelectedChoiceList');
    if (toggleSelectedChoiceList) {
        toggleSelectedChoiceList.disable();
    }
    var filterModeSelector = Ext.getCmp("_filterModeSelector");
    if (filterModeSelector) {
        filterModeSelector.disable();
    }
    ATO.components.ChoiceComponent.disableCSOREffectivityPicker(true);
}



/**
 *
 * 自动勾选相关函数
 * @param event
 */
ATO.components.ChoiceComponent.customSelectAll = function (selectParams) {
    // 开启加载程序
    ATO.components.ChoiceComponent.showProgress();
    ATO.components.ChoiceComponent.loadCSS('netmarkets/css/ato/options.css');
    // 初始化参数
    let params = new JCAClone(ATO.components.ChoiceComponent.params);
    console.log(params);
    // 设置初始化选择
    ATO.components.ChoiceComponent.params.hasInitialSelections = true;
    // 清理用户的选择
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
        params.panzerVersion = selectParams;
        // 发送请求到接口，进行选择
        // 回调函数中会自动根据参数处理勾选的选项选择
        PTC.atoChoiceSel.invokeChoiceSelectionController(params,
            ATO.components.ChoiceComponent.initChoiceComponentCallBack, true);
    };
    // 回调规则引擎
    ATO.components.ChoiceComponent.callRulesEngineAction(params);
    // 关闭加载程序
    ATO.components.ChoiceComponent.stopProgress();
};


ATO.components.ChoiceComponent.handleCustomSelections = function(response) {
    console.log("response" + response);
    var decodedResult = response;
    if(response.responseText && response.responseText !== null) {
        var responsetxt = response.responseText;
        decodedResult = Ext.decode(responsetxt.trim());
    }
    console.log("decodedResult" + decodedResult);
    // 保存用户选择
    ATO.components.ChoiceComponent.storeUserSelections(decodedResult, false);
};

/**
 * 自定义回调函数
 * @param rulesEngineResult
 */
ATO.components.ChoiceComponent.customSelectChoiceComponentCallBack = function(rulesEngineResult) {
    // 设置是否成功
    var wasSuccessful = ATO.components.ChoiceComponent.isSuccess(rulesEngineResult);
    // 自定义设置是否选中
    ATO.components.ChoiceComponent.handleCustomSelections(rulesEngineResult);
    // 克隆参数
    var params = new JCAClone(ATO.components.ChoiceComponent.params); //clone the config
    // 关闭等待
    ATO.components.ChoiceComponent.stopProgress();
    // 重新读取组件
    ATO.components.ChoiceComponent.renderChoiceComponent(params);
};