package ext.ziang.report.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import ext.ziang.common.result.Result;
import ext.ziang.report.helper.ReportFormConfigHelper;
import ext.ziang.report.model.ReportFormConfig;
import io.swagger.annotations.Api;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Api(value = "报表相关接口")
@Path("/report")
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML, MediaType.MULTIPART_FORM_DATA})
@Produces(MediaType.APPLICATION_JSON)
public class ReportFormController {

    /**
     * 执行SQL
     *
     * @param id
     * @return 创建成功
     * @throws Exception 例外
     */
    @GET
    @Path("/form/{id}")
    public Result execScript(@PathParam("id") Long id) throws Exception {
        // 通过id查询相关的信息
        ReportFormConfig config = ReportFormConfigHelper.findConfigById(id);
        if (Objects.isNull(config)) {
            return Result.fail("查找当前配置失败");
        }
        List<Map<String, Object>> maps = ReportFormConfigHelper.execSQL(config.getContent());
        return Result.ok(maps);
    }
}
