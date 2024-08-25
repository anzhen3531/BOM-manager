package ext.ziang.part.builder;

import com.ptc.jca.mvc.components.JcaComponentParams;
import com.ptc.mvc.components.*;
import com.ptc.netmarkets.model.NmOid;
import com.ptc.netmarkets.util.beans.NmCommandBean;

import ext.ziang.part.entity.DerivedPartLinkInfo;
import ext.ziang.part.model.derive.PartDeriveLink;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.part.WTPartMaster;
import wt.util.WTException;

import java.util.ArrayList;
import java.util.List;

/**
 * 验证料号列表
 */
@ComponentBuilder("ext.ziang.part.builder.DerivedPartBuilder")
public class DerivedPartBuilder extends AbstractComponentBuilder {

    public static final String DERIVED_FOR_NAME = "derivedForName";
    public static final String DERIVED_FOR_NUMBER = "derivedForNumber";
    public static final String DERIVES_NUMBER = "derivesNumber";
    public static final String DERIVES_NAME = "derivesName";
    public static final String STATE = "state";

    @Override
    public ComponentConfig buildComponentConfig(ComponentParams componentParams) throws WTException {
        // 获取组件配置工厂
        ComponentConfigFactory factory = getComponentConfigFactory();
        TableConfig result = factory.newTableConfig();
        result.setLabel("查看衍生部件");
        result.setSelectable(false);
        result.setId("DerivedPartBuilder");
        // 设置展示数量
        result.setShowCount(true);
        result.setConfigurable(true);
        createNewColumnConfig(DERIVED_FOR_NUMBER, result, factory, true);
        createNewColumnConfig(DERIVED_FOR_NAME, result, factory, true);
        createNewColumnConfig(DERIVES_NUMBER, result, factory, true);
        createNewColumnConfig(DERIVES_NAME, result, factory, true);
        createNewColumnConfig(STATE, result, factory, true);
        createNewColumnConfig("creator", result, factory, false);
        createNewColumnConfig("modifier", result, factory, false);
        createNewColumnConfig("thePersistInfo.createStamp", result, factory, false);
        createNewColumnConfig("thePersistInfo.modifyStamp", result, factory, false);
        return result;
    }

    @Override
    public Object buildComponentData(ComponentConfig componentConfig, ComponentParams componentParams)
        throws Exception {

        NmCommandBean nmCommandBean = ((JcaComponentParams)componentParams).getHelperBean().getNmCommandBean();
        NmOid primaryOid = nmCommandBean.getPrimaryOid();
        return convertBean(PersistenceHelper.manager.navigate((Persistable)primaryOid.getRefObject(),
            PartDeriveLink.DERIVES_ROLE, PartDeriveLink.class, false));
    }

    /**
     * 将查询数据转换Bean对象
     * 
     * @param navigate 查询结果
     * @return bean对象结集合
     * @throws WTException 设置oid异常
     */
    private List<DerivedPartLinkInfo> convertBean(QueryResult navigate) throws WTException {
        List<DerivedPartLinkInfo> infos = new ArrayList<>(navigate.size());
        while (navigate.hasMoreElements()) {
            PartDeriveLink partDeriveLink = (PartDeriveLink)navigate.nextElement();
            DerivedPartLinkInfo info = new DerivedPartLinkInfo();
            WTPartMaster derives = partDeriveLink.getDerives();
            WTPartMaster deriveFor = partDeriveLink.getDeriveFor();
            String state = partDeriveLink.getState();
            info.setDerivedForName(deriveFor.getName());
            info.setDerivedForNumber(deriveFor.getNumber());
            info.setDerivesName(derives.getName());
            info.setDerivesNumber(derives.getNumber());
            info.setState(state);
            info.setOid(PersistenceHelper.getObjectIdentifier(partDeriveLink));
            infos.add(info);
        }
        return infos;
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
        ColumnConfig modifyStamp = factory.newColumnConfig(columnConfigName, true);
        modifyStamp.setVariableHeight(true);
        if (flag) {
            modifyStamp.setDataUtilityId("DerivedDataUtility");
        }
        result.addComponent(modifyStamp);
    }
}
