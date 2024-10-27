<%@ page import="ext.ziang.part.histrory.history.ImportHistoryWTPartService" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%--<%--%>
<%--    // 读取excel--%>
<%--    ImportHistoryWTPartService.createPartByExcelAllSheet(--%>
<%--            "C:\\ptc\\Windchill_11.0\\Windchill\\src\\ext\\ziang\\histrory\\history\\历史物料属性导入1.xlsx");--%>
<%--%>--%>

<html>
<body>
<script>
    top.clearActionFormData();
    top.PTC.getMainWindow().PTC.performance.stopComponentTimer('WizardSubmit');
    top.handleSubmitResult(1, 4, ' ', '\x20', {"extraData": {}, "actionName": "derivedPart"})
</script>
</body>
</html>