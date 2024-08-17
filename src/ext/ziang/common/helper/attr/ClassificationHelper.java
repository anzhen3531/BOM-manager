package ext.ziang.common.helper.attr;

import java.util.*;

import org.apache.commons.collections.CollectionUtils;

import com.ptc.core.lwc.common.view.AttributeDefinitionReadView;
import com.ptc.core.lwc.common.view.PropertyValueReadView;
import com.ptc.core.lwc.common.view.TypeDefinitionReadView;
import com.ptc.windchill.csm.client.helpers.CSMTypeDefHelper;

import org.apache.commons.lang3.StringUtils;
import wt.util.WTException;
import wt.viewmarkup._multiFidelityDerivedImageLink;

/**
 * 分类属性查询助手
 * <p>
 * PropertyDefinitionConstants 属性常量
 * <p>
 * CSMTypeDefHelper 类型助手
 */
public class ClassificationHelper {

    /**
     * 获取分类属性列表
     *
     * @param innerName 分类属性id
     * @param propertyName 属性ID 例如显示名称 displayName等
     * @param notProperty 不需要属性
     * @return map 集合
     * @throws WTException WTException
     */
    public static Map<String, String> findClassificationAttrByName(String innerName, String propertyName,
        boolean notProperty) throws WTException {
        Map<String, String> classificationAttrMap = new HashMap<>();
        // 获取分类属性视图
        TypeDefinitionReadView classificationTypeDefView = CSMTypeDefHelper.getClassificationTypeDefView(innerName);
        if (Objects.isNull(classificationTypeDefView)) {
            return classificationAttrMap;
        }
        Collection<AttributeDefinitionReadView> allAttributes = classificationTypeDefView.getAllAttributes();
        if (CollectionUtils.isEmpty(allAttributes)) {
            return classificationAttrMap;
        }
        for (AttributeDefinitionReadView attributeView : allAttributes) {
            String name = attributeView.getName();
            if (notProperty && StringUtils.isNotBlank(propertyName)) {
                PropertyValueReadView propertyValueReadView = attributeView.getPropertyValueByName(propertyName);
                String valueAsString = propertyValueReadView.getValueAsString();
                classificationAttrMap.put(name, valueAsString);
            } else {
                classificationAttrMap.put(name, null);
            }
        }
        return classificationAttrMap;
    }

    /**
     * 通过分类节点获取分类属性
     * 
     * @param innerName 分类节点
     * @return 分类属性内部名称
     * @throws WTException 查询异常
     */
    public static Set<String> findClassificationAttr(String innerName) throws WTException {
        return findClassificationAttrByName(innerName, null, true).keySet();
    }
}
