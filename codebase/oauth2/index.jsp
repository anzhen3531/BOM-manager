<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.Base64" %>
<%
    String auth = request.getParameter("token");
    System.out.println("auth = " + auth);
    if (auth.contains("Basic ")) {
        auth = auth.replace("Basic ", "");
    }
    String credentials = new String(Base64.getDecoder().decode(auth));
    String[] split = credentials.split(":", 2);
    String userName = split[0];
    String password = split[1];
    System.out.println("password = " + password);
    System.out.println("userName = " + userName);
%>

<script>
    var chromeVersion = '';
    let arr = navigator.userAgent.split(' ');
    for (var i = 0; i < arr.length; i++) {
        if (/chrome/i.test(arr[i])) {
            chromeVersion = arr[i];
        }
    }

    chromeVersion = Number(chromeVersion.split('/')[1].split('.')[0]);
    if (chromeVersion < 49) {
        alert("您使用的谷歌浏览器版本过低，请升级到49及其以上版本!");
        window.close();
    }

    function login(url, fullName, password) {
        if (password == null || password === 'null' || password === "") {
            alert("您的账号在PDM系统未注册或已锁定，请联系系统管理员!");
            return false;
        }
        baseRedirect(url, fullName, password);
    }

    function baseRedirect(url, userName, password) {
        if (userName === "" || password === "") {
            alert("用户名或密码为空!");
            return false;
        }
        var client;
        try {
            client = new XMLHttpRequest();
        } catch (trymicrosoft) {
            try {
                client = new ActiveXObject("msxm12.XMLHTTP");
            } catch (othermicrosoft) {
                try {
                    client = new ActiveXObject("Microsoft.XMLHTTP");
                } catch (failed) {
                    //request = false;
                }
            }
        }
        client.onreadystatechange = function () {
            if (client.readyState === 4 && client.status === 200) {
                window.open(url, '_self');
            } else if (client.readyState === 4 && client.status !== 200) {
                alert("用户名或密码错误!");
                window.close();
            }
        }
        client.open("post", url, false, userName, password);
        client.setRequestHeader("IF-Modifed-SUNCE", "0");
        client.send();
    }

    let fullName = "<%=userName%>";
    let password = "<%=password%>";

    window.onload = function () {
        this.login("/Windchill/netmarkets/jsp/ext/ziang/oauth2/toMainPage.jsp", fullName, password);
    }
</script>

</html>
