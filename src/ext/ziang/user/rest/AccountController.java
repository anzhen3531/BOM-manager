package ext.ziang.user.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.RequestBody;

import com.sun.jndi.toolkit.chars.BASE64Encoder;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import ext.ziang.common.result.Result;
import ext.ziang.user.entity.UserExtendedInformation;
import ext.ziang.user.form.AccountForm;
import ext.ziang.user.service.UserExtendedInformationService;
import ext.ziang.user.service.UserExtendedInformationServiceImpl;
import io.swagger.annotations.Api;

/**
 * 帐户控制器
 *
 * @author anzhen
 * @date 2024/03/28
 */

@Api(value = "用户登录接口")
@Path("/account")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_HTML, MediaType.MULTIPART_FORM_DATA })
@Produces(MediaType.APPLICATION_JSON)
public class AccountController {
	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);


	// 通过数据库查询相关的数据
	UserExtendedInformationService service = new UserExtendedInformationServiceImpl();

	/**
	 * 访问令牌
	 *
	 * @param form 形式
	 * @return 创建成功
	 */
	@POST
	@Path("/token")
	public Result accessToken(@RequestBody AccountForm form) {
		try {
			if (ObjectUtil.isNull(form)) {
				return Result.error("传递数据不能为空！！！");
			} else {
				if (StrUtil.isBlank(form.getUsername())) {
					return Result.error("username is required");
				}
			}
			UserExtendedInformation information = service.findUserExtendedInformationByUserName(form.getUsername());
			if (ObjectUtil.isNull(information)) {
				return Result.error("user password information is missing");
			}
			// 将账号密码转换成为 token
			String input = StrUtil.format("{}:{}", information.getUsername(), information.getPassword());
			String encoding = new BASE64Encoder().encode(input.getBytes());
			return Result.ok(encoding);
		} catch (Exception e) {
			logger.error("Unexpected error", e);
			return Result.error(e);
		}
	}
}
