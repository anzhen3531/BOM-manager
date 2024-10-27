package com.ptc.core.components.forms;

import com.ptc.core.components.forms.DynamicRefreshInfo.Action;
import com.ptc.core.components.util.FeedbackMessage;
import com.ptc.netmarkets.model.NmOid;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.windchill.enterprise.navigation.NavigationUtilities;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import wt.access.AccessControlServerHelper;
import wt.access.NotAuthorizedException;
import wt.fc.ObjectNoLongerExistsException;
import wt.fc.ObjectReference;
import wt.fc.Persistable;
import wt.log4j.LogR;
import wt.util.HTMLEncoder;
import wt.util.LocalizableMessage;
import wt.util.WTException;
import wt.util.WTRuntimeException;
import wt.vc.Iterated;
import wt.vc.VersionReference;

public class FormResult implements Serializable {
    public static final String NAV_TREE_NODES_UPDATE = "navigatorTreePanelNodesforUpdate";
    private static final long serialVersionUID = 1L;
    private static final Logger log = LogR.getLogger(FormResult.class.getName());
    private FormProcessingStatus status = null;
    /** @deprecated */
    @Deprecated
    private FormResultAction nextAction = null;
    /** @deprecated */
    @Deprecated
    private String url = null;
    /** @deprecated */
    @Deprecated
    private String forcedUrl = null;
    /** @deprecated */
    @Deprecated
    private String javascript = null;
    /** @deprecated */
    @Deprecated
    private boolean isJavascriptEncoded = false;
    private ArrayList<FeedbackMessage> feedbackMessages = null;
    private ArrayList<Throwable> exceptions = null;
    private ArrayList<DynamicRefreshInfo> refreshInfo = null;
    private boolean responseHandled = false;
    /** @deprecated */
    @Deprecated
    private Boolean skipPageRefresh = false;
    private Boolean clearClipboard;
    private boolean navigatorTreePanelUpdateRequired;
    private Set<String> navigatorTreePanelNodes;
    private String messageLocation;
    private Map extraData;
    public static final String AJAX_ENABLED = "AjaxEnabled";
    public static final String AJAX_ROW = "row";
    public static final String AJAX_COMPONENT = "component";
    public static final String AJAX_TLN = "thirdLevelNav";

    public FormResult() {
        this.clearClipboard = Boolean.FALSE;
        this.navigatorTreePanelUpdateRequired = false;
        this.messageLocation = "top.PTC.getMainWindow()";
        this.extraData = new HashMap();
    }

    public FormResult(FormProcessingStatus var1) {
        this.clearClipboard = Boolean.FALSE;
        this.navigatorTreePanelUpdateRequired = false;
        this.messageLocation = "top.PTC.getMainWindow()";
        this.extraData = new HashMap();
        this.status = var1;
    }

    /** @deprecated */
    @Deprecated
    public FormResult(FormProcessingStatus var1, FormResultAction var2) {
        this.clearClipboard = Boolean.FALSE;
        this.navigatorTreePanelUpdateRequired = false;
        this.messageLocation = "top.PTC.getMainWindow()";
        this.extraData = new HashMap();
        this.status = var1;
        this.nextAction = var2;
    }

    public void setStatus(FormProcessingStatus var1) {
        this.status = var1;
    }

    public FormProcessingStatus getStatus() {
        return this.status;
    }

    /** @deprecated */
    @Deprecated
    public void setNextAction(FormResultAction var1) {
        this.nextAction = var1;
    }

    /** @deprecated */
    @Deprecated
    public FormResultAction getNextAction() {
        return this.forcedUrl == null ? this.nextAction : FormResultAction.FORWARD;
    }

    /** @deprecated */
    @Deprecated
    public void setURL(String var1) {
        this.url = var1;
    }

    /** @deprecated */
    @Deprecated
    public String getURL() {
        return this.forcedUrl == null ? this.url : this.forcedUrl;
    }

    /** @deprecated */
    @Deprecated
    public void setForcedUrl(String var1) {
        this.forcedUrl = var1;
    }

    /** @deprecated */
    @Deprecated
    public String getForcedUrl() {
        return this.forcedUrl;
    }

    /** @deprecated */
    @Deprecated
    public void setJavascript(String var1) {
        this.setJavascript(var1, false);
    }

    /** @deprecated */
    @Deprecated
    public void setJavascript(String var1, boolean var2) {
        this.javascript = var1;
        this.isJavascriptEncoded = var2;
    }

    /** @deprecated */
    @Deprecated
    public String getJavascript() {
        return this.javascript;
    }

    public ArrayList<DynamicRefreshInfo> getDynamicRefreshInfo() {
        return this.refreshInfo;
    }

    public void setDynamicRefreshInfo(ArrayList<DynamicRefreshInfo> var1) {
        this.refreshInfo = var1;
    }

    public void addDynamicRefreshInfo(DynamicRefreshInfo var1) {
        if (var1 != null) {
            if (this.refreshInfo == null) {
                this.refreshInfo = new ArrayList();
            }

            this.refreshInfo.add(var1);
        }

    }

    public void createAndAddDynamicRefreshInfo(Persistable var1, Persistable var2, String var3) {
        DynamicRefreshInfo var4 = new DynamicRefreshInfo(var1, var2, Action.getAction(var3));
        this.addDynamicRefreshInfo(var4);
    }

    public void addFeedbackMessage(FeedbackMessage var1) {
        if (var1 != null) {
            if (this.feedbackMessages == null) {
                this.feedbackMessages = new ArrayList();
            }

            this.feedbackMessages.add(var1);
        }

    }

    public List<FeedbackMessage> getFeedbackMessages() {
        return this.feedbackMessages;
    }

    public void addException(Throwable var1) {
        if (var1 != null && this.exceptions == null) {
            this.exceptions = new ArrayList();
        }

        this.exceptions.add(var1);
    }

    public List<Throwable> getExceptions() {
        return this.exceptions;
    }

    /** @deprecated */
    @Deprecated
    public void setSkipPageRefresh(boolean var1) {
        this.skipPageRefresh = var1;
    }

    public String getResponse() throws WTException {
        return this.getResponse((Object)null);
    }

    public String getResponse(Object var1) throws WTException {
        if (var1 != null && var1 instanceof HttpServletRequest
            && "checkinButton".equals(((HttpServletRequest)var1).getParameter("editButtonClicked"))
            && this.forcedUrl != null) {
            this.forcedUrl = null;
        }

        if (this.getNextAction() == null) {
            this.setNextAction(FormResultAction.REFRESH_OPENER);
        }

        StringBuffer var2 = new StringBuffer();
        FormProcessingStatus var3 = this.getStatus();
        if (var3 == null) {
            var3 = FormProcessingStatus.SUCCESS;
        }

        int var4;
        String var5;
        if ((var3 == FormProcessingStatus.FAILURE || var3 == FormProcessingStatus.NON_FATAL_ERROR) && var2.length() <= 0
            && this.exceptions != null && this.exceptions.size() > 0) {
            for (var4 = 0; var4 < this.exceptions.size(); ++var4) {
                var5 = FeedbackMessage.formatException((Throwable)this.exceptions.get(var4), (LocalizableMessage)null,
                    false, (Locale)null);
                // var2.append("top.wfalert_gen('");
                // var2.append(HTMLEncoder.encodeForJavascript(var5));
                // var2.append(");top.clearActionFormData();");
                // 修改提示 改为更加完美些的
                var2.append(
                    "top.eval('Ext.Msg.show({title: \"系统异常\",msg:\"<div style=\\'overflow-y: auto; height: 150px\\'>");
                var2.append(HTMLEncoder.encodeAndFormatForHTMLContent(var5));
                var2.append(
                    "</div>\",width:550,buttons:Ext.Msg.OK,icon:Ext.Msg.WARNING})');top.clearActionFormData();");
            }
        }

        int var12;
        if (this.feedbackMessages != null && this.feedbackMessages.size() > 0) {
            if (var3 == FormProcessingStatus.SUCCESS) {
                JSONArray var11 = new JSONArray();

                for (var12 = 0; var12 < this.feedbackMessages.size(); ++var12) {
                    var11.put(((FeedbackMessage)this.feedbackMessages.get(var12)).toJSONObject());
                }

                var2.append(this.getMessageLocation()).append(".PTC.messaging.showInlineMessage(");
                var2.append(var11.toString());
                var2.append(");");
            } else {
                for (var4 = 0; var4 < this.feedbackMessages.size(); ++var4) {
                    var5 = ((FeedbackMessage)this.feedbackMessages.get(var4)).getLocalizedDisplayMessage();
                    // 生成js alert
                    // var2.append("top.wfalert_gen('");
                    // var2.append(HTMLEncoder.encodeForJavascript(var5));
                    // var2.append("');");
                    var2.append(
                        "top.eval('Ext.Msg.show({title: \"系统异常\",msg:\"<div style=\\'overflow-y: auto; height: 150px\\'>");
                    var2.append(HTMLEncoder.encodeAndFormatForHTMLContent(var5));
                    var2.append(
                        "</div>\",width:550,buttons:Ext.Msg.OK,icon:Ext.Msg.WARNING})');top.clearActionFormData();");
                }
            }
        }

        var2.append("top.PTC.getMainWindow().PTC.performance.stopComponentTimer('WizardSubmit');");
        String var13 = this.getURL();
        if (var13 == null) {
            var13 = " ";
        }

        var12 = this.getNextAction().id();
        String var6 = null;
        if (var1 != null) {
            var6 = ((HttpServletRequest)var1).getParameter("AjaxEnabled");
        }

        boolean var7 = this.isComponentRefresh(var6);
        JSONObject var8 = this.getDynamicRefreshJSON(this.getDynamicRefreshInfo());

        try {
            this.addActionName(var1, var8);
            var8.put("extraData", this.extraData);
        } catch (JSONException var10) {
            throw new WTException(var10);
        }

        this.addOnPageActionRefreshInfo(var1, var7, var8);
        this.addNavigatorDynamicRefreshInfo(var1, var8);
        StringBuffer var9 = new StringBuffer("<html><body><script>");
        var9.append(var2.toString());
        var9.append(" top.handleSubmitResult(");
        var9.append(var3.id());
        var9.append(", ");
        var9.append(var12);
        var9.append(", '");
        var9.append(this.getEncodedJavascript());
        var9.append("', '");
        var9.append(HTMLEncoder.encodeForJavascript(var13));
        var9.append("', ");
        var9.append(var8);
        var9.append(")</script></body></html>");
        if (log.isDebugEnabled()) {
            log.debug("getResponse(): Returning response of: \n " + var9.toString());
        }

        return var9.toString();
    }

    private String getEncodedJavascript() {
        String var1 = this.getJavascript();
        if (var1 != null && !var1.isEmpty()) {
            return this.isJavascriptEncoded ? var1 : HTMLEncoder.encodeForJavascript(var1);
        } else {
            return " ";
        }
    }

    protected void addActionName(Object var1, JSONObject var2) throws JSONException {
        if (var1 != null && var1 instanceof HttpServletRequest) {
            String var3 = ((HttpServletRequest)var1).getParameter("actionName");
            if (var3 == null) {
                var3 = ((HttpServletRequest)var1).getParameter("executeLocation");
            }

            var2.put("actionName", var3);
        }

    }

    protected boolean isComponentRefresh(String var1) {
        return "component".equals(var1) || var1 == null || "thirdLevelNav".equals(var1);
    }

    protected void addOnPageActionRefreshInfo(Object var1, boolean var2, JSONObject var3) {
        if (var1 != null && var1 instanceof HttpServletRequest) {
            try {
                var3.put("tableID",
                    HTMLEncoder.encodeForJavascript(((HttpServletRequest)var1).getParameter("tableID")));
            } catch (JSONException var5) {
                throw new WTRuntimeException(var5);
            }
        }

    }

    protected void addNavigatorDynamicRefreshInfo(Object var1, JSONObject var2) {
        if (this.navigatorTreePanelUpdateRequired) {
            if (this.navigatorTreePanelNodes == null) {
                this.navigatorTreePanelNodes = new HashSet();
            }

            try {
                this.navigatorTreePanelNodes
                    .addAll(NavigationUtilities.getNavigatorTreeNodes((HttpServletRequest)var1));
                ArrayList var3 = this.getDynamicRefreshInfo();
                if (var3 != null) {
                    ArrayList var4 = new ArrayList();
                    Iterator var5 = var3.iterator();

                    while (var5.hasNext()) {
                        DynamicRefreshInfo var6 = (DynamicRefreshInfo)var5.next();
                        NmOid var7 = var6.getOid();
                        if (var7 != null) {
                            var4.add(var6.getOid());
                        }
                    }

                    this.navigatorTreePanelNodes.addAll(NavigationUtilities.getNavigatorTreeNodes(var4));
                }
            } catch (WTException var9) {
                log.error(var9.getMessage(), var9);
            }
        }

        if (this.navigatorTreePanelNodes != null && !this.navigatorTreePanelNodes.isEmpty()) {
            try {
                var2.put("navigatorTreePanelNodesforUpdate", new JSONArray(this.navigatorTreePanelNodes));
            } catch (JSONException var8) {
                log.error("Unable to create JSONArray for " + this.navigatorTreePanelNodes, var8);
            }
        }

    }

    protected JSONObject getDynamicRefreshJSON(List<? extends DynamicRefreshInfo> var1) throws WTException {
        if (var1 != null && var1.size() != 0) {
            HashMap var2 = new HashMap();
            HashMap var3 = new HashMap();
            var2.put("A", new HashMap());
            var2.put("U", new HashMap());
            var2.put("Del", new HashMap());
            Iterator var4 = var1.iterator();

            while (var4.hasNext()) {
                DynamicRefreshInfo var5 = (DynamicRefreshInfo)var4.next();
                if (var5 != null) {
                    Map var6 = (Map)var2.get(var5.getAction());
                    Object var7;
                    Object var8;
                    if (var5 instanceof DynamicNmContextRefreshInfo) {
                        DynamicNmContextRefreshInfo var9 = (DynamicNmContextRefreshInfo)var5;
                        var7 = String.valueOf(var9.getLocationContext());
                        var8 = var9.getRowContext();
                    } else {
                        if (var5.getLocation() == null) {
                            var7 = null;
                        } else {
                            this.addOidMapping(var3, var5.getLocation());
                            var7 = var5.getLocation().getHTMLId();
                        }

                        if (var5.getOid() == null) {
                            var8 = null;
                        } else {
                            this.addOidMapping(var3, var5.getOid());
                            var8 = var5.getOid().getHTMLId();
                        }

                        if (var7 == null) {
                            var7 = var8;
                        }
                    }

                    if (var8 != null && var6 != null) {
                        Object var16 = (List)var6.get(var7);
                        if (var16 == null) {
                            var16 = new ArrayList();
                            var6.put(var7, var16);
                        }

                        ((List)var16).add(var8);
                    }
                }
            }

            JSONObject var12 = new JSONObject(var2);
            Iterator var13 = var1.iterator();

            while (true) {
                DynamicRefreshInfo var14;
                do {
                    if (!var13.hasNext()) {
                        try {
                            var12.putOpt("OR_TO_VR_MAP", var3);
                        } catch (JSONException var10) {
                        }

                        return var12;
                    }

                    var14 = (DynamicRefreshInfo)var13.next();
                } while (var14 == null);

                try {
                    Map var15 = var14.getAdditionalRefreshInfo();
                    Iterator var17 = var15.entrySet().iterator();

                    while (var17.hasNext()) {
                        Map.Entry var18 = (Map.Entry)var17.next();
                        if (var18.getValue() instanceof Collection) {
                            var12.put((String)var18.getKey(), (Collection)var18.getValue());
                        } else {
                            var12.put((String)var18.getKey(), var18.getValue());
                        }
                    }
                } catch (JSONException var11) {
                    log.error(
                        "A JSONException was thrown while attempting to add the additional refresh info from the DynamicRefreshInfo class "
                            + var14 + " to the JSONObject that is returned to the client.",
                        var11);
                }
            }
        } else {
            return new JSONObject();
        }
    }

    protected void addOidMapping(HashMap<String, String> var1, Object var2) throws WTException {
        AccessControlServerHelper.disableNotAuthorizedAudit();

        try {
            if (var2 instanceof NmOid && ((NmOid)var2).isA(Persistable.class)) {
                NmOid var3 = (NmOid)var2;

                try {
                    Persistable var4 = (Persistable)var3.getRefObject();
                    if (var4 instanceof Iterated) {
                        ObjectReference var5 = ObjectReference.newObjectReference(var4);
                        VersionReference var6 = VersionReference.newVersionReference((Iterated)var4);
                        var1.put(var3.getRefFactory().getReferenceString(var5),
                            var3.getRefFactory().getReferenceString(var6));
                    }
                } catch (ObjectNoLongerExistsException var11) {
                }
            }
        } catch (WTRuntimeException var12) {
            if (!(var12.getNestedThrowable() instanceof NotAuthorizedException)) {
                throw var12;
            }

            log.trace(var12, var12);
        } finally {
            AccessControlServerHelper.reenableNotAuthorizedAudit();
        }

    }

    public String toString() {
        StringBuffer var1 = new StringBuffer();
        var1.append("----------FormResult----------\n");
        var1.append("status: " + this.status + "\n");
        var1.append("nextAction: " + this.nextAction + "\n");
        var1.append("url: " + this.url + "\n");
        var1.append("javascript: " + this.javascript + "\n");
        var1.append("feedback messages: \n");
        Iterator var2;
        if (this.feedbackMessages != null) {
            var2 = this.feedbackMessages.iterator();

            while (var2.hasNext()) {
                var1.append("\n\n " + ((FeedbackMessage)var2.next()).getLocalizedDisplayMessage());
            }
        }

        if (this.exceptions != null) {
            var1.append("\nexceptions: \n");
            var2 = this.exceptions.iterator();

            while (var2.hasNext()) {
                var1.append("\n\n" + ((Exception)var2.next()).getMessage());
            }
        }

        var1.append("------------------------------\n");
        return var1.toString();
    }

    public void mergeResults(FormResult var1) {
        if (var1 != null) {
            if (var1.getStatus() == FormProcessingStatus.FAILURE) {
                this.setStatus(FormProcessingStatus.FAILURE);
            } else if (var1.getStatus() == FormProcessingStatus.NON_FATAL_ERROR
                && this.getStatus() != FormProcessingStatus.FAILURE) {
                this.setStatus(FormProcessingStatus.NON_FATAL_ERROR);
            }

            this.extraData.putAll(var1.extraData);
            Iterator var2;
            if (var1.getFeedbackMessages() != null) {
                var2 = var1.getFeedbackMessages().iterator();

                while (var2.hasNext()) {
                    this.addFeedbackMessage((FeedbackMessage)var2.next());
                }
            }

            if (var1.getExceptions() != null) {
                var2 = var1.getExceptions().iterator();

                while (var2.hasNext()) {
                    this.addException((Throwable)var2.next());
                }
            }

            if (var1.getDynamicRefreshInfo() != null) {
                var2 = var1.getDynamicRefreshInfo().iterator();

                while (var2.hasNext()) {
                    this.addDynamicRefreshInfo((DynamicRefreshInfo)var2.next());
                }
            }

            if (var1.getJavascript() != null) {
                this.setJavascript(var1.getJavascript(), var1.isJavascriptEncoded);
            }

            if (var1.getNextAction() != null) {
                this.setNextAction(var1.getNextAction());
            }

            if (var1.getURL() != null) {
                this.setURL(var1.getURL());
            }

            if (var1.getNavigatorTreePanelNodes() != null) {
                this.setNavigatorTreePanelNodes(var1.getNavigatorTreePanelNodes());
            }

            if (var1.isNavigatorTreePanelUpdateRequired()) {
                this.setNavigatorTreePanelUpdateRequired(true);
            }

        }
    }

    public static String wrapResponseString(String var0) {
        return "<!-- FORM_RESULT -->" + var0 + "<!-- END_FORM_RESULT -->";
    }

    public String doResponseHandling(NmCommandBean var1) throws WTException {
        this._handleAdditionalInfo(var1);
        this.handleExecutionStatus(var1);
        this.clearClipBoard(var1);
        return wrapResponseString(this.getResponse(var1.getRequest()));
    }

    public void clearClipBoard(NmCommandBean var1) {
        if (this.isClearClipboard()) {
            var1.getClipboardBean().removeLatestClipboardItems();
        }

    }

    public void handleExecutionStatus(NmCommandBean var1) {
        if ((this.getStatus() == FormProcessingStatus.NON_FATAL_ERROR
            || this.getStatus() == FormProcessingStatus.FAILURE)
            && "row".equals(var1.getTextParameter("AjaxEnabled"))) {
            var1.setExecutionStatus("failed");
        }

    }

    /** @deprecated */
    @Deprecated
    public boolean skipPage(ServletRequest var1) {
        return this.skipPageRefresh;
    }

    public boolean isClearClipboard() {
        return this.clearClipboard;
    }

    public void setClearClipboard(boolean var1) {
        this.clearClipboard = var1;
    }

    public void setResponseHandled(boolean var1) {
        this.responseHandled = var1;
    }

    public boolean isResponseHandled() {
        return this.responseHandled;
    }

    public void updateNavigatorTree(boolean var1) {
        this.navigatorTreePanelUpdateRequired = var1;
    }

    public void updateNavigatorTree(List<NmOid> var1) throws WTException {
        this.navigatorTreePanelNodes = new HashSet();
        if (var1 != null) {
            this.navigatorTreePanelNodes.addAll(NavigationUtilities.getNavigatorTreeNodes(var1));
        }

    }

    public void updateNavigatorTree(List<NmOid> var1, List<String> var2) throws WTException {
        this.navigatorTreePanelNodes = new HashSet();
        if (var1 != null) {
            this.navigatorTreePanelNodes.addAll(NavigationUtilities.getNavigatorTreeNodes(var1));
        }

        if (var2 != null) {
            this.navigatorTreePanelNodes.addAll(var2);
        }

    }

    protected Set<String> getNavigatorTreePanelNodes() {
        return this.navigatorTreePanelNodes;
    }

    protected void setNavigatorTreePanelNodes(Set<String> var1) {
        this.navigatorTreePanelNodes = var1;
    }

    protected boolean isNavigatorTreePanelUpdateRequired() {
        return this.navigatorTreePanelUpdateRequired;
    }

    protected void setNavigatorTreePanelUpdateRequired(boolean var1) {
        this.navigatorTreePanelUpdateRequired = var1;
    }

    public void setExtraData(Map var1) {
        this.extraData = var1;
    }

    public Map getExtraData() {
        return this.extraData;
    }

    public void addExtraData(String var1, Object var2) {
        this.extraData.put(var1, var2);
    }

    public void setMessageLocation(String var1) {
        this.messageLocation = var1;
    }

    public String getMessageLocation() {
        return this.messageLocation;
    }

    /** @deprecated */
    @Deprecated
    protected void _handleAdditionalInfo(NmCommandBean var1) {
        if (var1 != null && var1.getRequest() != null && this.refreshInfo != null) {
            Iterator var2 = this.refreshInfo.iterator();

            while (var2.hasNext()) {
                DynamicRefreshInfo var3 = (DynamicRefreshInfo)var2.next();
                NmOid var4 = var3.getOid();
                if (var4 != null) {
                    HashMap var5 = var3.getOid().getAdditionalInfo();
                    if (var5 != null && !var5.isEmpty()) {
                        Object var6 = (List)var1.getRequest().getSession().getAttribute("OIDS_WITH_EXTRA_INFO");
                        if (var6 == null) {
                            var6 = new ArrayList();
                            var1.getRequest().getSession().setAttribute("OIDS_WITH_EXTRA_INFO", var6);
                        }

                        ((List)var6).add(var3.getOid());
                    }
                }
            }
        }

    }
}
