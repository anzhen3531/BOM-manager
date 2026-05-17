package ext.ziang.epm.controller;


import com.alibaba.fastjson.JSONObject;
import ext.ziang.epm.bean.CreateEpmBean;
import ext.ziang.epm.helper.EpmCommonHelper;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
@RequestMapping("/epm")
public class EPMCustomController {
    private static final Logger logger = LoggerFactory.getLogger(EPMCustomController.class);

    /**
     * 暂存编码数据
     *
     * @param request  请求
     * @param response 响应
     * @throws IOException ioexception
     */
    @RequestMapping(value = "/saveEPM", method = {RequestMethod.POST})
    public void saveItems(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject result = new JSONObject();

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
            System.out.println("epmBean = " + epmBean);
            epmBean.setInputStream(inputStream);
            EpmCommonHelper.createEPM(epmBean);
            result.put("code", 200);
            result.put("success", true);
            result.put("data", "");
        } catch (Exception e) {
            logger.error("保存EPM数据失败！", e);
            result.put("code", 500);
            result.put("msg", "错误：" + e.getLocalizedMessage());
            result.put("success", false);
            result.put("data", "");
        }
        // 获取formData
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        // 将JSON对象输出到响应
        response.getWriter().write(result.toString());
        response.getWriter().flush();
    }


    /**
     * 暂存编码数据
     *
     * @param request  请求
     * @param response 响应
     * @throws IOException ioexception
     */
    @RequestMapping(value = "/saveEPMLink", method = {RequestMethod.POST})
    public void saveLink(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject result = new JSONObject();
        // 构建俩个link即可
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
            System.out.println("epmBean = " + epmBean);
            epmBean.setInputStream(inputStream);
            EpmCommonHelper.createEPM(epmBean);
            result.put("code", 200);
            result.put("success", true);
            result.put("data", "");
        } catch (Exception e) {
            logger.error("保存EPM数据失败！", e);
            result.put("code", 500);
            result.put("msg", "错误：" + e.getLocalizedMessage());
            result.put("success", false);
            result.put("data", "");
        }
        // 获取formData
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        // 将JSON对象输出到响应
        response.getWriter().write(result.toString());
        response.getWriter().flush();
    }

}
