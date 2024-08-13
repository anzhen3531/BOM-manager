package ext.ziang.part.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import cn.hutool.core.util.StrUtil;
import ext.ziang.common.result.Result;
import ext.ziang.part.service.PartRestService;
import ext.ziang.part.service.PartRestServiceImpl;
import io.swagger.annotations.Api;

/**
 * 部件Rest相关接口 外部接口
 * 内部接口采用Spring接口
 * 外部接口需要发布Swagger
 *
 * @author anzhen
 * @date 2024/05/29
 */
@Api("部件Rest接口")
@Path("/part")
public class PartRestController {
	/**
	 * service 接口
	 */
	private PartRestService service = new PartRestServiceImpl();

	@GET
	@Path("/info")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_HTML, MediaType.MULTIPART_FORM_DATA })
	@Produces(MediaType.APPLICATION_JSON)
	public Result findPartInfoByOid(@QueryParam("oid") String oid) {
		if (StrUtil.isBlank(oid)) {
			return Result.fail();
		}
		return service.findPartInfoByOid(oid);
	}
}
