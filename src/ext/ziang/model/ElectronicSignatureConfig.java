package ext.ziang.model;

import java.io.Externalizable;

import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;
import com.ptc.windchill.annotations.metadata.SupportedAPI;

import wt.fc.ObjectReference;
import wt.fc.WTObject;
import wt.util.WTException;

/**
 * 电子签名配置
 *
 * @author anzhen
 * @date 2024/04/16
 */
@GenAsPersistable(superClass = WTObject.class,
        interfaces = { Externalizable.class },
        properties = {
				@GeneratedProperty(
						name = "objectType",
						type = ObjectReference.class
				),
				@GeneratedProperty(name = "contentType",
						type = String.class,
						constraints = @PropertyConstraints(upperLimit = 128 , required = true),
						supportedAPI = SupportedAPI.PUBLIC, javaDoc = "内容类型"),
                @GeneratedProperty(name = "workItemName",
                        type = String.class,
                        constraints = @PropertyConstraints(upperLimit = 128 , required = true),
                        supportedAPI = SupportedAPI.PUBLIC, javaDoc = "活动名称"),
                @GeneratedProperty(name = "signXIndex",
                        type = String.class,
                        constraints = @PropertyConstraints(upperLimit = 128),
                        supportedAPI = SupportedAPI.PUBLIC, javaDoc = "签字X轴索引"),
                @GeneratedProperty(name = "signYIndex",
                        type = String.class,
                        constraints = @PropertyConstraints(upperLimit = 128),
                        supportedAPI = SupportedAPI.PUBLIC, javaDoc = "签字Y轴索引"),
                @GeneratedProperty(name = "status",
                        type = Integer.class,
                        constraints = @PropertyConstraints(upperLimit = 2),
                        supportedAPI = SupportedAPI.PUBLIC, javaDoc = "状态"),
                @GeneratedProperty(name = "extendedField",
                        type = String.class,
                        constraints = @PropertyConstraints(upperLimit = 128),
                        supportedAPI = SupportedAPI.PUBLIC, javaDoc = "扩展字段"),
                @GeneratedProperty(name = "extendedField1",
                        type = String.class,
                        constraints = @PropertyConstraints(upperLimit = 128),
                        supportedAPI = SupportedAPI.PUBLIC, javaDoc = "扩展字段1"),
				@GeneratedProperty(name = "extendedField2",
						type = String.class,
						constraints = @PropertyConstraints(upperLimit = 128),
						supportedAPI = SupportedAPI.PUBLIC, javaDoc = "扩展字段2")
        })
public class ElectronicSignatureConfig extends _ElectronicSignatureConfig {
	/**
	 * 序列化id
	 */
	static final long serialVersionUID = 1L;

	public static ElectronicSignatureConfig newElectronicSignatureConfig() throws WTException {
		final ElectronicSignatureConfig newElectronicSignatureConfig =  new ElectronicSignatureConfig();
		newElectronicSignatureConfig.initialize();
		return newElectronicSignatureConfig;
	}
}
