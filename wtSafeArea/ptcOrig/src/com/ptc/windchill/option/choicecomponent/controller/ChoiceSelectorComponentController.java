//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.ptc.windchill.option.choicecomponent.controller;

import com.ptc.core.components.util.OidHelper;
import com.ptc.core.components.util.RequestHelper;
import com.ptc.netmarkets.model.NmOid;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.windchill.option.choicecomponent.ChoiceComponentHelper;
import com.ptc.windchill.option.choicecomponent.ChoiceComponentUtility;
import com.ptc.windchill.option.choicecomponent.ChoiceInfoBean;
import com.ptc.windchill.option.choicecomponent.ChoiceSelectionStatus;
import com.ptc.windchill.option.choicecomponent.SelectableChoiceBean;
import com.ptc.windchill.option.choicecomponent.builders.ChoiceDataFactory;
import com.ptc.windchill.option.choicecomponent.builders.RulesBasedChoiceDataFactory;
import com.ptc.windchill.option.delegate.OptionSetDelegateResult;
import com.ptc.windchill.option.model.Option;
import com.ptc.windchill.option.model.OptionSet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wt.fc.ObjectReference;
import wt.fc.ReferenceFactory;
import wt.fc.WTReference;
import wt.identity.IdentityFactory;
import wt.inf.container.WTContainerRef;
import wt.log4j.LogR;
import wt.session.SessionHelper;
import wt.util.LocalizableMessage;
import wt.util.WTException;
import wt.vc.VersionReference;

@Controller
@RequestMapping({"/ato.choiceSelectionData"})
public class ChoiceSelectorComponentController {
    private static final String IS_SELECTED = "isSelected";
    private static final String OPTION_OID_SEP = "#";
    private static final String CHOICE_DATA = "choiceData";
    private static final String TITLE = "title";
    private static final String SUCCESS = "success";
    private static final String COMMAND_BEAN = "commandBean";
    private static final String OPTION_OID = "optionOid";
    private static final String SELECT_ALL = "selectAll";
    private static final String ACTION_TYPE = "actionType";
    private static final String INITIAL_SELECTIONS = "initialSelections";
    private static final String CURRENT_SELECTIONS = "currentSelections";
    private static final Logger logger = LogR.getLogger(ChoiceSelectorComponentController.class.getName());

    public ChoiceSelectorComponentController() {
    }

    @RequestMapping(
            method = {RequestMethod.GET, RequestMethod.POST}
    )
    protected void processRequest(HttpServletRequest var1, HttpServletResponse var2) throws Exception {
        var2.setContentType("application/json; charset=UTF-8");
        var2.setHeader("Cache-Control", "no-cache");
        JSONObject var3 = new JSONObject();

        try {
            try {
                RequestHelper.initializeCommandBean(var1, var2);
                NmCommandBean var4 = (NmCommandBean)var1.getAttribute("commandBean");
                String var5 = var4.getTextParameter("actionType");
                if ("initialSelections".equals(var5)) {
                    var3 = this.getInitiallySelectedChoices(var4);
                } else if ("selectAll".equals(var5)) {
                    var3 = this.selectAllOptionChoices(var4);
                } else if ("currentSelections".equals(var5)) {
                    String var6 = var1.getParameter("rulesSessionId");
                    var3 = this.getCurrentlySelectedChoices(var4, var6);
                } else {
                    var3 = this.getSelectableChoices(var4);
                }
            } catch (WTException var7) {
                var3.put("success", false);
                logger.error("Unable to generate a json response data.", var7);
            }
        } catch (JSONException var8) {
            logger.error("Unable to create a json object.", var8);
        }

        this.respond(var2, var3);
    }

    private JSONObject getSelectableChoices(NmCommandBean var1) throws Exception {
        logger.debug("generating selectable choices for a option.");
        JSONObject var2 = new JSONObject();
        var2.put("title", this.getWindowTitle(var1));
        var2.put("choiceData", this.generateChoicesForOption(var1));
        var2.put("success", true);
        return var2;
    }

    private JSONObject selectAllOptionChoices(NmCommandBean var1) throws Exception {
        logger.debug("processing selected or unselected options");
        JSONObject var2 = new JSONObject();
        ArrayList var3 = new ArrayList();
        String var4 = var1.getTextParameter("optionOid");
        if (logger.isDebugEnabled()) {
            logger.debug("optionOid: " + var4);
        }

        ReferenceFactory var5 = new ReferenceFactory();
        if (var4 != null && !var4.isEmpty()) {
            String[] var6 = var4.split("#");
            var2.put("optionOid", var6);
            String[] var7 = var6;
            int var8 = var6.length;

            for(int var9 = 0; var9 < var8; ++var9) {
                String var10 = var7[var9];
                var3.add(var5.getReference(var10));
            }
        }

        Object var12 = new ArrayList();
        SelectableChoiceBean var11;
        if (var3.size() == 1) {
            logger.debug("processing only one selected option");
            var12 = ChoiceDataFactory.newInstance(var1).getSelectableChoices((WTReference)var3.get(0));
        } else if (!var3.isEmpty()) {
            logger.debug("processing multiple selected options.");
            Collection var13 = ChoiceDataFactory.newInstance(var1).getChoices();
            WTContainerRef var15 = ChoiceComponentHelper.getWTContainerRef(var1);
            Iterator var17 = var13.iterator();

            while(var17.hasNext()) {
                ChoiceInfoBean var19 = (ChoiceInfoBean)var17.next();
                var11 = new SelectableChoiceBean(var19, var15);
                if (var3.contains(var11.getOptionOid().getWtRef())) {
                    ((List)var12).add(var11);
                }
            }
        }

        Collections.sort((List)var12);
        JSONArray var14 = new JSONArray();
        boolean var16 = Boolean.valueOf(var1.getTextParameter("isSelected"));
        if (logger.isDebugEnabled()) {
            logger.debug("is option selected ? " + var16);
        }

        var2.put("isSelected", var16);
        ChoiceSelectionStatus var18 = var16 ? ChoiceSelectionStatus.CHECKED : ChoiceSelectionStatus.UNCHECKED;
        Iterator var20 = ((List)var12).iterator();

        while(var20.hasNext()) {
            var11 = (SelectableChoiceBean)var20.next();
            var11.setAllSelected(var16);
            var11.setChoiceSelectionStatus(var18);
            var14.put(var11.toJSONObject());
        }

        var2.put("choiceData", var14);
        return var2;
    }

    private JSONObject getInitiallySelectedChoices(NmCommandBean var1) throws Exception {
        logger.debug("collecting initially selected choices.");
        JSONObject var2 = new JSONObject();
        List var3 = ChoiceDataFactory.newInstance(var1).getInitiallySelectedChoices();
        Iterator var4 = var3.iterator();

        while(var4.hasNext()) {
            SelectableChoiceBean var5 = (SelectableChoiceBean)var4.next();
            var2.append("updateUserSelections", var5.toJSONObject());
        }

        return var2;
    }

    private void respond(HttpServletResponse var1, JSONObject var2) throws IOException {
        String var3 = var2.toString();
        if (logger.isDebugEnabled()) {
            logger.debug("response: " + var3);
        }

        var1.getWriter().print(var3);
    }

    protected String getWindowTitle(NmCommandBean var1) throws WTException {
        String var2 = "";
        Option var3 = this.getOption(var1);
        if (var3 != null) {
            LocalizableMessage var4 = IdentityFactory.getDisplayIdentifier(var3);
            var2 = var4.getLocalizedMessage(SessionHelper.getLocale());
        }

        if (logger.isDebugEnabled()) {
            logger.debug("window title : " + var2);
        }

        return var2;
    }

    WTReference getOptionReference(NmCommandBean var1) throws WTException {
        Object var2 = null;
        String var3 = var1.getTextParameter("optionOid");
        if (var3 != null && !var3.isEmpty()) {
            NmOid var4 = NmOid.newNmOid(var3);
            if (var4.isA(Option.class)) {
                var2 = var4.getWtRef();
            } else {
                logger.warn("optionOid was not a valid type of Option");
            }

            if (var2 instanceof VersionReference) {
                var2 = ObjectReference.newObjectReference(((WTReference)var2).getObject());
            }

            return (WTReference)var2;
        } else {
            logger.debug("Did not find an option oid.");
            return (WTReference)var2;
        }
    }

    protected Option getOption(NmCommandBean var1) throws WTException {
        WTReference var2 = this.getOptionReference(var1);
        return var2 != null ? (Option)var2.getObject() : null;
    }

    protected JSONArray generateChoicesForOption(NmCommandBean var1) throws Exception {
        NmOid var2 = var1.getPrimaryOid();
        if (this.notOptionSet(var2)) {
            OptionSetDelegateResult var3 = ChoiceComponentUtility.getOptionSetDelegateResult(var1);
            if (var3 != null) {
                OptionSet var4 = var3.getOptionSet();
                if (var4 != null) {
                    var2 = OidHelper.getNmOid(var4);
                }
            }
        }

        WTReference var5 = this.getOptionReference(var1);
        if (var5 == null) {
            throw new WTException("Unable to determine choices for the given request.");
        } else {
            return ChoiceDataFactory.newInstance(var1).getChoiceData(var5, true);
        }
    }

    private boolean notOptionSet(NmOid var1) {
        return var1 == null || !var1.isA(OptionSet.class);
    }

    protected List<SelectableChoiceBean> generateChoicesForSearch(NmCommandBean var1) throws WTException {
        ArrayList var2 = new ArrayList();
        return var2;
    }

    private JSONObject getCurrentlySelectedChoices(NmCommandBean var1, String var2) throws Exception {
        logger.debug("collecting currently selected choices.");
        JSONObject var3 = new JSONObject();
        WTContainerRef var4 = ChoiceComponentHelper.getWTContainerRef(var1);
        RulesBasedChoiceDataFactory var5 = new RulesBasedChoiceDataFactory();
        Set var6 = var5.getSelectedChoicesForSession(var2);
        Iterator var7 = var6.iterator();

        while(var7.hasNext()) {
            ChoiceInfoBean var8 = (ChoiceInfoBean)var7.next();
            SelectableChoiceBean var9 = new SelectableChoiceBean(var8, var4);
            JSONObject var10 = var9.toJSONObject();
            var10.put("id", var8.getChoiceId());
            var3.append("choices", var10);
        }

        return var3;
    }
}
