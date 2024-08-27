package ext.ziang.part.builder;

import com.ptc.jca.mvc.components.JcaComponentParams;
import com.ptc.mvc.components.*;
import com.ptc.netmarkets.util.beans.NmCommandBean;

import ext.ziang.part.model.derive.PartDeriveLink;
import wt.fc.PersistenceHelper;
import wt.part.WTPart;

/**
 * 验证料号列表
 */
@ComponentBuilder("ext.ziang.part.builder.PartDerivedBuilder")
public class PartDerivedBuilder extends AbstractComponentBuilder {

    public static final String DERIVED_FOR_NAME = "derivedForName";
    public static final String DERIVED_FOR_NUMBER = "derivedForNumber";
    public static final String DERIVES_NUMBER = "derivesNumber";
    public static final String DERIVES_NAME = "derivesName";
    public static final String STATE = "state";

    @Override
    public ComponentConfig buildComponentConfig(ComponentParams componentParams) {
        // 获取组件配置工厂
        ComponentConfigFactory factory = getComponentConfigFactory();
        TableConfig result = factory.newTableConfig();
        result.setLabel("查看衍生部件");
        result.setSelectable(false);
        result.setId("DerivedPartBuilder");
        // 设置展示数量
        result.setShowCount(true);
        result.setConfigurable(true);
        createNewColumnConfig(DERIVED_FOR_NUMBER, "衍生源物料编号", result, factory);
        createNewColumnConfig(DERIVED_FOR_NAME, "衍生源物料名称", result, factory);
        createNewColumnConfig(DERIVES_NUMBER, "衍生物料编号", result, factory);
        createNewColumnConfig(DERIVES_NAME, "衍生物料名称", result, factory);
        createNewColumnConfig(STATE, result, factory);
        createNewColumnConfig("thePersistInfo.createStamp", result, factory);
        createNewColumnConfig("thePersistInfo.modifyStamp", result, factory);
        return result;
    }

    @Override
    public Object buildComponentData(ComponentConfig componentConfig, ComponentParams componentParams)
        throws Exception {

        NmCommandBean nmCommandBean = ((JcaComponentParams)componentParams).getHelperBean().getNmCommandBean();
        Object refObject = nmCommandBean.getPrimaryOid().getRefObject();
        if (refObject instanceof WTPart) {
            WTPart wtPart = (WTPart)refObject;
            return PersistenceHelper.manager.navigate(wtPart.getMaster(), PartDeriveLink.DERIVES_ROLE,
                PartDeriveLink.class, false);
        }
        return null;
    }

    /**
     * 创建新列配置
     *
     * @param columnConfigName 列配置名称
     * @param result 结果
     * @param factory 厂
     */
    public static void createNewColumnConfig(String columnConfigName, String displayName, TableConfig result,
        ComponentConfigFactory factory) {
        ColumnConfig columnConfig = factory.newColumnConfig(columnConfigName, displayName, true);
        columnConfig.setVariableHeight(true);
        columnConfig.setSortable(true);
        columnConfig.setDataUtilityId("partDerivedDataUtility");
        result.addComponent(columnConfig);
    }

    /**
     * 创建新列配置
     *
     * @param columnConfigName 列配置名称
     * @param result 结果
     * @param factory 厂
     */
    public static void createNewColumnConfig(String columnConfigName, TableConfig result,
        ComponentConfigFactory factory) {
        createNewColumnConfig(columnConfigName, null, result, factory);
    }

}
