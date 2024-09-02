<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        form {
            background-color: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0px 0px 10px 0px rgba(0, 0, 0, 0.1);
        }

        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }

        input[type="submit"] {
            width: 100%;
            padding: 10px;
            margin-top: 10px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        input[type="submit"]:hover {
            background-color: #0056b3;
        }

        a {
            margin-top: 10px;

        }

        svg {
            margin-top: 10px;
        }
    </style>
</head>
<body>
<%----%>
<form id="login-form">
    <h2>Login Windchill</h2>
    <input type="text" id="username" name="username" placeholder="Username" required>
    <input type="password" id="password" name="password" placeholder="Password" required>
    <input type="submit" value="Login">
    <%--    使用github登录--%>
    <a href="https://github.com/login/oauth/authorize?client_id=6b4ecccee521e3c0ada6&response_type=code&redirect_uri=http://plm.ziang.com/Windchill/app">
        <svg height="32" aria-hidden="true" viewBox="0 0 16 16" version="1.1" width="32" data-view-component="true"
             class="octicon octicon-mark-github v-align-middle color-fg-default">
            <path d="M8 0c4.42 0 8 3.58 8 8a8.013 8.013 0 0 1-5.45 7.59c-.4.08-.55-.17-.55-.38 0-.27.01-1.13.01-2.2 0-.75-.25-1.23-.54-1.48 1.78-.2 3.65-.88 3.65-3.95 0-.88-.31-1.59-.82-2.15.08-.2.36-1.02-.08-2.12 0 0-.67-.22-2.2.82-.64-.18-1.32-.27-2-.27-.68 0-1.36.09-2 .27-1.53-1.03-2.2-.82-2.2-.82-.44 1.1-.16 1.92-.08 2.12-.51.56-.82 1.28-.82 2.15 0 3.06 1.86 3.75 3.64 3.95-.23.2-.44.55-.51 1.07-.46.21-1.61.55-2.33-.66-.15-.24-.6-.83-1.23-.82-.67.01-.27.38.01.53.34.19.73.9.82 1.13.16.45.68 1.31 2.69.94 0 .67.01 1.3.01 1.49 0 .21-.15.45-.55.38A7.995 7.995 0 0 1 0 8c0-4.42 3.58-8 8-8Z"></path>
        </svg>
    </a>
</form>
</body>
</html>


<script>
    /**
     * 监听提交事件
     */
    document.getElementById("login-form").addEventListener("submit", function (event) {
        event.preventDefault();
        let username = document.getElementById("username").value;
        let password = document.getElementById("password").value;
        let url = 'http://plm.ziang.com/Windchill/app/?MODE=LOGIN';
        let redirect = 'http://plm.ziang.com/Windchill/app';
        let data = {
            username: username,
            password: password
        };
        const headers = {
            'Content-Type': 'application/json'
        };
        // 示例用法
        var http = new HttpRequest();
        http.sendRequest('POST', url, data, headers,
            function (responseText) {
                console.log('Success:', responseText);
                window.location.href = redirect;
            },
            function (status) {
                console.error('Error:', status);
            }
        );
    });


    function HttpRequest() {
        // 创建 XMLHttpRequest 对象，兼容 IE 6 及以下使用 ActiveXObject
        this.xhr = (function () {
            if (window.XMLHttpRequest) {
                return new XMLHttpRequest();
            } else {
                try {
                    return new ActiveXObject("Microsoft.XMLHTTP");
                } catch (e) {
                    throw new Error("XMLHttpRequest is not supported by this browser.");
                }
            }
        })();
    }

    HttpRequest.prototype.sendRequest = function (method, url, data, headers, onSuccess, onError) {
        var xhr = this.xhr;
        xhr.open(method, url, true);
        // 设置请求头，兼容 IE 的做法
        if (headers) {
            for (var key in headers) {
                if (headers.hasOwnProperty(key)) {
                    xhr.setRequestHeader(key, headers[key]);
                }
            }
        }
        // IE 8 和 9 可能不支持 `xhr.responseType`，使用 `xhr.responseText`
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4) { // 请求完成
                if (xhr.status >= 200 && xhr.status < 300) {
                    if (onSuccess) {
                        onSuccess(xhr.responseText, xhr);
                    }
                } else {
                    if (onError) {
                        onError(xhr.status, xhr);
                    }
                }
            }
        };
        // 处理请求超时
        xhr.ontimeout = function () {
            if (onError) {
                onError('Request timed out', xhr);
            }
        };
        // 处理网络错误
        xhr.onerror = function () {
            if (onError) {
                onError('Network Error', xhr);
            }
        };
        // 发送请求，兼容性处理
        try {
            if (method === 'GET' || !data) {
                xhr.send();
            } else {
                xhr.send(JSON.stringify(data));
            }
        } catch (e) {
            if (onError) {
                onError(e.message, xhr);
            }
        }
    };


</script>