package ext.ziang.report.builder;

import com.ptc.mvc.components.*;

import ext.ziang.report.model.ReportFormConfig;
import wt.fc.PersistenceHelper;
import wt.query.QuerySpec;

/**
 * 定制配置SQL的表格
 */
@ComponentBuilder("ext.ziang.report.builder.PartDerivedBuilder")
public class ReportFormBuilder extends AbstractComponentBuilder {

    public static final String DESCRIPTION = "description";
    public static final String CONTENT = "content";
    public static final String STATE = "state";
    public static final String CREATOR = "creator";
    public static final String MODIFIER = "modifier";
    public static final String ID = "id";

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
        createNewColumnConfig(ID, "id", result, factory, true);
        createNewColumnConfig(DESCRIPTION, "描述", result, factory, false);
        createNewColumnConfig(CONTENT, "SQL内容", result, factory, false);
        createNewColumnConfig(STATE, "状态", result, factory, true);
        createNewColumnConfig(CREATOR, "创建者", result, factory, false);
        createNewColumnConfig(MODIFIER, "修改者", result, factory, false);
        createNewColumnConfig("thePersistInfo.createStamp", result, factory, false);
        createNewColumnConfig("thePersistInfo.modifyStamp", result, factory, false);
        return result;
    }

    @Override
    public Object buildComponentData(ComponentConfig componentConfig, ComponentParams componentParams)
        throws Exception {
        QuerySpec querySpec = new QuerySpec(ReportFormConfig.class);
        return PersistenceHelper.manager.find(querySpec);
    }

    /**
     * 创建新列配置
     *
     * @param columnConfigName 列配置名称
     * @param result 结果
     * @param factory 厂
     */
    public static void createNewColumnConfig(String columnConfigName, String displayName, TableConfig result,
        ComponentConfigFactory factory, boolean flag) {
        ColumnConfig columnConfig = factory.newColumnConfig(columnConfigName, displayName, true);
        columnConfig.setVariableHeight(true);
        columnConfig.setSortable(true);
        if (flag) {
            columnConfig.setDataUtilityId("reportFormDataUtility");
        }
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
        ComponentConfigFactory factory, boolean flag) {
        createNewColumnConfig(columnConfigName, null, result, factory, flag);
    }
}