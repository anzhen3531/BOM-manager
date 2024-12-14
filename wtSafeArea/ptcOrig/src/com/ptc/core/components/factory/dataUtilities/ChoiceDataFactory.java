package com.ptc.windchill.option.choicecomponent.builders;

import com.ptc.core.components.beans.FormDataHolder;
import com.ptc.core.components.beans.TableDataManagerBean;
import com.ptc.core.foundation.type.server.impl.TypeHelper;
import com.ptc.core.meta.common.TypeIdentifier;
import com.ptc.jca.mvc.components.JcaComponentParams;
import com.ptc.mvc.components.ComponentParams;
import com.ptc.netmarkets.model.NmOid;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.netmarkets.util.beans.NmHelperBean;
import com.ptc.windchill.option.choicecomponent.CheckedStatus;
import com.ptc.windchill.option.choicecomponent.ChoiceComponentHelper;
import com.ptc.windchill.option.choicecomponent.ChoiceComponentUtility;
import com.ptc.windchill.option.choicecomponent.ChoiceDisplayInfo;
import com.ptc.windchill.option.choicecomponent.ChoiceInfoBean;
import com.ptc.windchill.option.choicecomponent.ChoiceSelectionStatus;
import com.ptc.windchill.option.choicecomponent.OptionInfoBean;
import com.ptc.windchill.option.choicecomponent.SelectableChoiceBean;
import com.ptc.windchill.option.choicecomponent.session.rules.engine.RulesEngineSession;
import com.ptc.windchill.option.delegate.OptionSetDelegateResult;
import com.ptc.windchill.option.expression.ExpressionHelper;
import com.ptc.windchill.option.expressions.ExpressionClientHelper;
import com.ptc.windchill.option.expressions.ExpressionDisplay;
import com.ptc.windchill.option.expressions.ExpressionLeaf;
import com.ptc.windchill.option.model.Choice;
import com.ptc.windchill.option.model.ChoiceMappableChoiceLink;
import com.ptc.windchill.option.model.Group;
import com.ptc.windchill.option.model.IndependentAssignedExpression;
import com.ptc.windchill.option.model.Option;
import com.ptc.windchill.option.model.OptionSet;
import com.ptc.windchill.option.server.ChoiceInfo;
import com.ptc.windchill.option.server.OptionsClientHelper;
import com.ptc.windchill.option.service.OptionHelper;
import com.ptc.windchill.option.service.OptionUtility;
import com.ptc.wpcfg.logic.BaseParameter;
import com.ptc.wpcfg.logic.ChoiceMemberDefinition;
import com.ptc.wpcfg.logic.Expression;
import com.ptc.wpcfg.logic.ExpressionNot;
import com.ptc.wpcfg.logic.ExpressionParameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import wt.fc.BinaryLink;
import wt.fc.ObjectIdentifier;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.fc.WTReference;
import wt.fc.collections.WTHashSet;
import wt.fc.collections.WTList;
import wt.fc.collections.WTValuedHashMap;
import wt.fc.collections.WTValuedMap;
import wt.inf.container.WTContainer;
import wt.inf.container.WTContainerRef;
import wt.log4j.LogR;
import wt.option.ChoiceMappable;
import wt.option.ExpressionAssignable;
import wt.option.Expressionable;
import wt.pom.PersistenceException;
import wt.util.WTException;

public abstract class ChoiceDataFactory extends AbstractChoiceSelectionFactory {
    public static final String ASSIGN_EXPRESSION_ITEM = "assignExpressionItem";
    public static final String ASSIGN_EXPRESSION_USES = "assignExpressionUses";
    public static final String ASSIGN_EXPRESSION_OCCURRENCES = "assignExpressionOccurrences";
    public static final String RULES_BASED = "rulesBased";
    public static final String MANAGE_CHOICE = "manageChoice";
    public static final String CHOICE_INFO_BEAN_FACTORY = "choiceInfoBeanFactory";
    public static final String INCLUDE_RULE = "includeRule";
    public static final String ENABLE_RULE = "enableRule";
    public static final String EXCLUDE_RULE = "excludeRule";
    private static final Logger logger = LogR.getLogger(ChoiceDataFactory.class.getName());
    protected ReferenceFactory referenceFactory;
    private TypeIdentifier typeIdentifier;
    private boolean initiallySelected = false;
    private boolean indAssignedExpMode = false;
    private boolean choiceNotAccessible = false;

    public ChoiceDataFactory() {
    }

    public static ChoiceDataFactory newInstance(ComponentParams var0) throws WTException {
        NmCommandBean var1 = getNmCommandBean(var0);
        return newInstance((FormDataHolder)var1);
    }

    public boolean isChoiceNotAccessible() {
        return this.choiceNotAccessible;
    }

    public void setChoiceNotAccessible(boolean var1) {
        this.choiceNotAccessible = var1;
    }

    public static ChoiceDataFactory newInstance(FormDataHolder var0) throws WTException {
        String var1 = var0.getTextParameter("choiceInfoBeanFactory");
        Object var2 = null;
        if ("rulesBased".equals(var1)) {
            var2 = new RulesBasedChoiceDataFactory();
        } else if ("assignExpressionItem".equals(var1)) {
            var2 = new AssignExpressionItemDataFactory();
        } else if ("assignExpressionUses".equals(var1)) {
            var2 = new AssignExpressionUsesDataFactory();
        } else if ("assignExpressionOccurrences".equals(var1)) {
            var2 = new AssignExpressionOccurrencesDataFactory();
        } else if ("manageChoice".equals(var1)) {
            var2 = new ManageOptionDataFactory();
        } else if ("includeRule".equals(var1)) {
            var2 = new IncludeRuleDataFactory();
        } else if ("enableRule".equals(var1)) {
            var2 = new EnableRuleDataFactory();
        } else {
            if (!"excludeRule".equals(var1)) {
                throw new WTException("No choice info bean factory type found.");
            }

            var2 = new ExcludeRuleDataFactory();
        }

        ((ChoiceDataFactory)var2).setFormData(var0);
        ((ChoiceDataFactory)var2).referenceFactory = new ReferenceFactory();
        return (ChoiceDataFactory)var2;
    }

    protected NmCommandBean getNmCommandBean() {
        return this.getFormData() instanceof NmCommandBean ? (NmCommandBean)this.getFormData() : null;
    }

    protected ReferenceFactory getReferenceFactory() {
        if (this.referenceFactory == null) {
            this.referenceFactory = new ReferenceFactory();
        }

        return this.referenceFactory;
    }

    private static NmCommandBean getNmCommandBean(ComponentParams var0) throws WTException {
        NmHelperBean var1 = ((JcaComponentParams)var0).getHelperBean();
        NmCommandBean var2 = var1.getNmCommandBean();
        return var2;
    }

    public boolean isInitiallySelected() {
        return this.initiallySelected;
    }

    public void setInitiallySelected(boolean var1) {
        this.initiallySelected = var1;
    }

    protected boolean enforceSingleSelect() {
        return false;
    }

    protected boolean isDisableSourceSiblings() {
        return false;
    }

    public abstract Collection<ChoiceInfoBean> getChoices() throws Exception;

    public abstract Collection<ChoiceInfoBean> getChoices(WTReference var1) throws Exception;

    public List<OptionInfoBean> getOptionInfoBeans() throws Exception {
        Collection var1 = this.getChoices();
        RulesEngineSession var2 = ChoiceComponentHelper.getRuleEngineSesssion(this.getFormData());
        ArrayList var3 = new ArrayList();
        TableDataManagerBean var4 = TableDataManagerBean.newInstance(this.getFormData());
        String var5 = ChoiceComponentHelper.getTableId(this.getFormData());
        boolean var6 = ChoiceComponentHelper.isRulesEnabled(this.getFormData());
        WTContainerRef var7 = ChoiceComponentHelper.getWTContainerRef(this.getFormData());
        WTHashSet var8 = new WTHashSet();

        ChoiceInfoBean var10;
        OptionInfoBean var14;
        for(Iterator var9 = var1.iterator(); var9.hasNext(); var14.addChoiceDisplayInfos(ChoiceDisplayInfo.newChoiceDisplayInfo(var10, var7))) {
            var10 = (ChoiceInfoBean)var9.next();
            if (var10 == null || var10.getOptionId() == null) {
                this.setChoiceNotAccessible(true);
                var3.clear();
                break;
            }

            String var11 = var10.getOptionId();
            WTReference var12 = this.getReferenceFactory().getReference(var11);
            NmOid var13 = new NmOid();
            var13.setWtRef(var12);
            if (!var3.contains(var13)) {
                var14 = new OptionInfoBean(var10);
                var14.setAllSelected(true);
                var3.add(var14);
                var8.add(var14.getWtRef());
            }

            var14 = (OptionInfoBean)var3.get(var3.indexOf(var13));
            Boolean var15 = null;
            if (var2 != null && var6) {
                var15 = this.isChecked(var10, var2);
            } else if (this.isInitiallySelected()) {
                var15 = var10.isSelected();
            }

            if (var15 == null) {
                WTReference var16 = this.getReferenceFactory().getReference(var10.getChoiceId());
                String var17 = var16 != null ? this.getReferenceFactory().getReferenceString(var16) : "";
                var15 = this.isChecked(var17, var13, var4, var5);
            }

            if (var15) {
                SelectableChoiceBean var18 = new SelectableChoiceBean(var10, var7);
                var18.setChoiceSelectionStatus(ChoiceSelectionStatus.CHECKED);
                if (this.isInitiallySelected() && var10.isExcludeEnabled()) {
                    var18.setExcludeStatus(var10.getExcludeStatus());
                }

                var14.addSelectedChoices(var18);
            } else {
                var14.setAllSelected(false);
            }

            if (var10.isExcludeEnabled()) {
                var14.setExcludeChoicesEnabled(true);
            }

            if (CheckedStatus.CHECKED.equals(var10.getExcludeStatus())) {
                var14.setExcludeChoices(true);
            }
        }

        return var3;
    }

    public List<SelectableChoiceBean> getSelectableChoices(WTReference var1) throws Exception {
        Collection var2 = this.getChoices(var1);
        return this.getSelectableChoices(var1, var2);
    }

    public JSONArray getChoiceData(WTReference var1, boolean var2) throws Exception {
        Collection var3 = this.getChoices(var1);
        RulesEngineSession var4 = ChoiceComponentHelper.getRuleEngineSesssion(this.getFormData());
        return this.getChoiceData(var1, var4, var3, var2);
    }

    public JSONArray getChoiceData(WTReference var1) throws Exception {
        return this.getChoiceData(var1, false);
    }

    public List<SelectableChoiceBean> getInitiallySelectedChoices() throws Exception {
        ArrayList var1 = new ArrayList();
        this.setInitiallySelected(true);
        List var2 = this.getOptionInfoBeans();
        Iterator var3 = var2.iterator();

        while(var3.hasNext()) {
            OptionInfoBean var4 = (OptionInfoBean)var3.next();
            var1.addAll(var4.getSelectedChoices());
        }

        return var1;
    }

    public OptionSet getRegisteredOptionset() throws Exception {
        OptionSetDelegateResult var1 = ChoiceComponentUtility.getOptionSetDelegateResult(this.getFormData());
        return var1.getOptionSet();
    }

    public List<ChoiceInfoBean> getChoices(WTReference var1, Choice var2) throws WTException {
        Collection var3 = this.getChoiceInfo(var1);
        return this.getChoices(var1, var3, var2);
    }

    public List<ChoiceInfoBean> getChoices(WTReference var1, Collection<ChoiceInfo> var2, Choice var3) throws WTException {
        OptionSet var4 = null;
        if (var1 != null && OptionSet.class.isAssignableFrom(var1.getReferencedClass())) {
            var4 = (OptionSet)var1.getObject();
        }

        WTValuedMap var5 = this.getChoiceOptionMap(var2);
        String var6 = "";
        String var7 = "";
        boolean var8 = this.isDisableSourceSiblings();
        if (var3 != null) {
            var6 = this.getChoiceId(var3);
            Option var9 = (Option)var5.getPersistable(var3);
            if (var9 == null) {
                var9 = OptionHelper.service.getOptionForChoice(var3);
                var5.put(var3, var9);
            }

            if (!var8) {
                var8 = var9.isSingleChoiceSelection();
            }

            var7 = ((ObjectIdentifier)var5.getReference(var3).getKey()).getStringValue();
        }

        ArrayList var17 = new ArrayList(var2.size());
        Iterator var10 = var2.iterator();

        while(var10.hasNext()) {
            ChoiceInfo var11 = (ChoiceInfo)var10.next();
            Choice var12 = var11.getChoice();
            Option var13 = (Option)var5.getPersistable(var12);
            Group var14 = var11.getGroup();
            ChoiceInfoBean var15 = new ChoiceInfoBean();
            String var16 = this.getChoiceId(var12);
            var15.setChoiceId(var16);
            var15.setOptionName(var13.getName());
            var15.setOptionDescription(var13.getDescription());
            var15.setUnitOfMeasure(OptionUtility.getDisplayUnit(var13));
            var15.setOptionId(PersistenceHelper.getObjectIdentifier(var13).getStringValue());
            var15.setOptionDataType(var13.getOptionDataType());
            if (var14 != null) {
                var15.setGroup(var14.getName());
                var15.setGroupId(PersistenceHelper.getObjectIdentifier(var14).getStringValue());
            }

            var15.setChoiceName(var12.getName());
            var15.setChoiceNumber(var12.getNumber());
            var15.setDescription(var12.getDescription());
            var15.setNumericDisplayValue(var12.getNumericDisplayValue());
            if (var12.getNumericValue() != null) {
                var15.setDataValue(var12.getNumericValue().doubleValue());
            }

            if (this.enforceSingleSelect()) {
                var15.setSingleSelect(var13.isSingleChoiceSelection());
            }

            var15.setEnabled(!var16.equals(var6) && (!var8 || !var15.getOptionId().equals(var7)));
            if (logger.isTraceEnabled()) {
                logger.trace("Adding choice '" + var16 + "'  for choice " + var12.getName());
            }

            var15.setCheckedStatus(CheckedStatus.UNCHECKED);
            if (var4 != null) {
                var15.setExcludeEnabled(var4.isSupportExclusionOptionChoices());
            }

            var15.setExcludeStatus(CheckedStatus.UNCHECKED);
            var17.add(var15);
        }

        this.setSelectionStatus(var4, var17);
        return var17;
    }

    private String getChoiceId(Choice var1) {
        return PersistenceHelper.getObjectIdentifier(var1).getStringValue();
    }

    protected Collection<?> getExistingSelectedChoices() throws WTException {
        return new ArrayList();
    }

    private void setSelectionStatus(OptionSet var1, List<ChoiceInfoBean> var2) throws WTException {
        if (this.isInitiallySelected()) {
            Collection var3 = this.getExistingSelectedChoices();
            if (var3.isEmpty()) {
                logger.debug("no choice mappable objects found.");
                return;
            }

            if (var3.iterator().next() instanceof ChoiceMappable) {
                this.setSelectionStatusFromChoiceMappables(var1, (Set)var3, var2);
            } else if (var3.iterator().next() instanceof Choice) {
                this.setSelectionStatusFromChoices(var1, (Set)var3, var2);
            }

            if (var3.iterator().next() instanceof IndependentAssignedExpression) {
                this.setSelectionStatusFromChoiceMappables((IndependentAssignedExpression)var3.iterator().next(), var1, var2);
            }
        } else {
            boolean var9 = this.isSupportExclusionOptionChoices(var1);
            Map var4 = this.getChoiceIdToBeanMap(var2, true);
            Set var5 = this.getSelectedChoices();
            Iterator var6 = var5.iterator();

            while(var6.hasNext()) {
                ChoiceInfoBean var7 = (ChoiceInfoBean)var6.next();
                if (var4.containsKey(var7.getChoiceId())) {
                    ChoiceInfoBean var8 = (ChoiceInfoBean)var4.get(var7.getChoiceId());
                    var8.setCheckedStatus(CheckedStatus.CHECKED);
                    if (var9 && CheckedStatus.CHECKED.equals(var7.getExcludeStatus())) {
                        var8.setExcludeStatus(CheckedStatus.CHECKED);
                    }
                }
            }
        }

    }

    private void setSelectionStatusFromChoiceMappables(OptionSet var1, Set<ChoiceMappable> var2, List<ChoiceInfoBean> var3) throws WTException {
        Map var4 = this.getChoiceIdToBeanMap(var3, false);
        boolean var5 = this.getIndAssignedExpMode();
        if (!ExpressionClientHelper.isModeToggled(this.formData)) {
            if (var5) {
                this.updateBeanFromIndependentExpressions(var1, var2, var4);
            } else {
                this.updateBeanFromChoiceMappableChoiceLinks(var1, var2, var4);
            }
        }

    }

    private void setSelectionStatusFromChoiceMappables(IndependentAssignedExpression var1, OptionSet var2, List<ChoiceInfoBean> var3) throws WTException {
        Map var4 = this.getChoiceIdToBeanMap(var3, false);
        this.updateBeanFromIndependentExpressions(var1, var2, var4);
    }

    private void updateBeanFromIndependentExpressions(OptionSet var1, Set<ChoiceMappable> var2, Map<String, ChoiceInfoBean> var3) throws WTException, PersistenceException {
        ExpressionAssignable var4 = (ExpressionAssignable)var2.iterator().next();
        Expressionable var5 = ExpressionClientHelper.getExpressionable(this.getFormData(), var4);
        this.updateBeanFromIndependentExpressions((IndependentAssignedExpression)var5, var1, var3);
    }

    private void updateBeanFromIndependentExpressions(IndependentAssignedExpression var1, OptionSet var2, Map<String, ChoiceInfoBean> var3) throws WTException {
        Map var4 = ExpressionHelper.parseExpression(var1, var2, OptionsClientHelper.getContainerReference((NmCommandBean)this.formData), true);
        if (var4 != null) {
            Iterator var5 = var4.entrySet().iterator();

            while(var5.hasNext()) {
                Map.Entry var6 = (Map.Entry)var5.next();
                this.updateBeanFromExpressionObject((Expression)var6.getValue(), var3);
            }
        }

    }

    private void updateBeanFromExpressionObject(Expression var1, Map<String, ChoiceInfoBean> var2) throws WTException {
        ExpressionDisplay var3 = ExpressionDisplay.newInstance(var1);
        if (!(var3 instanceof ExpressionLeaf)) {
            for(int var4 = 0; var4 < var1.getNumArgs(); ++var4) {
                Expression var5 = var1.getArg(var4);
                this.updateBeanFromExpressionObject(var5, var2);
            }
        } else {
            CheckedStatus var11 = CheckedStatus.UNCHECKED;
            ExpressionLeaf var12 = (ExpressionLeaf)var3;
            ExpressionParameter var6 = var12.getExpressionParameter();
            if (var12.getExpression() instanceof ExpressionNot) {
                var11 = CheckedStatus.CHECKED;
            }

            BaseParameter var7 = var6.getDefinition();
            if (var7 instanceof ChoiceMemberDefinition) {
                ChoiceMemberDefinition var8 = (ChoiceMemberDefinition)var7;
                String var9 = ExpressionHelper.getObjectReference(var8).toString();
                if (var2.containsKey(var9)) {
                    ChoiceInfoBean var10 = (ChoiceInfoBean)var2.get(var9);
                    var10.setCheckedStatus(CheckedStatus.CHECKED);
                    var10.setExcludeStatus(var11);
                }
            }
        }

    }

    private void updateBeanFromChoiceMappableChoiceLinks(OptionSet var1, Set<ChoiceMappable> var2, Map<String, ChoiceInfoBean> var3) throws WTException {
        boolean var4 = this.isSupportExclusionOptionChoices(var1);
        TypeIdentifier var5 = this.getTypeIdentifier();
        QueryResult var6 = OptionHelper.service.getChoiceMappableChoiceLinks(var1, var2, var5, true);

        while(var6.hasMoreElements()) {
            Persistable[] var7 = (Persistable[])((Persistable[])var6.nextElement());
            Choice var8 = (Choice)var7[1];
            String var9 = this.getChoiceId(var8);
            if (var3.containsKey(var9)) {
                ChoiceInfoBean var10 = (ChoiceInfoBean)var3.get(var9);
                var10.setCheckedStatus(CheckedStatus.CHECKED);
                ChoiceMappableChoiceLink var11 = (ChoiceMappableChoiceLink)var7[0];
                if (var4 && var11.isExclude()) {
                    var10.setExcludeStatus(CheckedStatus.CHECKED);
                }
            }
        }

    }

    private void setSelectionStatusFromChoices(OptionSet var1, Set<Choice> var2, List<ChoiceInfoBean> var3) throws WTException {
        Map var4 = this.getChoiceIdToBeanMap(var3, false);
        Iterator var5 = var2.iterator();

        while(var5.hasNext()) {
            Choice var6 = (Choice)var5.next();
            String var7 = this.getChoiceId(var6);
            if (var4.containsKey(var7)) {
                ChoiceInfoBean var8 = (ChoiceInfoBean)var4.get(var7);
                var8.setCheckedStatus(CheckedStatus.CHECKED);
            }
        }

    }

    private boolean isSupportExclusionOptionChoices(OptionSet var1) {
        return var1 != null && var1.isSupportExclusionOptionChoices();
    }

    private Map<String, ChoiceInfoBean> getChoiceIdToBeanMap(List<ChoiceInfoBean> var1, boolean var2) throws WTException {
        HashMap var3 = new HashMap(var1.size());

        ChoiceInfoBean var5;
        String var6;
        for(Iterator var4 = var1.iterator(); var4.hasNext(); var3.put(var6, var5)) {
            var5 = (ChoiceInfoBean)var4.next();
            var6 = var5.getChoiceId();
            if (var2) {
                WTReference var7 = this.referenceFactory.getReference(var6);
                var6 = this.referenceFactory.getReferenceString(var7);
            }
        }

        return var3;
    }

    private WTValuedMap getChoiceOptionMap(Collection<ChoiceInfo> var1) {
        WTValuedHashMap var2 = new WTValuedHashMap(WTValuedHashMap.getInitialCapacity(var1.size()));
        Iterator var3 = var1.iterator();

        while(var3.hasNext()) {
            ChoiceInfo var4 = (ChoiceInfo)var3.next();
            var2.put(var4.getChoice(), var4.getOption());
        }

        return var2;
    }

    private Collection<ChoiceInfo> getChoiceInfo(WTReference var1) throws WTException {
        Object var2 = new ArrayList();
        if (WTContainer.class.isAssignableFrom(var1.getReferencedClass())) {
            WTContainerRef var3 = WTContainerRef.newWTContainerRef((ObjectIdentifier)var1.getKey());
            var2 = OptionsClientHelper.service.getChoiceInfo(var3, this.getTypeIdentifier(), false, true);
        } else if (OptionSet.class.isAssignableFrom(var1.getReferencedClass())) {
            var2 = OptionsClientHelper.service.getChoiceInfo((OptionSet)var1.getObject(), this.getTypeIdentifier(), false, true);
        }

        return (Collection)var2;
    }

    protected TypeIdentifier getTypeIdentifier() {
        if (this.typeIdentifier == null) {
            String var1 = this.getFormData().getTextParameter("typeIdentity");
            if (var1 != null && !var1.isEmpty()) {
                this.typeIdentifier = TypeHelper.getTypeIdentifier(var1);
            }
        }

        return this.typeIdentifier;
    }

    protected WTReference getOptionSetReference() throws WTException {
        OptionSetDelegateResult var1;
        try {
            var1 = ChoiceComponentUtility.getOptionSetDelegateResult(this.getNmCommandBean());
        } catch (Exception var4) {
            throw new WTException(var4);
        }

        OptionSet var2 = var1.getOptionSet();
        WTReference var3 = null;
        if (var2 != null) {
            var3 = this.getReferenceFactory().getReference(var2);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("OptionSet ref : " + var3);
        }

        return var3;
    }

    public boolean handleCheckOut() {
        return false;
    }

    public Map<Choice, BinaryLink> getExistingChoices(Persistable var1, WTList var2) throws WTException {
        throw new WTException("No implementation found");
    }

    public BinaryLink createChoiceLink(Persistable var1, Choice var2) throws WTException {
        throw new WTException("No create link implementation found.");
    }

    public boolean useRules() {
        return false;
    }

    public boolean enableOptionSelection() {
        return false;
    }

    protected void setIndAssignedExpMode(boolean var1) throws WTException {
        this.indAssignedExpMode = var1;
    }

    public boolean getIndAssignedExpMode() {
        return this.indAssignedExpMode;
    }
}
