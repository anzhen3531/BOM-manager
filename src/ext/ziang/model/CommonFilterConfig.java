package ext.ziang.model;

import java.io.Externalizable;

import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.IconProperties;
import com.ptc.windchill.annotations.metadata.OracleTableSize;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;
import com.ptc.windchill.annotations.metadata.SupportedAPI;
import com.ptc.windchill.annotations.metadata.TableProperties;

import wt.fc.WTObject;
import wt.util.WTException;

/**
 * @author anzhen
 * @date 2024/01/31
 */
// @format:off
@GenAsPersistable(superClass = WTObject.class, interfaces = {Externalizable.class}, properties = {
        @GeneratedProperty(name = "actionName",
                type = String.class,
                constraints = @PropertyConstraints(upperLimit = 64, required = true),
                supportedAPI = SupportedAPI.PUBLIC, javaDoc = "按钮名称"),
        @GeneratedProperty(name = "roleName",
                type = String.class,
                constraints = @PropertyConstraints(upperLimit = 64),
                supportedAPI = SupportedAPI.PUBLIC, javaDoc = "角色"),
        @GeneratedProperty(name = "groupName",
                type = String.class,
                constraints = @PropertyConstraints(upperLimit = 64),
                supportedAPI = SupportedAPI.PUBLIC, javaDoc = "团队"),
        @GeneratedProperty(name = "groupInnerName",
                type = String.class,
                constraints = @PropertyConstraints(upperLimit = 64),
                supportedAPI = SupportedAPI.PUBLIC, javaDoc = "团队内部名称"),
        @GeneratedProperty(name = "typeName",
                type = String.class,
                constraints = @PropertyConstraints(upperLimit = 64),
                supportedAPI = SupportedAPI.PUBLIC, javaDoc = "类型名称"),
        @GeneratedProperty(name = "typeInnerName",
                type = String.class,
                constraints = @PropertyConstraints(upperLimit = 128, required = true),
                supportedAPI = SupportedAPI.PUBLIC, javaDoc = "类型内部名称"),
        @GeneratedProperty(name = "lifecycleState",
                type = String.class,
                constraints = @PropertyConstraints(upperLimit = 64),
                supportedAPI = SupportedAPI.PUBLIC, javaDoc = "生命周期状态")
}, tableProperties = @TableProperties(
        compositeIndex2 = "+ actionName + lifecycleState",
        oracleTableSize = OracleTableSize.HUGE),
        iconProperties = @IconProperties(standardIcon = "wtcore/images/part.gif", openIcon = "wtcore/images/part.gif")
)
// @format:on
public class CommonFilterConfig extends _CommonFilterConfig {
    /**
     * 序列化id
     */
    static final long serialVersionUID = 1L;

    public static CommonFilterConfig newCommonFilterConfig() throws WTException {
        final CommonFilterConfig commonFilterConfig = new CommonFilterConfig();
        commonFilterConfig.initialize();
        return commonFilterConfig;
    }

    /**
     * 获取 Flex 类型 ID 路径
     *
     * @return {@link String}
     */
    public String getFlexTypeIdPath() {
        return null;
    }
}
