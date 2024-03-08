<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="ext.ziang.part.datautility.ExtSearchPartAttrDataUtility" %>
<%@ page import="java.util.List" %>

<%
    String columnId = request.getParameter("columnId");
    // TODO 获取文件进行处理
    List<String> materialFormContent = ExtSearchPartAttrDataUtility.getMaterialFormContent("C:\\ptc\\Windchill_11.0\\Windchill\\codebase\\netmarkets\\jsp\\ext\\soarwhale\\part\\materialAttr\\是为科技物料的材质清单.xlsx");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>选择填写的材质属性</title>
    <style>
        .search-container {
            width: 300px;
            position: relative;
        }

        .search-input {
            width: calc(100% - 30px);
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px 0 0 4px;
            font-size: 16px;
            outline: none;
        }


        .search-submit {
            width: 100%;
            padding: 8px 16px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 0 4px 4px 0;
            font-size: 16px;
            cursor: pointer;
            outline: none;
            position: absolute;
            top: 60px;
            right: 0;
            display: none;
        }

        .search-submit:hover {
            background-color: #45a049;
        }

        .search-dropdown {
            width: calc(100% - 2px);
            max-height: 200px;
            overflow-y: auto;
            position: absolute;
            top: 100%;
            left: 0;
            background-color: #fff;
            border: 1px solid #ccc;
            border-top: none;
            border-radius: 0 0 4px 4px;
            z-index: 1000;
            display: none;
        }

        .search-dropdown-item {
            padding: 8px;
            cursor: pointer;
        }

        .search-dropdown-item:hover {
            background-color: #f0f0f0;
        }
    </style>
</head>
<body>
<h1>可搜索的材质属性</h1>
<div class="search-container">
    <input type="text" id="searchTerm" class="search-input" placeholder="请输入搜索词" list="searchList">
    <datalist id="searchList">
        <% for (String value : materialFormContent) {%>
        <option value="<%=value%>">
                <%}%>
    </datalist>
    <button type="submit" class="search-submit" onclick="submitForm()">提交</button>
</div>

</body>
</html>
<script>
    let searchTermInput = document.getElementById("searchTerm");
    let submitButton = document.querySelector(".search-submit");

    searchTermInput.addEventListener("input", function () {
        let searchTerm = searchTermInput.value.trim();
        if (searchTerm === "") {
            submitButton.style.display = "none";
        } else {
            submitButton.style.display = "block";
        }
    });

    function submitForm() {
        let searchTerm = searchTermInput.value.trim();
        // 获取填写的值
        console.log("Form submitted! Search term: " + searchTerm);
        // 设置外围数据即可
        opener.document.getElementById("<%=columnId%>").value = searchTerm;
        window.close();
    }
</script>
