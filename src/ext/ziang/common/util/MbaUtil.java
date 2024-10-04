package ext.ziang.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.ptc.core.lwc.client.util.PropertyDefinitionHelper;
import com.ptc.core.meta.container.common.AttributeTypeSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ptc.core.lwc.client.commands.LWCCommands;
import com.ptc.core.lwc.common.view.AttributeDefinitionReadView;
import com.ptc.core.lwc.server.LWCFlexAttDefinition;
import com.ptc.core.lwc.server.LWCTypeDefinition;
import com.ptc.core.lwc.server.PersistableAdapter;
import com.ptc.core.meta.common.SearchOperationIdentifier;
import com.ptc.core.meta.common.TypeIdentifier;
import com.ptc.core.meta.common.UpdateOperationIdentifier;

import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import wt.type.TypedUtility;
import wt.units.FloatingPointWithUnits;
import wt.units.Unit;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.wip.WorkInProgressHelper;
import wt.vc.wip.Workable;

public class MbaUtil {
    // 日志助手
    private static final Logger log = LoggerFactory.getLogger(MbaUtil.class);

    /**
     * 获取局部属性
     *
     * @param object 物料
     * @return map
     */
    public static Map<String, Object> findAllMBAValue(Object object) {
        Map<String, Object> mapping = new HashMap<>();
        boolean enforce = true;
        try {
            enforce = wt.session.SessionServerHelper.manager.setAccessEnforced(false);
            TypeIdentifier typeIdentifier = TypedUtility.getTypeIdentifier(object);
            String typeInternalName = typeIdentifier.getTypeInternalName();
            LWCTypeDefinition lwcTypeDefinition = findTypeDefinitionByInnerName(typeInternalName);
            if (Objects.isNull(lwcTypeDefinition)) {
                return mapping;
            }
            String typeOid = ToolUtils.getOROid(lwcTypeDefinition);
            ArrayList<AttributeDefinitionReadView> typeAttributes = LWCCommands.getTypeAttributes(typeOid);
            PersistableAdapter persistableAdapter = new PersistableAdapter((Persistable)object, null,
                SessionHelper.getLocale(), new SearchOperationIdentifier());
            for (AttributeDefinitionReadView typeAttribute : typeAttributes) {
                if (typeAttribute.getAttDefClass().equals(LWCFlexAttDefinition.class.getName())) {
                    String name = typeAttribute.getName();
                    persistableAdapter.load(name);
                    mapping.put(name, null);
                }
            }
            for (Map.Entry<String, Object> entry : mapping.entrySet()) {
                String key = entry.getKey();
                mapping.put(key, persistableAdapter.get(key));
            }
            return mapping;
        } catch (WTException e) {
            log.error("查询失败", e);
        } finally {
            wt.session.SessionServerHelper.manager.setAccessEnforced(enforce);
        }
        return mapping;
    }

    /**
     * 通过内部名称查询类型定义
     * 
     * @param innerName 内部名称
     * @return
     * @throws WTException
     */
    public static LWCTypeDefinition findTypeDefinitionByInnerName(String innerName) throws WTException {
        QuerySpec querySpec = new QuerySpec(LWCTypeDefinition.class);
        querySpec.appendWhere(
            new SearchCondition(LWCTypeDefinition.class, LWCTypeDefinition.NAME, SearchCondition.EQUAL, innerName),
            new int[] {0});
        log.debug("findTypeDefinitionByInnerName sql {}", querySpec);
        QueryResult queryResult = PersistenceHelper.manager.find(querySpec);
        if (queryResult.hasMoreElements()) {
            return (LWCTypeDefinition)queryResult.nextElement();
        }
        return null;
    }

    /**
     * 设置MBA属性
     *
     * @param object 对象
     * @param key 字段名
     * @param value 字段值
     */
    public static void setMBAValue(Object object, String key, Object value)
        throws WTException, WTPropertyVetoException {
        Map<String, Object> allMBAValue = findAllMBAValue(object);
        if (allMBAValue.containsKey(key)) {
            Workable workable = WorkInProgressHelper.service
                .checkout((Workable)object, WorkInProgressHelper.service.getCheckoutFolder(), null).getWorkingCopy();
            PersistableAdapter persistableAdapter = new PersistableAdapter(workable, null,
                SessionHelper.getLocale(), new UpdateOperationIdentifier());
            persistableAdapter.load(key);
            persistableAdapter.set(key, value);
            persistableAdapter.apply();
            PersistenceHelper.manager.modify(workable);
            WorkInProgressHelper.service.checkin(workable, null);
        }
    }

    /**
     * 设置带单位的实数 MBA
     *
     * @param workable
     * @param attributeName
     * @param attributeValue
     * @return
     * @throws WTException
     * @throws WTPropertyVetoException wtproperty 否决异常
     */
    public static Workable setUnitAttributeValue(Workable workable, String attributeName, String attributeValue)
        throws WTException, WTPropertyVetoException {

        workable = WorkInProgressHelper.service
            .checkout(workable, WorkInProgressHelper.service.getCheckoutFolder(), null).getWorkingCopy();
        PersistableAdapter adapter =
            new PersistableAdapter(workable, null, SessionHelper.getLocale(), new UpdateOperationIdentifier());
        adapter.load(attributeName);
        // 获取属性相关的展示信息
        AttributeTypeSummary attInfo = adapter.getAttributeDescriptor(attributeName);
        // 获取单位显示值
        String displayUnit = attInfo.getDisplayUnits(PropertyDefinitionHelper.getMeasurementSystem());
        // 创建单位属性
        Unit value = new Unit(attributeValue, -1, displayUnit);
        adapter.set(attributeName, new FloatingPointWithUnits(value));
        adapter.apply();
        PersistenceHelper.manager.modify(workable);
        return WorkInProgressHelper.service.checkin(workable, null);
    }
}
