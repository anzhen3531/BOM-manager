package ext.ziang.report.rest;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ext.ziang.common.result.Result;
import ext.ziang.report.helper.ReportFormConfigHelper;
import ext.ziang.report.model.ReportFormConfig;
import io.swagger.annotations.Api;

@Api(value = "report api")
@Path("/report")
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML, MediaType.MULTIPART_FORM_DATA})
@Produces(MediaType.APPLICATION_JSON)
public class ReportFormController {
    private static final Logger logger = LoggerFactory.getLogger(ReportFormController.class);

    @GET
    @Path("/form/{id}")
    public Result execScript(@PathParam("id") Long id) {
        try {
            ReportFormConfig config = ReportFormConfigHelper.findConfigById(id);
            if (Objects.isNull(config)) {
                return Result.fail("report config not found");
            }
            List<Map<String, Object>> maps = ReportFormConfigHelper.execSQL(config.getContent());
            return Result.ok(maps);
        } catch (Exception e) {
            logger.error("Execute report form failed, id={}", id, e);
            return Result.error(e);
        }
    }
}
