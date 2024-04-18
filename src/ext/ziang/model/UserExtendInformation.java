package ext.ziang.model;

import java.io.Externalizable;

import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.OracleTableSize;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;
import com.ptc.windchill.annotations.metadata.SupportedAPI;
import com.ptc.windchill.annotations.metadata.TableProperties;

import wt.fc.WTObject;
import wt.util.WTException;

/**
 * 用户扩展信息
 *
 * @author anzhen
 * @date 2024/04/11
 */
// @format:off
@GenAsPersistable(superClass = WTObject.class,
		interfaces = { Externalizable.class },
		properties = {
		@GeneratedProperty(name = "username",
				type = String.class,
				constraints = @PropertyConstraints(upperLimit = 128, required = true),
				supportedAPI = SupportedAPI.PUBLIC, javaDoc = "用户名"),
		@GeneratedProperty(name = "password",
				type = String.class,
				constraints = @PropertyConstraints(upperLimit = 128 , required = true),
				supportedAPI = SupportedAPI.PUBLIC, javaDoc = "密码"),
		@GeneratedProperty(name = "alternateUserName1",
				type = String.class,
				constraints = @PropertyConstraints(upperLimit = 128),
				supportedAPI = SupportedAPI.PUBLIC, javaDoc = "别名1"),
		@GeneratedProperty(name = "alternateUserName2",
				type = String.class,
				constraints = @PropertyConstraints(upperLimit = 128),
				supportedAPI = SupportedAPI.PUBLIC, javaDoc = "别名2"),
		@GeneratedProperty(name = "alternateUserName3",
				type = String.class,
				constraints = @PropertyConstraints(upperLimit = 128),
				supportedAPI = SupportedAPI.PUBLIC, javaDoc = "别名3"),
		@GeneratedProperty(name = "alternateUserName4",
				type = String.class,
				constraints = @PropertyConstraints(upperLimit = 128),
				supportedAPI = SupportedAPI.PUBLIC, javaDoc = "别名4"),
		@GeneratedProperty(name = "state",
				type = Integer.class,
				constraints = @PropertyConstraints(upperLimit = 2),
				supportedAPI = SupportedAPI.PUBLIC, javaDoc = "状态")
}, tableProperties =
@TableProperties(compositeIndex2 = "+ username + password", oracleTableSize = OracleTableSize.HUGE))
// @format:on
public class UserExtendInformation extends _UserExtendInformation {
	/**
	 * 序列化id
	 */
	static final long serialVersionUID = 1L;

	public static UserExtendInformation newUserExtendInformation() throws WTException {
		final UserExtendInformation userExtendInformation =  new UserExtendInformation();
		userExtendInformation.initialize();
		return userExtendInformation;
	}
}
