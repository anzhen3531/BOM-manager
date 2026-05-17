package ext.ziang.epm.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;

import ext.ziang.common.result.Result;
import ext.ziang.epm.bean.CreateEpmBean;
import ext.ziang.epm.helper.EpmCommonHelper;

@Controller
@RequestMapping("/epm")
public class EPMCustomController {
    private static final Logger logger = LoggerFactory.getLogger(EPMCustomController.class);

    @RequestMapping(value = "/saveEPM", method = {RequestMethod.POST})
    public void saveItems(HttpServletRequest request, HttpServletResponse response) throws IOException {
        writeResult(response, createEpm(request));
    }

    @RequestMapping(value = "/saveEPMLink", method = {RequestMethod.POST})
    public void saveLink(HttpServletRequest request, HttpServletResponse response) throws IOException {
        writeResult(response, createEpm(request));
    }

    private Result createEpm(HttpServletRequest request) {
        DiskFileItemFactory fac = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(fac);
        try {
            List<FileItem> list = upload.parseRequest(request);
            InputStream inputStream = null;
            JSONObject jsonObject = new JSONObject();
            for (FileItem fileItem : list) {
                if (fileItem.isFormField()) {
                    jsonObject.put(fileItem.getFieldName(), fileItem.getString("utf-8"));
                } else {
                    inputStream = fileItem.getInputStream();
                }
            }
            CreateEpmBean epmBean = JSONObject.parseObject(jsonObject.toString(), CreateEpmBean.class);
            logger.debug("epmBean={}", epmBean);
            epmBean.setInputStream(inputStream);
            EpmCommonHelper.createEPM(epmBean);
            return Result.ok();
        } catch (Exception e) {
            logger.error("Save EPM data failed", e);
            return Result.error(e);
        }
    }

    private void writeResult(HttpServletResponse response, Result result) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(JSONObject.toJSONString(result));
        response.getWriter().flush();
    }
}
